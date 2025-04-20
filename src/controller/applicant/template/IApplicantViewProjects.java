package controller.applicant.template;

/**
 * Interface defining the operations related to viewing and applying for BTO projects.
 * This is a pure interface with no implementation. It is intended to be implemented by helper classes.
 */
public interface IApplicantViewProjects {

    /**
     * Displays a list of available and eligible BTO projects for the applicant.
     */
    public void viewProjectList();

    /**
     * Allows the applicant to submit a new application for a selected project.
     */
    public void applyForProject();
}

