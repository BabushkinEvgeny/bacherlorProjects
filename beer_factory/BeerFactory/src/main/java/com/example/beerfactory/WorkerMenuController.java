package com.example.beerfactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WorkerMenuController {

    @FXML
    private Button APP_MAKE;

    @FXML
    private Button DATA;

    @FXML
    private Button EXAMINE;

    @FXML
    private Button EXIT;

    @FXML
    private Button LIST;

    @FXML
    void initialize() {
        EXIT.setOnAction(event -> {
            Handler handler = new Handler();
            handler.openNewScene("login.fxml", EXIT);
        });

        DATA.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("employee_data.fxml", DATA);
        });

        EXAMINE.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("employee_ingredients.fxml", EXAMINE);
        });

        APP_MAKE.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("employee_beer_storage.fxml", APP_MAKE);
        });

        LIST.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("employee_orders.fxml", LIST);
        });
    }

}
