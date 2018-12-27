package com.Controller;

import javafx.scene.control.Alert;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

class SignatureCheck {
    //чтение из файла в байтовый массив
    public static byte[] readFromFile(String fileName) {
        byte[] info;
        try {
            FileInputStream fis =
                    new FileInputStream(fileName);
            info = new byte[fis.available()];
            fis.read(info);
            fis.close();
        } catch (Exception e) {
            System.err.println("Caught exception" +
                    e.toString());
            info = new byte[0];
        }
        return (info);
    }

    public static void SigCheck(String filename) {
        try {
            /* Получение public key из файла “pubkey” */
            byte[] encKey = readFromFile("src/resources/pubkey");

            /* Создание спецификации ключа */
            X509EncodedKeySpec pubKeySpec =
                    new X509EncodedKeySpec(encKey);

            KeyFactory keyFactory = KeyFactory.getInstance
                    ("DSA");
            PublicKey pubKey = keyFactory.generatePublic
                    (pubKeySpec);
            /* Чтение подписи из файла “signature” */
            byte[] sigToVerify = readFromFile("src/resources/signature");
            /* Создание объекта класса Signature и инициализация с помощью открытого ключа    */
            Signature sig = Signature.getInstance
                    ("SHA1withDSA");
            sig.initVerify(pubKey);
            /* Чтение данных из файла “data” и вызов метода update() */
            FileInputStream datafis = new FileInputStream
                    (filename);
            BufferedInputStream bufin =
                    new BufferedInputStream(datafis);
            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                sig.update(buffer, 0, len);
            }
            bufin.close();
            /* Верификация */
            boolean verifies = sig.verify(sigToVerify);
            System.out.println("Signature verifies:" + verifies);
            if (verifies == true) {
                //Успешная верификация
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Success");
                alert.setContentText("Coincidence of signature\n Integrity is verified");
                alert.showAndWait();
            } else {
                //Неудачная верификация
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Fail");
                alert.setHeaderText("Fail");
                alert.setContentText("Mismatch of signature\n Integrity isn't verified");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.err.println("Caught exception" +
                    e.toString());
        }
    }
}