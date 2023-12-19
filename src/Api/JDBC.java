package Api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    private static Connection client;
    private static Integer user_id;
    public static void connectToDatabase() {
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
    public static void stopConnection(){
        try {
            client.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setUser_id(Integer user_id) {
        JDBC.user_id = user_id;
    }
    public static Integer getUser_id() {
        return user_id;
    }
    public static Connection getClient() {
        return client;
    }
}
