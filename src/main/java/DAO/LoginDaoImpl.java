package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class creates Users Database methods
 *
 * @author Brandon Nguyen
 */
public class LoginDaoImpl {

    /**
     * Creates ObservableList of Users, Executes SQL Query to find all users then add to allUsers ObservableList
     * @return ObservableList allUsers with all users
     */
    public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> allUsers = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int userId = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDateCalendar = createDate.toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                LocalDateTime lastUpdateCalendar = lastUpdate.toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                Users users1 = new Users(userId, username, password, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdatedBy);
                allUsers.add(users1);
            }
        }
        catch (SQLException e) {
            System.out.println("getAllUsers Error: " + e.getMessage());
        }
        return allUsers;
    }

    /**
     * Create Users object, Execute SQL Query to find all users with selected USER ID then add to users1
     * @param userId User ID for users to find
     * @return Users users1, return null if Query failed
     */
    public static Users getUsersFromUserId(int userId) {
        try {
            String sql = "SELECT * FROM users WHERE User_ID = '" + userId + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDateCalendar = createDate.toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                LocalDateTime lastUpdateCalendar = lastUpdate.toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                Users users1 = new Users(userId, username, password, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdatedBy);
                return users1;
            }
        }
        catch (SQLException e) {
            System.out.println("getUsersFromUserId Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get User ID from Users Username, Execute SQL Query to get User ID from username
     * @param userName Username to get User ID
     * @return getUserIDResult if successful, return userID = 0 if Query failed
     */
    public static int getUserIdFromUsername(String userName) {
        int userId = 0;

        try {
            String sql = "SELECT User_ID FROM users WHERE User_Name = '" + userName + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                userId = rs.getInt("User_ID");

                int getUserIdResult = userId;

                System.out.println("getUserIdFromUsername #: " + userId);

                return getUserIdResult;
            }
        }
        catch (SQLException e) {
            System.out.println("getUserFromUserId Error:" + e.getMessage());
        }
        return userId;
    }

    /**
     * Validate Username if is in Database, Executes Query to select username provided, return true if found return false if not found
     * @param username Username to validate
     * @return return true if found username, return false if not found
     */
    public static boolean validateUsername(String username) {
        try {
            //"SELECT * From users WHERE User_Name =" + username; before change to generic SQL

            String sql = "SELECT * From users WHERE User_Name = '" + username + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println("Error: validateUsername" + e.getMessage());
        }
        return false;
    }

    /**
     * Validate Password if is in Database, Execute Query to select password provided, return true if found in return false if not found
     * @param password Password to validate
     * @return return true if found password, return false if not found
     */
    public static boolean validatePassword(String password) {
        try {
            String sql = "SELECT * FROM users WHERE Password = '" + password + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println("Error: validatePassword" + e.getMessage());
        }
        return false;
    }

    /**
     * Validate Login of user with provided credentials in User Table of Database, Executes Query to validate username and password provided, return true if found return false if not found
     * @param username Username to validate
     * @param password Password to validate
     * @return return true if found in Users table, return false if not found
     */
    public static boolean validateLogin(String username, String password) {

        try {
            String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println("Error: validateLogin" + e.getMessage());
        }
        return false;
    }


}
