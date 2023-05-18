package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.entity.Item;
import lk.ijse.pos.servlet.ItemServlet;

import javax.json.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDAOImpl implements ItemDAO {


    @Override
    public boolean add(Item item) throws SQLException, ClassNotFoundException {
        Connection connection = ItemServlet.dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");
        preparedStatement.setObject(1,item.getItemCode());
        preparedStatement.setObject(2,item.getItemName());
        preparedStatement.setObject(3,item.getPrice());
        preparedStatement.setObject(4,item.getQty());

        boolean isSaved = preparedStatement.executeUpdate() > 0;

        if (isSaved){
            connection.close();
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        Connection connection = ItemServlet.dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM item WHERE itemCode=?");
        statement.setObject(1,code);

        boolean isDeleted = statement.executeUpdate() > 0;

        if (isDeleted){
            connection.close();
            return true;
        }else{
            return false;
        }

    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        Connection connection = ItemServlet.dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE item SET itemName = ?,price = ?,qty = ? WHERE itemCode = ?");
        preparedStatement.setObject(1,item.getItemName());
        preparedStatement.setObject(2,item.getPrice());
        preparedStatement.setObject(3,item.getQty());
        preparedStatement.setObject(4,item.getItemCode());

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
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        Connection connection = ItemServlet.dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM item");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            objectBuilder.add("itemCode",resultSet.getString("itemCode"));
            objectBuilder.add("itemName",resultSet.getString("itemName"));
            objectBuilder.add("price",resultSet.getString("price"));
            objectBuilder.add("qty",resultSet.getString("qty"));

            arrayBuilder.add(objectBuilder.build());

        }
        connection.close();

        return arrayBuilder;
    }

    @Override
    public String generateNewItemCode() throws SQLException, ClassNotFoundException {
        Connection connection = ItemServlet.dataSource.getConnection();

        String code="";

        PreparedStatement statement = connection.prepareStatement("SELECT itemCode FROM item ORDER BY itemCode DESC LIMIT 1");
        ResultSet resultSet = statement.executeQuery();

        boolean isExits = resultSet.next();

        if (isExits){
            String itemCode=resultSet.getString(1);
            itemCode=itemCode.substring(1);

            int intCode=Integer.parseInt(itemCode);

            intCode++;

            if (intCode < 10) {
                code = "I00" + intCode;
            } else if (intCode < 100) {
                code = "I0" + intCode;
            } else {
                code = "I" + intCode;
            }
        } else {
            code = "I001";
        }

        return code;
    }

}
