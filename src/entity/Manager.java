package entity;

import java.util.ArrayList;

/**
 * Models a manager in the BTO system.
 * They are responsible for creating and managing multiple BTO projects.
 */
public class Manager extends User {

    /**
     * The list of projects this manager is overseeing.
     */
    private ArrayList<Project> managedProjects;

    /**
     * The manager's currently active project, if any.
     */
    private Project activeProject;

    /**
     * Default constructor.
     * Initializes the manager's list of managed projects.
     */
    public Manager() {
        this.managedProjects = new ArrayList<>();
    }

    /**
     * Constructs a manager with the specified user details.
     *
     * @param name           the manager's name
     * @param nric           the NRIC of the manager
     * @param age            the manager's age
     * @param maritalStatus  the marital status of the manager
     * @param password       the manager's login password
     */
    public Manager(String name, String nric, int age, String maritalStatus, String password) {
        setName(name);
        setNric(nric);
        setAge(age);
        setMaritalStatus(maritalStatus);
        setPassword(password);
        this.managedProjects = new ArrayList<>();
    }

    /**
     * Returns the manager's currently active project.
     *
     * @return the active project, or null if none is assigned
     */
    public Project getActiveProject() {
        return this.activeProject;
    }

    /**
     * Sets the manager's active project.
     *
     * @param project the project to mark as active
     */
    public void setActiveProject(Project project) {
        this.activeProject = project;
    }

    /**
     * Returns the list of projects managed by this manager.
     *
     * @return the list of managed projects
     */
    public ArrayList<Project> getManagedProjects() {
        return this.managedProjects;
    }

    /**
     * Adds a project to the manager's list of managed projects.
     *
     * @param project the project to add
     */
    public void addManagedProject(Project project) {
        this.managedProjects.add(project);
    }
}
