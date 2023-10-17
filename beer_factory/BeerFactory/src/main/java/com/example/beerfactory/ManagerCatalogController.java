package com.example.beerfactory;

import com.example.beerfactory.Entity.Beer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
public class ManagerCatalogController implements Initializable {
    ObservableList<Beer> beerTable = FXCollections.observableArrayList();
@FXML
private Button ADD;

@FXML
private TextField AMOUNT;

    @FXML
    private Button DELETE;

@FXML
private Button BACK;

@FXML
private ComboBox<String> BOTTLE;

@FXML
private TableView<Beer> CATALOG;

@FXML
private TableColumn<Beer, String> DESCRIPTION;

@FXML
private TableColumn<Beer, String> ID;

@FXML
private TableColumn<Beer, String> NAME;

@FXML
private TableColumn<Beer, String> TYPE;

@FXML
private TableColumn<Beer, String> VALUE;

@FXML
    void initialize() {
            BACK.setOnAction(event -> {
            Handler handler = new Handler();
            handler.openNewScene("manager_menu.fxml", BACK);
            });

            DELETE.setOnAction(event ->{
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                dataBaseHandler.deleteBeer(Integer.valueOf(CATALOG.getSelectionModel().getSelectedItem().getBeer_id().toString()));
                reloadPage();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            });
            ADD.setOnAction(event ->{
                Handler handler = new Handler();
                handler.openNewScene("add_beer.fxml", ADD);
            });



            }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                reloadPage();

        }

        void reloadPage()
        {
                try {
                        CATALOG.getItems().clear();

                        ResultSet set = null;
                        ArrayList<Beer> list = new ArrayList<>();
                        DataBaseHandler dataBaseHandler = new DataBaseHandler();
                        set = dataBaseHandler.getCatalog();


                        list = getFromSet(set);
                        ID.setCellValueFactory(new PropertyValueFactory<>("beer_id"));
                        NAME.setCellValueFactory(new PropertyValueFactory<>("name"));
                        DESCRIPTION.setCellValueFactory(new PropertyValueFactory<>("description"));
                        TYPE.setCellValueFactory(new PropertyValueFactory<>("type_name"));
                        VALUE.setCellValueFactory(new PropertyValueFactory<>("value"));


                        for(int i = 0; i < list.size();i ++)
                        {
                                //apps.add(list.get(i));
                                CATALOG.getItems().add(list.get(i));

                        }

                        System.out.println(list.size());
                } catch (SQLException e) {
                        throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                }
        }
        ArrayList<Beer> getFromSet(ResultSet set) throws SQLException {
        ArrayList<Beer> tmp = new ArrayList<>();
        String name, description;
        String  type_name, value;
        String id;
        set.next();
        do {
        id = set.getString("beer_id");
        name = set.getString("name");
        description =  set.getString("description");
        type_name =  set.getString("type_name");
        value =  set.getString("value");

        tmp.add(new Beer(id, name, description, type_name, value));
        }while(set.next());
        return tmp;
        }
        }