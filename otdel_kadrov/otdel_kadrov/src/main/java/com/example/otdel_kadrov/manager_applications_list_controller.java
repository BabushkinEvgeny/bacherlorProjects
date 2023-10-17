package com.example.otdel_kadrov;

import com.example.otdel_kadrov.entity.ApplicationList;
import com.example.otdel_kadrov.entity.Candidate;
import com.example.otdel_kadrov.entity.JobApplicationOutput;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class manager_applications_list_controller {

    ObservableList<ApplicationList> apps = FXCollections.observableArrayList();

    @FXML
    private Button BACK;

    @FXML
    private Label ERROR;

    @FXML
    private CheckBox CHECKBOX;

    @FXML
    private TableColumn<ApplicationList, String> DESCRIPTION;

    @FXML
    private TableColumn<ApplicationList, Integer> EXAMINER;

    @FXML
    private TableColumn<ApplicationList, Integer> ID;

    @FXML
    private TextField ID_APP;

    @FXML
    private TextField ID_EXAMINER;

    @FXML
    private Button RELOAD;

    @FXML
    private Button SET;

    @FXML
    private TableColumn<ApplicationList, String> STATUS;

    @FXML
    private TableView<ApplicationList> TABLE;

    @FXML
    void initialize()
    {
        BACK.setOnMouseClicked(event ->{
            Handler handler = new Handler();
            handler.openNewScene("manager_menu.fxml", BACK);
        });
        RELOAD.setOnMouseClicked(event ->
        {
            try {
                TABLE.getItems().clear();
                ResultSet set = null;
                ArrayList<ApplicationList> list1 = new ArrayList<>();
                ArrayList<ApplicationList> list2 = new ArrayList<>();

                DataBaseHandler dataBaseHandler = new DataBaseHandler();
                set = dataBaseHandler.getJobApplication();
                list1 = getFromSet(set);
                ID.setCellValueFactory(new PropertyValueFactory<>("id"));
                STATUS.setCellValueFactory(new PropertyValueFactory<>("status"));
                EXAMINER.setCellValueFactory(new PropertyValueFactory<>("id_examiner"));
                DESCRIPTION.setCellValueFactory(new PropertyValueFactory<>("description"));
                ResultSet set2 = null;
                set2 = dataBaseHandler.getRegularApplication();
                list2 = getFromSet(set2);
                for(int i = 0; i < list2.size();i ++)
                {
                   TABLE.getItems().add(list2.get(i));
                }
                for(int i = 0; i < list1.size();i ++)
                {
                    //apps.add(list.get(i));
                    TABLE.getItems().add(list1.get(i));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        });
        SET.setOnMouseClicked(event ->{
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {

                dataBaseHandler.changeExaminer(Integer.valueOf(ID_APP.getText()), Integer.valueOf(ID_EXAMINER.getText()));
                ERROR.setText("");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    ArrayList<ApplicationList> getFromSet(ResultSet set) throws SQLException {
        ArrayList<ApplicationList> tmp = new ArrayList<>();
        String id, id_examiner;
        String status, description;
        //set.next();
        do {
            id = set.getString("idApplication");
            id_examiner = set.getString("id_examiner");
            description = set.getString("description");
            status = set.getString("status");

            tmp.add(new ApplicationList(id,id_examiner, description, status));
        }while(set.next());
        return tmp;
    }
}
