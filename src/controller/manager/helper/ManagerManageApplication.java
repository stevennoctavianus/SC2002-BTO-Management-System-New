package controller.manager.helper;

import java.util.ArrayList;
import java.util.Scanner;
import entity.*;
import utils.Colour;
import container.*;
import controller.FilterSettings;
import controller.UserSession;
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
        FilterSettings filters = UserSession.getFilterSettings();
        ArrayList<Application> applications = applicationList.getApplicationsByProject(project);
        if (applications.isEmpty()) {
            System.out.println(Colour.RED + "No applications for this project." + Colour.RESET);
            return;
        }

        System.out.println(Colour.BLUE_UNDERLINED + "All Applications for Project: " + Colour.RESET + project.getProjectName());
        for (Application app : applications) {
            if (filters.getFlatType() != null && app.getFlatType() != filters.getFlatType()) {
                continue;
            }
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
        FilterSettings filters = UserSession.getFilterSettings();
        ArrayList<Application> pendingApplications = applicationList.getPendingApplicationsByProject(project);

        if (pendingApplications.isEmpty()) {
            System.out.println(Colour.RED + "No pending applications to manage." + Colour.RESET);
            return;
        }

        System.out.println(Colour.BLUE_UNDERLINED + "Pending Applications:" + Colour.RESET);
        ArrayList<Application> filteredPending = new ArrayList<>();
        for (int i = 0; i < pendingApplications.size(); i++) {
            Application app = pendingApplications.get(i);
            if (filters.getFlatType() == null || app.getFlatType() == filters.getFlatType()) {
                filteredPending.add(app);
                System.out.println((filteredPending.size()) + ". " + app);
            }
        }

        if (filteredPending.isEmpty()) {
            System.out.println(Colour.RED + "No pending applications match the current filters." + Colour.RESET);
            return;
        }

        System.out.print(Colour.BLUE + "Select application to manage (enter number): " + Colour.RESET);
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println(Colour.RED + "Invalid input." + Colour.RESET);
            return;
        }

        if (choice < 0 || choice >= filteredPending.size()) {
            System.out.println(Colour.RED + "Invalid choice." + Colour.RESET);
            return;
        }

        Application selectedApp = filteredPending.get(choice);
        Application.FlatType flatType = selectedApp.getFlatType();
        int availability;

        if (flatType == Application.FlatType.TWOROOM) {
            availability = project.getAvailableTwoRoom();
        } else {
            availability = project.getAvailableThreeRoom();
        }

        if (availability > 0) {
            System.out.println("\n1. Accept");
            System.out.println("2. Reject");
            System.out.println("Enter your choice (1 or 2): ");
            String action = scanner.nextLine();

            if (action.equals("1")) {
                selectedApp.setApplicationStatus(Application.ApplicationStatus.SUCCESSFUL);
                if (flatType == Application.FlatType.TWOROOM) {
                    project.setAvailableTwoRoom(availability - 1);
                } else {
                    project.setAvailableThreeRoom(availability - 1);
                }
                System.out.println(Colour.GREEN + "Application accepted." + Colour.RESET);
            } else if (action.equals("2")) {
                selectedApp.setApplicationStatus(Application.ApplicationStatus.UNSUCCESSFUL);
                selectedApp.getApplicant().setCurrentApplication(null);
                System.out.println(Colour.RED + "Application rejected." + Colour.RESET);
            } else {
                System.out.println(Colour.RED + "Invalid action." + Colour.RESET);
            }
        } else {
            selectedApp.setApplicationStatus(Application.ApplicationStatus.UNSUCCESSFUL);
            selectedApp.getApplicant().setCurrentApplication(null);
            System.out.println(Colour.RED + "No more flats available for " + flatType + ". Application rejected." + Colour.RESET);
        }
    }
}
