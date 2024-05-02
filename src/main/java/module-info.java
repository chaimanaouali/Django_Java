    module com.example.finalpidev {
        requires javafx.controls;
        requires javafx.fxml;
        requires java.sql;
        opens models to javafx.base;


        opens com.example.finalpidev to javafx.fxml;
            exports com.example.finalpidev;
    }