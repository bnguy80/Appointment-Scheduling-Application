package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.RememberMeToken;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Base64;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class RememberMeTokenDAO {
    private static void insertNewTokenForUser(int userId, String username, String token, Timestamp expiresAt) {
        try {
            String sql = "INSERT INTO remember_me_tokens (User_ID, User_Name, Token, Expires_At) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, username);
            ps.setString(3, token);
            ps.setTimestamp(4, expiresAt); //Create Timestamp.valueOf(LocalDateTime.now()) and add 1 minute to expire in 1 minute, for testing purposes
            ps.execute();
        }
        catch (SQLException e) {
            System.out.println("createNewTokenForUser Error: " + e.getMessage());
        }
    }
    // Create a new method called generateAndInsertTokenForUser to generate and insert a new token into the database for a given user
    public static void generateAndInsertTokenForUser(int userId, String username) {
        String token = generateRandomToken();
        Timestamp expiresAt = Timestamp.valueOf(LocalDateTime.now().plusMinutes(10));
        insertNewTokenForUser(userId, username, token, expiresAt);
    }
    // Create a new method to generateRandomToken
    private static String generateRandomToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[64];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
    public static RememberMeToken getRememberMeTokenForUser(int userId) {
        RememberMeToken rememberMeToken = null;
        try {
            String sql = "SELECT * FROM remember_me_tokens WHERE User_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int tokenID = rs.getInt("Token_ID");
                int userID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String token = rs.getString("Token");
                Timestamp expiresAt = rs.getTimestamp("Expires_At");
                LocalDateTime expiresAtCalendar = expiresAt.toLocalDateTime();
                rememberMeToken = new RememberMeToken(tokenID, userID, username, token, expiresAtCalendar);
            }
        }
        catch (SQLException e) {
            System.out.println("getRememberMeTokenForUser Error: " + e.getMessage());
        }
        return rememberMeToken;
    }

    public static boolean isTokenValid(RememberMeToken token) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = token.getExpires_At();
        return now.isBefore(expiresAt);
    }


}
