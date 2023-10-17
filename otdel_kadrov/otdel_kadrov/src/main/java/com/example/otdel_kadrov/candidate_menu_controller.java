package com.example.otdel_kadrov;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class candidate_menu_controller {

    @FXML
    private Button CANDIDATE_APPS;

    @FXML
    private Button CANDIDATE_DATA;

    @FXML
    private Button CANDIDATE_VACANCIES;

    @FXML
    private Button CANDIDATE_WRITE;
    @FXML
    private Button CANDIDATE_EXIT;
    @FXML
    void initialize()
    {
        CANDIDATE_DATA.setOnMouseClicked(event ->{
            System.out.println("Open scene!");
            openNewScene("candidate_data.fxml");
        });
        CANDIDATE_EXIT.setOnMouseClicked(event ->{
            openNewScene("login.fxml");
        });
        CANDIDATE_APPS.setOnMouseClicked(event ->{
            openNewScene("candidate_applications.fxml");
        });
        CANDIDATE_VACANCIES.setOnMouseClicked(event ->{
            openNewScene("candidate_vacancies.fxml");
        });

        CANDIDATE_WRITE.setOnMouseClicked(event ->{
            openNewScene("candidate_make_application.fxml");
        });
    }
    public void openNewScene(String window)
    {
        CANDIDATE_DATA.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

}