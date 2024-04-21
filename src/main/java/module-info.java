module com.example.assurance {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires com.google.zxing;
    requires javafx.swing;


    opens com.example.assurance to javafx.fxml;
    exports com.example.assurance;
}