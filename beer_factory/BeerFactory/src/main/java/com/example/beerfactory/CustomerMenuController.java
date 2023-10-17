package com.example.beerfactory;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.net.ssl.HandshakeCompletedEvent;
import java.sql.SQLException;

public class CustomerMenuController {

    @FXML
    private Button CANDIDATE_APPS;

    @FXML
    private Button CANDIDATE_DATA;

    @FXML
    private Button CANDIDATE_EXIT;

    @FXML
    private Button CANDIDATE_VACANCIES;

    @FXML
    private Button CANDIDATE_WRITE;

    @FXML
    void initialize()
    {
        CANDIDATE_EXIT.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("login.fxml", CANDIDATE_EXIT);
        });
        CANDIDATE_DATA.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("customer_data.fxml", CANDIDATE_DATA);
        });

        CANDIDATE_WRITE.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("customer_catalog.fxml", CANDIDATE_WRITE);
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                if(dataBaseHandler.getCurrentOrderId() == 0) {
                    dataBaseHandler.createOrder(Data.CUSTOMER_ID);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        CANDIDATE_APPS.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("customer_basket.fxml", CANDIDATE_APPS);
        });
        CANDIDATE_VACANCIES.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("customer_orders.fxml", CANDIDATE_VACANCIES);
        });
    }

}
