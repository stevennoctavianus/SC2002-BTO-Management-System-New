package controller.officer.template;

/**
 * A functional interface that defines the enquiry management capability
 * for an officer in the BTO system.
 * This is a pure interface with no implementation. It is intended to be implemented by helper classes.
 */

 public interface IOfficerManageEnquiries {

    /**
     * Displays all enquiries submitted for the project that the officer is managing.
     */
    public void viewEnquiries();

    /**
     * Allows the officer to respond to a selected enquiry.
     */
    public void replyToEnquiry();
}
