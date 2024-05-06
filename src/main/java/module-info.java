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



    requires com.google.protobuf;
    requires TrayNotification;
    requires java.persistence;

    requires javafx.graphics;

    requires com.google.zxing.javase;




    requires jdk.javadoc;

    requires itextpdf;

    opens models to javafx.base;

    opens com.example.user to javafx.fxml;
    exports com.example.user;
}