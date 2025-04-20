package controller.manager.template;

import entity.Project;

/**
 * A functional interface that defines application management operations
 * that a manager can perform in the BTO system.
 * This is a pure interface with no implementation. It is intended to be implemented by helper classes.
 */
public interface IManagerManageApplication {

    /**
     * Displays all applications submitted for the specified project.
     *
     * @param project the project whose applications are to be viewed
     */
    public void viewApplication(Project project);

    /**
     * Allows the manager to accept or reject pending applications for the specified project.
     *
     * @param project the project whose applications are to be managed
     */
    public void manageApplication(Project project);
}
