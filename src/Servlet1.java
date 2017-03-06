import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Servlet1")
public class Servlet1 extends HttpServlet {
    private static final long        serialVersionUID    = 1L;
    static String            url        = "jdbc:mysql://cmkoesters.ddns.net:3306/myDB";
    static String            user        = "newremoteuser";
    static String            password        = "password";
    static Connection            connection    = null;

    public Servlet1 () {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("-------- MySQL JDBC Connection Testing ------------<br>");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            response.getWriter().println("-------- Where is your MySQL JDBC Driver? ------------<br>");

            return;
        }
        response.getWriter().println("MySQL JDBC Driver Registered!<br>");
        connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            response.getWriter().println("-------- Connection failed!!! Check output console ------------<br>");

            return;
        }
        if (connection != null) {
            response.getWriter().println("You made it, take control your database now!<br>");
        } else {
            System.out.println("Failed to make connection!");
            response.getWriter().println("-------- Failed to make connection! ------------<br>");

        }
        try {
            String selectSQL = "SELECT * FROM inventory";
            //String theUserName = "user%";
            response.getWriter().println(selectSQL + "<br>");
            response.getWriter().println("------------------------------------------<br>");
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            //preparedStatement.setString(1, theUserName);
            ResultSet rs = preparedStatement.executeQuery();
            
            
            
            String message = "Welcome";
            request.setAttribute("message", message);
            request.setAttribute("resultSet", rs);
            // request.setAttribute("resultSet2", rs2);
            
            request.getRequestDispatcher("/WEB-INF/Page1.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("you fucked up");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}