package Api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    private static Connection client;
    private static String user_id;
    public JDBC() {
        try {
            String url = "jdbc:mysql://localhost:3306/pbo_project?user=root&password=";
            client = DriverManager.getConnection(url);
            System.out.println("Connection success.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Connection failure.");
            System.exit(1);
        }
    }
    public static void setUser_id(String user_id) {
        JDBC.user_id = user_id;
    }
    public static String getUser_id() {
        return user_id;
    }
    public static Connection getClient() {
        return client;
    }
}
