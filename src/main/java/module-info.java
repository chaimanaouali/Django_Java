module com.example.javadjango {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.apache.poi.poi;
    requires org.apache.pdfbox;
    requires com.google.protobuf;
    requires com.google.zxing;
    requires TrayNotification;
    requires java.persistence;
    requires twilio;


    opens com.example.javadjango to javafx.fxml;
    exports com.example.javadjango;
    exports Models;

}