package model;

import java.time.LocalDateTime;

/**
 * Model of FirstLevelDivision
 *
 * @author Brandon Nguyen
 */
public class FirstLevelDivision {

    /**
     * Division ID
     */
    private int Division_ID;

    /**
     * Division
     */
    private String Division;

    /**
     * Create Date
     */
    private LocalDateTime Create_Date;

    /**
     * Creator
     */
    private String Created_By;

    /**
     * Last Update Time
     */
    private LocalDateTime Last_Update;

    /**
     * Last Update By Person
     */
    private String Last_Update_By;

    /**
     * FirstLevelDivision Country ID
     */
    private int COUNTRY_ID;

    /**
     * Creates new FirstLevelDivision Constructor
     * @param division_ID
     * @param division
     * @param create_Date
     * @param created_By
     * @param last_Update
     * @param last_Update_By
     * @param COUNTRY_ID
     */
    public FirstLevelDivision(int division_ID, String division, LocalDateTime create_Date, String created_By, LocalDateTime last_Update, String last_Update_By, int COUNTRY_ID) {
        Division_ID = division_ID;
        Division = division;
        Create_Date = create_Date;
        Created_By = created_By;
        Last_Update = last_Update;
        Last_Update_By = last_Update_By;
        this.COUNTRY_ID = COUNTRY_ID;
    }

    /**
     * Setters and Getters
     * @param division_ID
     */

    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public void setCreate_Date(LocalDateTime create_Date) {
        Create_Date = create_Date;
    }

    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    public void setLast_Update(LocalDateTime last_Update) {
        Last_Update = last_Update;
    }

    public void setLast_Update_By(String last_Update_By) {
        Last_Update_By = last_Update_By;
    }

    public void setCOUNTRY_ID(int COUNTRY_ID) {
        this.COUNTRY_ID = COUNTRY_ID;
    }

    public int getDivision_ID() {
        return Division_ID;
    }

    public String getDivision() {
        return Division;
    }

    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public LocalDateTime getLast_Update() {
        return Last_Update;
    }

    public String getLast_Update_By() {
        return Last_Update_By;
    }

    public int getCOUNTRY_ID() {
        return COUNTRY_ID;
    }

    /**
     * @Override Division toString()
     * @return Division
     */
    @Override
    public String toString() {
        return Division;
    }
}
