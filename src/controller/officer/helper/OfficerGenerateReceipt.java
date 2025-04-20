package controller.officer.helper;

import entity.*;
import java.util.List;
import controller.officer.template.IOfficerGenerateReceipt;

/**
 * Generates a booking receipt for an applicant who has successfully booked a flat.
 * Only officers assigned to a project can issue a receipt for a valid BOOKED application.
 */
public class OfficerGenerateReceipt implements IOfficerGenerateReceipt {

    private Officer officer;
    private Application application;

    /**
     * Constructs a receipt generator for a specific officer and application.
     *
     * @param officer     the officer issuing the receipt
     * @param application the application that has been booked
     */
    public OfficerGenerateReceipt(Officer officer, Application application) {
        this.officer = officer;
        this.application = application;
    }

    /**
     * Prints a formatted receipt with the applicant's personal and project details.
     * Validates that the application is BOOKED and the officer is assigned to a project.
     */
    public void generateReceipt() {
        Project assignedProject = officer.getAssignedProject();

        if (assignedProject == null) {
            System.out.println("You are not currently assigned to any project.");
            return;
        }

        if (application == null || application.getApplicationStatus() != Application.ApplicationStatus.BOOKED) {
            System.out.println("No valid BOOKED application provided for receipt generation.");
            return;
        }

        Applicant applicant = application.getApplicant();
        if (applicant == null) {
            System.out.println("Error: Applicant information is missing.");
            return;
        }

        System.out.println("\n=== Flat Booking Receipt for Project: " + assignedProject.getProjectName() + " ===");
        System.out.println("-----------------------------------------");
        System.out.println("Name           : " + applicant.getName());
        System.out.println("NRIC           : " + applicant.getNric());
        System.out.println("Age            : " + applicant.getAge());
        System.out.println("Marital Status : " + applicant.getMaritalStatus());
        System.out.println("Flat Type      : " + application.getFlatType());
        System.out.println("Project Name   : " + assignedProject.getProjectName());
        System.out.println("Neighborhood   : " + assignedProject.getNeighborhood());
        System.out.println("-----------------------------------------");
    }
}

