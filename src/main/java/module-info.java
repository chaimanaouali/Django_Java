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
    requires org.controlsfx.controls;
    requires jcaptcha.all;
    requires javafx.web;
    requires com.google.zxing;
    requires javafx.swing;
    requires org.apache.pdfbox;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;



    requires jdk.javadoc;

    requires itextpdf;


    opens com.example.user to javafx.fxml;
    exports com.example.user;
}