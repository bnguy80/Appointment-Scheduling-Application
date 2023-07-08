package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class creates the Customers database methods
 *
 * @author Brandon Nguyen
 */
public class CustomerDaoImpl {

    /**
     * Creates ObservableList of Customers, Executes SQL Query to find all customers then add to allCustomers ObservableList
     * @return ObservableList allCustomers of all customers
     * @throws SQLException for SQL Query
     */
    public static ObservableList<Customers> getAllCustomers() throws SQLException{
        //List of all customers
        ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

        allCustomers.clear();

        try {
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhoneNumber = rs.getString("Phone");
                Timestamp customerCreateDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDateCalendar = customerCreateDate.toLocalDateTime();
                String customerCreatedBy = rs.getString("Created_By");
                Timestamp customerLastUpdated = rs.getTimestamp("Last_Update");
                LocalDateTime lastUpdateCalendar = customerLastUpdated.toLocalDateTime();
                String customerLastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");

                Customers customers1 = new Customers(customerId, customerName, customerAddress, customerPostalCode, customerPhoneNumber, createDateCalendar, customerCreatedBy, lastUpdateCalendar, customerLastUpdatedBy, divisionId);
                allCustomers.add(customers1);
            }
        }
        catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("getAllCustomers Error: " + e.getMessage());
            //Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, e);
        }
        return allCustomers;
    }

    /**
     * Creates Customers object, Executes SQL Query to find all customers with selected Customer ID then add to customers1
     * @param customerId Customer ID for customers to find
     * @return Customers customers1 of all customers with selected Customer ID
     */
    public static Customers getCustomersFromCustId(int customerId) {
        try {
            String sql = "SELECT * FROM customers WHERE Customer_ID = '" + customerId + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhoneNumber = rs.getString("Phone");
                Timestamp customerCreateDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDateCalendar = customerCreateDate.toLocalDateTime();
                String customerCreatedBy = rs.getString("Created_By");
                Timestamp customerLastUpdated = rs.getTimestamp("Last_Update");
                LocalDateTime lastUpdateCalendar = customerLastUpdated.toLocalDateTime();
                String customerLastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");

                Customers customers1 = new Customers(customerId, customerName, customerAddress, customerPostalCode, customerPhoneNumber, createDateCalendar, customerCreatedBy, lastUpdateCalendar, customerLastUpdatedBy, divisionId);
                return customers1;
            }
        }
        catch (SQLException e) {
            System.out.println("getCustomersFromCustId" + e.getMessage());
        }
        return  null;
    }

    /**
     * Add new customer to Database, Executes SQL Query to insert customer table of Database with selected values
     * @param customerName Name of customer to add
     * @param customerAddress Address of customer to add
     * @param customerPostalCode Postal Code of customer to add
     * @param customerPhoneNumber Phone Number of customer to add
     * @param customerCreateDate Create Date of customer to add
     * @param customerCreatedBy Creator of customer to add
     * @param customerLastUpdated Last Update Time of customer to add
     * @param customerLastUpdatedBy Last Updated By Creator of customer to add
     * @param Division_ID Divison ID of customer to add
     */
    public static void addCustomers(String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber, Timestamp customerCreateDate, String customerCreatedBy, Timestamp customerLastUpdated, String customerLastUpdatedBy, int Division_ID) {
        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setString(1, customerName);
            ps.setString(2, customerAddress);
            ps.setString(3, customerPostalCode);
            ps.setString(4, customerPhoneNumber);
            ps.setTimestamp(5, customerCreateDate);
            ps.setString(6, customerCreatedBy);
            ps.setTimestamp(7, customerLastUpdated);
            ps.setString(8, customerLastUpdatedBy);
            ps.setInt(9, Division_ID);

            ps.execute();

/*            String sql = "INSERT INTO Customer_Name ='" + customerName + "', Address='" + customerAddress + "', Postal_Code='" + customerPostalCode + "', Phone='" + customerPhoneNumber + "', Create_Date='" + customerCreateDate + "', Created_By='" + customerCreatedBy + "', Last_Update='" + customerLastUpdated + "', Last_Updated_By='" + customerLastUpdatedBy + "', Division_Id='" + Division_ID + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);*/
        }
        catch (SQLException e) {
            System.out.println("addCustomers Error" + e.getMessage());
        }
    }
    public static boolean deleteCustomer(int customerId) throws SQLException{
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID='" + customerId + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.execute();

            return true;
        }
        catch (SQLException e) {
            System.out.println("deleteCustomer Error: " + e.getMessage());
        }
        return false;
    }

    /**
     * Update existing customer with inputted values, Executes SQL Query to update existing customer with values inputted in CustomersModify Screen of application
     * @param customerId Customer ID of customer to modify
     * @param customerName Name of customer to modify
     * @param customerAddress Address of customer to modify
     * @param customerPostalCode Postal Code of customer to modify
     * @param phoneNumber Phone Number of customer to modify
     * @param lastUpdate Last Update Time of customer to modify
     * @param lastUpdatedBy Last Updated By Person to modify
     * @param divisionId Division ID of customer to modify
     * @return true if modify successful, return false if modify was successful
     * @throws SQLException for SQL Query
     */
    public static boolean modifyCustomer(int customerId, String customerName, String customerAddress, String customerPostalCode, String phoneNumber,Timestamp lastUpdate, String lastUpdatedBy, int divisionId) throws SQLException {
        try {
            String sql = "UPDATE customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setString(2, customerName);
            ps.setString(3, customerAddress);
            ps.setString(4, customerPostalCode);
            ps.setString(5, phoneNumber);
            ps.setTimestamp(6, lastUpdate);
            ps.setString(7, lastUpdatedBy);
            ps.setInt(8, divisionId);

            ps.executeUpdate();
            return true;
        }
        catch(SQLException e) {
            System.out.println("modifyCustomer Error: " + e.getMessage());
        }
        return false;
    }
}
