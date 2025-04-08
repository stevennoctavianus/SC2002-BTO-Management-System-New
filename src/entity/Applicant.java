package entity;
import java.util.ArrayList;

public class Applicant extends User {
    private Project appliedProject; // optional if still needed
    private Application currentApplication;
    private ArrayList<Application> applications;
    private ArrayList<Enquiry> enquiries;

    public Applicant() {
        this.applications = new ArrayList<>();
        this.enquiries = new ArrayList<>();
    }

    public Applicant(String name, String nric, int age, String maritalStatus, String password) {
        setName(name);
        setNric(nric);
        setAge(age);
        setMaritalStatus(maritalStatus);
        setPassword(password);
        this.applications = new ArrayList<>();
        this.enquiries = new ArrayList<>();
    }

    public Project getAppliedProject() {
        return appliedProject;
    }

    public void setAppliedProject(Project appliedProject) {
        this.appliedProject = appliedProject;
    }

    public Application getCurrentApplication(){
        return this.currentApplication;
    }

    public void setCurrentApplication(Application currentApplication){
        this.currentApplication = currentApplication;
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    public void setApplications(ArrayList<Application> applications) {
        this.applications = applications;
    }

    public void addApplication(Application application) {
        this.applications.add(application);
    }

    public void removeApplication(Application application) {
        this.applications.remove(application);
    }

    public ArrayList<Enquiry> getEnquiries() {
        return enquiries;
    }

    public void setEnquiries(ArrayList<Enquiry> enquiries) {
        this.enquiries = enquiries;
    }

    public void addEnquiry(Enquiry enquiry) {
        if (this.enquiries == null) {
            this.enquiries = new ArrayList<>();
        }
        this.enquiries.add(enquiry);
    }
}
