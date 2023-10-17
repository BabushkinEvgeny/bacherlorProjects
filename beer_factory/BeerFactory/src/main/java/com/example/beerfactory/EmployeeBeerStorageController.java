package com.example.beerfactory;

import com.example.beerfactory.Entity.Bottles;
import com.example.beerfactory.Entity.Ingredients;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeBeerStorageController implements Initializable {

    ObservableList<Ingredients> bottles = FXCollections.observableArrayList();


    @FXML
    private Button ADD;

    @FXML
    private TextField ADD_AMOUNT;

    @FXML
    private TableColumn<Bottles, String> AMOUNT;

    @FXML
    private Button BACK;

    @FXML
    private TableView<Bottles> BOTTLES;

    @FXML
    private TableColumn<Bottles, String> NAME;

    @FXML
    void initialize() {
        BACK.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("employee_menu.fxml", BACK);
        });

        ADD.setOnAction(event ->{
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                dataBaseHandler.fillBottlesStorage(Integer.valueOf(ADD_AMOUNT.getText().toString()), dataBaseHandler.getBottleId(BOTTLES.getSelectionModel().getSelectedItem().getName().toString()));
                 reloadPage();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        reloadPage();
    }

    void reloadPage()
    {
        BOTTLES.getItems().clear();
        try {

            ResultSet set = null;
            ArrayList<Bottles> list = new ArrayList<>();
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            set = dataBaseHandler.getBottlesFromStorage();


            list = getFromSet(set);

            NAME.setCellValueFactory(new PropertyValueFactory<>("name"));
            AMOUNT.setCellValueFactory(new PropertyValueFactory<>("amount"));



            for(int i = 0; i < list.size();i ++)
            {
                //apps.add(list.get(i));
                BOTTLES.getItems().add(list.get(i));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    ArrayList<Bottles> getFromSet(ResultSet set) throws SQLException {
        ArrayList<Bottles> tmp = new ArrayList<>();
        String name, amount;
        set.next();
        do {

            name = set.getString("name");
            amount = set.getString("amount");

            tmp.add(new Bottles(name, amount));
        }while(set.next());
        return tmp;
    }

}
