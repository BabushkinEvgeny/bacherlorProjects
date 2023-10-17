package com.example.beerfactory;


import com.example.beerfactory.Entity.*;

import javax.xml.xpath.XPathEvaluationResult;
import java.sql.*;
import java.time.LocalDateTime;

public class DataBaseHandler  extends Configs {

    Integer user_id;
    Connection dbConnection;

    public DataBaseHandler() {
        user_id = 0;
    }

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + host + ":"
                + port + "/" + name;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, user, pas);

        return dbConnection;
    }
    public ResultSet getCustomer(Customer candidate) throws SQLException, ClassNotFoundException {
        ResultSet reSet = null;

        String select = "select * from Customers where login = ? and password = ?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        try {
            prSt.setString(1, candidate.getCustomer_login());
            prSt.setString(2, candidate.getCustomer_password());
            reSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return reSet;
    }
    public ResultSet getEmployee(Worker worker) throws SQLException, ClassNotFoundException {
        ResultSet reSet = null;

        String select = "select * from Employees where login = ? and password = ? and role !=1;";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        try {
            prSt.setString(1, worker.getWorker_login());
            prSt.setString(2, worker.getWorker_password());
            reSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reSet;
    }
    public ResultSet getManager(Worker worker) throws SQLException, ClassNotFoundException {
        ResultSet reSet = null;

        String select = "select * from Employees where login = ? and password = ? and role =1;";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        try {
            prSt.setString(1, worker.getWorker_login());
            prSt.setString(2, worker.getWorker_password());
            reSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reSet;
    }
    public void regCustomer(Customer customer)
    {
        String insert = "insert into Customers(name, surname, bank, login, password)\n" +
                " VALUES(?,?,?,?,?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {
            prSt.setString(1, customer.getCustomer_name());
            prSt.setString(2, customer.getCustomer_surname());
            prSt.setString(3, "0");
            prSt.setString(4, customer.getCustomer_login());
            prSt.setString(5, customer.getCustomer_password());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setCustomerBank(Integer id, Integer money) throws SQLException, ClassNotFoundException {
        String str = "select bank from customers where user_id = " + id + ";";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(str);
        ResultSet resultSet = null;
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Integer bank = resultSet.getInt(1);
        Integer fin = bank + money;
        String update = "update customers set bank = " + fin + " where user_id = " + id +";";
        PreparedStatement preparedStatement1 = getDbConnection().prepareStatement(update);
        preparedStatement1.executeUpdate();
        Data.CUSTOMER_BANK = fin.toString();
    }

    public ResultSet getCatalog() throws SQLException, ClassNotFoundException {
        //String string = "select * from catalog";
        String string = "select cat.beer_id, cat.name, cat.description, b.type_name type_name, cat.value from catalog cat\n" +
                "join beertypes b on cat.id_type = b.id_type;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(string);
        ResultSet resultSet = null;
        resultSet = preparedStatement.executeQuery();
        return  resultSet;
    }

    public ResultSet getBottles() throws SQLException, ClassNotFoundException {
        String string = "select name from bottles;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(string);
        return preparedStatement.executeQuery();
    }

public Integer getBottleId(String name) throws SQLException, ClassNotFoundException {
    String select = "select bottle_id from bottles where name = ?";

    PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
    preparedStatement.setString(1,name);
    ResultSet resultSet = preparedStatement.executeQuery();
    resultSet.next();
    return Integer.valueOf(resultSet.getInt(1));
}

public Double getBeerPrice(Integer beer) throws SQLException, ClassNotFoundException {
    String select = "select value from catalog where beer_id = " + beer + ";";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);

    ResultSet resultSet = preparedStatement.executeQuery();
    resultSet.next();
    return resultSet.getDouble(1);
}
public void insertBeerBottle(Integer bottle, Integer beer) throws SQLException, ClassNotFoundException {
    Double price = getBeerPrice(beer);
    String insert = "replace into beerbottle (id_bottle, id_beer, total_price) value (?,?,?);";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
    preparedStatement.setString(1,bottle.toString());
    preparedStatement.setString(2, beer.toString());
    preparedStatement.setString(3, price.toString());
    preparedStatement.executeUpdate();
}

Integer getCurrentOrderId() throws SQLException, ClassNotFoundException {
    String select = "select order_id from orders where status_id = 911 and customer_id = ?;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
    preparedStatement.setString(1,Data.CUSTOMER_ID.toString());
    ResultSet resultSet = null;
    resultSet = preparedStatement.executeQuery();
    //resultSet.next();
    Integer id = 0;
    if(!resultSet.next())
    {
        return id;
    }
    else
    {
        id = Integer.valueOf(resultSet.getInt(1));
    }
    return id;
}
void createOrder(Integer customer) throws SQLException, ClassNotFoundException {
    String insert = "insert into orders(customer_id, status_id,total_cost) value(?,911,0)";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
    preparedStatement.setString(1,customer.toString());
    preparedStatement.executeUpdate();
}

Integer getBeerBottleId(Integer bottle, Integer beer) throws SQLException, ClassNotFoundException {
    String select = "select beer_bottle_id from beerbottle where id_bottle = ? and id_beer = ?;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
    preparedStatement.setString(1,bottle.toString());
    preparedStatement.setString(2,beer.toString());
    ResultSet resultSet = null;
    resultSet = preparedStatement.executeQuery();
    resultSet.next();
    return Integer.valueOf(resultSet.getInt(1));
}



void insertItemIntoCompound(Integer bottle, Integer beer, Integer amount) throws SQLException, ClassNotFoundException {
    String insert = "insert into ordercompound(id_beer_bottle, id_order, amount) value(?,?,?)";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
    preparedStatement.setString(1,getBeerBottleId(bottle,beer).toString());
    preparedStatement.setString(2,getCurrentOrderId().toString());
    preparedStatement.setString(3,amount.toString());
    preparedStatement.executeUpdate();
}

ResultSet getOrderCompound(Integer id) throws SQLException, ClassNotFoundException {
    String select = " select * from (select bb.beer_bottle_id, cat.name, oc.amount, bb.total_price, oc.id_order  from ordercompound oc\n" +
            "join  beerbottle bb on oc.id_beer_bottle = bb.beer_bottle_id\n" +
            "join catalog cat on cat.beer_id = bb.id_beer) a\n" +
            "where a.id_order = ?;";

    PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
    preparedStatement.setString(1,id.toString());
    ResultSet resultSet = preparedStatement.executeQuery();
    return resultSet;

}

void sendOrder(Integer order) throws SQLException, ClassNotFoundException {
    String update = "update orders set status_id = 99 where order_id = ?;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
    preparedStatement.setString(1,order.toString());
    preparedStatement.executeUpdate();
}

ResultSet getOrders(Integer customer) throws SQLException, ClassNotFoundException {
    String select = "select * from orders where order_id = ?;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
    preparedStatement.setString(1,customer.toString());
    ResultSet resultSet = preparedStatement.executeQuery();
    return  resultSet;
}
void deleteItem(Integer order, Integer item) throws SQLException, ClassNotFoundException {
    String delete = "delete from ordercompound where id_order = ? and id_beer_bottle = ?;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(delete);
    preparedStatement.setString(1,order.toString());
    preparedStatement.setString(2,item.toString());
    preparedStatement.executeUpdate();
}

ResultSet getOrdersFromCustomer(Integer customer) throws SQLException, ClassNotFoundException {
    String select = " select  o.order_id id, o.total_cost cost, stat.status from orders o\n" +
            "join statutes stat on o.status_id = stat.status_id\n" +
            "where customer_id = ?;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
    preparedStatement.setString(1,customer.toString());
    ResultSet resultSet = preparedStatement.executeQuery();
    return resultSet;
}

void pay(Integer order) throws SQLException, ClassNotFoundException {
    String update = "update orders set status_id = 5 where order_id = ? and status_id = 99;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
    preparedStatement.setString(1,order.toString());
    preparedStatement.executeUpdate();
}

void addOrderToLine(Integer order, Integer worker) throws SQLException, ClassNotFoundException {
    String insert = "insert into line(id_order, id_employee,start, id_status) values (?, ? ,? ,3);";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
    preparedStatement.setString(1,order.toString());
    preparedStatement.setString(2,worker.toString());
    preparedStatement.setString(3, LocalDateTime.now().toString());
    preparedStatement.executeUpdate();
}

void countTotalCost(Integer order, Integer cost) throws SQLException, ClassNotFoundException {
    String update = "update orders set total_cost = ? where order_id = ?;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
    preparedStatement.setString(1,cost.toString());
    preparedStatement.setString(2,order.toString());
    preparedStatement.executeUpdate();
}

void takeMoney(Integer customer, Integer money) throws SQLException, ClassNotFoundException {
    String update = "update customers set bank = bank - ? where user_id = ?;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
    preparedStatement.setString(1,money.toString());
    preparedStatement.setString(2,customer.toString());
    preparedStatement.executeUpdate();
}

ResultSet getWorkersList() throws SQLException, ClassNotFoundException {
    String select = "select user_id id, surname, salary from employees;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
    ResultSet resultSet = preparedStatement.executeQuery();
    return resultSet;
}

void deleteWorker(Integer id) throws SQLException, ClassNotFoundException {
    String update = "update line set id_employee = 12 where id_employee = ?";
    PreparedStatement preparedStatement1 = getDbConnection().prepareStatement(update);
    preparedStatement1.setString(1,id.toString());
    preparedStatement1.executeUpdate();
    String delete = "delete from employees where user_id = ?;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(delete);
    preparedStatement.setString(1,id.toString());
    preparedStatement.executeUpdate();
}

void changeSalary(Integer id, Integer salary) throws SQLException, ClassNotFoundException {
    String update = "update employees set salary = ? where user_id = ?;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
    preparedStatement.setString(1,salary.toString());
    preparedStatement.setString(2,id.toString());
    preparedStatement.executeUpdate();
}

ResultSet getLine() throws SQLException, ClassNotFoundException {
    String select = "select l.id_order id, o.customer_id customer, l.id_employee worker, ls.status from line l join linestatutes ls on\n" +
            "l.id_status = ls.id_status\n" +
            "join orders o on l.id_order = o.order_id;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
   ResultSet resultSet = preparedStatement.executeQuery();
   return resultSet;
}

void changeOrderWorker(Integer order, Integer worker) throws SQLException, ClassNotFoundException {
    String update = "update line set id_employee = ? where id_order = ?;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
    preparedStatement.setString(1,worker.toString());
    preparedStatement.setString(2,order.toString());
    preparedStatement.executeUpdate();
}

void deleteBeer(Integer item) throws SQLException, ClassNotFoundException {
    String delete1 = "delete from catalog where beer_id = ?;";
    PreparedStatement preparedStatement1 = getDbConnection().prepareStatement(delete1);
    preparedStatement1.setString(1,item.toString());
    preparedStatement1.executeUpdate();
}

void addBeer(String name, String description, Integer type, Integer value) throws SQLException, ClassNotFoundException {
    String insert = "insert into catalog(name,description, id_type, value) value(?,?,?,?);";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
    preparedStatement.setString(1,name);
    preparedStatement.setString(2,description);
    preparedStatement.setString(3,type.toString());
    preparedStatement.setString(4,value.toString());
    preparedStatement.executeUpdate();
}

Integer getBeerType(String type) throws SQLException, ClassNotFoundException {
    String select = "select id_type from beertypes where type_name = ?;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
    preparedStatement.setString(1,type);
    ResultSet resultSet = preparedStatement.executeQuery();
    resultSet.next();
    return resultSet.getInt(1);
}

ResultSet getBeerTypes() throws SQLException, ClassNotFoundException {
    String select = "select type_name from beertypes;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
    ResultSet resultSet = preparedStatement.executeQuery();
    return resultSet;
}


ResultSet getComponents() throws SQLException, ClassNotFoundException {
    String select = "select name from ingredients;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
    ResultSet resultSet = preparedStatement.executeQuery();
    return resultSet;
}

Integer getComponentId(String component) throws SQLException, ClassNotFoundException {
    String select = "select ingredient_id from ingredients where name = ?;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
    preparedStatement.setString(1,component);
    ResultSet resultSet = preparedStatement.executeQuery();
    resultSet.next();
    return resultSet.getInt(1);
}

void insertComponents(Integer beer, Integer component) throws SQLException, ClassNotFoundException {
    String insert = "insert into beercompound(id_ingredient, id_beer) value (?, ?);";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
    preparedStatement.setString(1,component.toString());
    preparedStatement.setString(2, beer.toString());
    preparedStatement.executeUpdate();
}

Integer getBeerId(String beer) throws SQLException, ClassNotFoundException {
    String select = "select beer_id from catalog where name = ?;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
    preparedStatement.setString(1,beer);
    ResultSet resultSet = preparedStatement.executeQuery();
    resultSet.next();
    return resultSet.getInt(1);
}

void insertItems(Integer bottle, Integer beer, Integer cost) throws SQLException, ClassNotFoundException {
    String insert = "insert into beerbottle(id_bottle, id_beer, total_price) value(?,?,?);";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
    preparedStatement.setString(1,bottle.toString());
    preparedStatement.setString(2, beer.toString());
    preparedStatement.setString(3,cost.toString());
    preparedStatement.executeUpdate();
}

ResultSet getBottlesId() throws SQLException, ClassNotFoundException {
    String select = "select bottle_id from bottles;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
    ResultSet resultSet = preparedStatement.executeQuery();
    return resultSet;
}

ResultSet getComponentsFromStorage() throws SQLException, ClassNotFoundException {
    String select = "select ingr.name, stor.amount from ingredientsstorage stor join ingredients ingr on\n" +
            "stor.ingredient_id = ingr.ingredient_id;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
    ResultSet resultSet = preparedStatement.executeQuery();
    return resultSet;
}


void fillIngredientsStorage(Integer amount,Integer component) throws SQLException, ClassNotFoundException {
    String update = "update ingredientsStorage set amount = amount + ? where ingredient_id = ?;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
    preparedStatement.setString(1,amount.toString());
    preparedStatement.setString(2,component.toString());
    preparedStatement.executeUpdate();
}

ResultSet getBottlesFromStorage() throws SQLException, ClassNotFoundException {
    String select = "select b.name, bs.amount from bottlesStorage bs join bottles b on\n" +
            "bs.bottle_id = b.bottle_id;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
    ResultSet resultSet = preparedStatement.executeQuery();
    return resultSet;
}

void fillBottlesStorage(Integer amount, Integer bottle) throws SQLException, ClassNotFoundException {
    String update = "update bottlesstorage set amount = amount + ? where bottle_id =?;";
    PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
    preparedStatement.setString(1,amount.toString());
    preparedStatement.setString(2,bottle.toString());
    preparedStatement.executeUpdate();
}

    ResultSet getLine(Integer worker) throws SQLException, ClassNotFoundException {
        String select = "select l.id_order id, ls.status, l.start, l.finish from line l join linestatutes ls on\n" +
                "l.id_status = ls.id_status\n" +
                "join orders o on l.id_order = o.order_id where id_employee = ?;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        preparedStatement.setString(1,worker.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    ResultSet getOrderSum(Integer order) throws SQLException, ClassNotFoundException {
        String select = " select * from (select * from (select sum(a.amount) sum, beer_bottle_id, beer_id, id_bottle from \n" +
                "(select * from (select bb.id_bottle, bb.beer_bottle_id, cat.name, oc.amount, bb.total_price, oc.id_order, cat.beer_id  from ordercompound oc\n" +
                "join  beerbottle bb on oc.id_beer_bottle = bb.beer_bottle_id\n" +
                "join catalog cat on cat.beer_id = bb.id_beer) a\n" +
                "where a.id_order = ?) a\n" +
                "group by beer_bottle_id) s\n" +
                "join beercompound bc on bc.id_beer = s.beer_id) f;";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        preparedStatement.setString(1,order.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    boolean enoughBottles(Integer amount, Integer bottle) throws SQLException, ClassNotFoundException {
        String select = "select * from bottlesstorage where bottle_id = ?";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        preparedStatement.setString(1,bottle.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("amount") >= amount;
    }

    boolean enoughIngredients(Integer amount, Integer ingredient) throws SQLException, ClassNotFoundException {
        String select = "select * from ingredientsstorage where ingredient_id = ?;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        preparedStatement.setString(1,ingredient.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("amount") >= amount;
    }
    void takeBottles(Integer bottle, Integer amount) throws SQLException, ClassNotFoundException {
        String update = "update bottlesstorage set amount = amount - ? where bottle_id = ?;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
        preparedStatement.setString(1,amount.toString());
        preparedStatement.setString(2,bottle.toString());
        preparedStatement.executeUpdate();
    }



    void takeIngredients(Integer ingredient, Integer amount) throws SQLException, ClassNotFoundException {
        String update = "update ingredientsstorage set amount = amount - ? where ingredient_id = ?;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
        preparedStatement.setString(1,amount.toString());
        preparedStatement.setString(2,ingredient.toString());
        preparedStatement.executeUpdate();
    }

    Integer countDifferenceBottles(Integer amount, Integer bottle) throws SQLException, ClassNotFoundException {
        String select = "select amount from bottlesstorage where bottle_id = ?;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        preparedStatement.setString(1,bottle.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) - amount;
    }

    Integer countDifferenceIngredients(Integer amount, Integer ingredient) throws SQLException, ClassNotFoundException {
        String select = "select amount from ingredientsstorage where ingredient_id = ?;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        preparedStatement.setString(1,ingredient.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) - amount;
    }

    void changeLineOrderStatus(Integer order) throws SQLException, ClassNotFoundException {
        String update1 = "update Orders set status_id = 7 where order_id = ?;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(update1);
        preparedStatement.setString(1,order.toString());
        String update2 = "update line set id_status = 4 where id_order = ?;";
        PreparedStatement preparedStatement1 = getDbConnection().prepareStatement(update2);
        preparedStatement1.setString(1,order.toString());
        preparedStatement1.executeUpdate();
        preparedStatement.executeUpdate();
    }

    String getBottleName(Integer bottle) throws SQLException, ClassNotFoundException {
        String select = "select name from bottles where bottle_id = ?;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        preparedStatement.setString(1,bottle.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getString(1);
    }

    void setFinish(String finish, Integer id) throws SQLException, ClassNotFoundException {
        String update = "update line set finish = ? where id_order = ?;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
        preparedStatement.setString(1,finish);
        preparedStatement.setString(2,id.toString());
        preparedStatement.executeUpdate();
    }

    Integer getBank(Integer user) throws SQLException, ClassNotFoundException {
        String select = "select bank from customers where user_id = ?;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        preparedStatement.setString(1,user.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

}
