package controller.officer.template;

/**
 * A functional interface that defines the project management capability
 * for an officer in the BTO system.
 * This is a pure interface with no implementation. It is intended to be implemented by helper classes.
 */

 public interface IOfficerManageProject {

    /**
     * Displays detailed information about the project the officer is currently assigned to.
     */
    public void viewProjectDetails();
}
