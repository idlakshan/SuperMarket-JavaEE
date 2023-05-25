package lk.ijse.pos.servlet;

import javax.annotation.Resource;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/purchase")
public class PurchaseOrderServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    public static DataSource dataSource;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         Connection connection = null;

        try {
            connection=dataSource.getConnection();
            connection.setAutoCommit(false);

            JsonReader reader = Json.createReader(req.getReader());
            JsonObject requestOb = reader.readObject();

            String orderID = requestOb.getString("orderID");
            String orderDate = requestOb.getString("orderDate");
            String cusID = requestOb.getString("cusID");


            System.out.println(orderID+" "+orderDate+" "+cusID);

            PreparedStatement psmt = connection.prepareStatement("INSERT INTO orders VALUES (?,?,?)");
            psmt.setObject(1,orderID);
            psmt.setObject(2,orderDate);
            psmt.setObject(3,cusID);



            if (!(psmt.executeUpdate() > 0)) {
                connection.rollback();
                throw new RuntimeException("Can't Save The Order");
            }else{
                   JsonArray orderDetails = requestOb.getJsonArray("orderdetails");
                for (JsonValue od : orderDetails) {

                   // String orderId=od.asJsonObject().getString("orderID");
                    String itemCode = od.asJsonObject().getString("itemCode");
                    double price = Double.parseDouble(od.asJsonObject().getString("price"));
                    int orderQty = Integer.parseInt(od.asJsonObject().getString("orderQty"));

                    PreparedStatement pstm2 = connection.prepareStatement("INSERT INTO orderdetails VALUES (?,?,?,?)");
                    pstm2.setObject(1,orderID);
                    pstm2.setObject(2,itemCode);
                    pstm2.setObject(3,price);
                    pstm2.setObject(4,orderQty);

                    if (!(pstm2.executeUpdate() > 0)) {
                        connection.rollback();
                        throw new RuntimeException("There is a Problem With Order Details.");
                    }
                }
                connection.commit();
                connection.setAutoCommit(true);
                //connection.close();

                JsonObjectBuilder responseObject = Json.createObjectBuilder();
                responseObject.add("state","done");
                responseObject.add("message","Successfully Purchased");
                responseObject.add("data","");
                resp.getWriter().print(responseObject.build());

            }

        } catch (SQLException | RuntimeException e) {
            JsonObjectBuilder jsonObject = Json.createObjectBuilder();

            try {
                connection.rollback();
                connection.setAutoCommit(true);
               // connection.close();

            } catch (SQLException ex) {
                jsonObject.add("state","error");
                jsonObject.add("message",e.getMessage());
            }
            jsonObject.add("state","error");
            jsonObject.add("message",e.getMessage());
            resp.getWriter().print(jsonObject.build());
            resp.setStatus(500);
        }
    }
}
