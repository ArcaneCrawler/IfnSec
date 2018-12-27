package com.Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class AllModelController {

    public Button choseButton;
    public Button encryptButton;
    public Button signatureButton;
    public String filePath;
    public TextField fileNameField;

    //Вызов выбора файла
    public void choseButtonClick() {
        try {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);
            filePath = selectedFile.getPath();
            fileNameField.setText(filePath);


        } catch (NullPointerException exceptionObject) {

        }
    }

    //Вызов генерации подписи
    public void encryptButtonClick() {
        try {
            Encrypt encrypt = new Encrypt();
            //Проверка наличия пути
            if (filePath == null) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Warning");
                error.setHeaderText("Warning");
                error.setContentText("Path file is missing! \n");
                error.showAndWait();
                return;
            }
            int check = encrypt.Process(filePath);
            if (check == 1) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Success");
                alert.setContentText("Encryption completed successfully!");
                alert.showAndWait();

            }

        } catch (java.lang.NumberFormatException exceptionObject) {

        }
    }

    //Вызов проверки подписи
    public void signatureButtonClick() {
        try {
            //Проверка наличия пути
            if (filePath == null) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Warning");
                error.setHeaderText("Warning");
                error.setContentText("Path file is missing! \n");
                error.showAndWait();
                return;
            }
            SignatureCheck signatureCheck = new SignatureCheck();
            signatureCheck.SigCheck(filePath);


        } catch (java.lang.NumberFormatException exceptionObject) {

        }
    }


    public void initialize() {

    }

    public void setMainApp() {
    }


}
