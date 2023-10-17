package com.example.beerfactory;

import com.example.beerfactory.Entity.Beer;
import com.example.beerfactory.Entity.Line;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeOrdersController implements Initializable {

    @FXML
    private Button BACK;

    @FXML
    private Button COMMIT;

    @FXML
    private TableColumn<Line, String> FINISH;

    @FXML
    private TableColumn<Line, String> ID;

    @FXML
    private TextArea NOT_ENOUGH;

    @FXML
    private TableView<Line> ORDERS;

    @FXML
    private TableColumn<Line, String> START;

    @FXML
    private TableColumn<Line, String> STATUS;

    @FXML
    void initialize() {
        BACK.setOnAction(event ->{
            Handler handler = new Handler();
            handler.openNewScene("employee_menu.fxml", BACK);
        });

        COMMIT.setOnAction(event ->{

            try {

                DataBaseHandler dataBaseHandler = new DataBaseHandler();
                String error = "";
                ResultSet resultSet = dataBaseHandler.getOrderSum(Integer.valueOf(ORDERS.getSelectionModel().getSelectedItem().getId()));
                while (resultSet.next()) {
                    Integer amount = resultSet.getInt("sum");
                    Integer ingredient = resultSet.getInt("id_ingredient");
                    Integer bottle = resultSet.getInt("id_bottle");

                    System.out.println("Enough bottles " + dataBaseHandler.enoughBottles(amount, bottle));
                    System.out.println("Diff bottles " + dataBaseHandler.countDifferenceBottles(amount,bottle));
                    System.out.println("enough ingredients " + dataBaseHandler.enoughIngredients(amount,ingredient));
                    System.out.println("Diff ingr " + dataBaseHandler.countDifferenceIngredients(amount,ingredient));
                    if(!ORDERS.getSelectionModel().getSelectedItem().getStatus().equals("Done"))
                    {
                        NOT_ENOUGH.setText("");
                        if(dataBaseHandler.countDifferenceBottles(amount,bottle) >= 0 && dataBaseHandler.countDifferenceIngredients(amount,ingredient) >= 0 )
                        {
                            dataBaseHandler.takeBottles(bottle,amount);
                            dataBaseHandler.takeIngredients(ingredient,amount);
                            dataBaseHandler.changeLineOrderStatus(Integer.valueOf(ORDERS.getSelectionModel().getSelectedItem().getId().toString()));
                            dataBaseHandler.setFinish(LocalDateTime.now().toString(),Integer.valueOf(ORDERS.getSelectionModel().getSelectedItem().getId()));
                            NOT_ENOUGH.setText("Успешно!");

                        }
                        else {
                            error+="\n";
                            error+="Не хватает: " + dataBaseHandler.getBottleName(bottle) + " " + Math.abs(dataBaseHandler.countDifferenceBottles(amount,bottle));
                            NOT_ENOUGH.setText(error);
                        }
                    }
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
        reloadPage();
    }

    void reloadPage()
    {
        try {
            ORDERS.getItems().clear();
            ResultSet set = null;
            ArrayList<Line> list = new ArrayList<>();
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            set = dataBaseHandler.getLine(Data.EMPLOYEE_ID);


            list = getFromSet(set);
            ID.setCellValueFactory(new PropertyValueFactory<>("id"));
            STATUS.setCellValueFactory(new PropertyValueFactory<>("status"));
            START.setCellValueFactory(new PropertyValueFactory<>("start"));
            FINISH.setCellValueFactory(new PropertyValueFactory<>("finish"));



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

    ArrayList<Line> getFromSet(ResultSet set) throws SQLException {
        ArrayList<Line> tmp = new ArrayList<>();
        String name, description;
        String  type_name, value;
        String id;
        set.next();
        do {
            id = set.getString("id");
            name = set.getString("status");
            description =  set.getString("start");
            type_name =  set.getString("finish");


            tmp.add(new Line(id, name, description, type_name));
        }while(set.next());
        return tmp;
    }
}
