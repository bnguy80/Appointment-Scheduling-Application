package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.RememberMeToken;

import java.security.SecureRandom;
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
        Timestamp expiresAt = Timestamp.valueOf(LocalDateTime.now().plusMinutes(1));
        insertNewTokenForUser(userId, username, token, expiresAt);
    }
    // Create a new method to generateRandomToken
    private static String generateRandomToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[64];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

}
