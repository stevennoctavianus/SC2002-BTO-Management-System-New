package controller.officer.helper;

import entity.*;
import utils.Colour;

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
            System.out.println(Colour.RED + "You are not currently assigned to any project."  + Colour.RESET);
            return;
        }

        if (application == null || application.getApplicationStatus() != Application.ApplicationStatus.BOOKED) {
            System.out.println(Colour.RED + "No valid BOOKED application provided for receipt generation." + Colour.RESET);
            return;
        }

        Applicant applicant = application.getApplicant();
        if (applicant == null) {
            System.out.println(Colour.RED + "Error: Applicant information is missing." + Colour.RESET);
            return;
        }

        System.out.println(Colour.BLUE + "\n==== Flat Booking Receipt for Project: " + assignedProject.getProjectName() + " ====" + Colour.RESET);
        System.out.println("-------------------------------------------");
        System.out.println(Colour.BLUE + "Name           : " + Colour.RESET + applicant.getName());
        System.out.println(Colour.BLUE + "NRIC           : " + Colour.RESET + applicant.getNric());
        System.out.println(Colour.BLUE + "Age            : " + Colour.RESET + applicant.getAge());
        System.out.println(Colour.BLUE + "Marital Status : " + Colour.RESET + applicant.getMaritalStatus());
        System.out.println(Colour.BLUE + "Flat Type      : " + Colour.RESET + application.getFlatType());
        System.out.println(Colour.BLUE + "Project Name   : " + Colour.RESET + assignedProject.getProjectName());
        System.out.println(Colour.BLUE + "Neighbourhood  : " + Colour.RESET + assignedProject.getNeighborhood());
        System.out.println("-------------------------------------------");
    }
}

