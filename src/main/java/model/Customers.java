package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Model of customers
 *
 * @author Brandon Nguyen
 */
public class Customers {

    /**
     * Customer ID
     */
    private int customerId;

    /**
     * Customer Name
     */
    private String customerName;

    /**
     * Customer Address
     */
    private String customerAddress;

    /**
     * Customer Postal Code
     */
    private String customerPostalCode;

    /**
     * Customer Phone Number
     */
    private String customerPhoneNumber;

    /**
     * Customer Create Date
     */
    private LocalDateTime customerCreateDate;

    /**
     * Creator
     */
    private String customerCreatedBy;

    /**
     * Last Update Time
     */
    private LocalDateTime customerLastUpdated;

    /**
     * Last Update By Person
     */
    private String customerLastUpdatedBy;

    /**
     * Customer Division ID
     */
    private int Division_ID;

    /**
     * New customer instance constructor
     * @param customerId
     * @param customerName
     * @param customerAddress
     * @param customerPostalCode
     * @param customerPhoneNumber
     * @param customerCreateDate
     * @param customerCreatedBy
     * @param customerLastUpdated
     * @param customerLastUpdatedBy
     * @param Division_ID
     */
    public Customers(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber, LocalDateTime customerCreateDate, String customerCreatedBy, LocalDateTime customerLastUpdated, String customerLastUpdatedBy, int Division_ID) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerCreateDate = customerCreateDate;
        this.customerCreatedBy = customerCreatedBy;
        this.customerLastUpdated = customerLastUpdated;
        this.customerLastUpdatedBy = customerLastUpdatedBy;
        this.Division_ID = Division_ID;
    }

    /**
     * Setters and Getters
     * @return
     */

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public LocalDateTime getCustomerCreateDate() {
        return customerCreateDate;
    }

    public void setCustomerCreateDate(LocalDateTime customerCreateDate) {
        this.customerCreateDate = customerCreateDate;
    }

    public String getCustomerCreatedBy() {
        return customerCreatedBy;
    }

    public void setCustomerCreatedBy(String customerCreatedBy) {
        this.customerCreatedBy = customerCreatedBy;
    }

    public LocalDateTime getCustomerLastUpdated() {
        return customerLastUpdated;
    }

    public void setCustomerLastUpdated(LocalDateTime customerLastUpdated) {
        this.customerLastUpdated = customerLastUpdated;
    }

    public String getCustomerLastUpdatedBy() {
        return customerLastUpdatedBy;
    }

    public void setCustomerLastUpdatedBy(String customerLastUpdatedBy) {
        this.customerLastUpdatedBy = customerLastUpdatedBy;
    }

    public int getDivision_ID() {
        return Division_ID;
    }

    public void setDivision_ID(int Division_ID) {
        this.Division_ID = Division_ID;
    }

    /**
     * @Override Customer Name toString()
     * @return Customer Name
     */
    @Override
    public String toString() {
        return customerName;
    }
}
