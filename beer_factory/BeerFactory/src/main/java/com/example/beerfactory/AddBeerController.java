package com.example.beerfactory;

import com.example.beerfactory.Entity.Beer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddBeerController implements Initializable {

    @FXML
    private Button ADD;

    @FXML
    private Button BACK;

    @FXML
    private TextField DESCRIPTION;

    @FXML
    private ListView<String> COMPONENTS;

    @FXML
    private TextField NAME;

    @FXML
    private ComboBox<String> TYPE;

    @FXML
    private TextField VALUE;

    @FXML
    void initialize() {

        BACK.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("manager_catalog.fxml", BACK);
        });
        ADD.setOnAction(event ->{
            Handler handler = new Handler();
            if(handler.isBad(VALUE.getText()))
            {
                VALUE.clear();
                VALUE.setPromptText("Ошибка!");
            }
            else {


                ObservableList<String> oList = FXCollections.observableArrayList();
                DataBaseHandler dataBaseHandler = new DataBaseHandler();

                String name = NAME.getText();
                String description = DESCRIPTION.getText();
                Integer value = Integer.valueOf(VALUE.getText());
                Integer type = 0;
                Integer beer_id = 0;
                try {
                    type = dataBaseHandler.getBeerType(TYPE.getSelectionModel().getSelectedItem());
                    dataBaseHandler.addBeer(name, description, type, value);
                    beer_id = dataBaseHandler.getBeerId(name);
                    ObservableList<String> selectedItems = COMPONENTS.getSelectionModel().getSelectedItems();
                    for (String s : selectedItems) {
                        dataBaseHandler.insertComponents(beer_id, dataBaseHandler.getComponentId(s));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                try {
                    ResultSet resultSet = dataBaseHandler.getBottles();
                    resultSet.next();
                    do {
                        Integer id = dataBaseHandler.getBottleId(resultSet.getString(1));
                        dataBaseHandler.insertBeerBottle(id, beer_id);
                    } while (resultSet.next());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        COMPONENTS.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ResultSet set = null;
        try {
            set = dataBaseHandler.getBeerTypes();
            while(set.next())
            {
                String tmp = set.getString("type_name");
                TYPE.getItems().add(tmp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        COMPONENTS.getItems().clear();
        //DataBaseHandler dataBaseHandler = new DataBaseHandler();
        try {
            ResultSet positionsSet = dataBaseHandler.getComponents();
            while(positionsSet.next())
            {
                String tmp = positionsSet.getString("name");
                COMPONENTS.getItems().add(tmp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



}
