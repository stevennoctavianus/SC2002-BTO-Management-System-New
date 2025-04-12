package controller.officer.helper;
import container.*;
import entity.*;
import java.util.List;
import java.util.Scanner;
import controller.officer.template.IOfficerViewProjects;
import controller.applicant.helper.ApplicantViewProjects;
public class OfficerViewProjects extends ApplicantViewProjects implements IOfficerViewProjects{
    private Officer officer;
    private ProjectList projectList;
    private RegistrationList registrationList;

    public OfficerViewProjects(Officer officer, ProjectList projectList, ApplicationList applicationList, RegistrationList registrationList) {
        super(officer, projectList, applicationList);
        this.officer = officer;
        this.projectList = projectList;
        this.registrationList = registrationList;
    }

    @Override
    public void viewProjectList() {

        System.out.println("Available BTO Projects:");
        for (Project project : projectList.getProjectList()) {

            // Skip projects this officer is registered for (even if not yet approved)
            Registration reg = registrationList.getRegistrationByOfficerAndProject(officer, project);
            if (reg != null) continue;

            // Skip if officer is assigned to this project
            if (officer.getAssignedProject() != null && officer.getAssignedProject().equals(project)) {
                continue;
            }

            // If SINGLE, show only with available 2-Rooms -> show all projects except which are registered and monitored
            // Age group consideration should be in applyProject()

            // if (officer.getMaritalStatus() == User.MaritalStatus.SINGLE) {
            //     if (project.getAvailableTwoRoom() > 0) {
            //         System.out.println(project);
            //     }
            // } else {
            //     System.out.println(project);
            // }
            if(project.getVisibility() == true){
                System.out.println(project);
            }
        }
    }

    @Override
    public void applyForProject() {
        Scanner sc = new Scanner(System.in);
        // Briefly show the name of available projects:
        System.out.println("Projects you can apply:");
        for (Project project : projectList.getProjectList()) {
            Registration reg = registrationList.getRegistrationByOfficerAndProject(officer, project);
            if (reg != null) continue;
            if (officer.getAssignedProject() != null && officer.getAssignedProject().equals(project)) continue;
            if(project.getVisibility() == true) System.out.println("+)" + project.getProjectName());
        }
        System.out.print("Enter Project Name to apply: ");
        String projectName = sc.nextLine();
        Project project = projectList.getProjectByName(projectName);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        // Officer is already assigned to manage this project
        if (officer.getAssignedProject() != null && officer.getAssignedProject().equals(project)) {
            System.out.println("You are already managing this project as an Officer. Cannot apply as an applicant.");
            return;
        }

        // Officer has registration record for this project
        Registration reg = registrationList.getRegistrationByOfficerAndProject(officer, project);
        if (reg != null) {
            System.out.println("You have already registered for this project as an Officer. Cannot apply.");
            return;
        }

        // Already has an application
        Application existingApp = super.applicationList.getApplicationByApplicant(officer);
        if (existingApp != null) {
            System.out.println("You already have an application.");
            return;
        }


        // Manage Age group:
        if(officer.getMaritalStatus() == User.MaritalStatus.SINGLE && officer.getAge() >= 35 && project.getAvailableTwoRoom() == 0){
            System.out.println("There is no available flat for your age group!");
            return;
        }


        // Proceed with application
        Application newApplication = new Application(project, officer);
        super.applicationList.addApplication(newApplication);
        System.out.println("Application submitted successfully!");
    }
}
