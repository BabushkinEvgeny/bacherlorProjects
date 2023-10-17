package com.example.otdel_kadrov;
import com.example.otdel_kadrov.DataBaseHandler;
import com.example.otdel_kadrov.Handler;
import com.example.otdel_kadrov.entity.JobApplicationOutput;
import com.example.otdel_kadrov.entity.Requirement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class candidate_vacancies_controller implements Initializable {
    ObservableList<Requirement> apps = FXCollections.observableArrayList();
    @FXML
    private Label AMOUNT;
    @FXML
    private Button RELOAD;

    @FXML
    private ChoiceBox<String> CHOICE_BOX;

    @FXML
    private AnchorPane COMBO_BOX_POSITIONS;

    @FXML
    private TableColumn<Requirement, String> REQUIREMENTS;
    @FXML
    private TableView<Requirement> TABLE_REQ;
    @FXML
    private Button VAC_BACK;




    @FXML
    void initialize()
    {
        VAC_BACK.setOnMouseClicked(event -> {
            Handler handler = new Handler();
            handler.openNewScene("candidate_menu.fxml", VAC_BACK);
        });
        RELOAD.setOnMouseClicked(event ->{
            DataBaseHandler handler = new DataBaseHandler();
            TABLE_REQ.getItems().clear();
            try {
                ArrayList<Requirement> list = new ArrayList<>();
                String s = CHOICE_BOX.getValue();

                ResultSet set = handler.getRequirementsForOnePosition(s);
                list = getFromSet(set);
                REQUIREMENTS.setCellValueFactory(new PropertyValueFactory<>("requirement"));

                for(int i = 0; i < list.size();i ++)
                {
                    //apps.add(list.get(i));
                    TABLE_REQ.getItems().add(list.get(i));
                    System.out.println(list.get(i));
                }

                AMOUNT.setText(handler.getAmountOfVacancies(s).toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        try {
            ResultSet positionsSet = dataBaseHandler.getPositions();
            while(positionsSet.next())
            {
                String tmp = positionsSet.getString("name");
                CHOICE_BOX.getItems().add(tmp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    ArrayList<Requirement> getFromSet(ResultSet set) throws SQLException {
        ArrayList<Requirement> tmp = new ArrayList<>();
        String requirement;
        set.next();
        do {
            requirement = set.getString("requirement");

            tmp.add(new Requirement(requirement));
        }while(set.next());
        return tmp;
    }
}
