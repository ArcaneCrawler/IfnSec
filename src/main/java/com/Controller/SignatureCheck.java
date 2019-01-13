package com.Controller;

import javafx.scene.control.Alert;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

class SignatureCheck {
    //Чтение из файла в байтовый массив
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

    //Проверка подписи
    public static void SigCheck(String filename, String signaturePath, String pbkeyPath) {
        try {
            //Получение публичного ключа из файла
            byte[] encKey = readFromFile(pbkeyPath);
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
            KeyFactory keyFactory = KeyFactory.getInstance("DSA");
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
            //Получение подписи из файла
            byte[] sigToVerify = readFromFile(signaturePath);
            Signature sig = Signature.getInstance("SHA1withDSA");
            sig.initVerify(pubKey);
            //Чтение данных из файла
            FileInputStream fis = new FileInputStream(filename);
            BufferedInputStream bufin = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                sig.update(buffer, 0, len);
            }
            bufin.close();
            //Верификация
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