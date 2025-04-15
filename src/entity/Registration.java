package entity;
public class Registration {
    public enum RegistrationStatus {
        PENDING, APPROVED, REJECTED
    }

    private Officer officer;
    private Project project;
    private RegistrationStatus status;

    public Registration(Officer officer, Project project) {
        this.officer = officer;
        this.project = project;
        this.status = RegistrationStatus.PENDING;
    }

    public Officer getOfficer() {
        return officer;
    }

    public Project getProject() {
        return project;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Officer: " + officer.getName() +
               ", Project: " + project.getProjectName() +
               ", Status: " + status;
    }
}
