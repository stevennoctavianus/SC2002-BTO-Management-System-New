
import java.util.ArrayList;

public class Applicant extends User {
    private Project appliedProject;
    private Application application;
    private ArrayList<Enquiry> enquiries;

    public Applicant(){
        this.enquiries = new ArrayList<>();
    }

    public Applicant(String name, String nric, int age, String maritalStatus, String password){
        setName(name);
        setNric(nric);
        setAge(age);
        setMaritalStatus(maritalStatus);
        setPassword(password);
        this.enquiries = new ArrayList<>();
    }

    public Project getAppliedProject() {
        return appliedProject;
    }
    
    public void setAppliedProject(Project appliedProject) {
        this.appliedProject = appliedProject;
    }
    
    public Application getApplication() {
        return application;
    }
    
    public void setApplication(Application application) {
        this.application = application;
    }
    
    public ArrayList<Enquiry> getEnquiries() {
        return enquiries;
    }
    
    public void setEnquiries(ArrayList<Enquiry> enquiries) {
        this.enquiries = enquiries;
    }
    
    // Optional: Method to add a single enquiry to the list
    public void addEnquiry(Enquiry enquiry) {
        if (this.enquiries == null) {
            this.enquiries = new ArrayList<>();
        }
        this.enquiries.add(enquiry);
    }
}
