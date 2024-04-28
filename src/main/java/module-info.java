module com.example.user {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.mail;
    requires layout;
    requires kernel;
    requires io;
    requires twilio;


    opens com.example.user to javafx.fxml;
    exports com.example.user;
}