package entity;

import java.util.ArrayList;

/**
 * Models an officer in the BTO system.
 * They inherit from applicants but also have additional responsibilities like handling project registrations and managing BTO projects.
 */
public class Officer extends Applicant {

    private Project assignedProject;
    private ArrayList<Registration> registrations;
    private ArrayList<Project> managedProjects;

    /**
     * Default constructor.
     * Initializes the officer's registration and managed project lists.
     */
    public Officer() {
        this.registrations = new ArrayList<>();
        this.managedProjects = new ArrayList<>();
    }

    /**
     * Constructs an officer with the specified user details.
     *
     * @param name           the officer's name
     * @param nric           the NRIC of the officer
     * @param age            the officer's age
     * @param maritalStatus  marital status of the officer
     * @param password       the officer's password
     */
    public Officer(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
        this.registrations = new ArrayList<>();
        this.managedProjects = new ArrayList<>();
    }

    /**
     * Returns the project currently assigned to this officer.
     *
     * @return the assigned project
     */
    public Project getAssignedProject() {
        return assignedProject;
    }

    /**
     * Assigns a project to this officer and adds it to their list of managed projects if not already present.
     *
     * @param assignedProject the project to assign
     */
    public void setAssignedProject(Project assignedProject) {
        this.assignedProject = assignedProject;
        if (assignedProject != null && !managedProjects.contains(assignedProject)) {
            this.managedProjects.add(assignedProject);
        }
    }

    /**
     * Returns the list of all registrations submitted by this officer.
     *
     * @return the officer's registration list
     */
    public ArrayList<Registration> getRegistrations() {
        return registrations;
    }

    /**
     * Adds a registration to the officer's registration list.
     *
     * @param registration the registration to add
     */
    public void addRegistration(Registration registration) {
        if (registration != null) {
            this.registrations.add(registration);
        }
    }

    /**
     * Removes a registration from the officer's registration list.
     *
     * @param registration the registration to remove
     */
    public void removeRegistration(Registration registration) {
        if (registration != null) {
            this.registrations.remove(registration);
        }
    }

    /**
     * Returns a list of all projects this officer has ever managed or is currently managing.
     *
     * @return the list of managed projects
     */
    public ArrayList<Project> getManagedProjects() {
        return managedProjects;
    }

    /**
     * Checks whether the officer is currently managing the given project.
     *
     * @param project the project to check
     * @return true if currently managing, false otherwise
     */
    public boolean isManagingProject(Project project) {
        return assignedProject != null && assignedProject.equals(project);
    }

    /**
     * Checks whether the officer has ever managed the given project (past or present).
     *
     * @param project the project to check
     * @return true if the project is in the officer's managed list
     */
    public boolean hasManagedProject(Project project) {
        return managedProjects.contains(project);
    }

    /**
     * Retrieves all registrations submitted by this officer that were approved.
     *
     * @return list of approved registrations
     */
    public ArrayList<Registration> getApprovedRegistrations() {
        ArrayList<Registration> approved = new ArrayList<>();
        for (Registration r : registrations) {
            if (r.getStatus() == Registration.RegistrationStatus.APPROVED) {
                approved.add(r);
            }
        }
        return approved;
    }

    /**
     * Retrieves all registrations submitted by this officer that are still pending.
     *
     * @return list of pending registrations
     */
    public ArrayList<Registration> getPendingRegistrations() {
        ArrayList<Registration> pending = new ArrayList<>();
        for (Registration r : registrations) {
            if (r.getStatus() == Registration.RegistrationStatus.PENDING) {
                pending.add(r);
            }
        }
        return pending;
    }

    /**
     * Returns a short description of the officer (name and NRIC).
     *
     * @return a string representation of the officer
     */
    @Override
    public String toString() {
        return "Officer: " + getName() + " (" + getNric() + ")";
    }
}
