package controller.officer.helper;

import container.*;
import entity.*;
import utils.ClearScreen;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import controller.officer.template.IOfficerManageApplication;

/**
 * Handles application-related actions for officers assigned to BTO projects.
 * Officers can view successful applications for their assigned project,
 * and update them to BOOKED status, triggering receipt generation.
 */
public class OfficerManageApplication implements IOfficerManageApplication {

    private Officer officer;
    private ApplicationList applicationList;
    private Scanner scanner;

    /**
     * Constructs the application manager for a specific officer.
     *
     * @param officer          the officer using the system
     * @param applicationList  the list of all applications
     */
    public OfficerManageApplication(Officer officer, ApplicationList applicationList) {
        this.officer = officer;
        this.applicationList = applicationList;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the NRICs of applicants with SUCCESSFUL applications
     * for the project the officer is currently managing.
     */
    public void viewApplications() {
        Project assignedProject = officer.getAssignedProject();
        if (assignedProject == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }

        System.out.println("\n=== Successful Applications for Project: " + assignedProject.getProjectName() + " ===");
        List<Application> applications = applicationList.getApplicationList();

        boolean found = false;
        for (Application application : applications) {
            if (application.getProject().equals(assignedProject)
                && application.getApplicationStatus() == Application.ApplicationStatus.SUCCESSFUL) {
                System.out.println("Applicant NRIC: " + application.getApplicant().getNric());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No successful applications found for your project.");
        }
    }

    /**
     * Updates a SUCCESSFUL application to BOOKED status and generates a receipt.
     * Reduces flat availability based on the chosen flat type.
     */
    public void updateApplicationStatus() {
        Project assignedProject = officer.getAssignedProject();
        if (assignedProject == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }

        List<Application> applications = applicationList.getApplicationList();

        List<Application> successfulApplication = applications.stream()
            .filter(app -> app.getProject().equals(assignedProject)
                && app.getApplicationStatus() == Application.ApplicationStatus.SUCCESSFUL)
            .toList();

        if (successfulApplication.isEmpty()) {
            System.out.println("No successful applications to update.");
            return;
        }

        System.out.println("\n=== Successful Applications (Eligible for Booking) ===");
        for (int i = 0; i < successfulApplication.size(); i++) {
            System.out.println((i + 1) + ") " + successfulApplication.get(i).getApplicant().getName()
                + ": " + successfulApplication.get(i).getApplicant().getNric());
        }

        System.out.print("Select an application to book (enter number or 0 to cancel): ");
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

        if (choice <= 0 || choice > successfulApplication.size()) {
            System.out.println("Cancelled or invalid choice.");
            return;
        }

        Application selectedApplication = successfulApplication.get(choice - 1);
        Application.FlatType type = selectedApplication.getFlatType();

        if (type == Application.FlatType.TWOROOM) {
            int current = assignedProject.getAvailableTwoRoom();
            assignedProject.setAvailableTwoRoom(current - 1);
        } else if (type == Application.FlatType.THREEROOM) {
            int current = assignedProject.getAvailableThreeRoom();
            assignedProject.setAvailableThreeRoom(current - 1);
        }

        selectedApplication.setBookedFlat(true);
        selectedApplication.setApplicationStatus(Application.ApplicationStatus.BOOKED);

        OfficerGenerateReceipt receiptHandler = new OfficerGenerateReceipt(officer, selectedApplication);
        receiptHandler.generateReceipt();
    }
}
