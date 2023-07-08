package controller;

import DAO.AppointmentsDaoImpl;
import DAO.ContactsDaoImpl;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Appointments;
import model.Contacts;
import model.Type;
import schedule.main.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static controller.MainMenuController.confirmDialog;
import static controller.MainMenuController.errorDialog;

/**
 * Controller class that provides control for report screen of application
 *
 * @author Brandon Nguyen
 */
public class ReportsController implements Initializable {

    /**
     * Appointment ID of Reports Table
     */
    @FXML
    private TableColumn<Appointments, Integer> AppointmentID;

    /**
     * Customer ID of Reports Table
     */
    @FXML
    private TableColumn<Appointments, Integer> CustomerID;

    /**
     * Appointment Description of Reports Table
     */
    @FXML
    private TableColumn<Appointments, String> Description;

    /**
     * Appointment End Time of Reports Table
     */
    @FXML
    private TableColumn<Appointments, Calendar> End;

    /**
     * Month ComboBox of Reports Table
     */
    @FXML
    private ComboBox<String> MonthCombo;

    /**
     * Month Label of MonthCombo
     */
    @FXML
    private Label MonthLabel;

    /**
     * Title of Reports Screen
     */
    @FXML
    private Label TitleLabel;

    /**
     * Reports Table View
     */
    @FXML
    private TableView<Appointments> ReportsTableView;

    /**
     * Schedule ComboBox of Reports Table
     */
    @FXML
    private ComboBox<Contacts> ScheduleCombo;

    /**
     * Contact ID of Reports Table
     */
    @FXML
    private TableColumn<Contacts, String> Contact;

    /**
     * Schedule Label of ScheduleCombo
     */
    @FXML
    private Label ScheduleLabel;

    /**
     * Appointment Start Time of Reports Table
     */
    @FXML
    private TableColumn<Appointments, Calendar> Start;

    /**
     * Appointment Title of Reports Table
     */
    @FXML
    private TableColumn<Appointments, String> Title;

    /**
     * Total of: Label of Reports Table
     */
    @FXML
    private Label TotalDescriptionLabel;

    /**
     * Total Count Of Customer Appointments of Reports Table
     */
    @FXML
    private Label TotalNumberLabel;

    /**
     * Appointment Type of Reports Table
     */
    @FXML
    private TableColumn<Appointments, Type> Type;

    /**
     * Type ComboBox of Reports Table
     */
    @FXML
    private ComboBox<Type> TypeCombo;

    /**
     * Hour ComboBox of Reports Table
     */
    @FXML
    private ComboBox<Integer> HourCombo;

    /**
     * Appointments Time Frame Description Label of Reports Table
     */
    @FXML
    private Label TotalTimeFrameLabel;

    /**
     * Appointments in Time Frame of Reports Table
     */
    @FXML
    private Label TotalLabel;

    /**
     * List of months for Month ComboBox
     */
    ObservableList<String> months = FXCollections.observableArrayList(Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));

    /**
     * List of hours for Hour ComboBox
     */
    ObservableList<Integer> hours = FXCollections.observableArrayList(Arrays.asList(1, 2, 3, 4, 5 , 6, 7 , 8 , 9, 10));

    /**
     * List of appointments to set Reports Table View
     */
    ObservableList<Appointments> appointments = FXCollections.observableArrayList();


    /**
     * Load MainMenu-view, MainMenuController
     * @param event cancel Reports-view action
     * @throws IOException for FXMLLoader
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getClassLoader()).getResource("MainMenu-view.fxml"));
        Parent scene = loader.load();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Set Reports Table View with appointments by selected Contact ID
     * @param event set Reports Table View by selected Contact ID action
     */
    @FXML
    void onActionScheduleCombo(ActionEvent event) {

        Contacts selectedContact = ScheduleCombo.getValue();
        int selectedContactId = selectedContact.getContactId();
        appointments.clear();

        try {
            appointments.addAll(AppointmentsDaoImpl.getAppFromContId(selectedContactId));
            ReportsTableView.setItems(appointments);
            System.out.println("Appointment_ID = " + AppointmentID);
        }
        catch (SQLException e) {
            System.out.println("onActionScheduleCombo Error: " + e.getMessage());
        }

    }

    /**
     * Set TotalNumberLabel with number of appointments by selected type of appointment and month
     * @param event get total number of appointments by type and month action
     */
    //Count not showing, effected like getAppointment15Min...Fixed
    @FXML
    void onActionTypeCombo(ActionEvent event) {
        Type typeSelected = TypeCombo.getValue();
        String monthSelected = MonthCombo.getValue();
        //int total = AppointmentsDaoImpl.getMonthAppCount(typeSelected, monthSelected);
        TotalNumberLabel.setText(String.valueOf(AppointmentsDaoImpl.getMonthAppCount(typeSelected, monthSelected)));
    }

    /**
     * Set TotalNumberLabel with number of appointments by selected type of appointment and month
     * @param event get total number of appointments by type and month action
     */
    //Count not showing, effected like getAppointment15Min...Fixed
    @FXML
    void onActionMonthCombo(ActionEvent event) {
        Type typeSelected = TypeCombo.getValue();
        String monthSelected = MonthCombo.getValue();
        //int total = AppointmentsDaoImpl.getMonthAppCount(typeSelected, monthSelected);
        TotalNumberLabel.setText(String.valueOf(AppointmentsDaoImpl.getMonthAppCount(typeSelected, monthSelected)));
    }

    @FXML
    void onActionTime(ActionEvent event) throws SQLException{
        int hourSelected = HourCombo.getValue();
        ObservableList<Appointments> allAppointments = AppointmentsDaoImpl.getAllAppointments();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowHour = now.plusHours(hourSelected);
        System.out.println("Now: " + now);
        System.out.println("NowHour: " + nowHour);

        FilteredList<Appointments> appointmentsFilteredList = new FilteredList<>(allAppointments);

        /**
         * lambda expression get Appointments Alerts within hour selected
         */
        appointmentsFilteredList.setPredicate(row -> {
                    LocalDateTime rowDate = row.getAppStartTime();
                    return rowDate.isAfter(now.minusMinutes(1)) && rowDate.isBefore(nowHour);
                }
        );

        if(appointmentsFilteredList.isEmpty()) {
            System.out.println("empty");
            TotalLabel.setText("No");
        }
        else {
            System.out.println("Have appointments");
            TotalLabel.setText("Yes");
        }
    }

    /**
     * SetItems of ComboBoxes
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

        try {
            HourCombo.setItems(hours);
            ScheduleCombo.setItems(ContactsDaoImpl.getAllContacts());
            MonthCombo.setItems(months);
            TypeCombo.setItems(AppointmentsDaoImpl.getAppType());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        AppointmentID.setCellValueFactory(new PropertyValueFactory<>("appId"));
        Title.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        Description.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        Type.setCellValueFactory(new PropertyValueFactory<>("appType"));
        Start.setCellValueFactory(new PropertyValueFactory<>("appStartTime"));
        End.setCellValueFactory(new PropertyValueFactory<>("appEndTime"));
        CustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        Contact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        ReportsTableView.setItems(appointments);
    }

}
