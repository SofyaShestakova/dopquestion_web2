import java.sql.*;

public class DatabaseConnector {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("classnotfound exception!");
            e.printStackTrace();
        }
        try {
            connection= DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/users", "postgres", "");
            statement =  connection.prepareStatement("select * from lab_users;");
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println("SQL exception!!");
            e.printStackTrace();
        }
        System.out.println("Connection success!!");
    }

}
