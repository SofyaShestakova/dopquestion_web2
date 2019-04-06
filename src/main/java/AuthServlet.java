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
        String username = request.getParameter("username"); // параметр из POST-запроса
        String password = request.getParameter("password");

        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = connection.prepareStatement("select password from users where login = ?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String dbPassword = resultSet.getString(1);
                if (dbPassword.equals(password)) {
                    request.getSession(true); // возвращает JSESSION_ID
                    System.out.println("AuthServlet: SessionId " + request.getRequestedSessionId());
                    usersMap.put(request.getRequestedSessionId(), new User(username, password));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ans");
        dispatcher.forward(request, response);
    }
}
