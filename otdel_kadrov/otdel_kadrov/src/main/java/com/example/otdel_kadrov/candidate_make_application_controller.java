package com.example.otdel_kadrov;

import com.example.otdel_kadrov.entity.Position;
import com.example.otdel_kadrov.entity.Requirement;
import com.example.otdel_kadrov.entity.Skill;
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

public class candidate_make_application_controller implements Initializable {
    private String description;

    @FXML
    private Button BACK;

    @FXML
    private Label ERROR;

    @FXML
    private Button INSERT;

    @FXML
    private TextArea DESCRIPTION;

    @FXML
    private ChoiceBox<String> POSITION;

    @FXML
    private Button SEND;

    @FXML
    private ListView<String> SKILLS;
    @FXML
    void initialize()
    {
        ObservableList<String> oList = FXCollections.observableArrayList();
        BACK.setOnMouseClicked(event ->{
            Handler handler = new Handler();
            handler.openNewScene("candidate_menu.fxml", BACK);
        });

        INSERT.setOnMouseClicked(event ->{

            try {

                DataBaseHandler dataBaseHandler= new DataBaseHandler();
                ResultSet set = dataBaseHandler.getRequirements();
                ArrayList<String> list = new ArrayList<>();

                SKILLS.setItems(oList);
                while(set.next())
                {
                    String tmp = set.getString("requirement");
                    oList.add(tmp);
                }
                SKILLS.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        SEND.setOnMouseClicked(event ->{



            ObservableList<String> selectedItems =  SKILLS.getSelectionModel().getSelectedItems();

            String app = "Указаны навыки:";
            for(String s : selectedItems){
                app+=" " + s;
            }
            app += ". Дополнительно: " +
            DESCRIPTION.getText() + ".";
            System.out.println(app);
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                ResultSet resultSet = dataBaseHandler.getPositions();
                ArrayList<String> list = new ArrayList<>();
                list = getFromSet(resultSet);
                String pos = POSITION.getValue();
                if(!selectedItems.isEmpty() && !pos.isEmpty())
                {
                    System.out.println(pos);
                    Integer id = dataBaseHandler.getPositionIdByName(pos);
                    System.out.println(id);
                    dataBaseHandler.makeJobApplication(id, app);
                    ERROR.setText("Заявление отправлено");
                }else
                {
                    ERROR.setText("Пустая форма");
                }

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
                POSITION.getItems().add(tmp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
   private ArrayList<String> getFromSet(ResultSet set) throws SQLException {
        ArrayList<String> tmp = new ArrayList<>();
        String name;
        set.next();
        do {
            name = set.getString("name");

            tmp.add(name);
        }while(set.next());
        return tmp;
    }
}
