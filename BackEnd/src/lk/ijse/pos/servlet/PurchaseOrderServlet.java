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

            String oId = requestOb.getString("oid");
            String date = requestOb.getString("date");
            String cusID = requestOb.getString("cusID");

            PreparedStatement psmt = connection.prepareStatement("Insert into `order` values(?,?,?)");
            psmt.setObject(1,oId);
            psmt.setObject(2,date);
            psmt.setObject(3,cusID);

            if (!(psmt.executeUpdate() > 0)) {
                connection.rollback();
                throw new RuntimeException("Can't Save The Order");
            }else{
                   JsonArray orderDetails = requestOb.getJsonArray("orderDetails");
                for (JsonValue od : orderDetails) {
                    String code = od.asJsonObject().getString("itemCode");
                    String qty = od.asJsonObject().getString("orderqty");
                    String price = od.asJsonObject().getString("price");

                    PreparedStatement pstm2 = connection.prepareStatement("Insert into orderDetails values(?,?,?,?)");
                    pstm2.setObject(1,oId);
                    pstm2.setObject(2,code);
                    pstm2.setObject(3,qty);
                    pstm2.setObject(4,price);


                    if (!(pstm2.executeUpdate() > 0)) {
                        connection.rollback();
                        throw new RuntimeException("There is a Problem With Order Details.");
                    }
                }
                connection.commit();
                connection.setAutoCommit(true);
                connection.close();

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
                connection.close();

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
