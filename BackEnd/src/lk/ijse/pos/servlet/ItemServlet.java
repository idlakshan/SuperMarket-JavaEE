package lk.ijse.pos.servlet;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.ItemBO;
import lk.ijse.pos.dto.ItemDTO;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    public static DataSource dataSource;

    ItemBO itemBO= (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.ITEM);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
           String itemCode = itemBO.generateNewItemCode();
            String itemName = req.getParameter("itemName");
            String itemPrice = req.getParameter("itemPrice");
            String itemQty = req.getParameter("itemQty");

            ItemDTO itemDTO=new ItemDTO(itemCode,itemName,Double.parseDouble(itemPrice),itemQty);
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
}
