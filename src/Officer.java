import java.util.ArrayList;

public class Officer extends Applicant {
    private Project assignedProject;
    private ArrayList<Registration> registrations;
    private ArrayList<Project> managedProjects;

    public Officer() {
        this.registrations = new ArrayList<>();
        this.managedProjects = new ArrayList<>();
    }

    public Officer(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
        this.registrations = new ArrayList<>();
        this.managedProjects = new ArrayList<>();
    }

    // Currently managing project
    public Project getAssignedProject() {
        return assignedProject;
    }

    public void setAssignedProject(Project assignedProject) {
        this.assignedProject = assignedProject;
        if (assignedProject != null && !managedProjects.contains(assignedProject)) {
            this.managedProjects.add(assignedProject);
        }
    }

    // Officer's registration history
    public ArrayList<Registration> getRegistrations() {
        return registrations;
    }

    public void addRegistration(Registration registration) {
        if (registration != null) {
            this.registrations.add(registration);
        }
    }

    public void removeRegistration(Registration registration) {
        if (registration != null) {
            this.registrations.remove(registration);
        }
    }

    // All previously or currently managed projects
    public ArrayList<Project> getManagedProjects() {
        return managedProjects;
    }

    public boolean isManagingProject(Project project) {
        return assignedProject != null && assignedProject.equals(project);
    }

    public boolean hasManagedProject(Project project) {
        return managedProjects.contains(project);
    }

    // Filtered registration checks
    public ArrayList<Registration> getApprovedRegistrations() {
        ArrayList<Registration> approved = new ArrayList<>();
        for (Registration r : registrations) {
            if (r.getStatus() == Registration.RegistrationStatus.APPROVED) {
                approved.add(r);
            }
        }
        return approved;
    }

    public ArrayList<Registration> getPendingRegistrations() {
        ArrayList<Registration> pending = new ArrayList<>();
        for (Registration r : registrations) {
            if (r.getStatus() == Registration.RegistrationStatus.PENDING) {
                pending.add(r);
            }
        }
        return pending;
    }

    @Override
    public String toString() {
        return "Officer: " + getName() + " (" + getNric() + ")";
    }
}
