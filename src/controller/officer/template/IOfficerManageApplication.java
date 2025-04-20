package controller.officer.template;

/**
 * A functional interface that defines the application making capability for an officer in the BTO system.
 * This is a pure interface with no implementation. It is intended to be implemented by helper classes.
 */

 public interface IOfficerManageApplication {

    /**
     * Displays all successful applications for the project that the officer is managing.
     */
    public void viewApplications();

    /**
     * Updates the status of a successful application to a booked one,
     * allowing the officer to confirm flat allocation.
     */
    public void updateApplicationStatus();
}
