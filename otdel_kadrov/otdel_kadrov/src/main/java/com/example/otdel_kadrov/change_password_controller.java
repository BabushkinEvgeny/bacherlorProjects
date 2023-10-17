package com.example.otdel_kadrov;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class change_password_controller {

    @FXML
    private Button BACK;

    @FXML
    private Button CHANGE;

    @FXML
    private PasswordField NEW_PASSWORD;

    @FXML
    private PasswordField OLD_PASSWORD;

    @FXML
    private Label SNILS_ERROR;

    @FXML
    private Label SUCCESS;

    @FXML
    void initialize() {
        CHANGE.setOnMouseClicked(event ->{
            String oldpassword = OLD_PASSWORD.getText();
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                if(hash(oldpassword).equals((dataBaseHandler.getPassword(Integer.valueOf(Data.EMPLOYEE_ID)))))
                {
                    System.out.println("go to change password");
                    dataBaseHandler.changePassword(NEW_PASSWORD.getText(), Integer.valueOf(Data.EMPLOYEE_ID));
                }
                System.out.println("not changed");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        BACK.setOnMouseClicked(event ->
        {
            Handler handler = new Handler();
            if(Data.EMPLOYEE_ROLE == "1") {
                handler.openNewScene("manager_menu.fxml", BACK);
            } else handler.openNewScene("employee_menu.fxml", BACK);
        });

    }
    public String hash(String password) throws NoSuchAlgorithmException {
        String str = password;
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        byte[] bytes = md5.digest(str.getBytes());
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02X ", b));
        }
        return builder.toString();
    }
}


