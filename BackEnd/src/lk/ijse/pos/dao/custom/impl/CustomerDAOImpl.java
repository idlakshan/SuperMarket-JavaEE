package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.servlet.CustomerServlet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean add(Customer customer) throws SQLException, ClassNotFoundException {
        Connection connection = CustomerServlet.dataSource.getConnection();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?,?)");
        statement.setObject(1,customer.getCusID());
        statement.setObject(2,customer.getCusName());
        statement.setObject(3,customer.getCusAddress());
        statement.setObject(4,customer.getCusSalary());

        if (statement.executeUpdate()>0){
            connection.close();
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        Connection connection = CustomerServlet.dataSource.getConnection();

        PreparedStatement statement = connection.prepareStatement("DELETE FROM customer WHERE cusID=?");
        statement.setObject(1,id);
        boolean isDeleted = statement.executeUpdate() > 0;
        connection.close();

        if (isDeleted){
            return true;
        }else {
            return false;

        }

    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        Connection connection = CustomerServlet.dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Customer SET CusName = ?,CusAddress = ?,cusSalary = ? WHERE CusID = ?");
        preparedStatement.setObject(1,customer.getCusName());
        preparedStatement.setObject(2,customer.getCusAddress());
        preparedStatement.setObject(3,customer.getCusSalary());
        preparedStatement.setObject(4,customer.getCusID());

        boolean isUpdated = preparedStatement.executeUpdate() > 0;

        if (isUpdated){
            connection.close();
            return true;
        }
        return false;
    }

    @Override
    public JsonObjectBuilder search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public JsonArrayBuilder getAll() throws SQLException, ClassNotFoundException {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();


        Connection connection = CustomerServlet.dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            objectBuilder.add("cusId",resultSet.getString("cusId"));
            objectBuilder.add("cusName",resultSet.getString("cusName"));
            objectBuilder.add("cusAddress",resultSet.getString("cusAddress"));
            objectBuilder.add("cusSalary",resultSet.getString("cusSalary"));

            arrayBuilder.add(objectBuilder.build());
        }
        connection.close();

        return arrayBuilder;
    }

    @Override
    public String generateNewCusId() throws SQLException, ClassNotFoundException {
        Connection connection = CustomerServlet.dataSource.getConnection();

        String id="";

        PreparedStatement statement = connection.prepareStatement("SELECT cusID FROM customer ORDER BY cusID DESC LIMIT 1");
        ResultSet resultSet = statement.executeQuery();

        boolean isExits = resultSet.next();

        if (isExits){
            String cusID=resultSet.getString(1);
            cusID=cusID.substring(1);

            int intId=Integer.parseInt(cusID);

            intId++;

            if (intId < 10) {
                id = "C00" + intId;
            } else if (intId < 100) {
                id = "C0" + intId;
            } else {
                id = "C" + intId;
            }
        } else {
            id = "C001";
        }

        return id;
        }

}
