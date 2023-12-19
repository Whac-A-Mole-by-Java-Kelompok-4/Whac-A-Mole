package Api;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Score {
    private static int userScore;
    private static ArrayList<String> usernames = new ArrayList<>();
    private static ArrayList<Integer> highscores = new ArrayList<>();
    public static void addScore(){
        userScore++;
    }
    public static int getScore(){
        return userScore;
    }
    public static void resetScore(){
        userScore = 0;
    }

    public static ArrayList<String> getUsernames() {
        return usernames;
    }
    public static ArrayList<Integer> getHighscores() {
        return highscores;
    }

    public static void updateScore() throws SQLException {
        System.out.println("user id : " + JDBC.getUser_id());
        String getHighestScoreQuery = "SELECT score FROM score WHERE player_id = ?";

        int highestScore = 0; // Skor tertinggi default
        try (PreparedStatement stmt = JDBC.getClient().prepareStatement(getHighestScoreQuery)) {
            stmt.setInt(1, JDBC.getUser_id());
            ResultSet rs = stmt.executeQuery(); // Perbaikan di sini
            if (rs.next()) {
                highestScore = rs.getInt("score");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (userScore > highestScore) {
            String updateScoreQuery = "UPDATE score SET score = " + userScore + " WHERE player_id = '" + JDBC.getUser_id() + "'";
            try (Statement stmt = JDBC.getClient().createStatement()) {
                stmt.executeUpdate(updateScoreQuery);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static void getUserScore() throws SQLException {
        String query = "SELECT u.username, s.score FROM users u INNER JOIN score s ON u.id = s.player_id ORDER BY s.score DESC";
        usernames.clear();
        highscores.clear();
        try (Statement stmt = JDBC.getClient().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                usernames.add(rs.getString("username"));
                highscores.add(rs.getInt("score"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
