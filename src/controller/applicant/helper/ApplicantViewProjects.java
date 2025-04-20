package controller.applicant.helper;

import container.*;
import controller.applicant.template.IApplicantViewProjects;
import entity.*;
import utils.ClearScreen;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Handles viewing and applying for BTO projects on behalf of an applicant.
 * Also enforces eligibility rules and flat type restrictions based on applicant profile.
 */
public class ApplicantViewProjects implements IApplicantViewProjects {

    private Applicant applicant;
    private ProjectList projectList;
    protected ApplicationList applicationList;
    private Scanner sc;

    /**
     * Constructs a handler for viewing and applying to projects.
     *
     * @param applicant       the applicant using this module
     * @param projectList     the list of available BTO projects
     * @param applicationList the shared application list
     */
    public ApplicantViewProjects(Applicant applicant, ProjectList projectList, ApplicationList applicationList) {
        this.applicant = applicant;
        this.projectList = projectList;
        this.applicationList = applicationList;
        this.sc = new Scanner(System.in);
    }

    /**
     * Displays a list of projects the applicant is eligible to view.
     * Filters are applied based on marital status, age, and project visibility.
     */
    public void viewProjectList() {
        if (applicant.getMaritalStatus() == User.MaritalStatus.SINGLE && applicant.getAge() >= 35) {
            for (Project project : projectList.getProjectList()) {
                if (project.getAvailableTwoRoom() > 0 && project.getVisibility()) {
                    System.out.println(project);
                }
            }
        } else {
            for (Project project : projectList.getProjectList()) {
                if (project.getVisibility() && (project.getAvailableThreeRoom() > 0 || project.getAvailableTwoRoom() > 0)) {
                    System.out.println(project);
                }
            }
        }
    }

    /**
     * Allows the applicant to apply for a visible and eligible BTO project.
     * Includes checks for application date, flat availability, and applicant restrictions.
     */
    public void applyForProject() {
        System.out.println("Projects you can apply: ");

        if (applicant.getMaritalStatus() == User.MaritalStatus.SINGLE && applicant.getAge() >= 35) {
            for (Project project : projectList.getProjectList()) {
                if (project.getAvailableTwoRoom() > 0 && project.getVisibility()) {
                    System.out.println(project.getProjectName());
                }
            }
        } else {
            for (Project project : projectList.getProjectList()) {
                if (project.getVisibility() && (project.getAvailableThreeRoom() > 0 || project.getAvailableTwoRoom() > 0)) {
                    System.out.println(project.getProjectName());
                }
            }
        }

        System.out.print("Enter Project Name to apply: ");
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

        if (project.getOpeningDate().after(new Date())) {
            System.out.println("The project is currently not open for application.");
            return;
        }

        Application.FlatType selectedFlatType;

        if (applicant.getMaritalStatus() == User.MaritalStatus.SINGLE && applicant.getAge() >= 35) {
            System.out.println("You are only eligible to apply for 2-ROOM flat");
            selectedFlatType = Application.FlatType.TWOROOM;
        } else {
            System.out.println("Select flat type to apply for:");
            System.out.println("Enter 1 -> 2-room flat");
            System.out.println("Enter 2 -> 3-room flat");
            System.out.print("Enter choice (1 or 2): ");
            int choice;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                ClearScreen.clear();
                System.out.println("Please input an integer!");
                sc.nextLine();
                return;
            }
            sc.nextLine();

            if (choice == 1) {
                if (project.getAvailableTwoRoom() == 0) {
                    System.out.println("Sorry, there is not any 2-room flats");
                    return;
                }
                selectedFlatType = Application.FlatType.TWOROOM;
            } else if (choice == 2) {
                if (project.getAvailableThreeRoom() == 0) {
                    System.out.println("Sorry, there is not any 3-room flats");
                    return;
                }
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
