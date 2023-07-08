package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * This class creates First_Level_Division database methods
 *
 * @author Brandon Nguyen
 */
public class FirstLevelDivisionDaoImpl {

    /**
     * Creates ObservableList of FirstLevelDivision, Executes SQL Query to find all Division with selected Country ID then add to allDivisions ObservableList
     * @param countryId Country ID to find Division
     * @return ObservableList allDivisions of all divisions by selected Country ID
     */
    public static ObservableList<FirstLevelDivision> getDivFromCountId(int countryId) {
        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID='" + countryId + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDateCalendar = createDate.toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                LocalDateTime lastUpdateCalendar = lastUpdate.toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int COUNTRY_ID = rs.getInt("COUNTRY_ID");

                FirstLevelDivision division1 = new FirstLevelDivision(divisionId, division, createDateCalendar, createdBy,lastUpdateCalendar, lastUpdatedBy, COUNTRY_ID);
                allDivisions.add(division1);
            }

        }
        catch (SQLException e) {
            System.out.println("getDivFromCountId Error: " + e.getMessage());
        }
        return allDivisions;
    }

    /**
     * Create FirstLevelDivision Object, Execute SQL Query to find all divisions with selected Division ID then ad to fromDivDivision
     * @param divisionId Division ID for divisions to find
     * @return return FirstLevelDivision fromDivIdDivision if found return null if not found
     */
    public static FirstLevelDivision getDivFromDivId(int divisionId) {
        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID ='" + divisionId + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDateCalendar = createDate.toLocalDateTime();
                String createBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                LocalDateTime lastUpdateCalendar = lastUpdate.toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryId = rs.getInt("COUNTRY_ID");

                FirstLevelDivision fromDivIdDivision = new FirstLevelDivision(divisionID, division, createDateCalendar, createBy, lastUpdateCalendar, lastUpdatedBy, countryId);
                return fromDivIdDivision;
            }
        }
        catch (SQLException e) {
            System.out.println("getDivFromDivId Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Creates ObservableList of FirstLevelDivision, Executes SQL Query to get all divisions by selected FirstLevelDivision Object then add to allDivisions ObservableList
     * @param firstLevelDivision Object to find
     * @return ObservableList allDivisions of selected object return null if not found
     */
    public static FirstLevelDivision getDivisionFromFLDObject(FirstLevelDivision firstLevelDivision) {
        //ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Division ='" + firstLevelDivision + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDateCalendar = createDate.toLocalDateTime();
                String createBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                LocalDateTime lastUpdateCalendar = lastUpdate.toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryId = rs.getInt("COUNTRY_ID");

                FirstLevelDivision firstLevelDivision1 = new FirstLevelDivision(divisionID, division, createDateCalendar, createBy, lastUpdateCalendar, lastUpdatedBy, countryId);
                return firstLevelDivision1;
            }
        }
        catch (SQLException e) {
            System.out.println("getDivision(FirstLevelDivision) Error: " + e.getMessage());
        }
        return null;
    }
}
