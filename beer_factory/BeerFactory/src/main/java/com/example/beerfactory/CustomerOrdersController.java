package com.example.beerfactory;

import com.example.beerfactory.Entity.Beer;
import com.example.beerfactory.Entity.Orders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.SimpleTimeZone;

public class CustomerOrdersController implements Initializable {

    ObservableList<Orders> orders = FXCollections.observableArrayList();

    @FXML
    private Button BACK;

    @FXML
    private TableColumn<Orders, String> COST;

    @FXML
    private TableColumn<Orders, String> ID;

    @FXML
    private TableView<Orders> ORDERS;

    @FXML
    private Button PAY;

    @FXML
    private Button RELOAD;

    @FXML
    private TableColumn<Orders, String> STATUS;

    @FXML
    void initialize() {
        BACK.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("customer_menu.fxml", BACK);

        });

        PAY.setOnAction(event -> {
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                String tmp = ORDERS.getSelectionModel().getSelectedItem().getId().toString();
                if(dataBaseHandler.getBank(Data.CUSTOMER_ID) >= Integer.valueOf(tmp))
                {
                    dataBaseHandler.pay(Integer.valueOf(tmp));
                    dataBaseHandler.takeMoney(Data.CUSTOMER_ID, Integer.valueOf(ORDERS.getSelectionModel().getSelectedItem().getCost().toString()));
                    dataBaseHandler.addOrderToLine(Integer.valueOf(ORDERS.getSelectionModel().getSelectedItem().getId()), 12);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        RELOAD.setOnAction(event -> {
            ORDERS.getItems().clear();
            reloadPage();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResultSet check = null;
        DataBaseHandler dataBaseHandler1 = new DataBaseHandler();
        try {
            check = dataBaseHandler1.getOrdersFromCustomer(Data.CUSTOMER_ID);
            if(check.next())
            {
                reloadPage();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    void reloadPage()
    {
        try {

            ResultSet set = null;
            ArrayList<Orders> list = new ArrayList<>();
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            set = dataBaseHandler.getOrdersFromCustomer(Data.CUSTOMER_ID);


            list = getFromSet(set);
            ID.setCellValueFactory(new PropertyValueFactory<>("id"));
            COST.setCellValueFactory(new PropertyValueFactory<>("cost"));
            STATUS.setCellValueFactory(new PropertyValueFactory<>("status"));



            for(int i = 0; i < list.size();i ++)
            {
                //apps.add(list.get(i));
                ORDERS.getItems().add(list.get(i));

            }



            System.out.println(list.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    ArrayList<Orders> getFromSet(ResultSet set) throws SQLException {
        ArrayList<Orders> tmp = new ArrayList<>();
        String id, cost,status;
        set.next();
        do {
            id = set.getString("id");
            cost = set.getString("cost");
            status =  set.getString("status");


            tmp.add(new Orders(id, cost, status));
        }while(set.next());
        return tmp;
    }


}
