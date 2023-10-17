package com.example.beerfactory;
import com.example.beerfactory.animations.*;
import com.example.beerfactory.Entity.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private Button LOGIN;

    @FXML
    private TextField LOG_FIELD;

    @FXML
    private PasswordField PAS_FIELD;

    @FXML
    private Button REGISTER;

    @FXML
    void execute() {
        Handler handler = new Handler();
        LOGIN.setOnMouseClicked(event ->{
            String loginText = LOG_FIELD.getText().trim();
            String passwordText = PAS_FIELD.getText().trim();

            if(!loginText.equals("") && !passwordText.equals(""))
            {
                try {
                    loginUser(loginText,passwordText);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
            else
            {
                Shake userLoginAnimation = new Shake(LOG_FIELD);
                Shake userPasswordAnimation = new Shake(PAS_FIELD);
                userLoginAnimation.playAnimation();
                userPasswordAnimation.playAnimation();
                System.out.println("Fill login and password");

            }
        });

        REGISTER.setOnMouseClicked(action ->{

            handler.openNewScene("register.fxml", REGISTER);

        });
    }
    private void loginUser(String loginText, String passwordText) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        Handler handler = new Handler();
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        Customer customer = new Customer();
        customer.setCustomer_login(loginText);
        String pas = hash(passwordText);
        customer.setCustomer_password(pas);
        Worker employee= new Worker();
        employee.setWorker_login(loginText);
        employee.setWorker_password(pas);
        ResultSet resultCandidate = dataBaseHandler.getCustomer(customer);
        ResultSet resultEmployee = dataBaseHandler.getEmployee(employee);
        ResultSet resultManager = dataBaseHandler.getManager(employee);

        int counterCandidate = 0;
        while(resultCandidate.next())
        {
            dataBaseHandler.user_id = resultCandidate.getInt(1);
            System.out.println(dataBaseHandler.user_id);
            Const.user = dataBaseHandler.user_id;
            fillCustomerData(resultCandidate);
            counterCandidate++;
        }
        int counterEmployee = 0;
        while(resultEmployee.next())
        {
            dataBaseHandler.user_id = resultEmployee.getInt(1);
            Const.user = dataBaseHandler.user_id;
            fillEmployeeData(resultEmployee);

            counterEmployee++;
        }
        int counterManager = 0;
        while(resultManager.next())
        {
            dataBaseHandler.user_id = resultManager.getInt(1);
            Const.user = dataBaseHandler.user_id;
            fillEmployeeData(resultManager);
            Data.EMPLOYEE_ROLE = "1";
            counterManager++;
        }

        if(counterCandidate >= 1)
        {
            System.out.println("Success!");
            handler.openNewScene("customer_menu.fxml", REGISTER);
        }
        else if(counterEmployee >=1)
        {
            System.out.println("Success!");
            handler.openNewScene("employee_menu.fxml", REGISTER);
        }
        else if (counterManager >= 1)
        {

            System.out.println("Success!");
            handler.openNewScene("manager_menu.fxml", REGISTER);
        }
        else
        {
            Shake userLoginAnimation = new Shake(LOG_FIELD);
            Shake userPasswordAnimation = new Shake(PAS_FIELD);
            userLoginAnimation.playAnimation();
            userPasswordAnimation.playAnimation();
        }

    }

    private void fillCustomerData(ResultSet set) throws SQLException {
        Data.CUSTOMER_ID = set.getInt(1);
        Data.CUSTOMER_NAME = set.getString(2);
        Data.CUSTOMER_SURNAME = set.getString(3);
        Data.CUSTOMER_BANK = set.getString(4);
        Data.CUSTOMER_LOGIN = set.getString(5);
        Data.CUSTOMER_PASSWORD = set.getString(6);

    }
    private void fillEmployeeData(ResultSet set) throws SQLException {
        Data.EMPLOYEE_ID = set.getInt(1);
        Data.EMPLOYEE_NAME = set.getString(2);
        Data.EMPLOYEE_SURNAME = set.getString(3);
        Data.EMPLOYEE_LOGIN = set.getString(4);
        Data.EMPLOYEE_PASSWORD = set.getString(5);
        Data.EMPLOYEE_SALARY = set.getString(6);
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
