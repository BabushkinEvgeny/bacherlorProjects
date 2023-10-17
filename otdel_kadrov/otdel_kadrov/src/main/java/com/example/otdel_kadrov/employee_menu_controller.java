package com.example.otdel_kadrov;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.sql.ResultSet;
import java.sql.SQLException;

public class employee_menu_controller {

    @FXML
    private Button APP_MAKE;

    @FXML
    private Button DATA;

    @FXML
    private Button EXIT;

    @FXML
    private Button LIST;
    @FXML
    private Button EXAMINE;
    @FXML
    void initialize(ActionEvent event) throws SQLException, ClassNotFoundException {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        ResultSet resultSet = dataBaseHandler.getIDRole(Integer.valueOf(Data.EMPLOYEE_ID));
        resultSet.next();
        Integer role = resultSet.getInt("id_role");
        Data.EMPLOYEE_ROLE = role.toString();
        if(role != 2)
        {
            EXAMINE.setDisable(true);
        }
    }

    @FXML
    void initialize() {
        EXIT.setOnMouseClicked(event ->{
            Handler handler = new Handler();
            handler.openNewScene("login.fxml", EXIT);
        });
        DATA.setOnMouseClicked(event ->
        {
            Handler handler = new Handler();
            handler.openNewScene("employee_data.fxml",DATA);
        });
        LIST.setOnMouseClicked(event ->{
            Handler handler = new Handler();
            handler.openNewScene("employee_list.fxml", LIST);
        });
        APP_MAKE.setOnMouseClicked(event->{
            Handler handler = new Handler();
            handler.openNewScene("employee_app_make.fxml", APP_MAKE);
        });
        EXAMINE.setOnMouseClicked(event ->{
            if(Integer.valueOf(Data.EMPLOYEE_ROLE) == 2)
            {
                Handler handler = new Handler();
                handler.openNewScene("employee_examine.fxml", EXAMINE);
            }
        });
    }

}
