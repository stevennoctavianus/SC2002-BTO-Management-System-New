package container;
import entity.*;
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

    public ArrayList<Application> getApplicationsByProject(Project project) {
        ArrayList<Application> projectApplication = new ArrayList<>();
        for (Application application : applicationList) {
            if (application.getProject().equals(project)) {
                projectApplication.add(application);
            }
        }
        return projectApplication; // No existing application
    }

    public ArrayList<Application> getPendingApplicationsByProject(Project project) {
        ArrayList<Application> pendingApplications = new ArrayList<>();
        for (Application application : applicationList) {
            if (application.getProject().equals(project) &&
                application.getApplicationStatus() == Application.ApplicationStatus.PENDING) {
                pendingApplications.add(application);
            }
        }
        return pendingApplications;
    }


    public void removeApplicationsByProject(Project project) {
        for (Application application : new ArrayList<>(applicationList)) {
            if (application.getProject().equals(project)) {
                // Clear applicant reference
                Applicant applicant = application.getApplicant();
                if (applicant != null) {
                    applicant.setCurrentApplication(null); // Only if such a setter exists
                }
                this.removeApplication(application);
            }
        }
    }

    public ArrayList<Application> getSuccessfulApplications() {
        ArrayList<Application> successful = new ArrayList<>();
        for (Application application : applicationList) {
            if (application.getApplicationStatus() == Application.ApplicationStatus.SUCCESSFUL) {
                successful.add(application);
            }
        }
        return successful;
    }

}