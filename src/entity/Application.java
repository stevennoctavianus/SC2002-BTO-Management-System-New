package entity;

/**
 * Models an application submitted by an applicant for a BTO project.
 * Stores details like the flat type, current status, and the applicant that made the application.
 */
public class Application {

    private boolean bookedFlat;

    /**
     * Enum representing the type of flat applied for.
     */
    public enum FlatType {
        TWOROOM,
        THREEROOM
    }

    /**
     * Enum representing the status of the application.
     */
    public enum ApplicationStatus {
        SUCCESSFUL,
        UNSUCCESSFUL,
        PENDING,
        BOOKED
    }

    private ApplicationStatus applicationStatus;
    private FlatType flatType;
    private Project project;
    private Applicant applicant;

    /**
     * Creates a new application associated with a given project and applicant.
     * Initializes the status as PENDING and assumes no flat has been booked yet.
     *
     * @param project   the project being applied for
     * @param applicant the applicant submitting the application
     */
    public Application(Project project, Applicant applicant) {
        this.project = project;
        this.applicant = applicant;
        this.applicationStatus = ApplicationStatus.PENDING;
        this.bookedFlat = false;
        this.flatType = null;
    }

    /**
     * Indicates whether the applicant has successfully booked a flat.
     *
     * @return true if the flat is booked; false otherwise
     */
    public boolean getBookedFlat() {
        return bookedFlat;
    }

    /**
     * Sets whether the flat has been booked.
     *
     * @param bookedFlat true if booked, false otherwise
     */
    public void setBookedFlat(boolean bookedFlat) {
        this.bookedFlat = bookedFlat;
    }

    /**
     * Returns the current status of the application.
     *
     * @return the application status
     */
    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    /**
     * Updates the status of the application.
     *
     * @param applicationStatus the new status to set
     */
    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    /**
     * Returns the type of flat the applicant applied for.
     *
     * @return the flat type
     */
    public FlatType getFlatType() {
        return flatType;
    }

    /**
     * Sets the type of flat for this application.
     *
     * @param flatType the chosen flat type
     */
    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    /**
     * Returns the project associated with this application.
     *
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Sets the project for this application.
     *
     * @param project the project to associate
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Returns the applicant who submitted this application.
     *
     * @return the applicant
     */
    public Applicant getApplicant() {
        return applicant;
    }

    /**
     * Updates the applicant linked to this application.
     *
     * @param applicant the applicant to set
     */
    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    /**
     * Returns a string summary of the application.
     *
     * @return a formatted string with applicant name, NRIC, flat type, and status
     */
    @Override
    public String toString() {
        return "Applicant: " + applicant.getName() +
            ", NRIC: " + applicant.getNric() +
            ", Flat Type: " + flatType +
            ", Status: " + applicationStatus;
    }
}

