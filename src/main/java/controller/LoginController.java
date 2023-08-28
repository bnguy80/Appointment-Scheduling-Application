package controller;

import DAO.AppointmentsDaoImpl;
import DAO.DBConnection;
import DAO.LoginDaoImpl;
import DAO.RememberMeTokenDAO;
import inteface.GeneralInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
import model.GlobalUsername;
import model.RememberMeToken;
import schedule.main.Main;

import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static controller.MainMenuController.confirmDialog;
import static controller.MainMenuController.errorDialog;

/**
 * Controller class that provides control for Login screen of application
 *
 * @author Brandon Nguyen
 */
public class LoginController implements Initializable {

    Locale locale = Locale.getDefault();
    ResourceBundle rs = ResourceBundle.getBundle("bundle.lang", locale);

    /**
     * TimeZone of Default
     */
    TimeZone timeZone = TimeZone.getDefault();
    /**
     *Set LocationAutoLabel by TimeZone
     */
    String tzs = timeZone.getID();

    /**
     * Login Button of Login Screen
     */
    @FXML
    private Button LoginButton;

    /**
     * Login Title of Login Screen
     */
    @FXML
    private Label LoginTitleLabel;

    /**
     * Exit Button of Login Screen
     */
    @FXML
    private Button ExitButton;

    /**
     * Username Label of UsernameField
     */
    @FXML
    private Label UsernameLabel;

    /**
     * PasswordLabel of PasswordField
     */
    @FXML
    private Label PasswordLabel;

    /**
     * LocationLabel of LocationAutoLabel
     */
    @FXML
    private Label LocationLabel;

    /**
     * Location of User of Login Screen
     */
    @FXML
    private Label LocationAutoLabel;

    /**
     * Username Field of Login Screen
     */
    @FXML
    private TextField UsernameField;

    /**
     * Password Field of Login Screen
     */
    @FXML
    private TextField PasswordFieldBox;

    /**
     * Remember Me Checkbox of Login Screen
     */
    @FXML
    private CheckBox rememberMeCheckbox;

    /**
     * List of scheduled appointments of current user
     */
    ObservableList<Appointments> scheduledAppointments = FXCollections.observableArrayList();


    /**
     * User ID of current user
     */
    private static int currentUserId;

    /**
     * Get user ID of user logging in
     * @param actionEvent set currentUserId of current user
     */
    @FXML
    public void onUsernameEnter(ActionEvent actionEvent){

        String userName = UsernameField.getText();
        currentUserId = LoginDaoImpl.getUserIdFromUsername(userName);

    }

    /**
     * Filters appointments in next 15 minutes of current user, checks scheduledAppointments ObservableList populated by getAppointment15MinList()
     * errorDialog if No appointments within 15 minutes for current user
     * confirmDialog if appointments within 15 minutes for current user
     */
    private void appointmentsFiltered() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime now15 = now.plusMinutes(15);
        System.out.println("Now: " + now);
        System.out.println("Now15: " + now15);

        FilteredList<Appointments> appointmentsFilteredList = new FilteredList<>(scheduledAppointments);

        /**
         * lambda expression get Appointments Alerts within 15minutes
         * appointments Maps adds Appointment ID, StartTime, Date to concat and show on custom message
         */
        appointmentsFilteredList.setPredicate(row -> {
                    LocalDateTime rowDate = row.getAppStartTime();
                    return rowDate.isAfter(now.minusMinutes(1)) && rowDate.isBefore(now15);
                }
                );
        //List<Appointments> appointments = new ArrayList<>(appointmentsFilteredList);
        Map<Appointments, Integer> appointmentsIntegerMap = new HashMap<>();
        Map<Appointments, LocalDateTime> appointmentsLocalDateTimeMap = new HashMap<>();
        Map<Appointments, LocalDate> appointmentsLocalDateMap = new HashMap<>();

        if(appointmentsFilteredList.isEmpty()) {
            errorDialog(rs.getString("NoAppointments"), "");
        }
        else {
            appointmentsFilteredList.forEach(appointments -> {
                int appID = appointments.getAppId();
                appointmentsIntegerMap.put(appointments, appID);
                LocalDateTime appStart = appointments.getAppStartTime();
                appointmentsLocalDateTimeMap.put(appointments, appStart);
                LocalDate date = appStart.toLocalDate();
                appointmentsLocalDateMap.put(appointments, date);
            });

            StringBuilder appId = new StringBuilder("Appointment ID: "); {
                for (int appID : appointmentsIntegerMap.values()) {
                    appId.append(appID).append(", ");
                }
                if(appId.length() > 14) {
                    appId.setLength(appId.length() - 2);
                }
            }

            StringBuilder appStartTime = new StringBuilder(); {
                for(LocalDateTime appStart : appointmentsLocalDateTimeMap.values()) {
                    appStartTime.append(appStartTime).append(", ");
                }
                if(appStartTime.length() > 14) {
                    appStartTime.setLength(appStartTime.length() - 2);
                }
            }

            StringBuilder appDate = new StringBuilder();
                for(LocalDate date : appointmentsLocalDateMap.values()) {
                    appDate.append(date).append(", ");
                }

            if(confirmDialog(rs.getString("YesAppointments"), "Notify: " + appId + "Start time: " + appStartTime + " Date: " + appDate)) {
                System.out.println("Finished appointmentsAlertFiltered()");
            }
/*            for (Map.Entry<Appointments, Integer> entry : appointmentsIntegerMap.entrySet()) {
                //Appointments appointments = entry.getKey();
                int appId = entry.getValue();
                confirmDialog(rs.getString("YesAppointments"), "Appointment ID: " + appId);
            }*/
        }
    }

    /**
     * Get all appointments of current user, add to scheduledAppointments ObservableList to be checked in appointmentsFiltered()
     */
    private void  getAppointment15MinList() {
        LocalDateTime now = LocalDateTime.now();
        //LocalDateTime now15 = now.plusMinutes(15);
        ZoneId localZoneId = ZoneId.systemDefault();
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime localZDT = now.atZone(localZoneId);
        //LocalDateTime ldt =localZDT.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        //LocalDateTime ldt15 =ldt.plusMinutes(15);

        //get int User_ID to search for appointments between ldt and ldt15 for notification query User_ID = from getUserIdFromUsername(String userName) so getAppointmentFromUserId (int userId).
        try {
            System.out.println("getAppointment15MinList() Start------");
            //"SELECT * FROM appointments WHERE Start BETWEEN '" + ldt + "' AND '" + ldt15 + "' AND " + "User_ID= " + userId //before change to generic SQL

            /*String sql = "SELECT * FROM appointments WHERE Start BETWEEN '" + now + "' AND '" + now15 + "' AND " + "User_ID = '" + currentUser + "'";*/

            String sql = "SELECT * FROM appointments WHERE User_ID = '" + currentUserId + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appId = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDescription = rs.getString("Description");
                String appLocation = rs.getString("Location");
                String appType = rs.getString("Type");

                Timestamp appStart = rs.getTimestamp("Start");
                ZonedDateTime utcAppStart = appStart.toLocalDateTime().atZone(utcZoneId);
                //ZonedDateTime appStartCalendarZDT = utcAppStart.withZoneSameInstant(localZoneId);
                LocalDateTime appStartCalendar = utcAppStart.toLocalDateTime();

                Timestamp appEnd = rs.getTimestamp("End");
                ZonedDateTime utcAppEnd = appEnd.toLocalDateTime().atZone(utcZoneId);
                //ZonedDateTime appEndCalendarZDT = utcAppEnd.withZoneSameInstant(localZoneId);
                LocalDateTime appEndCalendar = utcAppEnd.toLocalDateTime();

                Timestamp appCreateDate = rs.getTimestamp("Create_Date");
                LocalDateTime appCDCalendar = appCreateDate.toLocalDateTime();

                String appCreateBy = rs.getString("Created_By");
                Timestamp appLastUpdate = rs.getTimestamp("Last_Update");
                LocalDateTime appLUCalendar = appLastUpdate.toLocalDateTime();
                String appLastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int appUserId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                //System.out.println("Have appointments = " + appointments1.getAppDescription());
                System.out.println("Appointment_ID: " + appId);
                System.out.println("Local StartTime: " + appStartCalendar);
                System.out.println("Local EndTime: " + appEndCalendar);
                System.out.println("Title: " + appTitle);
                System.out.println("Type " + appTitle) ;
                System.out.println("Customer_ID: " + customerId);
                System.out.println("Customer_Name: " + appCreateBy);

                Appointments appointments1 = new Appointments(appId, appTitle, appDescription, appLocation, appType,appStartCalendar, appEndCalendar, appCDCalendar, appCreateBy, appLUCalendar, appLastUpdatedBy, customerId, appUserId, contactId);
                scheduledAppointments.add(appointments1);

                System.out.println("getAppointment15MinList() End-----");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * lambda expression return Login Alert @time
     */
    GeneralInterface getLoginAlert = s -> "Login Invalid at: " + s;

    /**
     * Create Login_Alert_Record.txt log file, inserts Timestamp and various errors for current user
     * @param info
     */
    public void loginAlertTxt(String info) {
        LocalDateTime now = LocalDateTime.now();
        ZoneId localZoneId = ZoneId.systemDefault();
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime utcNow = now.atZone(localZoneId).withZoneSameInstant(utcZoneId);
        Timestamp timeStampLocalZDT = Timestamp.valueOf(utcNow.toLocalDateTime());
        String timeStampLocalZDTST = timeStampLocalZDT.toString();

        try {
            String fileName = "Login_Alert_Record.txt";
            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.append(getLoginAlert.getMessage(timeStampLocalZDTST)).append(" ").append(info).append(" ").append("\n");
            System.out.println("Login_Alert_Recorded");
            writer.flush();
            writer.close();

        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Validates current user credentials stored in Database
     * @param actionEvent validates current user and login application action
     * @throws IOException for FXMLLoader
     * @throws SQLException for getAppointmentFromUserId()
     * errorDialogs if login errors
     */
    //"No username not working correctly rn"
    @FXML
    public void onActionLogin(ActionEvent actionEvent) throws IOException, SQLException {

        String username = UsernameField.getText();
        String password = PasswordFieldBox.getText();

        boolean validateLogin = LoginDaoImpl.validateLogin(username, password);
        boolean validateUsername = LoginDaoImpl.validateUsername(username);
        boolean validatePassword = LoginDaoImpl.validatePassword(password);

        int userId = LoginDaoImpl.getUserIdFromUsername(username);
        currentUserId = LoginDaoImpl.getUserIdFromUsername(username);

        if(password.isEmpty() || password.isBlank()) {
            errorDialog(rs.getString("NoPassword"), "");
            loginAlertTxt("No password");
        }

        //Problems between (No password & No username) & (Password Incorrect & Username Incorrect)
        else if(validateLogin) {
            GlobalUsername.username = UsernameField.getText();

            ObservableList<Appointments> appointments = AppointmentsDaoImpl.getAppointmentFromUserId(userId);
            if (rememberMeCheckbox.isSelected()) {
                RememberMeTokenDAO.generateAndInsertTokenForUser(userId, username);
                System.out.println("Remember Me Token Generated");

                RememberMeToken token = RememberMeTokenDAO.getRememberMeTokenForUser(userId);
//               if (token != null && RememberMeTokenDAO.isTokenValid(token)) {
//
//
//               }
            }
            if (appointments != null) {
                getAppointment15MinList();
                appointmentsFiltered();
            }
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getClassLoader()).getResource("MainMenu-view.fxml"));
            Parent scene = loader.load();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
             if(!validatePassword && validateUsername) {
                errorDialog(rs.getString("Error"), rs.getString("PasswordError"));
                loginAlertTxt("Incorrect password: " + PasswordFieldBox.getText());
            }else if(!validateUsername && validatePassword) {
                errorDialog(rs.getString("Error"), rs.getString("UsernameError"));
                loginAlertTxt("Incorrect username: " + UsernameField.getText());
            }
             else {
                 errorDialog(rs.getString("Error"), rs.getString("LoginError"));
                 loginAlertTxt("Login Error");
             }
        }
    }

    /**
     * Exit application
     * @param actionEvent Exit application action
     * confirmDialog for exit application
     */
    public void onActionExit(ActionEvent actionEvent) {
        if (confirmDialog(rs.getString("AreYouSure"), "")) {
            System.exit(0);
        }
    }

    /**
     * Set Login Screen Text by location
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resourceBundle
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocationAutoLabel.setText(tzs);
        LoginTitleLabel.setText(rs.getString("Title"));
        UsernameLabel.setText(rs.getString("Username"));
        PasswordLabel.setText(rs.getString("Password"));
        LocationLabel.setText(rs.getString("Location"));
        LoginButton.setText(rs.getString("Login"));
        ExitButton.setText(rs.getString("Exit"));
    }

}