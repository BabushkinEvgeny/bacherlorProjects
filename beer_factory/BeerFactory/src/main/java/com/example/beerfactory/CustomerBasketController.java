package com.example.beerfactory;

import com.example.beerfactory.Entity.BasketBeer;
import com.example.beerfactory.Entity.Beer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerBasketController implements Initializable {

    ObservableList<BasketBeer> beerTable = FXCollections.observableArrayList();

    @FXML
    private TableColumn<BasketBeer, Integer> AMOUNT;

    @FXML
    private Button APPLY;

    @FXML
    private Button BACK;

    @FXML
    private TableView<BasketBeer> BASKET;

    @FXML
    private TableColumn<BasketBeer, Integer> COST;

    @FXML
    private Button DELETE;

    @FXML
    private TableColumn<BasketBeer, Integer> ID;

    @FXML
    private TableColumn<BasketBeer, String> NAME;

    @FXML
    private Label TOTAL_COST;

    @FXML
    void initialize() {
        BACK.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("customer_menu.fxml", BACK);
        });

        APPLY.setOnAction(event ->{
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                dataBaseHandler.countTotalCost(dataBaseHandler.getCurrentOrderId(),Integer.valueOf(TOTAL_COST.getText()));
                dataBaseHandler.sendOrder(dataBaseHandler.getCurrentOrderId());

                Integer tmp = Integer.valueOf(Data.CUSTOMER_BANK);
                dataBaseHandler.setCustomerBank(Data.CUSTOMER_ID,tmp -  Integer.valueOf(TOTAL_COST.getText()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        DELETE.setOnAction(event ->{
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                dataBaseHandler.deleteItem(dataBaseHandler.getCurrentOrderId(), Integer.valueOf(BASKET.getSelectionModel().getSelectedItem().getBeer_bottle_id()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ResultSet set = null;
            ArrayList<BasketBeer> list = new ArrayList<>();
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            set = dataBaseHandler.getOrderCompound(dataBaseHandler.getCurrentOrderId());

            ResultSet check = null;
            check = dataBaseHandler.getOrderCompound(dataBaseHandler.getCurrentOrderId());
            if(check.next())
            {
                list = getFromSet(set);
                ID.setCellValueFactory(new PropertyValueFactory<>("beer_bottle_id"));
                NAME.setCellValueFactory(new PropertyValueFactory<>("name"));
                AMOUNT.setCellValueFactory(new PropertyValueFactory<>("amount"));
                COST.setCellValueFactory(new PropertyValueFactory<>("total_price"));
                Integer tmp = 0;


                for(int i = 0; i < list.size();i ++)
                {

                    //apps.add(list.get(i));
                    BASKET.getItems().add(list.get(i));

                }

                for (int i = 0; i < list.size(); i++)
                {
                    tmp += Integer.valueOf(BASKET.getItems().get(i).getTotal_price()) * Integer.valueOf(BASKET.getItems().get(i).getAmount());

                }
                TOTAL_COST.setText(tmp.toString());

            }

            System.out.println(list.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    ArrayList<BasketBeer> getFromSet(ResultSet set) throws SQLException {
        ArrayList<BasketBeer> tmp = new ArrayList<>();
        String name, amount;
        String  total_price, value;
        String id;
        set.next();
        do {
            id = set.getString("beer_bottle_id");
            name = set.getString("name");
            amount =  set.getString("amount");
            total_price =  set.getString("total_price");


            tmp.add(new BasketBeer(id, name, amount, total_price));
        }while(set.next());
        return tmp;
    }
}
