package com.Controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;

class Encrypt {
    //Получение даты и времени для имени файорв
    String out = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date());

    //сохранение байтового массива в файл
    public static void saveToFile(byte[] info, String filename) {
        try {
            FileOutputStream fos = new FileOutputStream
                    (filename);
            fos.write(info);
            fos.close();
        } catch (Exception e) {
            System.err.println("Caught exception" +
                    e.toString());
        }
    }

    //Об алгоритмах: https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#KeyPairGenerator
    public int Process() {
        try { //Генерация пары ключей по алгоритму DSA
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            keyGen.initialize(1024, random);
            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();
            //Запись открытого ключа в файл
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pub.getEncoded());
            FileOutputStream fos = new FileOutputStream(out + ".pubkey");
            fos.write(x509EncodedKeySpec.getEncoded());
            fos.close();
            //Запись закрытого  ключа в файл
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(priv.getEncoded());
            fos = new FileOutputStream(out + ".prvkey");
            fos.write(pkcs8EncodedKeySpec.getEncoded());
            fos.close();
        } catch (Exception e) {
            System.err.println("Caught exception " +
                    e.toString());
        }
        return 1;
    }

    public int Sign(String privateKeyPath, String filePath) {
        try {//Чтение приватного ключа из файла
            File filePrivateKey = new File(privateKeyPath);
            FileInputStream fis = new FileInputStream(privateKeyPath);
            byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
            fis.read(encodedPrivateKey);
            fis.close();
            KeyFactory keyFactory = KeyFactory.getInstance("DSA");
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
            //Создание подписи
            Signature dsa = Signature.getInstance("SHA1withDSA");
            dsa.initSign(privateKey);
            //Чтение файла
            FileInputStream fic = new FileInputStream(filePath);
            BufferedInputStream bufin = new BufferedInputStream(fic);
            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                dsa.update(buffer, 0, len);
            }
            bufin.close();
            //Подпис файла
            byte[] realSig = dsa.sign();
            //Сохранение подписи в файл
            saveToFile(realSig, out + ".signature");

        } catch (Exception e) {
            System.err.println("Caught exception " +
                    e.toString());
        }
        return 1;
    }
}
