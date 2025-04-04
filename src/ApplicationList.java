import java.util.ArrayList;

public class ApplicationList {
    private ArrayList<Application> applicationList;

    public ApplicationList(){
        this.applicationList = new ArrayList<>();
    }

    public void addApplication(Application application){
        this.applicationList.add(application);
    }

    public Application getApplicationbyApplicant(Applicant applicant) {
        for (Application application : applicationList) {
            if (application.getApplicant().equals(applicant)) {
                return application; 
            }
        }
        return null; // No existing application
    }
}
