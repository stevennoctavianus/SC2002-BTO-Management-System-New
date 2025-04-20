package controller.manager.template;

import entity.Project;

/**
 * A functional interface that defines the registration management operations
 * that a manager can perform in the BTO system.
 * This is a pure interface with no implementation. It is intended to be implemented by helper classes.
 */
public interface IManagerManageRegistration {

    /**
     * Displays all officer registration requests in the system.
     */
    public void viewRegistration();

    /**
     * Allows the manager to manage pending officer registrations for their assigned project.
     *
     * @param project the project for which registrations will be managed
     */
    public void manageRegistration(Project project);
}
