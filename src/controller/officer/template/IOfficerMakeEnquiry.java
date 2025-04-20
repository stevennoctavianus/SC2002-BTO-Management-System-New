package controller.officer.template;
import entity.Project;

/**
 * A functional interface that defines the enquiry making capability
 * for an officer in the BTO system.
 * This is a pure interface with no implementation. It is intended to be implemented by helper classes.
 */

 public interface IOfficerMakeEnquiry {

    /**
     * Allows the officer to submit a new enquiry to a project
     * they are not currently managing.
     */
    public void makeEnquiry();

    /**
     * Displays all enquiries submitted by the officer,
     * excluding those for projects they are managing.
     */
    public void viewEnquiry();

    /**
     * Checks if the officer is managing the given project.
     *
     * @param p the project to check
     * @return true if the officer manages the project, false otherwise
     */
    public boolean isManagingProject(Project p);

    /**
     * Allows the officer to edit a pending enquiry,
     * only if they are not managing the associated project.
     */
    public void editEnquiry();

    /**
     * Allows the officer to delete a pending enquiry,
     * only if they are not managing the associated project.
     */
    public void deleteEnquiry();
}
