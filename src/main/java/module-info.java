module com.example.assurance {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.assurance to javafx.fxml;
    exports com.example.assurance;
}