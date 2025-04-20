package controller.manager.helper;

import entity.*;
import container.*;
import java.util.ArrayList;
import java.util.Scanner;
import controller.manager.template.IManagerManageRegistration;

/**
 * Provides functionality for managers to view and manage officer registration requests.
 * Managers can approve or reject pending registrations for projects they handle.
 */
public class ManagerManageRegistration implements IManagerManageRegistration {

    private RegistrationList registrationList;
    private Scanner scanner;

    /**
     * Constructs a manager registration handler using the global registration list.
     *
     * @param registrationList the list of all officer registration requests
     */
    public ManagerManageRegistration(RegistrationList registrationList) {
        this.registrationList = registrationList;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays all registrations in the system, regardless of project.
     * Intended to give managers full visibility into current and past officer registrations.
     */
    public void viewRegistration() {
        ArrayList<Registration> allRegistrations = registrationList.getRegistrations();
        if (allRegistrations.isEmpty()) {
            System.out.println("No registrations found.");
            return;
        }

        System.out.println("All Registrations:");
        for (Registration registration : allRegistrations) {
            System.out.println(registration);
        }
    }

    /**
     * Allows the manager to review and process pending officer registrations for a specific project.
     * If officer slots are full, the registration is automatically rejected.
     *
     * @param project the project for which registrations are being managed
     */
    public void manageRegistration(Project project) {
        ArrayList<Registration> pendingRegistrations = registrationList.getPendingRegistrationsByProject(project);

        if (pendingRegistrations.isEmpty()) {
            System.out.println("No pending registrations for this project.");
            return;
        }

        System.out.println("Pending Registrations for Project: " + project.getProjectName());
        for (int i = 0; i < pendingRegistrations.size(); i++) {
            System.out.println((i + 1) + ". " + pendingRegistrations.get(i));
        }

        System.out.print("Select registration to manage (enter number): ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        if (choice < 0 || choice >= pendingRegistrations.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Registration selectedReg = pendingRegistrations.get(choice);

        if (project.getOfficers().size() < project.getMaxOfficer()) {
            System.out.println("1. Approve");
            System.out.println("2. Reject");
            System.out.print("Enter your choice: ");
            String action = scanner.nextLine();

            if (action.equals("1")) {
                selectedReg.setStatus(Registration.RegistrationStatus.APPROVED);
                project.addOfficers(selectedReg.getOfficer());
                System.out.println("Updated officers for project " + project.getProjectName() + ": " + project.getOfficers());
                selectedReg.getOfficer().setAssignedProject(project);
                System.out.println("Officer registration approved.");
            } else if (action.equals("2")) {
                selectedReg.setStatus(Registration.RegistrationStatus.REJECTED);
                System.out.println("Officer registration rejected.");
            } else {
                System.out.println("Invalid action.");
            }
        } else {
            selectedReg.setStatus(Registration.RegistrationStatus.REJECTED);
            System.out.println("Officer slots full. Registration rejected.");
        }
    }
}
