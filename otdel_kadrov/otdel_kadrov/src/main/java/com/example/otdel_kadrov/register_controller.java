package com.example.otdel_kadrov;

import com.example.otdel_kadrov.annimations.Shake;
import com.example.otdel_kadrov.entity.Candidate;
import com.example.otdel_kadrov.entity.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class register_controller {

    @FXML
    private Button BACK;
    @FXML
    private Label SNILS_ERROR;

    @FXML
    private TextField REG_LOG_FIELD;

    @FXML
    private TextField REG_NAME_FIELD;

    @FXML
    private PasswordField REG_PAS_FIELD;

    @FXML
    private Button REG_REG_BUTTON;

    @FXML
    private TextField REG_SNILS_FIELD;

    @FXML
    private TextField REG_SURNAME_FIELD;
    @FXML
    private Label SUCCESS;
    @FXML
    void initialize()
    {
        DataBaseHandler dbHandler = new DataBaseHandler();
        REG_REG_BUTTON.setOnMouseClicked(event ->{
            try {
                regNewCandidate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            //Handler handler = new Handler();
            //handler.openNewScene("login.fxml", REG_REG_BUTTON);
        });
        BACK.setOnMouseClicked(event ->{
            Handler handler = new Handler();
            handler.openNewScene("login.fxml", BACK);
        });
    }
    private void regNewCandidate() throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        SUCCESS.setText("");
        SNILS_ERROR.setText("");
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        String name = REG_NAME_FIELD.getText();
        String surname = REG_SURNAME_FIELD.getText();
        String login = REG_LOG_FIELD.getText();
        String password = REG_PAS_FIELD.getText();
        String snils = REG_SNILS_FIELD.getText();
        Handler handler = new Handler();

        if(REG_SURNAME_FIELD.getText().equals("")|| REG_NAME_FIELD.getText().equals("") || REG_LOG_FIELD.getText().equals("") || REG_PAS_FIELD.getText().equals("") || REG_SNILS_FIELD.getText().equals("")) {
            Shake userLoginAnimation = new Shake(REG_PAS_FIELD);
            Shake userPasswordAnimation = new Shake(REG_LOG_FIELD);
            userLoginAnimation.playAnimation();
            userPasswordAnimation.playAnimation();
            SUCCESS.setText("Empty fields");
            if(handler.isBad(REG_SNILS_FIELD.getText()))
            {
                SNILS_ERROR.setText("Bad snils");
            }

        }
        else if(handler.isBad(REG_SNILS_FIELD.getText()) || handler.snilsIsShortOrLong(REG_SNILS_FIELD.getText()))
        {
            SNILS_ERROR.setText("Bad snils");
        }

            else{
            Candidate candidate = new Candidate();
            //candidate.setPassword(password);
            candidate.setLogin(login);
            candidate.setName(name);
            candidate.setSurname(surname);
            candidate.setSnils(snils);

            Employee employee = new Employee();
            String pas = hash(password);
            password = pas;
            candidate.setPassword(password);
            employee.setPassword(password);
            employee.setLogin(login);
            ResultSet resCandidate = dataBaseHandler.getCandidate(candidate);
            ResultSet resEmployee = dataBaseHandler.getManager(employee);
            int countCandidate = 0;
            int countEmployee = 0;
            while (resCandidate.next()) {
                countCandidate++;
            }
            while (resEmployee.next()) {
                countEmployee++;
            }
            if (countCandidate != 0 || countEmployee != 0) {
                Shake userLoginAnimation = new Shake(REG_LOG_FIELD);
                Shake userPasswordAnimation = new Shake(REG_PAS_FIELD);
                userLoginAnimation.playAnimation();
                userPasswordAnimation.playAnimation();
            } else {
                dataBaseHandler.regCandidate(candidate);
            }
            SUCCESS.setText("Success");
        }

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
