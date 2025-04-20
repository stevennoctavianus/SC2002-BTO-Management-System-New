package controller.applicant.helper;

import container.*;
import entity.*;
import controller.applicant.template.IApplicantViewApplication;

/**
 * Allows applicants to view the current status of their BTO application.
 * Displays basic project and application details if an application exists.
 */
public class ApplicantViewApplication implements IApplicantViewApplication {

    private Applicant applicant;
    private ApplicationList applicationList;

    /**
     * Constructs a viewer to check an applicant's application status.
     *
     * @param applicant       the logged-in applicant
     * @param applicationList the list of all applications in the system
     */
    public ApplicantViewApplication(Applicant applicant, ApplicationList applicationList) {
        this.applicant = applicant;
        this.applicationList = applicationList;
    }

    /**
     * Displays the status of the applicant's current BTO application.
     * If the applicant has not applied for a project, a message is shown.
     */
    public void viewApplicationStatus() {
        Application application = applicationList.getApplicationByApplicant(applicant);

        if (application != null) {
            System.out.println("Project Name: " + application.getProject().getProjectName());
            System.out.println("Flat Type: " + application.getFlatType());
            System.out.println("Status: " + application.getApplicationStatus());
        } else {
            System.out.println("No Application found.");
        }
    }
}

