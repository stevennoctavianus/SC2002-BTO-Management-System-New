package entity;

import java.util.ArrayList;

/**
 * Models an applicant in the BTO management system.
 * They can view available BTO project listings, apply for available projects, make enquiries, and view their application statuses.
 */
public class Applicant extends User {

    /**
     * The project the applicant has applied for.
     */
    private Project appliedProject;

    /**
     * The current application submitted by the applicant.
     */
    private Application currentApplication;

    /**
     * A list of all applications submitted by the applicant.
     */
    private ArrayList<Application> applications;

    /**
     * A list of enquiries made by the applicant.
     */
    private ArrayList<Enquiry> enquiries;

    /**
     * Default constructor that initializes empty lists for applications and enquiries.
     */
    public Applicant() {
        this.applications = new ArrayList<>();
        this.enquiries = new ArrayList<>();
    }

    /**
     * Constructs an applicant with specified details.
     *
     * @param name the applicant's name
     * @param nric the applicant's NRIC
     * @param age the applicant's age
     * @param maritalStatus the marital status of the applicant
     * @param password the login password for the applicant
     */
    public Applicant(String name, String nric, int age, String maritalStatus, String password) {
        setName(name);
        setNric(nric);
        setAge(age);
        setMaritalStatus(maritalStatus);
        setPassword(password);
        this.applications = new ArrayList<>();
        this.enquiries = new ArrayList<>();
    }

    /**
     * Returns the project this applicant has applied for.
     *
     * @return the applied project
     */
    public Project getAppliedProject() {
        return appliedProject;
    }

    /**
     * Sets the project this applicant has applied for.
     *
     * @param appliedProject the project to associate with the applicant
     */
    public void setAppliedProject(Project appliedProject) {
        this.appliedProject = appliedProject;
    }

    /**
     * Returns the current application of the applicant.
     *
     * @return the current application
     */
    public Application getCurrentApplication() {
        return this.currentApplication;
    }

    /**
     * Sets the current application of the applicant.
     *
     * @param currentApplication the application to set as current
     */
    public void setCurrentApplication(Application currentApplication) {
        this.currentApplication = currentApplication;
    }

    /**
     * Returns the list of all applications submitted by the applicant.
     *
     * @return the list of applications
     */
    public ArrayList<Application> getApplications() {
        return applications;
    }

    /**
     * Sets the list of applications submitted by the applicant.
     *
     * @param applications the list of applications to assign
     */
    public void setApplications(ArrayList<Application> applications) {
        this.applications = applications;
    }

    /**
     * Adds an application to the applicant's list of applications.
     *
     * @param application the application to add
     */
    public void addApplication(Application application) {
        this.applications.add(application);
    }

    /**
     * Removes an application from the applicant's list of applications.
     *
     * @param application the application to remove
     */
    public void removeApplication(Application application) {
        this.applications.remove(application);
    }

    /**
     * Returns the list of enquiries made by the applicant.
     *
     * @return the list of enquiries
     */
    public ArrayList<Enquiry> getEnquiries() {
        return enquiries;
    }

    /**
     * Sets the list of enquiries for the applicant.
     *
     * @param enquiries the list of enquiries to assign
     */
    public void setEnquiries(ArrayList<Enquiry> enquiries) {
        this.enquiries = enquiries;
    }

    /**
     * Adds an enquiry to the applicant's enquiry list.
     *
     * @param enquiry the enquiry to add
     */
    public void addEnquiry(Enquiry enquiry) {
        if (this.enquiries == null) {
            this.enquiries = new ArrayList<>();
        }
        this.enquiries.add(enquiry);
    }
}

