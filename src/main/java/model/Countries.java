package model;

import java.time.LocalDateTime;

/**
 * Model of Countries
 *
 * @author Brandon Nguyen
 */
public class Countries {

    /**
     * Country ID
     */
    private int Country_ID;

    /**
     * Countries
     */
    private String Country;

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
     * Last Updated By Person
     */
    private String Last_Updated_By;

    /**
     * New countries instance constructor
     * @param Country_ID
     * @param Country
     * @param Create_Date
     * @param Created_By
     * @param Last_Update
     * @param Last_Updated_By
     */
    public Countries(int Country_ID, String Country, LocalDateTime Create_Date, String Created_By, LocalDateTime Last_Update, String Last_Updated_By) {
        this.Country_ID = Country_ID;
        this.Country = Country;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
    }

    /**
     * Setters and Getters
     * @return
     */

    public int getCountry_ID() {
        return Country_ID;
    }

    public String getCountry() {
        return Country;
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

    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    public void setCountry_ID(int country_ID) {
        this.Country_ID = country_ID;
    }

    public void setCountry(String country) {
        this.Country = country;
    }

    public void setCreate_Date(LocalDateTime create_Date) {
        this.Create_Date = create_Date;
    }

    public void setCreated_By(String created_By) {
        this.Created_By = created_By;
    }

    public void setLast_Update(LocalDateTime last_Update) {
        this.Last_Update = last_Update;
    }

    public void setLast_Updated_By(String last_Updated_By) {
        this.Last_Updated_By = last_Updated_By;
    }

    /**
     * @Override Country toString()
     * @return Country
     */
    @Override
    public String toString() {
        return Country;
    }
}
