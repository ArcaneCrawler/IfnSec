package com.Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class AllModelController {

    public Button fileButton;
    public Button pbkeyButton;
    public Button prkeyButton;
    public Button encryptButton;
    public Button signatureButton;
    public Button keyButton;
    public String filePath;
    public String pbkeyPath;
    public String prkeyPath;
    public String signaturePath;
    public TextField fileNameField;
    public TextField prkeyNameField;
    public TextField pbkkeyNameField;

    //Вызов выбора файла
    public void fileChooser() {
        try {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);
            filePath = selectedFile.getPath();
            fileNameField.setText(filePath);
        } catch (NullPointerException exceptionObject) {
        }
    }

    //Вызов выбора файла c закрытым ключом
    public void prkeyChooser() {
        try {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);
            prkeyPath = selectedFile.getPath();
            prkeyNameField.setText(prkeyPath);
        } catch (NullPointerException exceptionObject) {

        }
    }

    //Вызов выбора файла c открытым ключом
    public void pubkeyChooser() {
        try {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);
            pbkeyPath = selectedFile.getPath();
            pbkkeyNameField.setText(pbkeyPath);
        } catch (NullPointerException exceptionObject) {
        }
    }

    //Вызов генерации пары ключей
    public void keyButtonEvent() {
        try {
            Encrypt encrypt = new Encrypt();
            //encrypt.Process();
            if ( encrypt.Process() == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Success");
                alert.setContentText("Encryption completed successfully!");
                alert.showAndWait();}
        } catch (NullPointerException exceptionObject) {
        }
    }

    //Вызов генерации подписи
    public void encryptButtonClick() {
        try {
            Encrypt encrypt = new Encrypt();
            if (checkType("prvkey", prkeyPath) == false) return;
            if (checkFile() == false) return;
            int check = encrypt.Sign(prkeyPath, filePath);
            if (check == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Success");
                alert.setContentText("Encryption completed successfully!");
                alert.showAndWait();
            }
        } catch (java.lang.NullPointerException exceptionObject) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Warning");
            error.setHeaderText("Warning");
            error.setContentText("Missing key file\n");
            error.showAndWait();
        }
    }

    //Вызов проверки подписи
    public void signatureButtonClick() {
        try {
            if (checkFile() == false) return;
            if (checkType("pubkey", pbkeyPath) == false) return;
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);
            signaturePath = selectedFile.getPath();

            SignatureCheck signatureCheck = new SignatureCheck();
            signatureCheck.SigCheck(filePath, signaturePath, pbkeyPath);
        } catch (java.lang.NullPointerException exceptionObject) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Warning");
            error.setHeaderText("Warning");
            error.setContentText("Missing key file\n");
            error.showAndWait();
        }
    }

    //Проверка налчия файла
    Boolean checkFile() {
        if (filePath == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Warning");
            error.setHeaderText("Warning");
            error.setContentText("Path file is missing! \n");
            error.showAndWait();
            return false;
        }
        return true;
    }

    //Проверка типа данных ключей
    Boolean checkType(String excel, String path) {
        String extension = path.substring(path.lastIndexOf(".") + 1, path.length());
        if (!extension.equals(excel)) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Warning");
            error.setHeaderText("Warning");
            error.setContentText("Chosen wrong " + excel + "\n");
            error.showAndWait();
            return false;
        }
        return true;
    }

    public void initialize() {

    }

    public void setMainApp() {
    }
}
