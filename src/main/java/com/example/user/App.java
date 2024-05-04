package com.example.user;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class App extends Application {
    @Override
    public  void start(Stage primaryStage) throws  Exception{
        Parent root = FXMLLoader.load(getClass().getResource("test2.fxml"));
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 1315,900));
        primaryStage.show();
        }

        public static void main(String[] args) {
            launch();
        }
    }


