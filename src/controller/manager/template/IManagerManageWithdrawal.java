package controller.manager.template;

import entity.Project;

/**
 * A functional interface that defines the withdrawal request management operations
 * that a manager can perform in the BTO system.
 * This is a pure interface with no implementation. It is intended to be implemented by helper classes.
 */
public interface IManagerManageWithdrawal {

    /**
     * Allows the manager to review and process pending withdrawal requests
     * for applicants within a specific project.
     *
     * @param project the project associated with the withdrawal requests
     */
    public void manageWithdrawal(Project project);
}

