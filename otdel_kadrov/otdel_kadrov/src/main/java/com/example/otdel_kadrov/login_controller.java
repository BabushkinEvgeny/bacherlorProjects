package com.example.otdel_kadrov;

import com.example.otdel_kadrov.annimations.Shake;
import com.example.otdel_kadrov.entity.Candidate;
import com.example.otdel_kadrov.entity.Employee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class login_controller {

    @FXML
    private Button LOG_BUTTON;

    @FXML
    private TextField LOG_FIELD;

    @FXML
    private PasswordField PAS_FIELD;

    @FXML
    private Button REG_BUTTON;

    @FXML
    private CheckBox NOT_EMPLOYEE_CHECKBOX;



    @FXML
    void execute()
    {
        Handler handler = new Handler();
        LOG_BUTTON.setOnMouseClicked(event ->{
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

        REG_BUTTON.setOnMouseClicked(action ->{

        handler.openNewScene("register.fxml", REG_BUTTON);

        });
    }

    private void loginUser(String loginText, String passwordText) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        Handler handler = new Handler();
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        Candidate candidate = new Candidate();
        candidate.setLogin(loginText);
        String pas = hash(passwordText);
        candidate.setPassword(pas);
        Employee employee= new Employee();
        employee.setLogin(loginText);
        employee.setPassword(pas);
        ResultSet resultCandidate = dataBaseHandler.getCandidate(candidate);
        ResultSet resultEmployee = dataBaseHandler.getEmployee(employee);
        ResultSet resultManager = dataBaseHandler.getManager(employee);

        int counterCandidate = 0;
        while(resultCandidate.next())
        {
            dataBaseHandler.user_id = resultCandidate.getInt(1);
            System.out.println(dataBaseHandler.user_id);
            Const.user = dataBaseHandler.user_id;
            fillCandidateData(resultCandidate);
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
            handler.openNewScene("candidate_menu.fxml", REG_BUTTON);
        }
        else if(counterEmployee >=1)
        {
            System.out.println("Success!");
            handler.openNewScene("employee_menu.fxml", REG_BUTTON);
        }
        else if (counterManager >= 1)
        {

            System.out.println("Success!");
            handler.openNewScene("manager_menu.fxml", REG_BUTTON);
        }
        else
        {
            Shake userLoginAnimation = new Shake(LOG_FIELD);
            Shake userPasswordAnimation = new Shake(PAS_FIELD);
            userLoginAnimation.playAnimation();
            userPasswordAnimation.playAnimation();
        }

    }

    private void fillCandidateData(ResultSet set) throws SQLException {
        Data.CANDIDATE_ID = set.getInt(1);
        Data.CANDIDATE_NAME = set.getString(2);
        Data.CANDIDATE_SURNAME = set.getString(3);
        Data.CANDIDATE_LOGIN = set.getString(4);
        Data.CANDIDATE_PASSWORD = set.getString(5);
        Data.CANDIDATE_SNILS = set.getString(6);
    }
    private void fillEmployeeData(ResultSet set) throws SQLException {
        Data.EMPLOYEE_ID = set.getInt(1);
        Data.EMPLOYEE_NAME = set.getString(2);
        Data.EMPLOYEE_SURNAME = set.getString(3);
        Data.EMPLOYEE_LOGIN = set.getString(4);
        Data.EMPLOYEE_PASSWORD = set.getString(5);
        Data.EMPLOYEE_SNILS = set.getString(6);
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

    boolean noLetters(String str)
    {
        String template = "([A-Za-z])";
        Pattern pattern = null;
        try
        {
            pattern = Pattern.compile(template);
        }
        catch(PatternSyntaxException e)
        {
            e.printStackTrace();
        }
        if(pattern == null)
        {
            return false;
        }
        final Matcher regexp = pattern.matcher(str);
        return regexp.matches();
    }

}
