package com.example.finalpidev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {
    @Override
    public  void start(Stage primaryStage) throws  Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AffichageConstat.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
     launch();

    }
}