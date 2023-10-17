package com.example.beerfactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeDataController implements Initializable {

    @FXML
    private Button CANDIDATE_DATA_EXIT;

    @FXML
    private Label LOGIN;

    @FXML
    private Label NAME;

    @FXML
    private Label SNILS;

    @FXML
    private Label SURNAME;

    @FXML
    void initialize() {
        CANDIDATE_DATA_EXIT.setOnAction(event -> {
            Handler handler = new Handler();
            handler.openNewScene("employee_menu.fxml", CANDIDATE_DATA_EXIT);
        });


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NAME.setText(Data.EMPLOYEE_NAME);
        SURNAME.setText(Data.EMPLOYEE_SURNAME);
        LOGIN.setText(Data.EMPLOYEE_LOGIN);
        SNILS.setText(Data.EMPLOYEE_SALARY);
    }
}
