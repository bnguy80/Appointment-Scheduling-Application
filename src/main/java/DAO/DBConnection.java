package DAO;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class creates the Database connection
 *
 * @author Brandon Nguyen
 */

/*    //jdbc:mysql://hostname:port/databasename
    private static final String url = "jdbc:mysql://" + localHost + ":3066/" + DBName;*/

public class DBConnection {

    /**
     * values used to connect to Database
     */
    private static final String DBName = "client_schedule";
    private static final String localHost = "127.0.0.1";
    private static final String url1 = "jdbc:mysql://127.0.0.1/client_schedule" + "?connectionTimeZone=SERVER"; //JDBC drive v.8.0.33
    private static final String username = "root"; //sqlUser
    private static final String password = "root"; //Passw0rd!
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; //JDBC Driver not driver
    public static Connection connection;

    //Not sure if this is needed to connected to MySQL DataBase

/*    public void DatabaseConnectionManger(String host, String dbName, String username, String password) {

    }*/

    //Establishes connection with MySQL Database

    /**
     * Establish connection to Database
     */
    public static void connect() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(url1, username, password);
            System.out.println("Connected to MySQL DataBase");
        }
            catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
    }

    /**
     * Get connection for SQL Queries
     * @return
     */
    public static Connection getConnection() {
        try {
            return connection;
        }
        catch (Exception e)  {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Closes connection to Database
     */
    public static void closeConnection() {
        try {
            connection.close();
        }
        catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
