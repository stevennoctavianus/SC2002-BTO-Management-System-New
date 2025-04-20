package controller.manager.helper;

import java.util.ArrayList;
import java.util.Scanner;
import entity.*;
import container.*;
import controller.manager.template.IManagerManageApplication;

/**
 * Provides managers with tools to view and manage applications for a specific project.
 * Supports reviewing pending applications and approving/rejecting them based on flat availability.
 */
public class ManagerManageApplication implements IManagerManageApplication {

    private ApplicationList applicationList;
    private Scanner scanner;

    /**
     * Constructs a manager application handler using the global application list.
     *
     * @param applicationList the list of all applications in the system
     */
    public ManagerManageApplication(ApplicationList applicationList) {
        this.applicationList = applicationList;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays all applications that have been submitted for a specific project.
     *
     * @param project the project whose applications should be listed
     */
    public void viewApplication(Project project) {
        ArrayList<Application> applications = applicationList.getApplicationsByProject(project);
        if (applications.isEmpty()) {
            System.out.println("No applications for this project.");
            return;
        }

        System.out.println("All Applications for Project: " + project.getProjectName());
        for (Application app : applications) {
            System.out.println(app);
        }
    }

    /**
     * Allows the manager to process PENDING applications for a specific project.
     * The manager may approve or reject an application depending on flat availability.
     *
     * @param project the project whose applications are being managed
     */
    public void manageApplication(Project project) {
        ArrayList<Application> pendingApplications = applicationList.getPendingApplicationsByProject(project);

        if (pendingApplications.isEmpty()) {
            System.out.println("No pending applications to manage.");
            return;
        }

        System.out.println("Pending Applications:");
        for (int i = 0; i < pendingApplications.size(); i++) {
            System.out.println((i + 1) + ". " + pendingApplications.get(i));
        }

        System.out.print("Select application to manage (enter number): ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        if (choice < 0 || choice >= pendingApplications.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Application selectedApp = pendingApplications.get(choice);
        Application.FlatType flatType = selectedApp.getFlatType();
        int availability;

        if (flatType == Application.FlatType.TWOROOM) {
            availability = project.getAvailableTwoRoom();
        } else {
            availability = project.getAvailableThreeRoom();
        }

        if (availability > 0) {
            System.out.println("1. Accept");
            System.out.println("2. Reject");
            System.out.print("Enter your choice: ");
            String action = scanner.nextLine();

            if (action.equals("1")) {
                selectedApp.setApplicationStatus(Application.ApplicationStatus.SUCCESSFUL);
                if (flatType == Application.FlatType.TWOROOM) {
                    project.setAvailableTwoRoom(availability - 1);
                } else {
                    project.setAvailableThreeRoom(availability - 1);
                }
                System.out.println("Application accepted.");
            } else if (action.equals("2")) {
                selectedApp.setApplicationStatus(Application.ApplicationStatus.UNSUCCESSFUL);
                selectedApp.getApplicant().setCurrentApplication(null);
                System.out.println("Application rejected.");
            } else {
                System.out.println("Invalid action.");
            }
        } else {
            selectedApp.setApplicationStatus(Application.ApplicationStatus.UNSUCCESSFUL);
            selectedApp.getApplicant().setCurrentApplication(null);
            System.out.println("No more flats available for " + flatType + ". Application rejected.");
        }
    }
}
