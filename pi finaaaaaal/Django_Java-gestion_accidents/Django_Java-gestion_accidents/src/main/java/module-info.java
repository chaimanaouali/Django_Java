    module com.example.finalpidev {
        requires javafx.controls;
        requires javafx.fxml;
        requires java.sql;
        requires java.mail;
        requires layout;
        requires kernel;
        requires javafx.graphics;

        requires com.google.zxing.javase;
        requires com.google.zxing;

        opens models to javafx.base;
        opens com.example.finalpidev to javafx.graphics, javafx.fxml;

        exports com.example.finalpidev;


    }