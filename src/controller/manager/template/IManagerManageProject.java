package controller.manager.template;

/**
 * A functional interface that defines core project management operations
 * that a manager can perform in the BTO system.
 * This is a pure interface with no implementation. It is intended to be implemented by helper classes.
 */
public interface IManagerManageProject {

    /**
     * Initiates the creation of a new BTO project by the manager.
     */
    public void createProject();

    /**
     * Allows the manager to edit the details of an existing project they manage.
     */
    public void editProject();

    /**
     * Deletes a project managed by the manager, including all related data such as applications and registrations.
     */
    public void deleteProject();

    /**
     * Toggles the visibility of a project (e.g., to hide or show it in the applicant view).
     */
    public void changeProjectVisibility();

    /**
     * Displays all projects currently created and managed by the manager.
     */
    public void viewOwnProject();

    /**
     * Displays every project created in the system, including those by other managers.
     */
    public void viewAllCreatedProject();
}

