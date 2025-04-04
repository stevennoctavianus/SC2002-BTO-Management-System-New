public class ApplicantViewApplication {
    private Applicant applicant;
    private ApplicationList applicationList;

    public ApplicantViewApplication(Applicant applicant, ApplicationList applicationList){
        this.applicant = applicant;
        this.applicationList = applicationList;
    }

    public void viewApplicationStatus(){
        Application application = applicationList.getApplicationByApplicant(applicant);
        if (application != null){
            System.out.println("Status: " + application.getApplicationStatus());
        } else{
            System.out.println("No Application found.");
        }

    }

}
