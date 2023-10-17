package com.example.otdel_kadrov;

import com.example.otdel_kadrov.entity.Candidate;
import com.example.otdel_kadrov.entity.JobApplication;
import com.example.otdel_kadrov.entity.JobApplicationOutput;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class candidate_applications_controller {
    ObservableList<JobApplicationOutput> apps = FXCollections.observableArrayList();


        @FXML
    private Button APPLICATIONS_BACK;
    @FXML
    private Button RELOAD;

    @FXML
    private TableColumn<JobApplicationOutput, Integer> ID_APPLICATION;

    @FXML
    private TableColumn<JobApplicationOutput, String> POSITION;

    @FXML
    private TableColumn<JobApplicationOutput, String> STATUS;

    @FXML
    private TableView<JobApplicationOutput> TABLE_APPLICATIONS;

    @FXML
    void initialize() {

        APPLICATIONS_BACK.setOnMouseClicked(event -> {
            Handler handler = new Handler();
            handler.openNewScene("candidate_menu.fxml", APPLICATIONS_BACK);
        });

        RELOAD.setOnMouseClicked(event -> {

            try {

                ResultSet set = null;
                ArrayList<JobApplicationOutput> list = new ArrayList<>();
                DataBaseHandler dataBaseHandler = new DataBaseHandler();
                Candidate candidate = new Candidate();
                candidate.setId(Data.CANDIDATE_ID);
                set = dataBaseHandler.getCandidateApplication(candidate);
                list = getFromSet(set);
                STATUS.setCellValueFactory(new PropertyValueFactory<>("status"));
                ID_APPLICATION.setCellValueFactory(new PropertyValueFactory<>("idJobApplication"));
                POSITION.setCellValueFactory(new PropertyValueFactory<>("name"));
                for(int i = 0; i < list.size();i ++)
                {
                    //apps.add(list.get(i));
                    TABLE_APPLICATIONS.getItems().add(list.get(i));
                }

                System.out.println(list.size());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }



    ArrayList<JobApplicationOutput> getFromSet(ResultSet set) throws SQLException {
        ArrayList<JobApplicationOutput> tmp = new ArrayList<>();
        String name, status;
        String id;
        set.next();
        do {
             id = set.getString("idJobApplication");
            name = set.getString("name");
            status =  set.getString("status");

            tmp.add(new JobApplicationOutput(id,name,status));
        }while(set.next());
        return tmp;
    }

}





