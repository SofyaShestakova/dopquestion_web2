import org.postgresql.core.SqlCommand;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AuthServlet extends HttpServlet {
    private Connection connection = null;
    private Map<String, User> usersMap = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:55555/postgres", "postgres", "postgres");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        usersMap = new HashMap<>();
        ServletContext context = getServletContext();
        context.setAttribute("usersMap", usersMap);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        removeOldData(request.getRequestedSessionId(), request);
        String username = request.getParameter("username"); // параметр из POST-запроса
        String password = request.getParameter("password");

        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = connection.prepareStatement("select * from users where login = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                request.getSession(true); // возвращает JSESSION_ID
                System.out.println("AuthServlet: SessionId " + request.getRequestedSessionId());
                usersMap.put(request.getRequestedSessionId(), new User(username, password));
            }
        } catch (SQLException | NullPointerException e) {
            request.getSession().setAttribute("errorMessage", "Server not available");
        } /*catch (SQLException e){
            connect();
        }*/
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ans");
        dispatcher.forward(request, response);
    }

    private void removeOldData(String requestedSessionId, HttpServletRequest request) {
        usersMap.remove(requestedSessionId);
        if (request.getSession().getAttribute("errorMessage") != null) {
            request.getSession().removeAttribute("errorMessage");
        }
    }

    /*private void connect(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:55555/postgres", "postgres", "postgres");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }*/
}
