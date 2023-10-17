package com.example.beerfactory;

import com.example.beerfactory.Entity.Orders;
import com.example.beerfactory.Entity.Workers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManagerWorkersListController {
    ObservableList<Workers> LIST = FXCollections.observableArrayList();
    @FXML
    private Button BACK;

    @FXML
    private Button CHANGE;

    @FXML
    private Label ERROR;

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

    @FXML
    private TableView<Workers> TABLE;

    @FXML
    private TableColumn<Workers, String> TABLE_ID;

    @FXML
    private TableColumn<Workers, String> TABLE_SALARY;

    @FXML
    private TableColumn<Workers, String> TABLE_SURNAME;

    @FXML
    void initialize() {

        FIRE.setOnAction(event ->{
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                Handler handler = new Handler();
                if(!handler.isBad(FIRE_FIELD.getText()))
                {
                    TABLE.getItems().clear();
                    dataBaseHandler.deleteWorker(Integer.valueOf(FIRE_FIELD.getText()));
                }
                else {
                    FIRE_FIELD.clear();
                    FIRE_FIELD.setPromptText("Ошибка!");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        CHANGE.setOnAction(event ->{
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                Handler handler = new Handler();
                if(!handler.isBad(SALARY_FIELD.getText()))
                {
                    TABLE.getItems().clear();
                    dataBaseHandler.changeSalary(Integer.valueOf(ID_SALARY.getText()), Integer.valueOf(SALARY_FIELD.getText()));
                }
                else {
                    SALARY_FIELD.clear();
                    SALARY_FIELD.setPromptText("Ошибка!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        RELOAD.setOnAction(event ->{
            try {

                ResultSet set = null;
                ArrayList<Workers> list = new ArrayList<>();
                DataBaseHandler dataBaseHandler = new DataBaseHandler();
                set = dataBaseHandler.getWorkersList();


                list = getFromSet(set);
                TABLE_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
                TABLE_SURNAME.setCellValueFactory(new PropertyValueFactory<>("surname"));
                TABLE_SALARY.setCellValueFactory(new PropertyValueFactory<>("salary"));



                for(int i = 0; i < list.size();i ++)
                {
                    //apps.add(list.get(i));
                    TABLE.getItems().add(list.get(i));

                }



                System.out.println(list.size());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        BACK.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("manager_menu.fxml", BACK);
        });
    }


    ArrayList<Workers> getFromSet(ResultSet set) throws SQLException {
        ArrayList<Workers> tmp = new ArrayList<>();
        String id, cost,status;
        set.next();
        do {
            id = set.getString("id");
            cost = set.getString("surname");
            status =  set.getString("salary");


            tmp.add(new Workers(id, cost, status));
        }while(set.next());
        return tmp;
    }

}
