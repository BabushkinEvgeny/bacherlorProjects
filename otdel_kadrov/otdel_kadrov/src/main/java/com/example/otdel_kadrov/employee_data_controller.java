package com.example.otdel_kadrov;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class employee_data_controller implements Initializable {

    @FXML
    private Button EMPLOYEE_DATA_EXIT;

    @FXML
    private Label LOGIN;

    @FXML
    private Label NAME;
    @FXML
    private Label POSITION;

    @FXML
    private Label SALARY;

    @FXML
    private Label SNILS;

    @FXML
    private Label STATUS;





    @FXML
    private Label SURNAME;

    @FXML
    void initialize() {
        EMPLOYEE_DATA_EXIT.setOnMouseClicked(event ->{
            Handler handler = new Handler();
            if(Data.EMPLOYEE_ROLE == "1") {
                handler.openNewScene("manager_menu.fxml", EMPLOYEE_DATA_EXIT);
            } else handler.openNewScene("employee_menu.fxml", EMPLOYEE_DATA_EXIT);
        });

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataBaseHandler dataBaseHandler1 = new DataBaseHandler();
        NAME.setText(Data.EMPLOYEE_NAME);
        SURNAME.setText(Data.EMPLOYEE_SURNAME);
        LOGIN.setText(Data.EMPLOYEE_LOGIN);
        System.out.println(Data.EMPLOYEE_SNILS+"ggg");
        SNILS.setText(Data.EMPLOYEE_SNILS);
        try {
            ResultSet setPos = null;
            setPos = dataBaseHandler1.getEmployeePosition(Data.EMPLOYEE_ID);
            setPos.next();
            String textPos = setPos.getString("name");
            POSITION.setText(textPos);


            ResultSet setStat = null;
            setStat = dataBaseHandler1.getEmployeeStatus(Data.EMPLOYEE_ID);
            setStat.next();
            String textStat = setStat.getString("status");
            STATUS.setText(textStat);
            //STATUS.setText(dataBaseHandler1.getEmployeeStatus(Data.EMPLOYEE_ID).getString("status"));
            SALARY.setText(dataBaseHandler1.getSalaryById(Data.EMPLOYEE_ID).toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}