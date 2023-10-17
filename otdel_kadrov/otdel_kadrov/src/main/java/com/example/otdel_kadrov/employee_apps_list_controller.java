package com.example.otdel_kadrov;

import com.example.otdel_kadrov.entity.EmployeeApplication;
import com.example.otdel_kadrov.entity.Requirement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class employee_apps_list_controller {
    ObservableList<EmployeeApplication> apps = FXCollections.observableArrayList();

    @FXML
    private Button APPLICATIONS_BACK;

    @FXML
    private TableColumn<EmployeeApplication, String> DESCRIPTION;

    @FXML
    private TableColumn<EmployeeApplication, String> ID_APPLICATION;

    @FXML
    private Button RELOAD;

    @FXML
    private TableColumn<EmployeeApplication, String> STATUS;

    @FXML
    private TableView<EmployeeApplication> TABLE_APPLICATIONS;

    @FXML
    void initialize() {
        APPLICATIONS_BACK.setOnMouseClicked(event -> {
            Handler handler = new Handler();
            handler.openNewScene("employee_menu.fxml", APPLICATIONS_BACK);
        });
        RELOAD.setOnMouseClicked(event ->{
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            TABLE_APPLICATIONS.getItems().clear();
            try {
                ResultSet set = dataBaseHandler.getRegularApplicationemployee(Data.EMPLOYEE_ID);
                ArrayList<EmployeeApplication> list = new ArrayList<>();
                list = getFromSet(set);
                ID_APPLICATION.setCellValueFactory(new PropertyValueFactory<>("id"));
                STATUS.setCellValueFactory(new PropertyValueFactory<>("status"));
                DESCRIPTION.setCellValueFactory(new PropertyValueFactory<>("description"));
                for (int i = 0; i < list.size(); i++)
                {
                    TABLE_APPLICATIONS.getItems().add(list.get(i));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

//            DataBaseHandler handler = new DataBaseHandler();
//            TABLE_REQ.getItems().clear();
//            try {
//                ArrayList<Requirement> list = new ArrayList<>();
//                String s = CHOICE_BOX.getValue();
//
//                ResultSet set = handler.getRequirementsForOnePosition(s);
//                list = getFromSet(set);
//                REQUIREMENTS.setCellValueFactory(new PropertyValueFactory<>("requirement"));
//
//                for(int i = 0; i < list.size();i ++)
//                {
//                    //apps.add(list.get(i));
//                    TABLE_REQ.getItems().add(list.get(i));
//                    System.out.println(list.get(i));
//                }
//
//                AMOUNT.setText(handler.getAmountOfVacancies(s).toString());
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }

        });
    }
    private ArrayList<EmployeeApplication> getFromSet(ResultSet set) throws SQLException {
        ArrayList<EmployeeApplication> tmp = new ArrayList<>();
        String id,status,description;
        set.next();
        do {
            id = set.getString("id");
            status = set.getString("status");
            description = set.getString("description");

            tmp.add(new EmployeeApplication(id,status,description));
        }while(set.next());
        return tmp;
    }

}
