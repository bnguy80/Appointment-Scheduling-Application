package model;

/**
 * Model of Contacts
 *
 * @author Brandon Nguyen
 */
public class Contacts {

    /**
     * Contact ID
     */
    private int contactId;

    /**
     * Contact Name
     */
    private String contactName;

    /**
     * Contact Email
     */
    private String contactEmail;

    /**
     * New contact instance constructor
     * @param contactId
     * @param contactName
     * @param contactEmail
     */
    public Contacts(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Setters and Getters
     * @return
     */

    public int getContactId() {
        return contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * Override Contact Name toString()
     * @return Contact Name
     */
    @Override
    public String toString() {
        return contactName;
    }
}
