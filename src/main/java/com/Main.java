package com;

import com.Controller.AllModelController;
import com.Controller.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage = null;
    private BorderPane rootPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override

    //Инициализация окна программы
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Signature checker");
        this.primaryStage.setResizable(false);
        this.primaryStage.sizeToScene();
        initRootLayout();
        showAllModel();

    }

    //Создание основного экрана
    private void initRootLayout() {


        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/RootLayout.fxml"));
            rootPane = loader.load();
            Scene scene = new Scene(rootPane);
            primaryStage.setScene(scene);
            primaryStage.show();
            RootLayoutController rootLayoutController = loader.getController();
            rootLayoutController.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Создание интерфейса пользвателя
    private void showAllModel() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/AllModel.fxml"));
            AnchorPane mainPane = loader.load();
            rootPane.setCenter(mainPane);

            //EntityManager em = Persistence.createEntityManagerFactory("EPIDEMIC").createEntityManager();

            AllModelController allModelController = loader.getController();
            allModelController.setMainApp();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
