package com.example.otdel_kadrov;

import com.example.otdel_kadrov.entity.Requirement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class manager_positions_controller implements Initializable {

    ObservableList<Requirement> apps = FXCollections.observableArrayList();

    @FXML
    private TextField WORKING_RATE;

    @FXML
    private Label AMOUNT;
    @FXML
    private Label ERROR;
    @FXML
    private Button ADD_POSITION;
Handler handler = new Handler();
    @FXML
    private TextField ADD_POSITION_FIELD;

    @FXML
    private Button ADD_REQUIREMENT;

    @FXML
    private Button ADD_VACCANCIES_BUTTON;

    @FXML
    private TextField ADD_VACCANCIES_FIELD;

    @FXML
    private Button BACK;

    @FXML
    private Button DELETE_POSITION;

    @FXML
    private Button DELETE_REQUIREMENT;

    @FXML
    private ChoiceBox<String> POSITIONS;

    @FXML
    private Button RELOAD;

    @FXML
    private TableView<Requirement> REQUIREMENTS;

    @FXML
    private TextField REQUIREMENT_FIELD;

    @FXML
    private TableColumn<Requirement, String> COLUMN;
    @FXML
    void initialize()
    {
        BACK.setOnMouseClicked(event ->{
            Handler handler = new Handler();
            handler.openNewScene("manager_menu.fxml", BACK);
        });
        RELOAD.setOnMouseClicked(event ->{
            DataBaseHandler handler = new DataBaseHandler();
            REQUIREMENTS.getItems().clear();
            try {
                ArrayList<Requirement> list = new ArrayList<>();
                String s = POSITIONS.getValue();

                ResultSet set = handler.getRequirementsForOnePosition(s);
                list = getFromSet(set);
                COLUMN.setCellValueFactory(new PropertyValueFactory<>("requirement"));

                for(int i = 0; i < list.size();i ++)
                {
                    //apps.add(list.get(i));
                    REQUIREMENTS.getItems().add(list.get(i));
                    System.out.println(list.get(i));
                }

                AMOUNT.setText(handler.getAmountOfVacancies(s).toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        ADD_POSITION.setOnMouseClicked(event ->{
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            if(handler.isBad(WORKING_RATE.getText()))
            {
                ERROR.setText("Bad");
            }
            else {
                try {
                    dataBaseHandler.createPosition(ADD_POSITION_FIELD.getText(), Integer.valueOf(WORKING_RATE.getText()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

                try {
                    ResultSet positionsSet = dataBaseHandler.getPositions();
                    while(positionsSet.next())
                    {
                        String tmp = positionsSet.getString("name");
                        POSITIONS.getItems().add(tmp);
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        DELETE_POSITION.setOnMouseClicked(event ->{
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                dataBaseHandler.deletePosition(POSITIONS.getSelectionModel().getSelectedItem());
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                ResultSet positionsSet = dataBaseHandler.getPositions();
                while(positionsSet.next())
                {
                    String tmp = positionsSet.getString("name");
                    POSITIONS.getItems().add(tmp);
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        ADD_VACCANCIES_BUTTON.setOnMouseClicked(event ->{
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                dataBaseHandler.addVaccancies(POSITIONS.getSelectionModel().getSelectedItem().toString(),Integer.valueOf(ADD_VACCANCIES_FIELD.getText()));
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        DELETE_REQUIREMENT.setOnMouseClicked(event ->{
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                dataBaseHandler.deleteRequirementFromPosition(REQUIREMENTS.getSelectionModel().getSelectedItem().getRequirement().toString(), POSITIONS.getSelectionModel().getSelectedItem().toString());
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        ADD_REQUIREMENT.setOnMouseClicked(event ->{
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                dataBaseHandler.addRequirementToPosition(REQUIREMENT_FIELD.getText(),POSITIONS.getSelectionModel().getSelectedItem().toString());
            } catch (SQLException | ClassNotFoundException e) {
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
                POSITIONS.getItems().add(tmp);
            }
        } catch (SQLException | ClassNotFoundException e) {
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
