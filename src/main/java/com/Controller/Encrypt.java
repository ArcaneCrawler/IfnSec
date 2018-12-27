package com.Controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;

class Encrypt {
    //сохранение байтового массива в файл
    public static void saveToFile(byte[] info,
                                  String filename) {
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

    public static int Process(String filename) {
        try {
            System.out.println("Start encrypt " + filename);
            /* Генерация ключей */
            KeyPairGenerator keyGen =
                    KeyPairGenerator.getInstance("DSA");
            SecureRandom random =
                    SecureRandom.getInstance("SHA1PRNG");
            keyGen.initialize(1024, random);
            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();
            /* Создание объекта класса Signature */
            Signature dsa =
                    Signature.getInstance("SHA1withDSA");
            /* Инициализация частным ключом */
            dsa.initSign(priv);
            /* Чтение данных из файла */
            FileInputStream fis = new FileInputStream(filename);
            BufferedInputStream bufin =
                    new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                dsa.update(buffer, 0, len);
            }
            bufin.close();
            /* Генерация подписи */
            byte[] realSig = dsa.sign();
            /* Сохранение подписи в файл “signature” */
            saveToFile(realSig, "src/resources/signature");
            /* Сохранение открытого ключа в файл “pubkey” */
            byte[] key = pub.getEncoded();
            saveToFile(key, "src/resources/pubkey");
            System.out.println("Success");
        } catch (Exception e) {
            System.err.println("Caught exception " +
                    e.toString());
        }
        return 1;
    }
}
