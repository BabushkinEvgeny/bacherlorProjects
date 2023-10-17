package com.example.beerfactory;

import com.example.beerfactory.Entity.MangeOrders;
import com.example.beerfactory.Entity.Workers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManagerOrdersListController {
    ObservableList<MangeOrders> LIST = FXCollections.observableArrayList();

    @FXML
    private Button APPLY;

    @FXML
    private TextField APPLY_FIELD;

    @FXML
    private Button BACK;

    @FXML
    private TableColumn<MangeOrders, String> CUSTOMER;

    @FXML
    private TableColumn<MangeOrders, String> ID;

    @FXML
    private Button RELOAD;

    @FXML
    private TableColumn<MangeOrders, String> STATUS;

    @FXML
    private TableView<MangeOrders> TABLE;

    @FXML
    private TableColumn<MangeOrders, String> WORKER;

    @FXML
    void initialize() {
        RELOAD.setOnAction(event ->{
            try {

                ResultSet set = null;
                ArrayList<MangeOrders> list = new ArrayList<>();
                DataBaseHandler dataBaseHandler = new DataBaseHandler();
                set = dataBaseHandler.getLine();


                list = getFromSet(set);
                ID.setCellValueFactory(new PropertyValueFactory<>("id"));
                CUSTOMER.setCellValueFactory(new PropertyValueFactory<>("customer"));
                WORKER.setCellValueFactory(new PropertyValueFactory<>("worker"));
                STATUS.setCellValueFactory(new PropertyValueFactory<>("status"));


                for(int i = 0; i < list.size();i ++)
                {
                    //apps.add(list.get(i));
                    TABLE.getItems().add(list.get(i));

                }
                System.out.println(list.size());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        BACK.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("manager_menu.fxml", BACK);
        });

        APPLY.setOnAction(event -> {
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {

                dataBaseHandler.changeOrderWorker(Integer.valueOf(TABLE.getSelectionModel().getSelectedItem().getId()), Integer.valueOf(APPLY_FIELD.getText().toString()));
                TABLE.getItems().clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }


    ArrayList<MangeOrders> getFromSet(ResultSet set) throws SQLException {
        ArrayList<MangeOrders> tmp = new ArrayList<>();
        String id, customer,worker,status;
        set.next();
        do {
            id = set.getString("id");
            customer = set.getString("customer");
            worker =  set.getString("worker");
            status =  set.getString("status");

            tmp.add(new MangeOrders(id,customer, worker, status));
        }while(set.next());
        return tmp;
    }

}
