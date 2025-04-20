package controller.manager.template;

import entity.Project;

/**
 * A functional interface that defines enquiry management operations
 * that a manager can perform in the BTO system.
 * This is a pure interface with no implementation. It is intended to be implemented by helper classes.
 */
public interface IManagerManageEnquiries {

    /**
     * Displays all enquiries across the system, regardless of project ownership.
     */
    public void viewEnquiry();

    /**
     * Displays enquiries that belong to the manager's currently handled project.
     *
     * @param currentProject the project currently managed by the manager
     */
    public void viewHandledProjectEnquiry(Project currentProject);

    /**
     * Enables the manager to reply to pending enquiries for their active project.
     *
     * @param currentProject the project currently managed by the manager
     */
    public void replyHandledProjectEnquiry(Project currentProject);
}


