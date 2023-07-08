package model;

import java.time.LocalDateTime;

/**
 * Model of users
 *
 * @author Brandon Nguyen
 */
public class Users {

    /**
     * User ID
     */
    private int userId;

    /**
     * Username
     */
    private String username;

    /**
     * Password
     */
    private String password;

    /**
     * Create Date
     */
    private LocalDateTime userCreateDate;

    /**
     * Creator
     */
    private String userCreatedBy;

    /**
     * Last Update Time
     */
    private LocalDateTime userLastUpdate;

    /**
     * Last Update By Person
     */
    private String userLastUpdateBy;

    /**
     * New users instance constructor
     * @param userId
     * @param username
     * @param password
     * @param userCreateDate
     * @param userCreatedBy
     * @param userLastUpdate
     * @param userLastUpdateBy
     */
    public Users(int userId, String username, String password, LocalDateTime userCreateDate, String userCreatedBy, LocalDateTime userLastUpdate, String userLastUpdateBy) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userCreateDate = userCreateDate;
        this.userCreatedBy = userCreatedBy;
        this.userLastUpdate = userLastUpdate;
        this.userLastUpdateBy = userLastUpdateBy;
    }

    /**
     * Setters and Getters
     * @return
     */

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getUserCreateDate() {
        return userCreateDate;
    }

    public void setUserCreateDate(LocalDateTime userCreateDate) {
        this.userCreateDate = userCreateDate;
    }

    public String getUserCreatedBy() {
        return userCreatedBy;
    }

    public void setUserCreatedBy(String userCreatedBy) {
        this.userCreatedBy = userCreatedBy;
    }

    public LocalDateTime getUserLastUpdate() {
        return userLastUpdate;
    }

    public void setUserLastUpdate(LocalDateTime userLastUpdate) {
        this.userLastUpdate = userLastUpdate;
    }

    public String getUserLastUpdateBy() {
        return userLastUpdateBy;
    }

    public void setUserLastUpdateBy(String userLastUpdateBy) {
        this.userLastUpdateBy = userLastUpdateBy;
    }

    /**
     * @Override Username toString()
     * @return Username
     */
    @Override
    public String toString() {
        return username;
    }
}
