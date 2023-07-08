package controller;

import DAO.CountryDaoImpl;
import DAO.CustomerDaoImpl;
import DAO.FirstLevelDivisionDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Countries;
import model.FirstLevelDivision;
import model.GlobalUsername;
import schedule.main.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

import static controller.MainMenuController.errorDialog;

/**
 * Controller class provides control for add customer screen of application
 *
 * @author Brandon Nguyen
 */
public class CustomersAddController implements Initializable {

    /**
     * Title of Customers Table
     */
    @FXML
    private Label TitleLabel;

    /**
     * Address Field of Customers Table
     */
    @FXML
    private TextField AddressField;

    /**
     * Address Label of AddressField
     */
    @FXML
    private Label AddressLabel;

    /**
     * Country ID ComboBox of Customers Table
     */
    @FXML
    private ComboBox<Countries> CountryCombo;

    /**
     * Country Label of CountryCombo
     */
    @FXML
    private Label CountryLabel;

    /**
     * Division ComboBox of Customers Table
     */
    @FXML
    private ComboBox<FirstLevelDivision> DivisionCombo;

    /**
     * Division Label of DivisionCombo
     */
    @FXML
    private Label DivisionLabel;

    /**
     * Customer ID Field Not-Editable of Customers Table
     */
    @FXML
    private Label IDAuto;

    /**
     * Customer ID Label of IDAuto
     */
    @FXML
    private Label IDLabel;

    /**
     * Name Field of Customers Table
     */
    @FXML
    private TextField NameField;

    /**
     * Name Label of NameField
     */
    @FXML
    private Label NameLabel;

    /**
     * Customer Phone Number Field of Customers Table
     */
    @FXML
    private TextField PhoneNumberField;

    /**
     * Customer Phone Number Label of PhoneNumberField
     */
    @FXML
    private Label PhoneNumberLabel;

    /**
     * Customer Postal Code of PostalCodeField
     */
    @FXML
    private Label PostalCode;

    /**
     * Customer Postal Code Field of Customers Table
     */
    @FXML
    private TextField PostalCodeField;

    /**
     * Load Customers-view, CustomerController
     * @param event cancel view CustomersAdd-view action
     * @throws IOException for FXMLLoader
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getClassLoader()).getResource("Customer-view.fxml"));
        Parent scene = loader.load();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Set Country ComboBox with selected value, setItems of Division ComboBox
     * @param event set Country ComboBox with selected value, setItems of DivisionBox action
     */
    @FXML
    void onActionCountryCombo(ActionEvent event) {
        Countries countryComboValue = CountryCombo.getValue();
        DivisionCombo.setItems(FirstLevelDivisionDaoImpl.getDivFromCountId(countryComboValue.getCountry_ID()));
    }

    /**
     * Set Division ComboBox with selected value
     * @param event set Division ComboBox with selected value action
     */
    @FXML
    void onActionDivisionCombo(ActionEvent event) {
    }

    /**
     * Save new customer
     * @param event save new customer action
     * errorDialogs if Fields empty on save action
     */
    @FXML
    void onActionSave(ActionEvent event) {
        try {
            String name = NameField.getText();
            String address = AddressField.getText();
            String postalCode = PostalCodeField.getText();
            String phoneNumber = PhoneNumberField.getText();
            Timestamp createBy = Timestamp.valueOf(LocalDateTime.now());
            String createdBy = GlobalUsername.username;
            Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            String lastUpdateBy = GlobalUsername.username;

            //Make FirstLevelDivision and DivisionCombo to select w/CountryCombo connected
            FirstLevelDivision selectedDivision = DivisionCombo.getValue();

            if(name.isEmpty()) {
                errorDialog("Warning", "No name");
            }
            else if(address.isEmpty()) {
                errorDialog("Warning", "No address");
            }
            else if(postalCode.isEmpty()) {
                errorDialog("Warning", "No postal code");
            }
            else if(phoneNumber.isEmpty()) {
                errorDialog("Warning", "No phone number");
            }
            else if(DivisionCombo.getSelectionModel().getSelectedItem() == null) {
                errorDialog("Warning", "No division selected");
            }
            else if(CountryCombo.getSelectionModel().getSelectedItem() == null) {
                errorDialog("Warning", "No country selected");
            }
            else {
                int intDivision = selectedDivision.getDivision_ID();
                CustomerDaoImpl.addCustomers(name, address, postalCode, phoneNumber, createBy, createdBy, lastUpdate, lastUpdateBy, intDivision);

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getClassLoader()).getResource("Customer-view.fxml"));
                Parent scene = loader.load();
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch (Exception e) {
            System.out.println("onActionSave CustomersAddController Error: " + e.getMessage());
        }
    }

    /**
     * Set ComboBoxes
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
            CountryCombo.setItems(CountryDaoImpl.getAllCountries());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
