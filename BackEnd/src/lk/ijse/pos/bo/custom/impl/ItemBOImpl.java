package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.ItemBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.entity.Item;

import javax.json.JsonArrayBuilder;
import java.sql.SQLException;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO= (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);


    @Override
    public JsonArrayBuilder getAllItem() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean addItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Item item=new Item(itemDTO.getItemCode(),itemDTO.getItemName(),itemDTO.getPrice(),itemDTO.getQty());
        return itemDAO.add(item);
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewItemCode() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewItemCode();
    }
}
