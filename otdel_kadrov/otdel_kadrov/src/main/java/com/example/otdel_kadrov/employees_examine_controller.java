package com.example.otdel_kadrov;

import com.example.otdel_kadrov.entity.ApplicationExamine;
import com.example.otdel_kadrov.entity.ApplicationList;
import com.example.otdel_kadrov.entity.Requirement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class employees_examine_controller {
    ObservableList<ApplicationExamine> apps = FXCollections.observableArrayList();
    @FXML
    private Button ACCEPT;

    @FXML
    private TableColumn<ApplicationExamine, String> NAME;

    @FXML
    private TableColumn<ApplicationExamine, String> PURPOSE;

    @FXML
    private Button BACK;
    @FXML
    private TextField FIELD_ID;

    @FXML
    private Button DECLINE;

    @FXML
    private TableColumn<ApplicationExamine, String> DESCRIPTION;

    @FXML
    private TableColumn<ApplicationExamine, String> ID;

    @FXML
    private Button RELOAD;
    @FXML
    private TableColumn<ApplicationExamine,String> STATUS;


    @FXML
    private TableView<ApplicationExamine> TABLE;
    @FXML
    void examine()
    {
        BACK.setOnMouseClicked(event ->{
            Handler handler = new Handler();
            handler.openNewScene("employee_menu.fxml",BACK);
        });
        RELOAD.setOnMouseClicked(event -> {
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            TABLE.getItems().clear();
            ArrayList<ApplicationExamine> list1 = new ArrayList<>();
            try {
                ResultSet set1 = null;
                set1 = dataBaseHandler.getRegularAppExamine(Data.EMPLOYEE_ID);
                list1 = getFromSet(set1);
                STATUS.setCellValueFactory(new PropertyValueFactory<>("status"));
                DESCRIPTION.setCellValueFactory(new PropertyValueFactory<>("description"));
                ID.setCellValueFactory(new PropertyValueFactory<>("id"));
                PURPOSE.setCellValueFactory(new PropertyValueFactory<>("purpose"));
                NAME.setCellValueFactory(new PropertyValueFactory<>("surname"));


                for (int i = 0; i < list1.size(); i++)
                {
                    TABLE.getItems().add(list1.get(i));
                }
                ArrayList<ApplicationExamine> list2 = new ArrayList<>();
                ResultSet set2 = null;
                set2 = dataBaseHandler.getJobAppExamine(Data.EMPLOYEE_ID);
                list2 = getFromSet(set2);
                for (int i = 0; i < list2.size(); i++)
                {
                    TABLE.getItems().add(list2.get(i));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        ACCEPT.setOnMouseClicked(event ->{
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                ResultSet set = dataBaseHandler.getSelectedApp(Integer.valueOf(TABLE.getSelectionModel().getSelectedItem().getId()));
                Integer counter = 0;
                while(set.next())
                {
                    counter++;
                }
                if(counter > 0)
                {
                    ResultSet set1 = dataBaseHandler.getSelectedApp(Integer.valueOf(TABLE.getSelectionModel().getSelectedItem().getId()));
                    set1.next();
                    System.out.println("regular");
                    String purpose = set1.getString("application_type");
                    dataBaseHandler.acceptRegularApp(purpose,dataBaseHandler.getUserIdFromApplication(Integer.valueOf(TABLE.getSelectionModel().getSelectedItem().getId())));
                    dataBaseHandler.changeStatusToAcceptJobAPP(Integer.valueOf(TABLE.getSelectionModel().getSelectedItem().getId()));
                }
                else {
                    System.out.println("job");
                    dataBaseHandler.acceptJobApp(Integer.valueOf(TABLE.getSelectionModel().getSelectedItem().getId()));
                    dataBaseHandler.changeStatusToAcceptJobAPP(Integer.valueOf(TABLE.getSelectionModel().getSelectedItem().getId()));

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        DECLINE.setOnMouseClicked(event ->{
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                dataBaseHandler.declineJobAPP(Integer.valueOf(TABLE.getSelectionModel().getSelectedItem().getId()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }


    ArrayList<ApplicationExamine> getFromSet(ResultSet set) throws SQLException {
        ArrayList<ApplicationExamine> tmp = new ArrayList<>();
        String id, id_examiner;
        String status, description, name,purpose;
        set.next();
        do {
            id = set.getString("id");
            description = set.getString("description");
            status = set.getString("status");
            name = set.getString("surname");
            purpose = set.getString("purpose");
            tmp.add(new ApplicationExamine(id, status, description, name,purpose));
        }while(set.next());
        return tmp;
    }
}
