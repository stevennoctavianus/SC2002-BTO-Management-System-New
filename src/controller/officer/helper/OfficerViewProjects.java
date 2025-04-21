package controller.officer.helper;

import container.*;
import entity.*;
import entity.Registration.RegistrationStatus;
import controller.officer.template.IOfficerViewProjects;
import controller.FilterSettings;
import controller.UserSession;
import controller.applicant.helper.ApplicantViewProjects;
import utils.ClearScreen;
import utils.Colour;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Allows officers to view and apply for BTO projects similarly to applicants,
 * but with additional checks to prevent conflicts with their registration or managerial roles.
 * <p>
 * Extends {@link ApplicantViewProjects} and implements {@link IOfficerViewProjects}.
 */
public class OfficerViewProjects extends ApplicantViewProjects implements IOfficerViewProjects {

    private Officer officer;
    private ProjectList projectList;
    private RegistrationList registrationList;

    /**
     * Constructs the officer-facing project view and application logic.
     *
     * @param officer           the officer using the system
     * @param projectList       the list of all BTO projects
     * @param applicationList   the global application list
     * @param registrationList  the global registration list
     */
    public OfficerViewProjects(Officer officer, ProjectList projectList, ApplicationList applicationList, RegistrationList registrationList) {
        super(officer, projectList, applicationList);
        this.officer = officer;
        this.projectList = projectList;
        this.registrationList = registrationList;
    }

    /**
     * Displays a filtered list of projects the officer can view and apply for.
     * Projects already managed or registered by the officer are excluded.
     */
    @Override
    public void viewProjectList() {
        FilterSettings filters = UserSession.getFilterSettings();
        if (officer.getMaritalStatus() == User.MaritalStatus.SINGLE && officer.getAge() >= 35){
            for (Project project : projectList.getFilteredProjects(filters)) {
                Registration reg = registrationList.getRegistrationByOfficerAndProject(officer, project);
                if (reg != null && reg.getStatus() != RegistrationStatus.REJECTED) continue;
                if (officer.getAssignedProject() != null && officer.getAssignedProject().equals(project)) continue;
                if (project.getAvailableTwoRoom() > 0 && project.getVisibility()) {
                    System.out.println(project);
                }
            }
        }
        else {
            for (Project project : projectList.getFilteredProjects(filters)) {
                Registration reg = registrationList.getRegistrationByOfficerAndProject(officer, project);
                if (reg != null && reg.getStatus() != RegistrationStatus.REJECTED) continue;
                if (officer.getAssignedProject() != null && officer.getAssignedProject().equals(project)) continue;
                if (project.getVisibility() && ((project.getAvailableThreeRoom() > 0 || project.getAvailableTwoRoom() > 0))) {
                    System.out.println(project);
                }
            }

        }
    }

    /**
     * Allows the officer to apply for a project, similar to an applicant.
     * Includes additional validation to avoid duplicate applications and registration conflicts.
     */
    @Override
    public void applyForProject() {
        Scanner sc = new Scanner(System.in);
        FilterSettings filters = UserSession.getFilterSettings();

        System.out.println(Colour.BLUE_UNDERLINED + "Projects you can apply:" + Colour.RESET);
        if (officer.getMaritalStatus() == User.MaritalStatus.SINGLE && officer.getAge() >= 35){
            for (Project project : projectList.getFilteredProjects(filters)) {
                Registration reg = registrationList.getRegistrationByOfficerAndProject(officer, project);
                if (reg != null && reg.getStatus() != RegistrationStatus.REJECTED) continue;
                if (officer.getAssignedProject() != null && officer.getAssignedProject().equals(project)) continue;
                if (project.getAvailableTwoRoom() > 0 && project.getVisibility()) {
                    System.out.println(project.getProjectName());
                }
            }
        }
        else {
            for (Project project : projectList.getFilteredProjects(filters)) {
                Registration reg = registrationList.getRegistrationByOfficerAndProject(officer, project);
                if (reg != null && reg.getStatus() != RegistrationStatus.REJECTED) continue;
                if (officer.getAssignedProject() != null && officer.getAssignedProject().equals(project)) continue;
                if (project.getVisibility() && ((project.getAvailableThreeRoom() > 0 || project.getAvailableTwoRoom() > 0))) {
                    System.out.println(project.getProjectName());
                }
            }

        }

        System.out.println(Colour.BLUE + "Enter Project Name to apply: " + Colour.RESET);
        String projectName = sc.nextLine();
        Project project = projectList.getProjectByName(projectName);

        if (project == null) {
            System.out.println(Colour.RED + "Project not found." + Colour.RESET);
            return;
        }

        if (officer.getAssignedProject() != null &&
            officer.getAssignedProject().equals(project)) {
            System.out.println(Colour.RED + "You are already managing this project as an Officer. Cannot apply as an applicant." + Colour.RESET);
            return;
        }

        Registration reg = registrationList.getRegistrationByOfficerAndProject(officer, project);
        if (reg != null && reg.getStatus() != RegistrationStatus.REJECTED) {
            System.out.println(Colour.RED + "You have already registered for this project as an Officer. Cannot apply." + Colour.RESET);
            return;
        }

        if (super.applicationList.getApplicationByApplicant(officer) != null) {
            System.out.println(Colour.RED + "You already have an application." + Colour.RESET);
            return;
        }

        if (project.getOpeningDate().after(new Date())) {
            System.out.println(Colour.RED + "The project is currently not open for application." + Colour.RESET);
            return;
        }

        Application.FlatType selectedFlatType;

        if (officer.getMaritalStatus() == User.MaritalStatus.SINGLE &&
            officer.getAge() >= 35) {
            System.out.println(Colour.YELLOW + "You are only eligible to apply for 2-ROOM flat" + Colour.RESET);
            selectedFlatType = Application.FlatType.TWOROOM;
        } else {
            System.out.println("Select flat type to apply for:");
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

        Application newApplication = new Application(project, officer);
        newApplication.setFlatType(selectedFlatType);
        super.applicationList.addApplication(newApplication);
        officer.addApplication(newApplication);
        officer.setCurrentApplication(newApplication);
        System.out.println(Colour.GREEN + "Application submitted successfully!" + Colour.RESET);
    }
}


