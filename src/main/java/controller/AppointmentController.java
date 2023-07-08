package controller;

import DAO.AppointmentsDaoImpl;
import DAO.CustomerDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;
import schedule.main.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Objects;
import java.util.ResourceBundle;

import static controller.MainMenuController.confirmDialog;
import static controller.MainMenuController.errorDialog;

/**
 * Controller class that provides control for main appointment screen of application
 *
 * @author Brandon Nguyen
 */
public class AppointmentController implements Initializable {

    /**
     *Customer ID of Customers Table
     */
    @FXML private TableColumn<Customers, Integer> CustomerID;

    /**
     * Customer Name of Customers Table
     */
    @FXML
    private TableColumn<Customers, String> CustomerName;

    /**
     * Customers Table View
     */
    @FXML
    private TableView<Customers> CustomerTableView;

    /**
     * Description of Appointments Table
     */
    @FXML
    private TableColumn<Appointments, String> Description;

    /**
     * End Time of Appointments Table
     */
    @FXML
    private TableColumn<Appointments, Calendar> End;

    /**
     * Last Update Time of Appointments Table
     */
    @FXML
    private TableColumn<Appointments, Calendar> LastUpdate;

    /**
     * Location of Appointments Table
     */
    @FXML
    private TableColumn<Appointments, String> Location;

    /**
     * Start Time of Appointments Table
     */
    @FXML
    private TableColumn<Appointments, Calendar> Start;

    /**
     * Title of Appointments Table
     */
    @FXML
    private TableColumn<Appointments, String> Title;

    /**
     *Type of Appointments Table
     */
    @FXML
    private TableColumn<Appointments, String> Type;

    /**
     * User ID of Appointments Table
     */
    @FXML
    private TableColumn<Appointments, Integer> UserID;

    /**
     * Contact ID of Appointments Table
     */
    @FXML
    private TableColumn<Appointments, Integer> ContactID;

    /**
     * Create Date of Appointments Table
     */
    @FXML
    private TableColumn<Appointments, Calendar> CreateDate;

    /**
     * Appointment ID of Appointments Table
     */
    @FXML
    private TableColumn<Appointments, Integer> AppID;

    /**
     * Appointment Table View
     */
    @FXML
    private TableView<Appointments> AppTableView;

    /**
     *Total of all Radio Button
     */
    @FXML
    private RadioButton TotalButton;

    /**
     * Weekly Total Radio Button
     */
    @FXML
    private RadioButton WeeklyButton;

    /**
     * Monthly Total Radio Button
     */
    @FXML
    private RadioButton MonthlyButton;

    /**
     *List of  appointment from table view to delete
     */
    private static ObservableList<Appointments> appointmentsTableViewNew = FXCollections.observableArrayList();

    /**
     * List of appointments selected for total and AppTableView intialization
     */
    private static ObservableList<Appointments> appointmentSelected = FXCollections.observableArrayList();

    /**
     * Load AppointmentsAdd-view, AppointmentsAddController
     * @param event add appointment screen action
     * @throws IOException for FXMLLoader
     */
    @FXML
    void onActionAdd(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getClassLoader()).getResource("AppointmentsAdd-view.fxml"));
        Parent scene = loader.load();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Load MainMenu-view, MainMenuController
     * @param event cancel Appointment-view action
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
     * Delete appointment from Appointments Table
     * errorDialog if no appointment select
     * confirmDialog if delete appointment
     * @param event delete appointment action
     * @throws Exception for getAllAppointments()
     */
    @FXML
    void onActionDelete(ActionEvent event) throws Exception{
        if(AppTableView.getSelectionModel().getSelectedItem() == null) {
            errorDialog("Error", "No appointment selected to delete");
        }
        else {
            if (confirmDialog("Deleting Appointment with Appointment_ID: " + AppTableView.getSelectionModel().getSelectedItem().getAppId() + " and Type: " + AppTableView.getSelectionModel().getSelectedItem().getAppType(), "Are you sure?")) {
                AppointmentsDaoImpl.deleteAppointments(AppTableView.getSelectionModel().getSelectedItem().getAppId());
                appointmentsTableViewNew = AppointmentsDaoImpl.getAllAppointments();
                AppTableView.setItems(appointmentsTableViewNew);
                AppTableView.refresh();
            }
        }
    }

    /**
     * Modify appointment from appointments table
     * @param event modify appointment screen action
     *errorDialog if no appointment selected
     * @throws Exception for FXMLLoader
     */
    @FXML
    void onActionModify(ActionEvent event) throws Exception {
        if(AppTableView.getSelectionModel().getSelectedItem() == null) {
            errorDialog("Warning", "No appointment selected");
        }
        else {
            Appointments appointmentSelected = AppTableView.getSelectionModel().getSelectedItem();
            AppointmentsModifyController.loadSelectedAppointments(appointmentSelected);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getClassLoader()).getResource("AppointmentsModify-view.fxml"));
            Parent scene = loader.load();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Show appointments from selected customer
     * @param mouseEvent show customer appointment action
     */
    public void onMouseClickedCustomer(javafx.scene.input.MouseEvent mouseEvent) {
        Customers selectedCustomer = CustomerTableView.getSelectionModel().getSelectedItem();
        int mouseCustomerId = selectedCustomer.getCustomerId();
        AppTableView.setItems(AppointmentsDaoImpl.getAppFromCustId(mouseCustomerId));
        AppTableView.refresh();
    }

    /**
     * Reset customer table view
     * @param event reset customer table view action
     * @throws SQLException for getAllAppointments()
     */
    @FXML
    void onActionReset(ActionEvent event) throws SQLException {
        CustomerTableView.refresh();
        AppTableView.setItems(AppointmentsDaoImpl.getAllAppointments());
        AppTableView.refresh();
    }

    /**
     * Total number of appointments
     * @param event total number appointments action
     * @throws SQLException for getAllAppointments()
     */
    @FXML
    void onActionTotal(ActionEvent event) throws SQLException {
        MonthlyButton.setSelected(false);
        WeeklyButton.setSelected(false);
        appointmentSelected.clear();
        appointmentSelected.addAll(AppointmentsDaoImpl.getAllAppointments());
    }

    /**
     * Total number of monthly appointments
     * @param event total number monthly appointments action
     * @throws SQLException for getMonthlyAppointments()
     */
    @FXML
    void onActionMonthly(ActionEvent event) throws SQLException {
        TotalButton.setSelected(false);
        WeeklyButton.setSelected(false);
        appointmentSelected.clear();
        appointmentSelected.addAll(AppointmentsDaoImpl.getMonthlyAppointments());
    }

    /**
     * Total number of weekly appointments
     * @param event total number weekly appointments action
     * @throws SQLException for getWeeklyAppointments()
     */
    @FXML
    void onActionWeekly(ActionEvent event) throws SQLException {
        TotalButton.setSelected(false);
        MonthlyButton.setSelected(false);
        appointmentSelected.clear();
        appointmentSelected.addAll(AppointmentsDaoImpl.getWeeklyAppointments());
    }

    /**
     * Check appointment overlap when adding and modifying appointment
     * @param customerId Customer ID to check overlap
     * @param appId Appointment ID of add or modify appointment
     * @param appStart Start time of appointment
     * @param appEnd End time of appointment
     * @return return true if appointment overlap. return false if no appointment overlap
     * @throws SQLException for getAllAppointments()
     * errorDialog if overlap appointment times
     * errorDialog if start time same as another appointment
     * errorDialog if end time same as another appointment
     */
    public static boolean checkOverlap(int customerId, int appId, LocalDateTime appStart, LocalDateTime appEnd) throws SQLException {
        ObservableList<Appointments> appointmentsForList = AppointmentsDaoImpl.getAllAppointments();
        LocalDateTime checkStart;
        LocalDateTime checkEnd;

        for(Appointments a: appointmentsForList) {
            checkStart = a.getAppStartTime();
            checkEnd = a.getAppEndTime();
            if (customerId != a.getCustomerId()) {
                continue;
            }
            if (appId == a.getAppId()) {
                continue;
            }
            else if (checkStart.isEqual(appStart) || checkStart.isEqual(appEnd) || checkEnd.isEqual(appStart) || checkEnd.isEqual(appEnd)) {
                errorDialog("Overlap appointment times", "");
                return true;
            }
            else if (appStart.isAfter(checkStart) && appStart.isBefore(checkEnd)) {
                errorDialog("Appointment start time during existing appointments", "");
                return true;
            }
            else if (appEnd.isAfter(checkStart) && appEnd.isBefore(checkEnd)) {
                errorDialog("Appointment end time during existing appointments", "");
                return true;
            }
        }
        return false;
    }

    /**
     * Set Appointments TableView, Customer Table View, Total button selected
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
        AppID.setCellValueFactory(new PropertyValueFactory<>("appId"));
        Title.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        Description.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        Location.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        Type.setCellValueFactory(new PropertyValueFactory<>("appType"));
        Start.setCellValueFactory(new PropertyValueFactory<>("formattedStartTime"));
        End.setCellValueFactory(new PropertyValueFactory<>("formattedEndTime"));
        CreateDate.setCellValueFactory(new PropertyValueFactory<>("appCreateDate"));
        LastUpdate.setCellValueFactory(new PropertyValueFactory<>("appLastUpdate"));
        CustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        CustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        UserID.setCellValueFactory(new PropertyValueFactory<>("userId"));
        ContactID.setCellValueFactory(new PropertyValueFactory<>("contactId"));


        try {
            TotalButton.setSelected(true);
            CustomerTableView.setItems(CustomerDaoImpl.getAllCustomers());
            appointmentSelected.addAll(AppointmentsDaoImpl.getAllAppointments());
            AppTableView.setItems(appointmentSelected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
