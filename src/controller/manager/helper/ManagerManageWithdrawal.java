package controller.manager.helper;

import container.*;
import entity.*;
import utils.Colour;

import java.util.ArrayList;
import java.util.Scanner;
import controller.manager.template.IManagerManageWithdrawal;

/**
 * Provides functionality for managers to handle withdrawal requests submitted by applicants.
 * Managers can approve or reject pending withdrawals tied to projects they manage.
 */
public class ManagerManageWithdrawal implements IManagerManageWithdrawal {

    private WithdrawalList withdrawalList;
    private ApplicationList applicationList;
    private Scanner scanner;

    /**
     * Constructs a withdrawal handler for a manager.
     *
     * @param withdrawalList   the list of all withdrawal requests in the system
     * @param applicationList  the list of applications tied to those withdrawals
     */
    public ManagerManageWithdrawal(WithdrawalList withdrawalList, ApplicationList applicationList) {
        this.withdrawalList = withdrawalList;
        this.applicationList = applicationList;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Allows the manager to view and process pending withdrawal requests for a specific project.
     * Approved withdrawals update the application's status and restore flat availability if applicable.
     *
     * @param project the project whose withdrawals are being managed
     */
    public void manageWithdrawal(Project project) {
        ArrayList<Withdrawal> pendingWithdrawals = withdrawalList.getPendingWithdrawalsByProject(project);

        if (pendingWithdrawals.isEmpty()) {
            System.out.println(Colour.RED + "No pending withdrawals for this project." + Colour.RESET);
            return;
        }

        System.out.println(Colour.BLUE_UNDERLINED + "Pending Withdrawals: " + Colour.RESET);
        for (int i = 0; i < pendingWithdrawals.size(); i++) {
            System.out.println((i + 1) + ". " + pendingWithdrawals.get(i));
        }

        System.out.print(Colour.BLUE + "Select a withdrawal to manage (enter number): " + Colour.RESET);
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println(Colour.RED + "Invalid input." + Colour.RESET);
            return;
        }

        if (choice < 0 || choice >= pendingWithdrawals.size()) {
            System.out.println(Colour.RED + "Invalid choice." + Colour.RESET);
            return;
        }

        Withdrawal withdrawal = pendingWithdrawals.get(choice);
        Application application = withdrawal.getApplication();
        Applicant applicant = application.getApplicant();

        System.out.println("\n1. Approve");
        System.out.println("2. Reject");
        System.out.println(Colour.BLUE + "Enter your choice: " + Colour.RESET);
        String action = scanner.nextLine();

        if (action.equals("1")) {
            withdrawal.setWithdrawalStatus(Withdrawal.WithdrawalStatus.APPROVED);

            if (application.getBookedFlat()) {
                Application.FlatType flatType = application.getFlatType();

                // Restore flat availability
                if (flatType == Application.FlatType.TWOROOM) {
                    project.setAvailableTwoRoom(project.getAvailableTwoRoom() + 1);
                } else if (flatType == Application.FlatType.THREEROOM) {
                    project.setAvailableThreeRoom(project.getAvailableThreeRoom() + 1);
                }
            }

            application.setApplicationStatus(Application.ApplicationStatus.UNSUCCESSFUL);
            System.out.println(Colour.GREEN + "Withdrawal approved. Application marked as UNSUCCESSFUL." + Colour.RESET);
        } else if (action.equals("2")) {
            withdrawal.setWithdrawalStatus(Withdrawal.WithdrawalStatus.REJECTED);
            System.out.println(Colour.RED + "Withdrawal rejected." + Colour.RESET);
        } else {
            System.out.println(Colour.RED + "Invalid action." + Colour.RESET);
        }
    }
}
