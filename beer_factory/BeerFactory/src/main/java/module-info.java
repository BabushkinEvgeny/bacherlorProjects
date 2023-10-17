module com.example.beerfactory {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.beerfactory to javafx.fxml;
    exports com.example.beerfactory;

    exports com.example.beerfactory.Entity;


}