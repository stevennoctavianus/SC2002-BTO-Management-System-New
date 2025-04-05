import java.util.Scanner;

class ApplicantViewProjects{
    private Applicant applicant;
    private ProjectList projectList;
    protected ApplicationList applicationList;
    private Scanner sc;

    public ApplicantViewProjects(Applicant applicant, ProjectList projectList, ApplicationList applicationList){
        this.applicant = applicant;
        this.projectList = projectList;
        this.applicationList = applicationList;
        this.sc = new Scanner(System.in);
    }

    public void viewProjectList(){
        if(applicant.getMaritalStatus() == User.MaritalStatus.SINGLE){
            for (Project project: projectList.getProjectList()){
                if(project.getAvailableTwoRoom() > 0 && project.getVisibility() == true){
                    System.out.println(project);
                }
            }
        } else{
            for(Project project: projectList.getProjectList()){
                if(project.getVisibility() == true){
                    System.out.println(project);
                }
            }
        }
    }

    public void applyForProject() {
        System.out.println("Enter Project Name to apply: ");
        String projectName = sc.nextLine();
        Project project = projectList.getProjectByName(projectName);
    
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
    
        Application currentApp = applicant.getCurrentApplication();

        if (currentApp != null && currentApp.getApplicationStatus() != Application.ApplicationStatus.UNSUCCESSFUL) {
            System.out.println("You already have an active application.");
            return;
        }
    
        Application.FlatType selectedFlatType;
    
        if (applicant.getMaritalStatus() == User.MaritalStatus.SINGLE) {
            selectedFlatType = Application.FlatType.TWOROOM;
            System.out.println("As you are single, a 2-room flat has been automatically selected.");
        } else {
            System.out.println("Select flat type to apply for:");
            System.out.println("1. 2-room flat");
            System.out.println("2. 3-room flat");
            System.out.print("Enter choice (1 or 2): ");
            String choice = sc.nextLine();
    
            if (choice.equals("1")) {
                selectedFlatType = Application.FlatType.TWOROOM;
            } else if (choice.equals("2")) {
                selectedFlatType = Application.FlatType.THREEROOM;
            } else {
                System.out.println("Invalid choice. Application cancelled.");
                return;
            }
        }
    
        Application newApplication = new Application(project, applicant);
        newApplication.setFlatType(selectedFlatType);
        applicationList.addApplication(newApplication);
        applicant.addApplication(newApplication);
        applicant.setCurrentApplication(newApplication);
    
        System.out.println("Application submitted successfully!");
    }
    

}