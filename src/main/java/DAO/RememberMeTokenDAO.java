package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.RememberMeToken;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class RememberMeTokenDAO {
    public static void createNewTokenForUser(int userId, String username, String token, Timestamp expiresAt) {
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
}
