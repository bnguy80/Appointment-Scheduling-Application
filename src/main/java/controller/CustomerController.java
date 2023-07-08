package controller;

import DAO.AppointmentsDaoImpl;
import DAO.CustomerDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;
import schedule.main.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Objects;
import java.util.ResourceBundle;

import static controller.MainMenuController.confirmDialog;
import static controller.MainMenuController.errorDialog;

/**
 * Controller class that provides control for main customer screen of application
 *
 * @author Brandon Nguyen
 */
public class CustomerController implements Initializable {

    /**
     * Customer Address of Customers Table
     */
    @FXML
    private TableColumn<Customers, String> Address;

    /**
     * Created Date of Customers Table
     */
    @FXML
    private TableColumn<Customers, Calendar> CreateBy;

    /**
     * Created By of Customers Table
     */
    @FXML
    private TableColumn<Customers, String> CreatedBy;

    /**
     * Customer ID of Customers Table
     */
    @FXML
    private TableColumn<Customers, Integer> CustomerID;

    /**
     * Last Update Time of Customers Table
     */
    @FXML
    private TableColumn<Customers, Calendar> LastUpdate;

    /**
     * Last Updated By of Customers Table
     */
    @FXML
    private TableColumn<Customers, String> LastUpdateBy;

    /**
     * Customer Name of Customers Table
     */
    @FXML
    private TableColumn<Customers, String> CustomerName;

    /**
     * Customer Phone Number of Customers Table
     */
    @FXML
    private TableColumn<Customers, String> Phone;

    /**
     * Customer Postal Code of Customers Table
     */
    @FXML
    private TableColumn<Customers, String> PostalCode;

    /**
     * Customers Table View
     */
    @FXML
    private TableView<Customers> CustomerTableView;

    /**
     * Division ID of Customers Table
     */
    @FXML
    private TableColumn<Customers, Integer> DivisionID;

    //private static ObservableList<Customers> customersTableViewNew = FXCollections.observableArrayList();

    /**
     * Load CustomersAdd-view, CustomersAddController
     * @param event add customer screen action
     * @throws IOException for FXMLLoader
     */
    @FXML
    void OnActionAdd(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getClassLoader()).getResource("CustomersAdd-view.fxml"));
        Parent scene = loader.load();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Load MainMenu-view, MainMenuController
     * @param event cancel Customer-view action
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
     * Delete customer from Customers Table
     * @param event delete customer action
     * @throws SQLException for getAllAppointments()
     * errorDialog if no customer selected to delete
     * errorDialog if customer has appointments still
     * confirmDialog if delete appointment
     * confirmDialog if delete, Customer Name show delete
     */
    @FXML
    void onActionDelete(ActionEvent event) throws SQLException {
        if (CustomerTableView.getSelectionModel().getSelectedItem() == null){
            errorDialog("Error", "No customer selected to delete");
        }
        else if(confirmDialog("Are you sure?", "Cannot undo")) {
            ObservableList<Appointments> appForList = AppointmentsDaoImpl.getAllAppointments();
            for (Appointments a: appForList) {
                if (a.getCustomerId() == CustomerTableView.getSelectionModel().getSelectedItem().getCustomerId()) {
                    if (confirmDialog("Delete Appointment_ID?","Cannot undo")) {
                        AppointmentsDaoImpl.deleteAppointmentsID(CustomerTableView.getSelectionModel().getSelectedItem().getCustomerId(), a.getAppId());
                    }
                }
            }
            if (AppointmentsDaoImpl.getAppCount(CustomerTableView.getSelectionModel().getSelectedItem().getCustomerId()) > 0) {
                errorDialog("Must delete all appointments connected to customer first", "");
            }
            else {
                String deleteCustomer = CustomerTableView.getSelectionModel().getSelectedItem().getCustomerName();
                if (confirmDialog("Delete " + deleteCustomer, "")) {
                    CustomerDaoImpl.deleteCustomer(CustomerTableView.getSelectionModel().getSelectedItem().getCustomerId());
                    CustomerTableView.setItems(CustomerDaoImpl.getAllCustomers());
                    CustomerTableView.refresh();
                }
            }
        }
    }

    /**
     * Modify customer from customers table
     * @param event modify customer screen action
     * @throws IOException for FXMLLoader
     * errorDialog if no customer selected to modify
     */
    @FXML
    public void onActionModify(ActionEvent event) throws IOException {
        if(CustomerTableView.getSelectionModel().getSelectedItem() == null) {
            errorDialog("Warning", "No customer selected");
        } else {
            Customers customerSelected = CustomerTableView.getSelectionModel().getSelectedItem();
            CustomersModifyController.loadSelectedCustomer(customerSelected);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getClassLoader()).getResource("CustomersModify-view.fxml"));
            Parent scene = loader.load();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Set Customers TableView
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
        CustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        CustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        Address.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        PostalCode.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        Phone.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        CreatedBy.setCellValueFactory(new PropertyValueFactory<>("customerCreateDate"));
        CreateBy.setCellValueFactory(new PropertyValueFactory<>("customerCreatedBy"));
        LastUpdate.setCellValueFactory(new PropertyValueFactory<>("customerLastUpdated"));
        LastUpdateBy.setCellValueFactory(new PropertyValueFactory<>("customerLastUpdatedBy"));
        DivisionID.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));

        try {
            CustomerTableView.setItems(CustomerDaoImpl.getAllCustomers());
        } catch (Exception ex) {
            ex.printStackTrace();
            //Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
