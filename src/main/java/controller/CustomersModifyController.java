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
import model.Customers;
import model.FirstLevelDivision;
import schedule.main.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

import static controller.MainMenuController.confirmDialog;
import static controller.MainMenuController.errorDialog;

/**
 * Controller class provides control for modify customer screen of application
 */
public class CustomersModifyController implements Initializable {

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
     * Country Label of ContryCombo
     */
    @FXML
    private Label CountryLabel;

    /**
     * Division ID ComboBox of Customers Table
     */
    @FXML
    private ComboBox<FirstLevelDivision> DivisionCombo;

    /**
     * Division ID Label of DivisionCombo
     */
    @FXML
    private Label DivisionLabel;


    /**
     * Customer ID Field, Not editable of Customers Table
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
     * Customer Phone Number field of Custmoers Table
     */
    @FXML
    private TextField PhoneNumberField;

    /**
     * Customer Phone Number Label of PhoneNumberField
     */
    @FXML
    private Label PhoneNumberLabel;

    /**
     * Customer Postal Code Field of Customers Table
     */
    @FXML
    private TextField PostalCodeField;

    /**
     * Customer Postal Code of Customers Table
     */
    @FXML
    private Label PostalCodeLabel;

    /**
     * Title of Modify Customers Screen of Application
     */
    @FXML
    private Label ModifyLabel;

    /**
     * Customer selected in CustomerController
     */
    private static Customers selectedCustomer;

    /**
     * Set value of Country ComboBox, setItems of Division ComboBox
     * @param event set value of Country ComboBox, setItems of Division ComboBox action
     */
    @FXML
    void onActionCountryCombo(ActionEvent event) {
        DivisionCombo.setValue(null);
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
     * Save modified customer
     * @param event save modified customer action
     * errorDialogs if empty fields
     * confirmDialog if add modified customer
     */
    @FXML
    void onActionSave(ActionEvent event) {
        try {
            int customerId = Integer.parseInt(IDAuto.getText());
            String name = NameField.getText();
            String address =  AddressField.getText();
            String postalCode = PostalCodeField.getText();
            String phoneNumber = PhoneNumberField.getText();
            Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            String lastUpdatedBy = "Make global.username";
            FirstLevelDivision selectedDivision = DivisionCombo.getValue();
            int intDivision = selectedDivision.getDivision_ID();

            if (name.isEmpty()) {
                errorDialog("Enter name", "");
            }
            else if (address.isEmpty()) {
                errorDialog("Enter address", "");
            }
            else if (postalCode.isEmpty()) {
                errorDialog("Enter postal code", "");
            }
            else if(DivisionCombo.getSelectionModel().getSelectedItem() == null) {
                errorDialog("Select division", "");
            }
            else {
                if(confirmDialog("Modify Confirm", "Are you sure?")) {
                    CustomerDaoImpl.modifyCustomer(customerId, name, address, postalCode, phoneNumber, lastUpdate, lastUpdatedBy, intDivision);

                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getClassLoader()).getResource("Customer-view.fxml"));
                    Parent scene = loader.load();
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }

        }
        catch (Exception e) {
            System.out.println("onActionSave CustomerModifyController Error: " + e.getMessage());
        }
    }

    /**
     * Customer selected from CustomerController
     * @param customers set selectedCustomer
     */
    public static void loadSelectedCustomer(Customers customers) {

        selectedCustomer = customers;
    }

    /**
     * Load Customer-view, CustomerController
     * @param event
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
     * Set Customers Table View with values of selectedCustomer, Set CountryCombo with value of selectedCustomer and setItems CountryCombo
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

        IDAuto.setText(Integer.toString(selectedCustomer.getCustomerId()));
        NameField.setText(selectedCustomer.getCustomerName());
        AddressField.setText(selectedCustomer.getCustomerAddress());
        PhoneNumberField.setText(selectedCustomer.getCustomerPhoneNumber());
        PostalCodeField.setText(selectedCustomer.getCustomerPostalCode());

        CountryCombo.setItems(CountryDaoImpl.getAllCountries());
        DivisionCombo.setValue(FirstLevelDivisionDaoImpl.getDivFromDivId(selectedCustomer.getDivision_ID()));
        FirstLevelDivision selectedDivision = FirstLevelDivisionDaoImpl.getDivisionFromFLDObject(DivisionCombo.getValue());

        //NullPointerException
        int selectedCountryId = selectedDivision.getCOUNTRY_ID();

        Countries selectedCountry = CountryDaoImpl.getCountriesFromCountId(selectedCountryId);
        CountryCombo.setValue(selectedCountry);

    }
}
