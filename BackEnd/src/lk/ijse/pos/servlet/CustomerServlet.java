package lk.ijse.pos.servlet;

import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.CustomerBO;
import lk.ijse.pos.dto.CustomerDTO;

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
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
            customerBO.addCustomer(customerDTO);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       // System.out.println(cusId+" "+cusName);

    }
}
