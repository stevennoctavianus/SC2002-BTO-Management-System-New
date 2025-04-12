package controller.applicant.helper;
import container.*;
import controller.applicant.template.IApplicantViewProjects;
import entity.*;
import java.util.Scanner;
import utils.BackButton;
public class ApplicantViewProjects implements IApplicantViewProjects{
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
        // Show all the visible projects:
        // Move the condition of user's group to applyProject() method

        if(applicant.getMaritalStatus() == User.MaritalStatus.SINGLE && applicant.getAge() >= 35){
            for (Project project: projectList.getProjectList()){
                if(project.getAvailableTwoRoom() > 0 && project.getVisibility() == true){
                    System.out.println(project);
                }
            }
        }
        else{
            for(Project project: projectList.getProjectList()){
                if(project.getVisibility() == true){
                    System.out.println(project);
                }
            }
        }
        // Go back to Menu:
        BackButton.goBack();
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
            // Add new return:
            return;
        }
        // Check user's group:
        // If the applicant is single and above 35 years old and there is no available 2-room flat type of the project -> return
        // *********************************************/


        // *********************************************/
        Application newApplication = new Application(project, applicant);
        applicationList.addApplication(newApplication);
        System.out.println("Success Application!");
        // Go back to menu:
        BackButton.goBack();
    }

}