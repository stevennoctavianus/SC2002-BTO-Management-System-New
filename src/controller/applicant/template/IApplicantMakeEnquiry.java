package controller.applicant.template;

/**
 * A functional interface that defines the core enquiry-related operations
 * that an applicant can perform in the BTO system.
 * This is a pure interface with no implementation. It is intended to be implemented by helper classes.
 */
public interface IApplicantMakeEnquiry {

    /**
     * Allows the applicant to submit a new enquiry.
     */
    public void makeEnquiry();

    /**
     * Displays all enquiries submitted by the applicant.
     */
    public void viewEnquiry();

    /**
     * Enables the applicant to edit an existing enquiry, if it's still pending.
     */
    public void editEnquiry();

    /**
     * Removes a pending enquiry submitted by the applicant.
     */
    public void deleteEnquiry();
}

