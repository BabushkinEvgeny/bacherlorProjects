module com.example.otdel_kadrov {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.otdel_kadrov to javafx.fxml;
    opens com.example.otdel_kadrov.entity to javafx.base;
    exports com.example.otdel_kadrov;

}