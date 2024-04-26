module com.example.django2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.apache.poi.ooxml;
    requires org.apache.pdfbox;
    requires twilio;


    opens com.example.django2 to javafx.fxml;
    exports com.example.django2;
    exports utils;
    opens utils to javafx.fxml;
}