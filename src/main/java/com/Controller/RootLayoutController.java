package com.Controller;

import com.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class RootLayoutController {

    private Main main;

    public void clickAbout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("The Epidemic Modeling");
        alert.setHeaderText("About program");
        alert.setContentText("Author: Ilya Rodionov\nGroup # 455\nSaint-Petersburg State Institute of Technology\n2018-2019");
        alert.showAndWait();

    }

        public void initialize() {

    }

    public void setMainApp(Main main) {
        this.main = main;
    }
}
