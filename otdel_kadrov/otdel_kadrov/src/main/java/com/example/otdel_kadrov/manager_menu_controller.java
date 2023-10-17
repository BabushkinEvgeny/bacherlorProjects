package com.example.otdel_kadrov;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class manager_menu_controller {

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
    void initialize()
    {
        EXIT.setOnMouseClicked(event ->{
            Handler handler = new Handler();
            handler.openNewScene("login.fxml", EXIT);
        });
        DATA.setOnMouseClicked(event ->
        {
            Handler handler = new Handler();
            handler.openNewScene("employee_data.fxml",DATA);
        });
        LIST.setOnMouseClicked(event ->
        {
            Handler handler = new Handler();
            handler.openNewScene("manager_applications_list.fxml",LIST);
        });

        EMPLOYEES_LIST.setOnMouseClicked(event ->
        {
            Handler handler = new Handler();
            handler.openNewScene("manager_workers_list.fxml",EMPLOYEES_LIST);
        });
        REQUIREMENTS.setOnMouseClicked(event ->{
            Handler handler = new Handler();
            handler.openNewScene("manager_positions.fxml", REQUIREMENTS);
        });
    }
}
