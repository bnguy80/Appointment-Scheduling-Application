package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates the Contacts Database methods
 *
 * @author Brandon Nguyen
 */
public class ContactsDaoImpl {

    /**
     * Create ObservableList of Contacts, Executes SQL Query to find all contacts then add to allContacts ObservableList
     * @return ObservableList allContacts with all contacts
     */
    public static ObservableList<Contacts> getAllContacts() {
        ObservableList<Contacts> allContacts = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                Contacts contacts1 = new Contacts(contactId, contactName, contactEmail);
                allContacts.add(contacts1);

                //System.out.println(contacts1.getContactName());
            }
        }
        catch (SQLException e) {
            System.out.println("getAllContacts Error: " + e.getMessage());
        }
        return allContacts;
    }

    /**
     * Create Contacts object, Execute SQL Query to find all contacts with selected Contact ID then add to contacts1
     * @param contactId Contact ID for Contacts to find
     * @return Contacts contacts1, return null if Query failed
     */
    public static Contacts getContactFromContId(int contactId) {
        try {
            String sql = "SELECT * FROM contacts WHERE Contact_ID = '" + contactId + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                Contacts contacts1 = new Contacts(contactId, contactName, contactEmail);
                return contacts1;
            }
        }
        catch (SQLException e) {
            System.out.println("getContactFromContId Error: " + e.getMessage());
        }
        return null;
    }
}
