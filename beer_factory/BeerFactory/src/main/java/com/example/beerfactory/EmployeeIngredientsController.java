package com.example.beerfactory;

import com.example.beerfactory.Entity.Beer;
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

public class EmployeeIngredientsController implements Initializable {

    ObservableList<Ingredients> ingredients = FXCollections.observableArrayList();


    @FXML
    private Button ADD;

    @FXML
    private TextField ADD_AMOUNT;

    @FXML
    private TableColumn<Ingredients, String> AMOUNT;

    @FXML
    private Button BACK;

    @FXML
    private TableView<Ingredients> INGREDIENTS;

    @FXML
    private TableColumn<Ingredients, String> NAME;

    @FXML
    void initialize() {
        BACK.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("employee_menu.fxml", BACK);
        });
        ADD.setOnAction(event ->{
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {

                dataBaseHandler.fillIngredientsStorage(Integer.valueOf(ADD_AMOUNT.getText()),dataBaseHandler.getComponentId(INGREDIENTS.getSelectionModel().getSelectedItem().getName().toString()));
                reloadPage();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reloadPage();
    }

    void reloadPage()
    {
        INGREDIENTS.getItems().clear();
        try {

            ResultSet set = null;
            ArrayList<Ingredients> list = new ArrayList<>();
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            set = dataBaseHandler.getComponentsFromStorage();


            list = getFromSet(set);

            NAME.setCellValueFactory(new PropertyValueFactory<>("name"));
            AMOUNT.setCellValueFactory(new PropertyValueFactory<>("amount"));



            for(int i = 0; i < list.size();i ++)
            {
                //apps.add(list.get(i));
                INGREDIENTS.getItems().add(list.get(i));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    ArrayList<Ingredients> getFromSet(ResultSet set) throws SQLException {
        ArrayList<Ingredients> tmp = new ArrayList<>();
        String name, amount;
        set.next();
        do {

            name = set.getString("name");
            amount = set.getString("amount");

            tmp.add(new Ingredients(name, amount));
        }while(set.next());
        return tmp;
    }


}
