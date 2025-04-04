public class Application {
    private boolean bookedFlat;
    public enum FlatType{
        TWOROOM,
        THREEROOM
    }
    public enum ApplicationStatus{
        SUCCESSFULL,
        UNSUCESSFULL,
        PENDING,
        BOOKED
    }
    private ApplicationStatus applicationStatus;
    private FlatType flatType;
    private Project project;
    private Applicant applicant;

    public Application(Project project, Applicant applicant){
        this.project = project;
        this.applicant = applicant;
        this.applicationStatus = ApplicationStatus.PENDING;
        this.bookedFlat = false;
        this.flatType = null;
    }


    //GetterSetter
    public boolean getBookedFlat() {
        return bookedFlat;
    }
    
    public void setBookedFlat(boolean bookedFlat) {
        this.bookedFlat = bookedFlat;
    }
    
    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }
    
    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
    
    public FlatType getFlatType() {
        return flatType;
    }
    
    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }
    
    public Project getProject() {
        return project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    public Applicant getApplicant() {
        return applicant;
    }
    
    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
    

}
