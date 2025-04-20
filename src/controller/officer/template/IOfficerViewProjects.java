package controller.officer.template;

/**
 * A functional interface that defines the project viewing capability
 * for an officer in the BTO system.
 * This is a pure interface with no implementation. It is intended to be implemented by helper classes.
 */

 public interface IOfficerViewProjects {

    /**
     * Displays the list of available BTO projects for the officer.
     */
    public void viewProjectList();

    /**
     * Allows the officer to apply for a selected BTO project.
     */
    public void applyForProject();
}
