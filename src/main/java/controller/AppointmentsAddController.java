package controller;

import DAO.AppointmentsDaoImpl;
import DAO.ContactsDaoImpl;
import DAO.CustomerDaoImpl;
import DAO.LoginDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;
import schedule.main.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

import static controller.MainMenuController.confirmDialog;
import static controller.MainMenuController.errorDialog;

/**
 * Controller class provides control for add appointment screen of application
 *
 * @author Brandon Nguyen
 */
public class AppointmentsAddController implements Initializable {

    /**
     * Appointment ID of Appointments Table
     */
    @FXML
    private Label AppAutoID;

    /**
     *Appointment ID Label of AppAutoID
     */
    @FXML
    private Label AppIDLabel;

    /**
     * Contacts ID ComboBox of Appointments Table
     */
    @FXML
    private ComboBox<Contacts> ContactCombo;

    /**
     * Contact ID Label of ContactCombo
     */
    @FXML
    private Label ContactLabel;

    /**
     * Customer ID ComboBox of Appointments Table
     */
    @FXML
    private ComboBox<Customers> CustomerIDCombo;

    /**
     * Customer ID Label of CustomerIDCombo
     */
    @FXML
    private Label CustomerIDLabel;

    /**
     * Description Field of Appointments Table
     */
    @FXML
    private TextField DescriptionField;

    /**
     * Description Label of DescriptionField
     */
    @FXML
    private Label DescriptionLabel;

    /**
     * End Time ComboBox of Appointments Table
     */
    @FXML
    private ComboBox<LocalTime> EndCombo;

    /**
     * End Time Label of EndCombo
     */
    @FXML
    private Label EndTimeLabel;

    /**
     * Location Field of Appointments Table
     */
    @FXML
    private TextField LocationField;

    /**
     * Location Label of LocationField
     */
    @FXML
    private Label LocationLabel;

    /**
     * Start Time ComboBox of Appointments Table
     */
    @FXML
    private ComboBox<LocalTime> StartCombo;

    /**
     * Start Date Label of StartCombo
     */
    @FXML
    private Label StartDateLabel;

    /**
     * Title Field of Appointments Table
     */
    @FXML
    private TextField TitleField;

    /**
     * Title Label of TitleField
     */
    @FXML
    private Label TitleLabel;

    /**
     * Type Field of Appointments Table
     */
    @FXML
    private TextField TypeField;

    /**
     * Type Label of TypeField
     */
    @FXML
    private Label TypeLabel;

    /**
     * User ID ComboBox of Appointments Table
     */
    @FXML
    private ComboBox<Users> UserIDCombo;

    /**
     * User ID Label of UserIDCombo
     */
    @FXML
    private Label UserIDLabel;

    /**
     * Start Date DatePicker of Appointments Table
     */
    @FXML
    private DatePicker StartDateDatePicker;

    /**
     * Zone ID of UTC
     */
    private final ZoneId utcZoneId = ZoneId.of("UTC");

    /**
     * Zone ID of User System
     */
    private final ZoneId localMachineZoneId = ZoneId.systemDefault();

    /**
     * Zone ID of EST
     */
    private final ZoneId estZoneId = ZoneId.of("America/New_York");


    /**
     * Save new appointment
     * @param event save new appointment action
     * @throws SQLException for checkOverlap()
     * @throws IOException for if() and else if() statements, for FXMLLoader
     * errorDialog if no date picked
     * errorDialogs if appointment times conflict
     * errorDialog if checkOverlap == true
     * errorDialogs if empty fields
     * confirmDialog if add new appointment
     */
    //ComboBoxes nullpointerexception error, errorDialog not catching
    @FXML
    void onActionSave(ActionEvent event) throws SQLException, IOException {
        LocalDate localDate = StartDateDatePicker.getValue();

        if(localDate == null) {
            errorDialog("Must pick date", "");
        }
        else if (localDate != null) {
            String appTitle = TitleField.getText();
            String appDescription = DescriptionField.getText();
            String appLocation = LocationField.getText();
            String appType = TypeField.getText();
            Timestamp appCreateDate = Timestamp.valueOf(LocalDateTime.now());
            String appCreatedBy = GlobalUsername.username;
            Timestamp appLastUpdate = Timestamp.valueOf(LocalDateTime.now());
            String appLastUpdatedBy = GlobalUsername.username;
            int contactId = ContactCombo.getValue().getContactId();
            int customerId = CustomerIDCombo.getValue().getCustomerId();
            int userId = UserIDCombo.getValue().getUserId();

            //localStartTime && localEndTime --> show App
            LocalDateTime localStartTime = LocalDateTime.of(StartDateDatePicker.getValue(), StartCombo.getSelectionModel().getSelectedItem());
            LocalDateTime localEndTime = LocalDateTime.of(StartDateDatePicker.getValue(), EndCombo.getSelectionModel().getSelectedItem());
            System.out.println("localStartTime: " + localStartTime);
            System.out.println("localEndTime: " + localEndTime);
            System.out.println();

            //Convert localStartTime & localEndTime --> UTC --> Database
            ZonedDateTime utcStartTime = localStartTime.atZone(localMachineZoneId).withZoneSameInstant(utcZoneId);
            ZonedDateTime utcEndTime = localEndTime.atZone(localMachineZoneId).withZoneSameInstant(utcZoneId);
            System.out.println("utcStartTime: " + utcStartTime);
            System.out.println("utcEndTime" + utcEndTime);
            System.out.println();

            //Timestamp utcStartTime & utcEndTime --> store Database
            Timestamp timeStampStart = Timestamp.valueOf(utcStartTime.toLocalDateTime());
            Timestamp timeStampEnd = Timestamp.valueOf(utcEndTime.toLocalDateTime());
            System.out.println("timeStampStart: " + timeStampStart);
            System.out.println("timeStampEnd: " + timeStampEnd);
            //int contactCheck = 0;

            //Convert localStartTime & localEndTime --> EST -business hours
            ZonedDateTime estStartTimeZoned = localStartTime.atZone(localMachineZoneId).withZoneSameInstant(estZoneId);
            LocalDateTime estStartTime = estStartTimeZoned.toLocalDateTime();
            ZonedDateTime estEndTimeZoned = localEndTime.atZone(localMachineZoneId).withZoneSameInstant(estZoneId);
            LocalDateTime estEndTime = estEndTimeZoned.toLocalDateTime();
            LocalDateTime startBusiness = LocalDateTime.of(StartDateDatePicker.getValue(), LocalTime.of(8, 0));
            LocalDateTime endBusiness = LocalDateTime.of(StartDateDatePicker.getValue(), LocalTime.of(22,0));
            System.out.println("estStartTime: " + estStartTime);
            System.out.println("etcEndTime: " + estEndTime);
            System.out.println();

            if(estStartTime.isAfter(estEndTime)) {
                errorDialog("Appointment StartTime is after EndTime", "");
            }
            else if(estEndTime.isBefore(estStartTime)) {
                errorDialog("Appointment EndTime is before StartTime", "");
            }
            else if(estEndTime.isEqual(estStartTime)) {
                errorDialog("Appointment StartTime is same as EndTime", "");
            }
            else if(estStartTime.isBefore(startBusiness)) {
                errorDialog("Appointment StartTime is before business hours", "");
            }
            else if(estEndTime.isAfter(endBusiness)) {
                errorDialog("Appointment EndTime is after business hours", "");
            }
            else if (appTitle.isEmpty()) {
                errorDialog("Enter Title","");
            }
            else if (appDescription.isEmpty()) {
                errorDialog("Enter Description","");
            }
            else if (appLocation.isEmpty()) {
                errorDialog("", "");
            }
            else if (ContactCombo.getSelectionModel().getSelectedItem() == null) {
                errorDialog("Contact empty","");
            }
            else if (CustomerIDCombo.getSelectionModel().getSelectedItem() == null) {
                errorDialog("Customer_ID empty","");
            }
            else if (UserIDCombo.getSelectionModel().getSelectedItem() == null) {
                errorDialog("User_ID empty","");
            }
            else if(AppointmentController.checkOverlap(customerId, 0,  localStartTime, localEndTime)) {
                errorDialog("AppointmentController.checkOverlap == true", "");
                return;
            }
            else {
                if (confirmDialog("Confirm Add ", "Are you sure?")) {
                    AppointmentsDaoImpl.addAppointments(appTitle, appDescription,appLocation, appType,timeStampStart, timeStampEnd,appCreateDate, appCreatedBy, appLastUpdate, appLastUpdatedBy, customerId, userId, contactId);
                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getClassLoader()).getResource("Appointment-view.fxml"));
                    Parent scene = loader.load();
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        }
        else {
            System.out.println("onActionSave AppointmentsAddController Error");
        }
    }

    /**
     * Load Appointment-view, AppointmentsController
     * @param event cancel view AppointmentsAdd-view action
     * @throws IOException for FXMLLoader
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getClassLoader()).getResource("Appointment-view.fxml"));
        Parent scene = loader.load();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Set ComboBoxes, disable weekends on DatePicker
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

        StartDateDatePicker.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.getDayOfWeek() == DayOfWeek.SATURDAY || item.getDayOfWeek() == DayOfWeek.SUNDAY);
            }
        }
        );

        try {
            CustomerIDCombo.setItems(CustomerDaoImpl.getAllCustomers());
            UserIDCombo.setItems(LoginDaoImpl.getAllUsers());
            ContactCombo.setItems(ContactsDaoImpl.getAllContacts());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        LocalTime startStartCombo = LocalTime.of(6, 0, 0);

        LocalTime startEndCombo = LocalTime.of(23,0,0);

        LocalTime endStartCombo = LocalTime.of(6, 15, 0);

        LocalTime endEndCombo = LocalTime.of(23, 15, 0);

        while(startStartCombo.isBefore(startEndCombo.plusSeconds(1))) {
            StartCombo.getItems().add(startStartCombo);
            startStartCombo = startStartCombo.plusMinutes(15);
        }

        while(endStartCombo.isBefore(endEndCombo.plusSeconds(1))) {
            EndCombo.getItems().add(endStartCombo);
            endStartCombo = endStartCombo.plusMinutes(15);
        }
    }
}
