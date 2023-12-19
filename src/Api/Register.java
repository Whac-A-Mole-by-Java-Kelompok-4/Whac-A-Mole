package Api;

import java.sql.*;

public class Register {
    public static void register(String username) throws SQLException {
        String query = "INSERT INTO USERS (username) VALUES (?)";
        try (PreparedStatement pstmt = JDBC.getClient().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1,username);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User successfully inserted.");
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        JDBC.setUser_id(userId);
                        System.out.println("Generated User ID: " + userId);
                    } else {
                        System.out.println("Failed to retrieve user ID.");
                    }
                }
            } else {
                System.out.println("Failed to insert user.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
