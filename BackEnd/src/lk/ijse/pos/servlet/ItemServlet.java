package lk.ijse.pos.servlet;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.ItemBO;
import lk.ijse.pos.dto.ItemDTO;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    public static DataSource dataSource;

    ItemBO itemBO= (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.ITEM);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        try {
            JsonArrayBuilder allItem = itemBO.getAllItem();

            objectBuilder.add("state","Done");
            objectBuilder.add("message","Success");
            objectBuilder.add("data",allItem.build());
            writer.print(objectBuilder.build());


        } catch (SQLException e) {
            objectBuilder.add("state","Failed");
            objectBuilder.add("message",e.getMessage());
            resp.setStatus(400);
            writer.print(objectBuilder.build());

        } catch (ClassNotFoundException e) {

            objectBuilder.add("state","Failed");
            objectBuilder.add("message",e.getMessage());
            resp.setStatus(500);
            writer.print(objectBuilder.build());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
           String itemCode = itemBO.generateNewItemCode();
            String itemName = req.getParameter("itemName");
            String price = req.getParameter("itemPrice");
            String qty = req.getParameter("itemQty");

            ItemDTO itemDTO=new ItemDTO(itemCode,itemName,Double.parseDouble(price),qty);
            boolean isSaved = itemBO.addItem(itemDTO);

            if (isSaved){
                JsonObjectBuilder jsonObject = Json.createObjectBuilder();
                jsonObject.add("state","Done");
                jsonObject.add("message","Item Added Successfully..!");
                resp.getWriter().print(jsonObject.build());
            }

        } catch (SQLException e) {
            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            jsonObject.add("state","Failed");
            jsonObject.add("message",e.getMessage());
            resp.getWriter().print(jsonObject.build());
            resp.setStatus(400);

        } catch (ClassNotFoundException e) {
            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            jsonObject.add("state","Failed");
            jsonObject.add("message",e.getMessage());
            resp.getWriter().print(jsonObject.build());
            resp.setStatus(500);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemCode = req.getParameter("itemCode");
        try {
            boolean isDeleted = itemBO.deleteItem(itemCode);

            if (isDeleted){
                JsonObjectBuilder jsonObject = Json.createObjectBuilder();
                jsonObject.add("state","Done");
                jsonObject.add("message","Item Deleted Successfully..!");
                resp.getWriter().print(jsonObject.build());
            }else {
                JsonObjectBuilder jsonObject = Json.createObjectBuilder();
                jsonObject.add("state","Failed");
                jsonObject.add("message","no Item to Delete");
                resp.getWriter().print(jsonObject.build());
            }


        } catch (SQLException e) {
            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            jsonObject.add("state","Failed");
            jsonObject.add("message",e.getMessage());
            resp.getWriter().print(jsonObject.build());
            resp.setStatus(400);
        } catch (ClassNotFoundException e) {
            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            jsonObject.add("state","Failed");
            jsonObject.add("message",e.getMessage());
            resp.getWriter().print(jsonObject.build());
            resp.setStatus(500);
        }
    }
}
