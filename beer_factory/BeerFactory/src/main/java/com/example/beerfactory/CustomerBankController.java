package com.example.beerfactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class CustomerBankController {

    @FXML
    private TextField AMOUNT;

    @FXML
    private Button PAY;

    @FXML
    private Button BACK;

    @FXML
    void initialize() {
        BACK.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("customer_data.fxml", BACK);
        });

        PAY.setOnAction(event ->{
            Handler handler = new Handler();
            if(handler.isBad(AMOUNT.getText()) || Integer.valueOf(AMOUNT.getText()) < 0)
            {
                AMOUNT.clear();
                AMOUNT.setPromptText("Ошибка!");
            }
            else
            {
                Integer amount = Integer.valueOf(AMOUNT.getText());
                DataBaseHandler dataBaseHandler = new DataBaseHandler();
                try {
                    dataBaseHandler.setCustomerBank(Data.CUSTOMER_ID, amount);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}