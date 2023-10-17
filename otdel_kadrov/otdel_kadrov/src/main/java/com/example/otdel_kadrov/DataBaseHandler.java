package com.example.otdel_kadrov;
import com.example.otdel_kadrov.entity.*;

import java.sql.*;

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

    public void regCandidate(Candidate candidate) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.CANDIDATES_TABLE + "(" +
                Const.CANDIDATES_NAME + "," + Const.CANDIDATES_SURNAME + "," + Const.CANDIDATES_LOGIN + "," + Const.CANDIDATES_PASSWORD +
                "," + Const.CANDIDATES_SNILS + ")" +
                "VALUES(?,?,?,?,?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {
            prSt.setString(1, candidate.getName());
            prSt.setString(2, candidate.getSurname());
            prSt.setString(3, candidate.getLogin());
            prSt.setString(4, candidate.getPassword());
            prSt.setString(5, candidate.getSnils());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getCandidate(Candidate candidate) throws SQLException, ClassNotFoundException {
        ResultSet reSet = null;

        String select = "SELECT * FROM " + Const.CANDIDATES_TABLE +
                " WHERE " + Const.CANDIDATES_LOGIN + "=? AND " + Const.CANDIDATES_PASSWORD + "=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        try {
            prSt.setString(1, candidate.getLogin());
            prSt.setString(2, candidate.getPassword());
            reSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reSet;
    }

    public ResultSet getEmployee(Employee employee) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.EMPLOYEE_TABLE +
                " WHERE " + Const.EMPLOYEE_LOGIN + "=? AND " + Const.EMPLOYEE_PASSWORD + "=?" + " AND " + Const.EMPLOYEE_ROLE + "!=1";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        try {
            prSt.setString(1, employee.getLogin());
            prSt.setString(2, employee.getPassword());
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;

    }

    public ResultSet getManager(Employee employee) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.EMPLOYEE_TABLE +
                " WHERE " + Const.EMPLOYEE_LOGIN + "=? AND " + Const.EMPLOYEE_PASSWORD + "=?" + " AND " + Const.EMPLOYEE_ROLE + "=1";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        try {
            prSt.setString(1, employee.getLogin());
            prSt.setString(2, employee.getPassword());
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet getCandidateById(Integer id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.CANDIDATES_TABLE +
                " WHERE " + Const.CANDIDATES_ID + "=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        try {
            prSt.setString(1, id.toString());
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet getCandidateApplication(Candidate candidate) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;

        String select = "select ff.* from (select app.id_candidate, app.idJobApplication, pos.name, stat.status from jobapplication app\n" +
                "join positions pos on \n" +
                "pos.idposition = app.id_position\n" +
                "join statutes stat on \n" +
                "stat.idStatutes = app.id_status \n" +
                "where id_candidate = ?) ff;";

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        try {
            prSt.setString(1, candidate.getId().toString());
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public boolean deleteJobApplication(Integer id) throws SQLException, ClassNotFoundException {
        if (id != Data.CANDIDATE_ID) {
            return false;
        }
        String delete = "delete from jobapplication where idjobapplication = ?";
        PreparedStatement prSt = getDbConnection().prepareStatement(delete);
        prSt.setString(1, id.toString());
        prSt.executeQuery();
        return true;
    }

    public ResultSet getVacanciesRequirements() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;

        String select = "select pos.name, req.requirement from positionrequirement posreq\n" +
                "join otdel_kadrov.requirements req on\n" +
                "req.idRequirement = id_requirement\n" +
                "left join otdel_kadrov.positions pos\n" +
                "on pos.idposition = posreq.id_position\n" +
                "order by name;";

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        try {

            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet getPositions() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;

        String select = "select name from positions;";

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        try {

            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet getRequirementsForOnePosition(String name) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        String select = "select requirement from (\n" +
                "select pos.name, req.requirement from positionrequirement posreq\n" +
                "join otdel_kadrov.requirements req on\n" +
                "req.idRequirement = id_requirement\n" +
                "left join otdel_kadrov.positions pos\n" +
                "on pos.idposition = posreq.id_position\n" +
                "order by name) fin\n" +
                "where fin.name = ?;";

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        try {
            prSt.setString(1, name);
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;

    }

    Integer getAmountOfVacancies(String position) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;

        String select = "select vacancies_amount from positions where name = ?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        try {
            prSt.setString(1, position);
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resultSet.next();
        return resultSet.getInt("vacancies_amount");

    }

    ResultSet getRequirements() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;

        String select = "select requirement from requirements";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        try {
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resultSet.next();
        return resultSet;
    }

    boolean makeJobApplication(Integer pos, String descr) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        String insert = "insert into jobapplication(id_candidate,id_status,id_position,id_examiner,description)" +
                "values(?,?,?,?,?);";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        try {
            prSt.setString(1, Data.CANDIDATE_ID.toString());
            prSt.setString(2, "531");
            prSt.setString(3, pos.toString());
            prSt.setString(4, "8");
            prSt.setString(5, descr);
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    Integer getPositionIdByName(String name) throws SQLException, ClassNotFoundException {
        String insert = "select idPosition from positions where name = \"" + name + "\";";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        ResultSet resultSet = null;
        resultSet = prSt.executeQuery();
        resultSet.next();
        return resultSet.getInt("idPosition");
    }

    ResultSet getSalary(Integer id) throws SQLException, ClassNotFoundException {
        String insert = "select salary from employees where idemployees = " + id + ";";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        ResultSet resultSet = null;
        resultSet = prSt.executeQuery();
        return resultSet;
    }

    Integer getEmployeeId(String NAME) throws SQLException, ClassNotFoundException {
        String select = "select e.idemployees from employees e where e.name = \"" + NAME + "\"";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        //prSt.setString(1,NAME);
        ResultSet resultSet = prSt.executeQuery();
        resultSet.next();
        Integer out = resultSet.getInt("idemployees");
        return out;
    }

    ResultSet getJobApplication() throws SQLException, ClassNotFoundException {
        String select = "select idJobapplication idapplication, id_examiner, description, stat.status from jobapplication " +
                "join statutes stat on " +
                "id_status = stat.idstatutes;";

        ResultSet resultSet = null;
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        try {

            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resultSet.next();
        return resultSet;
    }

    ResultSet getJobApplicationEmployee(Integer id) throws SQLException, ClassNotFoundException {
        String select = "select f.description, f.status, f.idjobapplication id from (select * from jobapplication join statutes stat on stat.idstatutes = id_status) f\n" +
                "where id_examiner =" + id + ";";
        ResultSet resultSet = null;
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        try {

            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resultSet.next();
        return resultSet;
    }

    ResultSet getRegularApplication() throws SQLException, ClassNotFoundException {
        String select = "select idapplication, id_examiner, desription description,stat.status from regularapplication " +
                "join statutes stat on " +
                "id_status = stat.idstatutes;";

        ResultSet resultSet = null;
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        try {

            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resultSet.next();
        return resultSet;
    }

    ResultSet getRegularApplicationemployee(Integer id) throws SQLException, ClassNotFoundException {
        String select = "select idapplication id, f.status status, desription description from(\n" +
                "select * from regularApplication join\n" +
                "statutes stat on\n" +
                "stat.idstatutes = id_status) f where f.id_examiner =" + id + ";";

        ResultSet resultSet = null;
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        try {

            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public void changeExaminer(Integer id_app, Integer id_examiner) throws SQLException, ClassNotFoundException {
        String check = "select * from employees where idemployees = ?";
        PreparedStatement preparedStatement1 = getDbConnection().prepareStatement(check);
        preparedStatement1.setString(1,id_examiner.toString());
        ResultSet resultSet = preparedStatement1.executeQuery();
        //System.out.println(resultSet.getString("idEmployees"));
        if (resultSet.next())
        {
            String update = "UPDATE jobapplication SET id_examiner= " + id_examiner + " WHERE idjobapplication = " +
                    id_app + ";";
            String update2 = "UPDATE regularapplication SET id_examiner= " + id_examiner + " WHERE idapplication = " +
                    id_app + ";";
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.executeUpdate();
            prSt = getDbConnection().prepareStatement(update2);
            prSt.executeUpdate();

            String change = "update jobapplication set id_status = 26 where idjobapplication = " + id_app + ";";
            String change2 = "update Regularapplication set id_status = 26 where idapplication = " + id_app + ";";
            prSt = getDbConnection().prepareStatement(update2);
            prSt.executeUpdate();
            prSt = getDbConnection().prepareStatement(update2);
            prSt.executeUpdate();
        }
        else {
            System.out.println("bad");
        }

    }

    public String getPassword(Integer id) throws SQLException, ClassNotFoundException {

        String select = "select password from employees where idemployees = ?;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        ResultSet resultSet = null;
        preparedStatement.setString(1,id.toString());
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        String pas = resultSet.getString("password");
        System.out.println("old password " + pas);
        return pas;
    }
    public void changePassword(String password, Integer id) throws SQLException, ClassNotFoundException {
        System.out.println("new pas" + password);
        String update = "update employees set password = ? where idemployees = ?;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
        preparedStatement.setString(1,password);
        preparedStatement.setString(2,id.toString());
        preparedStatement.executeUpdate();
    }

    public ResultSet getEmployees() throws SQLException, ClassNotFoundException {
        String select = "select idEmployees, surname, salary from employees;";
        ResultSet resultSet = null;

        PreparedStatement prSt = getDbConnection().prepareStatement(select);

        resultSet = prSt.executeQuery();

        return resultSet;
    }

    void deleteEmployee(Integer id) throws SQLException, ClassNotFoundException {
        String delete = "delete from employees where idemployees = " + id + ";";
        PreparedStatement prst = getDbConnection().prepareStatement(delete);
        prst.executeUpdate();
    }

    void changeSalary(Integer id, Integer money) throws SQLException, ClassNotFoundException {
        String update = "update employees set salary = " + money + " where idemployees = " + id + ";";
        PreparedStatement prSt = getDbConnection().prepareStatement(update);
        prSt.executeUpdate();
    }

    void setSalary(Integer id, Integer hours) throws SQLException, ClassNotFoundException {
        System.out.println("enter set salary");
        String select1 = "select id_position from employees where idemployees = ?";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select1);
        ResultSet set1 = null;
        preparedStatement.setString(1,id.toString());

        set1 = preparedStatement.executeQuery();
        set1.next();
        Integer id_pos = set1.getInt("id_position");

        String select2 = "select working_rate from positions where idposition = ?";
        PreparedStatement preparedStatement1 = getDbConnection().prepareStatement(select2);
        preparedStatement1.setString(1,id_pos.toString());
        ResultSet set2 = preparedStatement1.executeQuery();
        set2.next();
        Integer working_rate = set2.getInt("working_rate");
        System.out.println(working_rate);
        String update = "update employees set salary = ? where idemployees = ?;";
        PreparedStatement preparedStatement2 = getDbConnection().prepareStatement(update);
        Integer total = hours * working_rate;
        System.out.println(total);
        preparedStatement2.setString(1,total.toString());
        preparedStatement2.setString(2, id.toString());
        System.out.println(preparedStatement2);
        preparedStatement2.executeUpdate();
    }

    void makeApplication(Integer id_employee, String description) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        String insert = "insert into regularapplication(id_employee, id_status, id_examiner,desription, id_app_type) " +
                "values(?,531, 8, ?, 31);";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1, id_employee.toString());
        preparedStatement.setString(2, description);
        preparedStatement.executeUpdate();
    }

    ResultSet getIDRole(Integer id) throws SQLException, ClassNotFoundException {
        ResultSet res = null;
        String select = "select Id_role, idemployees from employees\n" +
                "where idemployees =" + id + ";";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        res = preparedStatement.executeQuery();
        return res;
    }



    public void declineJobAPP(Integer k) throws SQLException, ClassNotFoundException {
        String select = "update jobapplication set id_status = 27 where idjobapplication = " + k + ";";
        String select2 = "update regularapplication set id_status = 27 where idapplication = " + k + ";";
        PreparedStatement preparedStatement1 = getDbConnection().prepareStatement(select);
        PreparedStatement preparedStatement2 = getDbConnection().prepareStatement(select2);
        preparedStatement2.executeUpdate();
        preparedStatement1.executeUpdate();
    }


    public ResultSet getJobAppExamine(Integer employee_id) throws SQLException, ClassNotFoundException {
        String select = "select b.idjobapplication id, b.surname, b.position purpose, stat.status, b.description from \n" +
                "(select a.idJobapplication, a.surname, pos.name position, a.id_status, a.description  from \n" +
                "(select * from jobapplication job\n" +
                "join  candidates cand on \n" +
                "cand.idcandidates = id_candidate where id_examiner = " + employee_id +  ") a \n" +
                "join positions pos on a.id_position = pos.idposition) b\n" +
                "join statutes stat on stat.idstatutes = id_status;";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    public ResultSet getRegularAppExamine(Integer employee_id) throws SQLException, ClassNotFoundException {
        String select = "select b.idapplication id, b.surname, apptype.application_type purpose, b.status, b.desription description from\n" +
                "                (select a.*, empl.surname  from\n" +
                "                (select * from regularapplication\n" +
                "                join statutes stat on stat.idstatutes = id_status where id_examiner = 9) a\n" +
                "                join employees empl on empl.idemployees = id_employee) b\n" +
                "                join applicationtypes apptype on apptype.idapplicationtypes = id_app_type;";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    public Integer getUserIdFromJobApplication(Integer idApp) throws SQLException, ClassNotFoundException {
        String select = "select id_candiate from jobapplication whre idjobapplication = " + idApp + ";";
        ResultSet set =null;
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        set = preparedStatement.executeQuery();
        Integer out = set.getInt("id_candidate");
        return out;
    }

    public void acceptJobApp(Integer idApp) throws SQLException, ClassNotFoundException {
        String select = "select idjobapplication, id_candidate, id_position from JobApplication where idjobapplication = " + idApp + ";";
        ResultSet set = null;
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        set = preparedStatement.executeQuery();
        ResultSet set2 = null;
        set.next();
        set2 = dataBaseHandler.getCandidateById(set.getInt("id_candidate"));
        String delete = "delete from candidates where idcandidates = " + set.getInt("id_candidate") + ";";
        PreparedStatement deleteStatement = getDbConnection().prepareStatement(delete);
        deleteStatement.executeUpdate();
        set2.next();
        System.out.println(set2.getString("SNILS"));
        Candidate candidate = new Candidate(
                 Integer.valueOf(set2.getString("idcandidates")),
                set2.getString("name"),
                set2.getString("surname"),
                set2.getString("login"),
                set2.getString("password"),
                set2.getString("SNILS")
        );
        regEmployee(candidate, set.getString("id_position"));

        String update = "update positions set vacancies_amount = vacancies_amount-1;";
        PreparedStatement preparedStatement1 = getDbConnection().prepareStatement(update);
        preparedStatement1.executeUpdate();

    }

    public void regEmployee(Candidate candidate, String id_position) throws SQLException, ClassNotFoundException {
        System.out.println(id_position);
        String insert = "insert into employees" +
        "(idemployees, name, surname,login,password,id_role,snils,id_status, working_hours, salary, id_position)\n" +
                "values (?,?,?,?,?,?,?,5,0,?,?);";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1,candidate.getId().toString());
        preparedStatement.setString(2, candidate.getName());
        preparedStatement.setString(3, candidate.getSurname());
        preparedStatement.setString(4, candidate.getLogin());
        preparedStatement.setString(5, candidate.getPassword());
        if(id_position.equals("107"))
        {
        preparedStatement.setString(6,"2");
        }
        else {
            preparedStatement.setString(6,"3");
        }
        System.out.println(candidate.getSnils() + "afafafa");

        preparedStatement.setString(7, candidate.getSnils());
        preparedStatement.setString(8, "0");
        preparedStatement.setString(9,id_position);

        preparedStatement.executeUpdate();
    }


    public void changeStatus(Integer status,Integer id) throws SQLException, ClassNotFoundException {
        System.out.println("cahnge status");
        String update = "update employees set id_status = "+ status + " where idemployees = " + id + ";";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
        preparedStatement.executeUpdate();
    }

    public void acceptRegularApp(String purpose, Integer id) throws SQLException, ClassNotFoundException {
        System.out.println("enter accept");
        System.out.println(purpose);
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        Integer type = dataBaseHandler.getApptypeId(purpose);

        if (purpose.equals("Want a vaccation"))
        {
            System.out.println("vaccation");
            changeStatus(6,id);
            Data.EMPLOYEE_STATUS = "6";
        }
        else if(purpose.equals("Want to quit"))
        {
            System.out.println("quit");
            changeStatus(7,id);
            Data.EMPLOYEE_STATUS = "7";
        }
    }

    public Integer getUserIdFromApplication(Integer idApp) throws SQLException, ClassNotFoundException {
        String select = "select id_employee from regularapplication where idapplication = " + idApp + ";";
        ResultSet set = null;
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        set = preparedStatement.executeQuery();
        set.next();
        Integer id = set.getInt("id_employee");
        return id;
    }

    public void createRequirement(String requirement) throws SQLException, ClassNotFoundException {
        String insert = "insert into requirements(requirement) values(?)";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1,requirement);
        preparedStatement.executeUpdate();
    }

    public void createPosition(String name, Integer rate) throws SQLException, ClassNotFoundException {
        String insert = "insert into positions(name,vacancies_amount,working_rate) values(?,0,?)";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,rate.toString());
        preparedStatement.executeUpdate();
    }

    public void addRequirementToPosition(String requirement, String position) throws SQLException, ClassNotFoundException {
        String insert = "insert into requirements(requirement) values(?);";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1,requirement);
        preparedStatement.executeUpdate();

        String select1 = "select idrequirement from requirements where requirement = ?";
        PreparedStatement preparedStatement2 = getDbConnection().prepareStatement(select1);
        preparedStatement2.setString(1,requirement);
        ResultSet set1 = null;
        set1 = preparedStatement2.executeQuery();
        set1.next();
        Integer idRequirement = set1.getInt("idRequirement");


        String select2 = "select idPosition from positions where name = ?;";
        PreparedStatement preparedStatement1 = getDbConnection().prepareStatement(select2);
        preparedStatement1.setString(1,position);
        ResultSet set2 = preparedStatement1.executeQuery();
        set2.next();
        Integer idPosition = set2.getInt("idPosition");

        String insert2 = "insert into positionrequirement(id_position, id_requirement) values(?,?);";
        PreparedStatement preparedStatement3 = getDbConnection().prepareStatement(insert2);
        preparedStatement3.setString(1,idPosition.toString());
        preparedStatement3.setString(2,idRequirement.toString());
        preparedStatement3.executeUpdate();
    }

    public void deleteRequirementFromPosition(String requirement, String position) throws SQLException, ClassNotFoundException {
        String select = "select idRequirement from requirements where requirement = ?;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        preparedStatement.setString(1,requirement);
        ResultSet set1 = preparedStatement.executeQuery();
        set1.next();
        Integer idRequirement = set1.getInt("idRequirement");

        String select2 = "select idPosition from positions where name = ?;";
        PreparedStatement preparedStatement1 = getDbConnection().prepareStatement(select2);
        preparedStatement1.setString(1,position);
        ResultSet set2 = preparedStatement1.executeQuery();
        set2.next();
        Integer idPosition = set2.getInt("idPosition");

        String delete = "delete from positionrequirement where id_position = ? and id_requirement =?;";
        PreparedStatement preparedStatement2 = getDbConnection().prepareStatement(delete);
        preparedStatement2.setString(1,idPosition.toString());
        preparedStatement2.setString(2,idRequirement.toString());
        preparedStatement2.executeUpdate();
    }

    public void deletePosition(String name) throws SQLException, ClassNotFoundException {
        String delete = "delete from positions where name = ?";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(delete);
        preparedStatement.setString(1,name);
        preparedStatement.executeUpdate();
    }

    public void addVaccancies(String name, Integer amount) throws SQLException, ClassNotFoundException {
        String update = "update positions set vacancies_amount = ? where name = ?";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
        preparedStatement.setString(1,amount.toString());
        preparedStatement.setString(2,name);
        preparedStatement.executeUpdate();
    }

    public ResultSet getSelectedApp(Integer id) throws SQLException, ClassNotFoundException {
        ResultSet set = null;
        String select = "select reg.*, apptype.* from regularapplication reg\n" +
                "join applicationtypes apptype on apptype.idapplicationtypes = id_app_type" +
                " where idapplication = ?;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        preparedStatement.setString(1,id.toString());
        set = preparedStatement.executeQuery();
        return set;
    }

    public Integer getEmployeeIdByApppId(Integer id) throws SQLException, ClassNotFoundException {
        String select = "select * from regulaapplication where id_employee = ?";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        preparedStatement.setString(1,id.toString());
        ResultSet set = null;
        set = preparedStatement.executeQuery();
        return set.getInt("id_employee");
    }

    public Integer getApptypeId(String name) throws SQLException, ClassNotFoundException {
        String select = "select * from applicationtypes where application_type = ?;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        preparedStatement.setString(1,name);
        ResultSet set = null;
        set = preparedStatement.executeQuery();
        set.next();
        return set.getInt("idApplicationTypes");
    }

    public void changeStatusToAcceptJobAPP(Integer k) throws SQLException, ClassNotFoundException {
        String select = "update jobapplication set id_status = 25 where idjobapplication = " + k + ";";
        String select2 = "update regularapplication set id_status = 25 where idapplication = " + k + ";";
        PreparedStatement preparedStatement1 = getDbConnection().prepareStatement(select);
        PreparedStatement preparedStatement2 = getDbConnection().prepareStatement(select2);
        preparedStatement2.executeUpdate();
        preparedStatement1.executeUpdate();
    }

    ResultSet getEmployeeStatus(Integer id) throws SQLException, ClassNotFoundException {
        String select = "select empl.id_status, stat.status from employees empl\n" +
                "join employeestatus stat on empl.id_status = stat.id_status\n" +
                "where empl.idemployees = ?;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        preparedStatement.setString(1,id.toString());
        ResultSet set = null;
        set = preparedStatement.executeQuery();
        return set;
    }

    ResultSet getEmployeePosition(Integer id) throws SQLException, ClassNotFoundException {
        ResultSet set = null;
        String select = "select  pos.name from employees join\n" +
                "Positions pos on idposition = id_position where idemployees = ?;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        preparedStatement.setString(1,id.toString());
        set = preparedStatement.executeQuery();
        return set;
    }

    Integer getSalaryById(Integer id) throws SQLException, ClassNotFoundException {
        String select = "select salary from employees where idemployees = ?";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        preparedStatement.setString(1,id.toString());
        ResultSet set = null;
        set = preparedStatement.executeQuery();
        set.next();
        return Integer.valueOf(set.getString("salary"));
    }

    Integer getWorkingHours(Integer id) throws SQLException, ClassNotFoundException {
        String select = "select * from employees where idemployees = ?;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        preparedStatement.setString(1,id.toString());
        ResultSet set = null;
        set = preparedStatement.executeQuery();
        set.next();
        return set.getInt("working_hours");
    }



}

