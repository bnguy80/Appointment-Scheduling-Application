package model;

import DAO.AppointmentsDaoImpl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Model of Appointments
 *
 * @author Brandon Nguyen
 */
public class Appointments {

    /**
     * Appointment ID
     */
    private int appId;

    /**
     * Appointment Title
     */
    private String appTitle;

    /**
     * Appointment Description
     */
    private String appDescription;

    /**
     * Appointment Location
     */
    private String appLocation;

    /**
     * Appointment Type
     */
    private String appType;

    /**
     * Appointment Start Time
     */
    private LocalDateTime appStartTime;

    /**
     * Appointment End Time
     */
    private LocalDateTime appEndTime;

    /**
     * Appointment Create Date
     */
    private LocalDateTime appCreateDate;

    /**
     * Appointment Creator
     */
    private String appCreatedBy;

    /**
     * Appointment Last Update Time
     */
    private LocalDateTime appLastUpdate;

    /**
     * Appointment Last Updated By Person
     */
    private String appLastUpdateBy;

    /**
     * Appointment Customer ID
     */
    private int customerId;

    /**
     * Appointment User ID
     */
    private int userId;

    /**
     * Appointment Contact ID
     */
    private int contactId;

    /**
     * New appointment instance constructor
     * @param appId
     * @param appTitle
     * @param appDescription
     * @param appLocation
     * @param appType
     * @param appStartTime
     * @param appEndTime
     * @param appCreateDate
     * @param appCreatedBy
     * @param appLastUpdate
     * @param appLastUpdateBy
     * @param customerId
     * @param userId
     * @param contactId
     */
    public Appointments(int appId, String appTitle, String appDescription, String appLocation, String appType, LocalDateTime appStartTime, LocalDateTime appEndTime, LocalDateTime appCreateDate, String appCreatedBy, LocalDateTime appLastUpdate, String appLastUpdateBy, int customerId, int userId, int contactId) {
        this.appId = appId;
        this.appTitle = appTitle;
        this.appDescription = appDescription;
        this.appLocation = appLocation;
        this.appType = appType;
        this.appStartTime = appStartTime;
        this.appEndTime = appEndTime;
        this.appCreateDate = appCreateDate;
        this.appCreatedBy = appCreatedBy;
        this.appLastUpdate = appLastUpdate;
        this.appLastUpdateBy = appLastUpdateBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * Setters and Getters
     * @return
     */

    public int getAppId() {
        return appId;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public String getAppLocation() {
        return appLocation;
    }

    public String getAppType() {
        return appType;
    }

    public LocalDateTime getAppStartTime() {
        return appStartTime;
    }

/*    public String getAppStartTimeST() throws SQLException {
        Appointments allApps = AppointmentsDaoImpl.getAllAppointmentsList();
        String getAppStartTimeST = allApps.getAppStartTime().toString();
        return getAppStartTimeST;
    }*/

    /**
     * Format AppStartTime from DB to systemDefault
     * @return LocalDateTime formattedStartTime
     */
    public LocalDateTime getFormattedStartTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ZonedDateTime zonedDateTime = this.appStartTime.atZone(ZoneId.of("UTC"));
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime utc = zonedDateTime.withZoneSameInstant(zoneId);
        String formattedStartTime = utc.format(dateTimeFormatter);
        return LocalDateTime.parse(formattedStartTime, dateTimeFormatter);
    }

    /**
     * Format AppEndTime from DB to systemDefault
     * @return LocalDateTime formattedEndTime
     */
    public LocalDateTime getFormattedEndTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ZonedDateTime zonedDateTime = this.appEndTime.atZone(ZoneId.of("UTC"));
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime utc = zonedDateTime.withZoneSameInstant(zoneId);
        String formattedEndTime = utc.format(dateTimeFormatter);
        return LocalDateTime.parse(formattedEndTime, dateTimeFormatter);
    }

    public LocalDateTime getAppEndTime() {
        return appEndTime;
    }

    public LocalDateTime getAppCreateDate() {
        return appCreateDate;
    }

    public String getAppCreatedBy() {
        return appCreatedBy;
    }

    public LocalDateTime getAppLastUpdate() {
        return appLastUpdate;
    }

    public String getAppLastUpdateBy() {
        return appLastUpdateBy;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getUserId() {
        return userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public void setAppLocation(String appLocation) {
        this.appLocation = appLocation;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public void setAppStartTime(LocalDateTime appStartTime) {
        this.appStartTime = appStartTime;
    }

    public void setAppEndTime(LocalDateTime appEndTime) {
        this.appEndTime = appEndTime;
    }

    public void setAppCreateDate(LocalDateTime appCreateDate) {
        this.appCreateDate = appCreateDate;
    }

    public void setAppCreatedBy(String appCreatedBy) {
        this.appCreatedBy = appCreatedBy;
    }

    public void setAppLastUpdate(LocalDateTime appLastUpdate) {
        this.appLastUpdate = appLastUpdate;
    }

    public void setAppLastUpdateBy(String appLastUpdateBy) {
        this.appLastUpdateBy = appLastUpdateBy;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
