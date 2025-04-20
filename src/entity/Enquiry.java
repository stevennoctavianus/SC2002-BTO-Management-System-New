package entity;

/**
 * Models an enquiry submitted by an applicant regarding a BTO project.
 * Includes the original message, optional reply, and its current status.
 */
public class Enquiry {

    private Applicant applicant;
    private Project project;
    private String message;
    private String reply;
    private EnquiryStatus status;

    /**
     * Enum representing the current status of the enquiry.
     * PENDING means it has not been responded to,
     * RESPONDED indicates a reply has been provided.
     */
    public enum EnquiryStatus {
        PENDING,
        RESPONDED
    }

    /**
     * Constructs a new enquiry submitted by an applicant for a specific project.
     * The enquiry starts in PENDING state with no reply.
     *
     * @param applicant the applicant submitting the enquiry
     * @param project   the project the enquiry is related to
     * @param message   the content of the enquiry
     */
    public Enquiry(Applicant applicant, Project project, String message) {
        this.applicant = applicant;
        this.project = project;
        this.message = message;
        this.status = EnquiryStatus.PENDING;
    }

    /**
     * Returns the applicant who submitted the enquiry.
     *
     * @return the applicant
     */
    public Applicant getApplicant() {
        return applicant;
    }

    /**
     * Returns the project that the enquiry is about.
     *
     * @return the related project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Retrieves the message content of the enquiry.
     *
     * @return the enquiry message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Updates the enquiry message (e.g., in case of edits).
     *
     * @param message the new message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the reply provided to the enquiry, if any.
     *
     * @return the reply message, or null if not yet responded
     */
    public String getReply() {
        return reply;
    }

    /**
     * Sets the reply to this enquiry.
     *
     * @param reply the response message
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    /**
     * Returns the current status of the enquiry.
     *
     * @return the enquiry status (PENDING or RESPONDED)
     */
    public EnquiryStatus getStatus() {
        return status;
    }

    /**
     * Updates the status of the enquiry.
     *
     * @param status the new status to assign
     */
    public void setStatus(EnquiryStatus status) {
        this.status = status;
    }

    /**
     * Returns a string summary of the enquiry, including applicant name, project, message, reply, and status.
     *
     * @return a readable string representation of the enquiry
     */
    @Override
    public String toString() {
        return "Enquiry [Applicant=" + applicant.getName() +
               ", Project=" + project.getProjectName() +
               ", Message=" + message +
               ", Reply=" + reply +
               ", Status=" + status + "]";
    }
}
