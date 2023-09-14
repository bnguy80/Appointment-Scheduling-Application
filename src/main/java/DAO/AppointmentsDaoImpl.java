package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Objects;

/**
 * This class creates the Appointments database methods
 *
 * @author Brandon Nguyen
 */
public class AppointmentsDaoImpl {

    /**
     * Create ObservableList of Type, Executes SQL Query to find all distinct types of appointments then add to appointmentType ObservableList
     * @return ObservableList appointmentType with all distinct types of appointments
     */
    public static ObservableList<Type> getAppType() {
        ObservableList<Type> appointmentType = FXCollections.observableArrayList();
        try {
            String sql = "SELECT DISTINCT Type From appointments";
            PreparedStatement ps = Objects.requireNonNull(DBConnection.getConnection()).prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String type = rs.getString("Type");

                Type type1 = new Type(type);

                appointmentType.add(type1);
            }
        }
        catch (SQLException e) {
            System.out.println("appType Error: " + e.getMessage());
        }
        return appointmentType;
    }

    /**
     * Create ObservableList of Appointments, Executes SQL Query to find all appointments then add to getAllAppointments ObservableList
     * @return ObservableList getAllAppointments of all appointments
     * @throws SQLException for SQL Query
     */
    public static ObservableList<Appointments> getAllAppointments() throws SQLException {

        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
        allAppointments.clear();;

        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = Objects.requireNonNull(DBConnection.getConnection()).prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appId = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDescription = rs.getString("Description");
                String appLocation = rs.getString("Location");
                String appType = rs.getString("Type");
                Timestamp appStart = rs.getTimestamp("Start");
                LocalDateTime appStartCalendar = appStart.toLocalDateTime();
                Timestamp appEnd = rs.getTimestamp("End");
                LocalDateTime appEndCalendar = appEnd.toLocalDateTime();
                Timestamp appCreateDate = rs.getTimestamp("Create_Date");
                LocalDateTime appCDCalendar = appCreateDate.toLocalDateTime();
                String appCreateBy = rs.getString("Created_By");
                Timestamp appLastUpdate = rs.getTimestamp("Last_Update");
                LocalDateTime appLUCalendar = appLastUpdate.toLocalDateTime();
                String appLastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments appointments1 = new Appointments(appId, appTitle, appDescription, appLocation, appType,appStartCalendar, appEndCalendar, appCDCalendar, appCreateBy, appLUCalendar, appLastUpdatedBy, customerId, userId, contactId);

                allAppointments.add(appointments1);
            }
        }
        catch (SQLException e) {
            System.out.println("getAllAppointments Error: " + e.getMessage());
        }
        return allAppointments;
    }

    /**
     *Create Appointment object, Execute SQL Query to find all appointments then add to appointments1
     * @return Appointment appointments1
     * @throws SQLException for SQL Query
     */
    public static Appointments getAllAppointmentsList() throws SQLException {


        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = Objects.requireNonNull(DBConnection.getConnection()).prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appId = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDescription = rs.getString("Description");
                String appLocation = rs.getString("Location");
                String appType = rs.getString("Type");
                Timestamp appStart = rs.getTimestamp("Start");
                LocalDateTime appStartCalendar = appStart.toLocalDateTime();
                Timestamp appEnd = rs.getTimestamp("End");
                LocalDateTime appEndCalendar = appEnd.toLocalDateTime();
                Timestamp appCreateDate = rs.getTimestamp("Create_Date");
                LocalDateTime appCDCalendar = appCreateDate.toLocalDateTime();
                String appCreateBy = rs.getString("Created_By");
                Timestamp appLastUpdate = rs.getTimestamp("Last_Update");
                LocalDateTime appLUCalendar = appLastUpdate.toLocalDateTime();
                String appLastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments appointments1 = new Appointments(appId, appTitle, appDescription, appLocation, appType,appStartCalendar, appEndCalendar, appCDCalendar, appCreateBy, appLUCalendar, appLastUpdatedBy, customerId, userId, contactId);

                return appointments1;
            }
        }
        catch (SQLException e) {
            System.out.println("getAllAppointments Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get total count of Appointments in Database, Execute SQL Query to count all appointments with selected Customer ID
     * @param customerID Customer ID for appointments to count
     * @return int count of total appointments with selected Customer ID
     */
    public static int getAppCount(int customerID) {
        int appCountResult = 0;
        try {
            String sql = "SELECT COUNT(*) AS appCount FROM appointments WHERE Customer_ID = '" + customerID + "'";
            PreparedStatement ps = Objects.requireNonNull(DBConnection.getConnection()).prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                appCountResult = rs.getInt("appCount");
            }
        }
        catch (SQLException e) {
            System.out.println("getAppCount Error: " + e.getMessage());
        }
        System.out.println("appCountResult = " + appCountResult);
        return appCountResult;
    }

    /**
     * Get count of monthly appointments with selected type and month, Executes SQL Query to count all appointments with selected type and month
     * @param type Type selected to count
     * @param month Month Selected to count
     * @return int count of total of appointments by month and type selected
     */
    public static int getMonthAppCount(Type type, String month) {
        int monthAppCount = 0;
        try {
            String sql = "SELECT COUNT(*) AS monthCount FROM appointments WHERE Type = '" + type + "'" + "AND MONTHNAME(Start) = '" + month + "'";
            PreparedStatement ps = Objects.requireNonNull(DBConnection.getConnection()).prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                monthAppCount = rs.getInt("monthCount");
            }
        }
        catch (SQLException e) {
            System.out.println("getMonthAppCount Error: " + e.getMessage());
        }
        return monthAppCount;
    }

    /**
     * Create ObservableList of Appointments, Executes SQL Query to find all appointments with LocalDate of current month then add to monthlyapp ObservableList
     * @return ObservableList monthlyApp of all appointments LocalDate current week
     * @throws SQLException for SQL Query
     */
    public static ObservableList<Appointments> getMonthlyAppointments() throws SQLException {
        ObservableList<Appointments> monthlyApp = FXCollections.observableArrayList();

        try {
            LocalDate start = LocalDate.now();
            LocalDate end = LocalDate.now().plusMonths(1);
            String sql = "SELECT * FROM appointments WHERE Start >='" +  start + "'" + "AND Start <='" + end + "'";
            PreparedStatement ps = Objects.requireNonNull(DBConnection.getConnection()).prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appId = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDescription = rs.getString("Description");
                String appLocation = rs.getString("Location");
                String appType = rs.getString("Type");
                Timestamp appStart = rs.getTimestamp("Start");
                LocalDateTime appStartCalendar = appStart.toLocalDateTime();
                Timestamp appEnd = rs.getTimestamp("End");
                LocalDateTime appEndCalendar = appEnd.toLocalDateTime();
                Timestamp appCreateDate = rs.getTimestamp("Create_Date");
                LocalDateTime appCDCalendar = appCreateDate.toLocalDateTime();
                String appCreateBy = rs.getString("Created_By");
                Timestamp appLastUpdate = rs.getTimestamp("Last_Update");
                LocalDateTime appLUCalendar = appLastUpdate.toLocalDateTime();
                String appLastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments appointments1 = new Appointments(appId, appTitle, appDescription, appLocation, appType,appStartCalendar, appEndCalendar, appCDCalendar, appCreateBy, appLUCalendar, appLastUpdatedBy, customerId, userId, contactId);

                monthlyApp.add(appointments1);

                System.out.println("Monthly Appointments loaded");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("getMonthlyAppointments Error: " + e.getMessage());
        }
        return monthlyApp;
    }

    /**
     * Creates ObservableList of Appointments,  Executes SQL Query to find all appointments with LocalDate of current week then add to weeklyApp ObservableList
     * @return ObservableList weeklyApp of all appointments LocalDate current week
     * @throws SQLException for SQL Query
     */
    public static ObservableList<Appointments> getWeeklyAppointments() throws SQLException {
        ObservableList<Appointments> weeklyApp = FXCollections.observableArrayList();

        try {
            LocalDate start = LocalDate.now();
            LocalDate end = LocalDate.now().plusWeeks(1);
            String sql = "SELECT * FROM appointments WHERE Start >='" +  start + "'" + "AND Start <='" + end + "'";
            PreparedStatement ps = Objects.requireNonNull(DBConnection.getConnection()).prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appId = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDescription = rs.getString("Description");
                String appLocation = rs.getString("Location");
                String appType = rs.getString("Type");
                Timestamp appStart = rs.getTimestamp("Start");
                LocalDateTime appStartCalendar = appStart.toLocalDateTime();
                Timestamp appEnd = rs.getTimestamp("End");
                LocalDateTime appEndCalendar = appEnd.toLocalDateTime();
                Timestamp appCreateDate = rs.getTimestamp("Create_Date");
                LocalDateTime appCDCalendar = appCreateDate.toLocalDateTime();
                String appCreateBy = rs.getString("Created_By");
                Timestamp appLastUpdate = rs.getTimestamp("Last_Update");
                LocalDateTime appLUCalendar = appLastUpdate.toLocalDateTime();
                String appLastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments appointments1 = new Appointments(appId, appTitle, appDescription, appLocation, appType,appStartCalendar, appEndCalendar, appCDCalendar, appCreateBy, appLUCalendar, appLastUpdatedBy, customerId, userId, contactId);

                weeklyApp.add(appointments1);

                System.out.println("Weekly Appointments loaded");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("getWeeklyAppointments Error: " + e.getMessage());
        }
        return weeklyApp;
    }

    /**
     * Create ObservableList of Appointments, Executes SQL Query to find all appointments with selected User ID then add to userIdAppointments ObservableList
     * @param userId User ID for appointments to find
     * @return ObservableList userIdAppointments of all appointments by User ID selected
     * @throws SQLException for SQL Query
     */
    public static ObservableList<Appointments> getAppointmentFromUserId (int userId) throws SQLException{
        ObservableList<Appointments> userIdAppointments = FXCollections.observableArrayList();


        try {
            String sql = "SELECT * FROM appointments WHERE User_ID = '" + userId + "'";
            PreparedStatement ps = Objects.requireNonNull(DBConnection.getConnection()).prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appId = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDescription = rs.getString("Description");
                String appLocation = rs.getString("Location");
                String appType = rs.getString("Type");
                Timestamp appStart = rs.getTimestamp("Start");
                LocalDateTime appStartCalendar = appStart.toLocalDateTime();
                Timestamp appEnd = rs.getTimestamp("End");
                LocalDateTime appEndCalendar = appEnd.toLocalDateTime();
                Timestamp appCreateDate = rs.getTimestamp("Create_Date");
                LocalDateTime appCDCalendar = appCreateDate.toLocalDateTime();
                String appCreateBy = rs.getString("Created_By");
                Timestamp appLastUpdate = rs.getTimestamp("Last_Update");
                LocalDateTime appLUCalendar = appLastUpdate.toLocalDateTime();
                String appLastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int appUserId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments appointments1 = new Appointments(appId, appTitle, appDescription, appLocation, appType,appStartCalendar, appEndCalendar, appCDCalendar, appCreateBy, appLUCalendar, appLastUpdatedBy, customerId, appUserId, contactId);
                userIdAppointments.add(appointments1);

                //System.out.println("getAppointmentFromUserId End Time: " + appointments1.getAppEndTime());
            }
        }
        catch (SQLException e) {
            System.out.println("getAppointmentFromUserId" + e.getMessage());
        }

        return userIdAppointments;
    }

    /**
     * Create ObservableList of Appointments, Executes SQL Query to find all appointments with selected Customer ID then add to getAllApps ObservableList
     * @param customerId Customer ID for appointments to find
     * @return ObservableList getAllApps of all appointments by Customer ID selected
     */
    public static ObservableList<Appointments> getAppFromCustId (int customerId) {
        ObservableList<Appointments> getAllApps = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE Customer_ID ='" + customerId + "'";
            PreparedStatement ps = Objects.requireNonNull(DBConnection.getConnection()).prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appId = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDescription = rs.getString("Description");
                String appLocation = rs.getString("Location");
                String appType = rs.getString("Type");
                Timestamp appStart = rs.getTimestamp("Start");
                LocalDateTime appStartCalendar = appStart.toLocalDateTime();
                Timestamp appEnd = rs.getTimestamp("End");
                LocalDateTime appEndCalendar = appEnd.toLocalDateTime();
                Timestamp appCreateDate = rs.getTimestamp("Create_Date");
                LocalDateTime appCDCalendar = appCreateDate.toLocalDateTime();
                String appCreateBy = rs.getString("Created_By");
                Timestamp appLastUpdate = rs.getTimestamp("Last_Update");
                LocalDateTime appLUCalendar = appLastUpdate.toLocalDateTime();
                String appLastUpdatedBy = rs.getString("Last_Updated_By");
                int appUserId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments appointments1 = new Appointments(appId, appTitle, appDescription, appLocation, appType,appStartCalendar, appEndCalendar, appCDCalendar, appCreateBy, appLUCalendar, appLastUpdatedBy, customerId, appUserId, contactId);
                getAllApps.add(appointments1);
            }
        }
        catch (SQLException e) {
            System.out.println("getAppointmentFromUserId Error: " + e.getMessage());
        }
        return getAllApps;
    }

    /**
     * Created ObservableList of Appointments, Executes SQL Query to find all appointments with selected Contact ID then add to allContactApp ObservableList
     * @param contactId Contact ID for appointments to find
     * @return ObservableList allContactApp of all appointments by Contact ID selected
     * @throws SQLException for SQL Query
     */
    public static ObservableList<Appointments> getAppFromContId(int contactId) throws SQLException {
        ObservableList<Appointments> allContactApp = FXCollections.observableArrayList();
        //ObservableList<Appointments> contactForList =AppointmentsDaoImpl.getAllAppointments();

        try {
            String sql = "SELECT * FROM appointments WHERE Contact_ID = '" + contactId + "'";
            PreparedStatement ps = Objects.requireNonNull(DBConnection.getConnection()).prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appId = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDescription = rs.getString("Description");
                String appLocation = rs.getString("Location");
                String appType = rs.getString("Type");
                Timestamp appStart = rs.getTimestamp("Start");
                LocalDateTime appStartCalendar = appStart.toLocalDateTime();
                Timestamp appEnd = rs.getTimestamp("End");
                LocalDateTime appEndCalendar = appEnd.toLocalDateTime();
                Timestamp appCreateDate = rs.getTimestamp("Create_Date");
                LocalDateTime appCDCalendar = appCreateDate.toLocalDateTime();
                String appCreateBy = rs.getString("Created_By");
                Timestamp appLastUpdate = rs.getTimestamp("Last_Update");
                LocalDateTime appLUCalendar = appLastUpdate.toLocalDateTime();
                String appLastUpdatedBy = rs.getString("Last_Updated_By");
                int appUserId = rs.getInt("User_ID");
                int customerId = rs.getInt("Customer_ID");

                Appointments appointments1 = new Appointments(appId, appTitle, appDescription, appLocation, appType,appStartCalendar, appEndCalendar, appCDCalendar, appCreateBy, appLUCalendar, appLastUpdatedBy, customerId, appUserId, contactId);
                allContactApp.add(appointments1);
            }
        }
        catch (SQLException e) {
            System.out.println("getAppFromContId Error: " + e.getMessage());
        }
        return  allContactApp;
    }

    /**
     * Delete appointment, Executes SQL Query to delete appointments in Database with selected Appointment ID, return true if deleted, return false if didn't delete
     * @param id Appointment ID for appointments to delete
     * @return return true if delete successful, return false if delete was not successful
     */
    public static boolean deleteAppointments(int id) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = " + id;
            PreparedStatement ps = Objects.requireNonNull(DBConnection.getConnection()).prepareStatement(sql);
            int update = ps.executeUpdate(sql);
            if(update == 1) {
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
        return false;
    }

    /**
     * Delete appointment, Executes SQL Query to delete appointments in Database with selected Customer ID and Appointment ID, return true if successful, return false if not successful
     * @param customerId Customer ID for appoint to delete
     * @param appointmentId Appointment ID for appointment to delete
     */
    public static void deleteAppointmentsID(int customerId, int appointmentId) {
        try {
            String sql = "DELETE FROM appointments WHERE Customer_ID = '" + customerId + "'" +  "AND Appointment_ID = '" + appointmentId + "'";
            PreparedStatement ps = Objects.requireNonNull(DBConnection.getConnection()).prepareStatement(sql);
            ps.execute();
        }
        catch (SQLException e) {
            System.out.println("deleteAppointmentsID Error: " + e.getMessage());
        }
    }

    /**
     * Add new appointment to Database, Executes SQL Query to insert appointment table of Database with selected values
     * @param appTitle Title of appointment to add
     * @param appDescription Description of appointment to add
     * @param appLocation Location of appointment to add
     * @param appType Type of appointment to add
     * @param appStartTime Start Time of appointment to add
     * @param appEndTime End Time of appointment to add
     * @param appCreateDate Create Date of appointment add
     * @param appCreatedBy Creator of appointment to add
     * @param lastUpdate Last Update Time of appointment to add
     * @param appLastUpdatedBy Last Updated By Creator of appointment to add
     * @param customerId Customer ID of appointment to add
     * @param userId User ID of appointment to add
     * @param contactId Contact ID of appointment to add
     */
    public static void addAppointments(String appTitle, String appDescription, String appLocation, String appType, Timestamp appStartTime, Timestamp appEndTime, Timestamp appCreateDate, String appCreatedBy, Timestamp lastUpdate, String appLastUpdatedBy, int customerId, int userId, int contactId) {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = Objects.requireNonNull(DBConnection.getConnection()).prepareStatement(sql);
            ps.setString(1, appTitle);
            ps.setString(2, appDescription);
            ps.setString(3, appLocation);
            ps.setString(4, appType);
            ps.setTimestamp(5, appStartTime); //Datepicker in AppointmentController
            ps.setTimestamp(6, appEndTime); //Datepicker in AppointmentController
            ps.setTimestamp(7, appCreateDate); //Create Timestamp.valueOf(LocalDateTime.now())
            ps.setString(8, appCreatedBy);
            ps.setTimestamp(9, lastUpdate); //Create Timestamp.valueOf(LocalDateTime.now())
            ps.setString(10, appLastUpdatedBy);
            ps.setInt(11, customerId);
            ps.setInt(12, userId);
            ps.setInt(13, contactId);

            ps.execute();
        }
        catch (SQLException e) {
            System.out.println("addAppointments Error" + e.getMessage());
        }
    }

    /**
     * Update existing appointment with inputted values, Executes SQL Query to update existing appointment with values inputted in AppointmentsModify Screen of application
     * @param appID Appointment ID of appointment to modify
     * @param appTitle Title of appointment to modify
     * @param appDescription Description of appointment to modify
     * @param appLocation Location of appointment to modify
     * @param appType Type of appointment to modify
     * @param appStartTime Start Time of appointment to modify
     * @param appEndTime End Time of appointment to modify
     * @param lastUpdate Last Update Time of appointment modify
     * @param appLastUpdatedBy Last Updated By Person of appointment to modify
     * @param customerId Customer ID of appointment to modify
     * @param userId User ID of appointment to modify
     * @param contactId Contact ID of appointment to modify
     * @return return true if modify successful, return false if modify was not successful
     */
    public static boolean modifyAppointments(int appID, String appTitle, String appDescription, String appLocation, String appType, Timestamp appStartTime, Timestamp appEndTime,  Timestamp lastUpdate, String appLastUpdatedBy, int customerId, int userId, int contactId) {
        try {
            String sql = "UPDATE appointments SET Appointment_ID = ?, Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ? Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = Objects.requireNonNull(DBConnection.getConnection()).prepareStatement(sql);
            ps.setInt(1, appID);
            ps.setString(2, appTitle);
            ps.setString(3, appDescription);
            ps.setString(4, appLocation);
            ps.setString(5, appType);
            ps.setTimestamp(6, appStartTime);
            ps.setTimestamp(7, appEndTime);
            ps.setTimestamp(8, lastUpdate);
            ps.setString(9, appLastUpdatedBy);
            ps.setInt(10, customerId);
            ps.setInt(11, userId);
            ps.setInt(12, appID);

            ps.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            System.out.println("modifyAppointments Error: " + e.getMessage());
        }
        return false;
    }
}
