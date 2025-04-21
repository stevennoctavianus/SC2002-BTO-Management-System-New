package controller.applicant.helper;

import container.*;
import entity.*;
import utils.Colour;

import java.util.Scanner;
import controller.applicant.template.IApplicantMakeWithdrawal;

/**
 * Handles the withdrawal functionality for applicants.
 * Allows an applicant to cancel their current application and record the withdrawal.
 */
public class ApplicantMakeWithdrawal implements IApplicantMakeWithdrawal {

    private Applicant applicant;
    private WithdrawalList withdrawalList;
    private ApplicationList applicationList;
    private Scanner scanner;

    /**
     * Constructs a withdrawal handler for a given applicant.
     *
     * @param applicant       the applicant performing the withdrawal
     * @param withdrawalList  the global list of withdrawals
     * @param applicationList the list of all submitted applications
     */
    public ApplicantMakeWithdrawal(Applicant applicant, WithdrawalList withdrawalList, ApplicationList applicationList) {
        this.applicant = applicant;
        this.withdrawalList = withdrawalList;
        this.applicationList = applicationList;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Allows the applicant to withdraw their current application.
     * If the withdrawal is confirmed, it is recorded and the application is removed from the system.
     */
    public void withdrawApplication() {
        Application application = applicationList.getApplicationByApplicant(applicant);
        if (application == null) {
            System.out.println(Colour.RED + "You do not have an active application to withdraw." + Colour.RESET);
            return;
        }

        System.out.print(Colour.BLUE + "Are you sure you want to withdraw? (Yes/No): " + Colour.RESET);
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            Withdrawal withdrawal = new Withdrawal(application);
            withdrawalList.addWithdrawal(withdrawal);
            applicationList.removeApplication(application);
            applicant.setCurrentApplication(null);
            System.out.println(Colour.GREEN + "Application withdrawn successfully." + Colour.RESET);
        } else {
            System.out.println(Colour.RED + "Withdrawal cancelled." + Colour.RESET);
        }
    }
}
