package com.example.beerfactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerDataController implements Initializable {

    @FXML
    private Button CANDIDATE_DATA_EXIT;

    @FXML
    private Label LOGIN;

    @FXML
    private Button MONEY;

    @FXML
    private Label NAME;

    @FXML
    private Label SNILS;

    @FXML
    private Label SURNAME;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NAME.setText(Data.CUSTOMER_NAME);
        SURNAME.setText(Data.CUSTOMER_SURNAME);
        LOGIN.setText(Data.CUSTOMER_LOGIN);
        //SNILS.setText(Data.CUSTOMER_BANK);
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        try {
            SNILS.setText( dataBaseHandler.getBank(Data.CUSTOMER_ID).toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        CANDIDATE_DATA_EXIT.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("customer_menu.fxml", CANDIDATE_DATA_EXIT);
        });
        MONEY.setOnAction(event ->{
            Handler handler = new Handler();

        });
        MONEY.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("customer_bank.fxml", MONEY);
        });
    }


}

