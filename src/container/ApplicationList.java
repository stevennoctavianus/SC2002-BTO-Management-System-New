import java.util.ArrayList;

public class ApplicationList {
    private ArrayList<Application> applicationList;

    public ApplicationList(){
        this.applicationList = new ArrayList<>();
    }

    public void addApplication(Application application){
        this.applicationList.add(application);
    }

    public void removeApplication(Application application) {
        if (applicationList.remove(application)) {
            System.out.println("Application removed successfully.");
        } else {
            System.out.println("Application not found.");
        }
    }

    public ArrayList<Application> getApplicationList(){
        return applicationList;
    }

    public Application getApplicationByApplicant(Applicant applicant) {
        for (Application application : applicationList) {
            if (application.getApplicant().equals(applicant)) {
                return application; 
            }
        }
        return null; // No existing application
    }
}
