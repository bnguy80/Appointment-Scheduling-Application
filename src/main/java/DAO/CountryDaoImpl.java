package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class creates the Countries Database methods
 *
 * @author Brandon Nguyen
 */
public class CountryDaoImpl {

    /**
     * Creates ObservableList of Countries, Executes SQL Query to get all countries then add to allCountries ObservableList
     * @return ObservableList allCountries of all countries
     */
    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> allCountries = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                Timestamp countryCreate = rs.getTimestamp("Create_Date");
                LocalDateTime countryCreateDate = countryCreate.toLocalDateTime();
                String countryCreateBy = rs.getString("Created_By");
                Timestamp countryLast = rs.getTimestamp("Last_Update");
                LocalDateTime countryLastUpdate = countryLast.toLocalDateTime();
                String countryLastUpdatedBy = rs.getString("Last_Updated_By");

                Countries countries1 = new Countries(countryId, country, countryCreateDate, countryCreateBy, countryLastUpdate, countryLastUpdatedBy);
                allCountries.add(countries1);

                System.out.println(countries1.getCountry());
            }
        }
        catch (SQLException e) {
            System.out.println("getAllCountries CountryDaoImpl Error" + e.getMessage());
        }
        return allCountries;
    }

    /**
     * Creates Countries Object, Executes SQL Query to find all countries with selected Country ID then add to countries1
     * @param countryId Country ID for countries to find
     * @return Countries countries1
     */
    public static Countries getCountriesFromCountId(int countryId) {
        try {
            String sql = "SELECT * FROM countries WHERE Country_ID = '" + countryId + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String country = rs.getString("Country");
                Timestamp countryCreate = rs.getTimestamp("Create_Date");
                LocalDateTime countryCreateDate = countryCreate.toLocalDateTime();
                String countryCreateBy = rs.getString("Created_By");
                Timestamp countryLast = rs.getTimestamp("Last_Update");
                LocalDateTime countryLastUpdate = countryLast.toLocalDateTime();
                String countryLastUpdatedBy = rs.getString("Last_Updated_By");

                Countries countries1 = new Countries(countryId, country, countryCreateDate, countryCreateBy, countryLastUpdate, countryLastUpdatedBy);
                return countries1;
            }
        }
        catch (SQLException e) {
            System.out.println("getCountriesFromCountId Error: " + e.getMessage());
        }
        return null;
    }
}
