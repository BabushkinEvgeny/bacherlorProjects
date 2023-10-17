package com.example.otdel_kadrov;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class employee_app_make_controller implements Initializable {

    @FXML
    private ChoiceBox<String> APP_TYPE;

    @FXML
    private Button BACK;

    @FXML
    private TextArea DESCRIPTION;

    @FXML
    private DatePicker FINISH;

    @FXML
    private Button SEND;
    @FXML
    private Label SUCCESS;

    @FXML
    private DatePicker START;

    @FXML
    void initialize()
    {
        BACK.setOnMouseClicked(event ->{
            Handler handler = new Handler();
            handler.openNewScene("employee_menu.fxml", BACK);
        });
        APP_TYPE.setOnAction(event ->{
            if (APP_TYPE.getValue() == "Увольнение")
            {
                FINISH.setDisable(true);
                START.setDisable(true);
            }
            else {
                FINISH.setDisable(false);
                START.setDisable(false);
            }
        });
        SEND.setOnMouseClicked(event ->{
            String descr = "Тип заявления: " + APP_TYPE.getValue() + "\n";
            if(!START.isDisabled())
            {
                descr += "Даты: ";
                String tmp = START.getValue().toString() + "-" + FINISH.getValue().toString() + "\n";
                if(tmp.isBlank())
                {
                    SUCCESS.setText("Календарь!");
                }
            }
            descr += "Дополнительно: " + DESCRIPTION.getText();
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                dataBaseHandler.makeApplication(Data.EMPLOYEE_ID,descr);
            } catch (SQLException e) {
                SUCCESS.setText("Ошибка");
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                SUCCESS.setText("Ошибка");
                throw new RuntimeException(e);
            }
            SUCCESS.setText("Успешно");

        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        APP_TYPE.getItems().add("Отпуск");
        APP_TYPE.getItems().add("Больничный");
        APP_TYPE.getItems().add("Увольнение");
    }
}
