package entity;

/**
 * Models an officer's registration request to manage a BTO project.
 * Each registrations contains details like the officer making the registration, the project he/she is registering for and the status of it.
 */
public class Registration {

    /**
     * The current status of the registration.
     * PENDING: waiting for manager approval,
     * APPROVED: accepted to manage the project,
     * REJECTED: declined by the manager.
     */
    public enum RegistrationStatus {
        PENDING, APPROVED, REJECTED
    }

    private Officer officer;
    private Project project;
    private RegistrationStatus status;

    /**
     * Creates a new registration request from an officer for a specific project.
     * The registration status is set to PENDING by default.
     *
     * @param officer the officer requesting to register
     * @param project the project the officer wishes to manage
     */
    public Registration(Officer officer, Project project) {
        this.officer = officer;
        this.project = project;
        this.status = RegistrationStatus.PENDING;
    }

    /**
     * Returns the officer who submitted the registration.
     *
     * @return the officer
     */
    public Officer getOfficer() {
        return officer;
    }

    /**
     * Returns the project this registration is associated with.
     *
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Gets the current status of the registration.
     *
     * @return the registration status
     */
    public RegistrationStatus getStatus() {
        return status;
    }

    /**
     * Updates the status of the registration (e.g., approved or rejected).
     *
     * @param status the new status to set
     */
    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    /**
     * Returns a short summary of the registration, including officer name, project name, and status.
     *
     * @return a string summary of the registration
     */
    @Override
    public String toString() {
        return "Officer: " + officer.getName() +
               ", Project: " + project.getProjectName() +
               ", Status: " + status;
    }
}
