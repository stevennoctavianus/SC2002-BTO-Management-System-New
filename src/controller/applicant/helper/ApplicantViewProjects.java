package controller.applicant.helper;

import container.*;
import controller.FilterSettings;
import controller.UserSession;
import controller.applicant.template.IApplicantViewProjects;
import entity.*;
import utils.ClearScreen;
import utils.Colour;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Provides functionality for applicants to view and apply for BTO projects.
 * <p>
 * Implements logic based on eligibility and user-defined filter settings.
 */
public class ApplicantViewProjects implements IApplicantViewProjects {

    private Applicant applicant;
    private ProjectList projectList;
    protected ApplicationList applicationList;
    private Scanner sc;

    /**
     * Constructs a project viewing handler for the given applicant.
     *
     * @param applicant        the currently logged-in applicant
     * @param projectList      the list of available BTO projects
     * @param applicationList  the list of existing applications
     */
    public ApplicantViewProjects(Applicant applicant, ProjectList projectList, ApplicationList applicationList) {
        this.applicant = applicant;
        this.projectList = projectList;
        this.applicationList = applicationList;
        this.sc = new Scanner(System.in);
    }

    /**
     * Displays all projects the applicant is eligible to view,
     * filtered by marital status, age, and active user filters.
     */
    public void viewProjectList() {
        FilterSettings filters = UserSession.getFilterSettings();
        if (applicant.getMaritalStatus() == User.MaritalStatus.SINGLE && applicant.getAge() >= 35) {
            for (Project project : projectList.getFilteredProjects(filters)) {
                if (project.getAvailableTwoRoom() > 0 && project.getVisibility()) {
                    System.out.println(project);
                }
            }
        } else {
            for (Project project : projectList.getFilteredProjects(filters)) {
                if (project.getVisibility() && (project.getAvailableThreeRoom() > 0 || project.getAvailableTwoRoom() > 0)) {
                    System.out.println(project);
                }
            }
        }
    }

    /**
     * Allows the applicant to apply for a BTO project based on eligibility rules.
     * Performs checks for existing applications, project visibility, availability,
     * and flat type selection.
     */
    public void applyForProject() {
        FilterSettings filters = UserSession.getFilterSettings();
        System.out.println(Colour.BLUE_UNDERLINED + "Projects you can apply: " + Colour.RESET);

        if (applicant.getMaritalStatus() == User.MaritalStatus.SINGLE && applicant.getAge() >= 35) {
            for (Project project : projectList.getFilteredProjects(filters)) {
                if (project.getAvailableTwoRoom() > 0 && project.getVisibility()) {
                    System.out.println(project.getProjectName());
                }
            }
        } else {
            for (Project project : projectList.getFilteredProjects(filters)) {
                if (project.getVisibility() && (project.getAvailableThreeRoom() > 0 || project.getAvailableTwoRoom() > 0)) {
                    System.out.println(project.getProjectName());
                }
            }
        }

        Application currentApp = applicant.getCurrentApplication();
        if (currentApp != null && currentApp.getApplicationStatus() != Application.ApplicationStatus.UNSUCCESSFUL) {
            ClearScreen.clear();
            System.out.println(Colour.RED + "You already have an active application." + Colour.RESET);
            return;
        }

        System.out.print(Colour.BLUE + "Enter Project Name to apply: " + Colour.RESET);
        String projectName = sc.nextLine();
        Project project = projectList.getProjectByName(projectName);

        if (project == null) {
            System.out.println(Colour.RED + "Project not found." + Colour.RESET);
            return;
        }

        if (project.getOpeningDate().after(new Date())) {
            System.out.println(Colour.RED + "The project is currently not open for application." + Colour.RESET);
            return;
        }

        Application.FlatType selectedFlatType;

        if (applicant.getMaritalStatus() == User.MaritalStatus.SINGLE && applicant.getAge() >= 35) {
            System.out.println(Colour.YELLOW + "You are only eligible to apply for 2-ROOM flat" + Colour.RESET);
            selectedFlatType = Application.FlatType.TWOROOM;
        } else {
            System.out.println(Colour.BLUE + "Select flat type to apply for: " + Colour.RESET);
            System.out.println("\nEnter 1 -> 2-Room Flat");
            System.out.println("Enter 2 -> 3-Room Flat");
            System.out.print("Enter choice (1 or 2): ");
            int choice;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                ClearScreen.clear();
                System.out.println(Colour.RED + "Please input an integer!" + Colour.RESET);
                sc.nextLine();
                return;
            }
            sc.nextLine();

            if (choice == 1) {
                if (project.getAvailableTwoRoom() == 0) {
                    System.out.println(Colour.BLUE + "Sorry, there are no available 2-room flats." + Colour.RESET);
                    return;
                }
                selectedFlatType = Application.FlatType.TWOROOM;
            } else if (choice == 2) {
                if (project.getAvailableThreeRoom() == 0) {
                    System.out.println(Colour.BLUE + "Sorry, there are no available 3-room flats." + Colour.RESET);
                    return;
                }
                selectedFlatType = Application.FlatType.THREEROOM;
            } else {
                System.out.println(Colour.RED + "Invalid choice. Application cancelled." + Colour.RESET);
                return;
            }
        }

        Application newApplication = new Application(project, applicant);
        newApplication.setFlatType(selectedFlatType);
        applicationList.addApplication(newApplication);
        applicant.addApplication(newApplication);
        applicant.setCurrentApplication(newApplication);
        System.out.println(Colour.GREEN + "Application submitted successfully!" + Colour.RESET);
    }
}
