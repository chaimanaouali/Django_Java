module com.example.djangoassurancejava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.pdfbox;
    requires java.datatransfer;
    requires java.desktop;
    requires com.google.zxing;
    requires javafx.swing;
    requires twilio;
    requires java.mail;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    //requires mail;
    //requires mail;
    //requires mail;

    opens com.example.djangoassurancejava to javafx.fxml;
    exports com.example.djangoassurancejava;

}