package controller.applicant.template;

/**
 * Interface for viewing the status of an applicant's BTO application.
 * This is a pure interface with no implementation. It is meant to be implemented by helper classes. */
public interface IApplicantViewApplication {

    /**
     * Displays the current status of the applicant's application,
     * including the project name, flat type, and status.
     */
    public void viewApplicationStatus();
}
