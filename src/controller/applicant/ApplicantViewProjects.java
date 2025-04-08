package controller.applicant;
import container.*;
import entity.*;
import java.util.Scanner;

public class ApplicantViewProjects{
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

    public void applyForProject(){
        System.out.println("Enter Project Name to apply: ");
        String projectName = sc.nextLine();
        Project project = projectList.getProjectByName(projectName);

        if (project == null){
            System.out.println("Project not found.");
            return;
        }

        if (applicationList.getApplicationByApplicant(applicant) != null){
            System.out.println("You already have an application.");
        }

        Application newApplication = new Application(project, applicant);
        applicationList.addApplication(newApplication);
        System.out.println("Success!");

    }

}