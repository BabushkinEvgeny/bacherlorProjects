package com.example.otdel_kadrov;

import com.example.otdel_kadrov.entity.ApplicationList;
import com.example.otdel_kadrov.entity.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class manager_workers_list_controller {

    ObservableList<ApplicationList> apps = FXCollections.observableArrayList();

    @FXML
    private Button BACK;

    @FXML
    private Button CHANGE;

    @FXML
    private Button FIRE;

    @FXML
    private TextField FIRE_FIELD;

    @FXML
    private TextField ID_SALARY;

    @FXML
    private Button RELOAD;

    @FXML
    private TextField SALARY_FIELD;
    Handler handler = new Handler();

    @FXML
    private TableView<Worker> TABLE;

    @FXML
    private TableColumn<Worker, String> TABLE_ID;

    @FXML
    private Label ERROR;
    @FXML
    private TableColumn<Worker, String> TABLE_SALARY;

    @FXML
    private TableColumn<Worker, String> TABLE_SURNAME;
    @FXML
    void initialize()
    {
        BACK.setOnMouseClicked(event ->{
            Handler handler = new Handler();
            handler.openNewScene("manager_menu.fxml", BACK);
        });

        RELOAD.setOnMouseClicked(event ->{
            ERROR.setText("");
            try {
                TABLE.getItems().clear();
                ResultSet set = null;
                ArrayList<Worker> list = new ArrayList<>();
                DataBaseHandler dataBaseHandler = new DataBaseHandler();
                set = dataBaseHandler.getEmployees();
                list = getFromSet(set);
                TABLE_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
                TABLE_SURNAME.setCellValueFactory(new PropertyValueFactory<>("surname"));
                TABLE_SALARY.setCellValueFactory(new PropertyValueFactory<>("salary"));
                for(int i = 0; i < list.size();i ++)
                {
                    TABLE.getItems().add(list.get(i));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        FIRE.setOnMouseClicked(event ->{
            ERROR.setText("");
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                if(handler.isBad(FIRE_FIELD.getText()))
                {
                    ERROR.setText("Ошибка");
                }
                dataBaseHandler.deleteEmployee(Integer.valueOf(FIRE_FIELD.getText()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        CHANGE.setOnMouseClicked(event ->{
            ERROR.setText("");
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            if(handler.isBad(SALARY_FIELD.getText()))
            {
                ERROR.setText("Ошибка");
            }
            else {
                try {
                    //dataBaseHandler.changeSalary(Integer.valueOf(ID_SALARY.getText()),Integer.valueOf(SALARY_FIELD.getText()));
                    dataBaseHandler.setSalary(Integer.valueOf(ID_SALARY.getText()), Integer.valueOf(SALARY_FIELD.getText()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        });

    }
    ArrayList<Worker> getFromSet(ResultSet set) throws SQLException {
        ArrayList<Worker> tmp = new ArrayList<>();
        String id, surname,salary;
        String status, description;
        set.next();
        do {
            surname = set.getString("surname");
            id = set.getString("idemployees");
            salary = set.getString("salary");
            tmp.add(new Worker(surname,id,salary));
        }while(set.next());
        return tmp;
    }

}
