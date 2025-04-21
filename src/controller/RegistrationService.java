package controller;

import container.ApplicantList;
import entity.Applicant;
import utils.ClearScreen;
import utils.Colour;

import java.util.Scanner;

/**
 * Handles registration of new applicants in the BTO system.
 * <p>
 * This service prompts the user for personal information and creates
 * a new {@link Applicant} account if valid and not already registered.
 */
public class RegistrationService {

    private ApplicantList applicantList;

    /**
     * Constructs a registration service with access to the current applicant list.
     *
     * @param applicantList the list that stores all registered applicants
     */
    public RegistrationService(ApplicantList applicantList) {
        this.applicantList = applicantList;
    }

    /**
     * Prompts the user to enter personal information and registers
     * a new applicant if the NRIC is valid and not already in use.
     */
    public void registerNewApplicant() {
        Scanner scanner = new Scanner(System.in);
        ClearScreen.clear();

        System.out.println(Colour.BLUE + "=== Applicant Registration ===");

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter NRIC (e.g., S1234567A): " + Colour.RESET);
        String nric = scanner.nextLine();
        if (!AuthenticationService.validNRIC(nric)) {
            System.out.println(Colour.RED + "Invalid NRIC format." + Colour.RED);
            return;
        }

        if (applicantList.getApplicantByNric(nric) != null) {
            System.out.println(Colour.RED + "An account with this NRIC already exists." + Colour.RESET);
            return;
        }

        System.out.print(Colour.BLUE + "Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Marital Status (Single/Married): ");
        String maritalStatus = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        Applicant applicant = new Applicant(name, nric, age, maritalStatus, password);
        applicantList.addApplicant(applicant);

        System.out.println("Account registered successfully!" + Colour.BLUE);
    }
}


