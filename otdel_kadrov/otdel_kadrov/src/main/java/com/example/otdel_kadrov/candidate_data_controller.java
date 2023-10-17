package com.example.otdel_kadrov;

import com.example.otdel_kadrov.entity.Candidate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class candidate_data_controller implements Initializable {

    @FXML
    private Label LOGIN;

    @FXML
    private Label NAME;



    @FXML
    private Label SNILS;

    @FXML
    private Button CANDIDATE_DATA_EXIT;

    @FXML
    private Label SURNAME;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NAME.setText(Data.CANDIDATE_NAME);
        SURNAME.setText(Data.CANDIDATE_SURNAME);
        LOGIN.setText(Data.CANDIDATE_LOGIN);

        SNILS.setText(Data.CANDIDATE_SNILS);

    }

    @FXML
    void initialize()
    {
        Handler handler = new Handler();
        CANDIDATE_DATA_EXIT.setOnMouseClicked(event ->{
            handler.openNewScene("candidate_menu.fxml", CANDIDATE_DATA_EXIT);
        });
    }
}
