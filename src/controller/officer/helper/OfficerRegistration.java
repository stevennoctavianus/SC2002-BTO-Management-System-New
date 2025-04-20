package controller.officer.helper;

import container.*;
import entity.*;
import utils.ClearScreen;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import controller.officer.template.IOfficerRegistration;

/**
 * Handles registration logic for officers who wish to manage a BTO project.
 * Officers can only register if they are not already assigned or registered,
 * and have not applied as applicants for the same project.
 */
public class OfficerRegistration implements IOfficerRegistration {

    private Officer officer;
    private ProjectList projectList;
    private RegistrationList registrationList;
    private ApplicationList applicationList;
    private Scanner scanner;

    /**
     * Constructs an officer registration handler for the given system context.
     *
     * @param officer          the logged-in officer
     * @param projectList      the list of all projects
     * @param registrationList the list of all officer registrations
     * @param applicationList  the list of all project applications
     */
    public OfficerRegistration(Officer officer, ProjectList projectList,
                               RegistrationList registrationList, ApplicationList applicationList) {
        this.officer = officer;
        this.projectList = projectList;
        this.registrationList = registrationList;
        this.applicationList = applicationList;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Allows the officer to register for a project if they are not already assigned,
     * and haven't applied to that project as an applicant.
     * Prevents duplicate or invalid registrations.
     */
    public void registerForProject() {
        if (officer.getAssignedProject() != null ||
            !officer.getPendingRegistrations().isEmpty() ||
            !officer.getApprovedRegistrations().isEmpty()) {
            System.out.println("You are already managing or registering for a project. Cannot register again.");
            return;
        }

        ArrayList<Project> availableProjects = new ArrayList<>();
        for (Project project : projectList.getProjectList()) {
            boolean hasSlot = project.getMaxOfficer() > project.getOfficers().size();
            boolean hasNotApplied = applicationList.getApplicationByApplicant(officer) == null;

            if (hasSlot && hasNotApplied) {
                availableProjects.add(project);
            }
        }

        if (availableProjects.isEmpty()) {
            System.out.println(" No projects are currently available for registration.");
            return;
        }

        System.out.println("\nAvailable Projects for Officer Registration:");
        for (int i = 0; i < availableProjects.size(); i++) {
            System.out.println((i + 1) + ") " + availableProjects.get(i).getProjectName());
        }

        System.out.print("Select a project to register (0 to cancel): ");
        int choice;
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            ClearScreen.clear();
            System.out.println("Please input an integer!");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();

        if (choice < 1 || choice > availableProjects.size()) {
            System.out.println("Registration cancelled.");
            return;
        }

        Project selectedProject = availableProjects.get(choice - 1);
        Registration registration = new Registration(officer, selectedProject);
        registrationList.addRegistration(registration);
        officer.addRegistration(registration);

        System.out.println("Registration submitted successfully for project: " + selectedProject.getProjectName());
    }

    /**
     * Displays the officer’s past and current registration attempts,
     * along with their corresponding project names and approval statuses.
     */
    public void viewRegistrationStatus() {
        ArrayList<Registration> registrations = officer.getRegistrations();
        if (registrations.isEmpty()) {
            System.out.println("No registration history found.");
            return;
        }

        System.out.println("\nRegistration History:");
        for (Registration r : registrations) {
            System.out.println("- Project: " + r.getProject().getProjectName() + " | Status: " + r.getStatus());
        }
    }
}

