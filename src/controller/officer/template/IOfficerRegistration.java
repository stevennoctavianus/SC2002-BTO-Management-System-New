package controller.officer.template;

/**
 * A functional interface that defines the capability to register for a BTO Project.
 * for an officer in the BTO system.
 * This is a pure interface with no implementation. It is intended to be implemented by helper classes.
 */

 public interface IOfficerRegistration {

    /**
     * Allows the officer to register for a BTO project.
     */
    public void registerForProject();

    /**
     * Displays the registration history and status of the officer.
     */
    public void viewRegistrationStatus();
}
