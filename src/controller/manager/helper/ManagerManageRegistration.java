package controller.manager.helper;

import entity.*;
import utils.Colour;
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
            System.out.println(Colour.RED + "No registrations found." + Colour.RESET);
            return;
        }

        System.out.println(Colour.BLUE_UNDERLINED + "All Registrations: " + Colour.RESET);
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
            System.out.println(Colour.RED + "No pending registrations for this project." + Colour.RESET);
            return;
        }

        System.out.println(Colour.BLUE_UNDERLINED + "Pending Registrations for Project: " + project.getProjectName() + Colour.RESET);
        for (int i = 0; i < pendingRegistrations.size(); i++) {
            System.out.println((i + 1) + ". " + pendingRegistrations.get(i));
        }

        System.out.print(Colour.BLUE + "Select registration to manage (enter number): " + Colour.RESET);
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println(Colour.RED + "Invalid input." + Colour.RESET);
            return;
        }

        if (choice < 0 || choice >= pendingRegistrations.size()) {
            System.out.println(Colour.RED + "Invalid choice." + Colour.RESET);
            return;
        }

        Registration selectedReg = pendingRegistrations.get(choice);

        if (project.getOfficers().size() < project.getMaxOfficer()) {
            System.out.println("\n1. Approve");
            System.out.println("2. Reject");
            System.out.println(Colour.BLUE + "Enter your choice: " + Colour.RESET);
            String action = scanner.nextLine();

            if (action.equals("1")) {
                selectedReg.setStatus(Registration.RegistrationStatus.APPROVED);
                project.addOfficers(selectedReg.getOfficer());
                System.out.println(Colour.GREEN + "Updated officers for project " + project.getProjectName() + ": " + project.getOfficers() + Colour.RESET);
                selectedReg.getOfficer().setAssignedProject(project);
                System.out.println(Colour.GREEN + "Officer registration approved." + Colour.RESET);
            } else if (action.equals("2")) {
                selectedReg.setStatus(Registration.RegistrationStatus.REJECTED);
                System.out.println(Colour.RED + "Officer registration rejected." + Colour.RESET);
            } else {
                System.out.println(Colour.BLUE + "Invalid action." + Colour.RESET);
            }
        } else {
            selectedReg.setStatus(Registration.RegistrationStatus.REJECTED);
            System.out.println(Colour.RED + "Officer slots full. Registration rejected." + Colour.RESET);
        }
    }
}
