package lk.ijse.pos.servlet;

import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.CustomerBO;
import lk.ijse.pos.dto.CustomerDTO;

import javax.annotation.Resource;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    public static DataSource dataSource;

    CustomerBO customerBO= (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.CUSTOMER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        try {
            JsonArrayBuilder allCustomer = customerBO.getAllCustomer();
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

            objectBuilder.add("state","Done");
            objectBuilder.add("message","Success");
            objectBuilder.add("data",allCustomer.build());
            writer.print(objectBuilder.build());


        } catch (SQLException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

            objectBuilder.add("state","Failed");
            objectBuilder.add("message",e.getMessage());
            resp.setStatus(400);
            writer.print(objectBuilder.build());

        } catch (ClassNotFoundException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

            objectBuilder.add("state","Failed");
            objectBuilder.add("message",e.getMessage());
            resp.setStatus(500);
            writer.print(objectBuilder.build());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {
           // String cusId = req.getParameter("cusId");
            String cusId=customerBO.generateNewCusId();
            String cusName = req.getParameter("cusName");
            String cusAddress = req.getParameter("cusAddress");
            String cusSalary = req.getParameter("cusSalary");

            CustomerDTO customerDTO=new CustomerDTO(cusId,cusName,cusAddress,Double.parseDouble(cusSalary));
            boolean isSaved = customerBO.addCustomer(customerDTO);

            if (isSaved){
                JsonObjectBuilder jsonObject = Json.createObjectBuilder();
                jsonObject.add("state","Done");
                jsonObject.add("message","Customer Added Successfully..!");
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
       // System.out.println(cusId+" "+cusName);

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusId = req.getParameter("cusId");

        PrintWriter writer = resp.getWriter();
        try {
            boolean deleteCustomer = customerBO.deleteCustomer(cusId);

            if (deleteCustomer){
                JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
                jsonObjectBuilder.add("state","Done");
                jsonObjectBuilder.add("message","Customer Deleted..");
                writer.print(jsonObjectBuilder.build());
            }else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("state","Failed");
                objectBuilder.add("message","Not customer to Delete..");
                writer.print(objectBuilder.build());
            }

        } catch (SQLException e) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("state","Failed");
            jsonObjectBuilder.add("message",e.getMessage());
            resp.setStatus(400);
            writer.print(jsonObjectBuilder.build());

        } catch (ClassNotFoundException e) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("state","Failed");
            jsonObjectBuilder.add("message",e.getMessage());
            resp.setStatus(500);
            writer.print(jsonObjectBuilder.build());
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String cusId = jsonObject.getString("cusId");
        String cusName = jsonObject.getString("cusName");
        String cusAddress = jsonObject.getString("cusAddress");
        String cusSalary = jsonObject.getString("cusSalary");

        PrintWriter writer = resp.getWriter();
        CustomerDTO customerDTO=new CustomerDTO(cusId,cusName,cusAddress,Double.parseDouble(cusSalary));

        try {
            boolean isUpdated = customerBO.updateCustomer(customerDTO);

            if (isUpdated){
                JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
                jsonObjectBuilder.add("state","Done");
                jsonObjectBuilder.add("message","Customer Updated..");
                writer.print(jsonObjectBuilder.build());
            }else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("state","Failed");
                objectBuilder.add("message","Not customer to Update..");
                writer.print(objectBuilder.build());
            }


        } catch (SQLException e) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("state","Failed");
            jsonObjectBuilder.add("message",e.getMessage());
            resp.setStatus(400);
            writer.print(jsonObjectBuilder.build());

        } catch (ClassNotFoundException e) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("state","Failed");
            jsonObjectBuilder.add("message",e.getMessage());
            resp.setStatus(500);
            writer.print(jsonObjectBuilder.build());
        }
    }
}
