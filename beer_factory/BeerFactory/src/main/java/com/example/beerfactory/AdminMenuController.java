package com.example.beerfactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminMenuController {

    @FXML
    private Button DATA;

    @FXML
    private Button EMPLOYEES_LIST;

    @FXML
    private Button EXIT;

    @FXML
    private Button LIST;

    @FXML
    private Button REQUIREMENTS;

    @FXML
    void initialize() {
        EMPLOYEES_LIST.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("manager_workers_list.fxml", EMPLOYEES_LIST);
        });

        LIST.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("manager_orders_list.fxml", LIST);
        });

        DATA.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("manager_data.fxml", DATA);
        });

        EXIT.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("login.fxml", EXIT);
        });
        REQUIREMENTS.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("manager_catalog.fxml", REQUIREMENTS);
        });
    }

}
