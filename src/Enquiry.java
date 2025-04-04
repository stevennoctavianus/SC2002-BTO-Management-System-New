public class Enquiry {
    private Applicant applicant;
    private Project project;
    private String message;
    private EnquiryStatus status;

    public enum EnquiryStatus {
        PENDING, RESPONDED, CLOSED;
    }
    

    public Enquiry(Applicant applicant, Project project, String message) {
        this.applicant = applicant;
        this.project = project;
        this.message = message;
        this.status = EnquiryStatus.PENDING;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Project getProject(){
        return project;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EnquiryStatus getStatus() {
        return status;
    }

    public void setStatus(EnquiryStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Enquiry [Applicant=" + applicant.getName() + 
               ", Project=" + project.getProjectName() + 
               ", Message=" + message + 
               ", Status=" + status + "]";
    }
}
