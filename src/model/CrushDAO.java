package model;

import java.sql.*;
import java.util.Random;

public class CrushDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/crushpredictordb?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private String jdbcUsername = "root";
    private String jdbcPassword = "1234";

    public int generateMatchPercentage() {
        return new Random().nextInt(51) + 50; // 50 - 100%
    }

    public String getCrushMessage(int percentage) {
        if (percentage >= 90) return "Perfect match!";
        else if (percentage >= 75) return "Strong connection.";
        else if (percentage >= 60) return "Maybe give it a shot?";
        else return "Not quite there yet...";
    }

    public void savePrediction(String yourName, String crushName, int percentage, String message) throws SQLException {
        String sql = "INSERT INTO predictions (your_name, crush_name, match_percentage, result_message) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, yourName);
            pstmt.setString(2, crushName);
            pstmt.setInt(3, percentage);
            pstmt.setString(4, message);
            pstmt.executeUpdate();
        }
    }
}