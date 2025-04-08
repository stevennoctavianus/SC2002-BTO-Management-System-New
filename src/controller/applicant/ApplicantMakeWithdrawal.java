package controller.applicant;
import container.*;
import entity.*;
import java.util.Scanner;

public class ApplicantMakeWithdrawal {
    private Applicant applicant;
    private WithdrawalList withdrawalList;
    private ApplicationList applicationList;
    private Scanner scanner;

    public ApplicantMakeWithdrawal(Applicant applicant, WithdrawalList withdrawalList, ApplicationList applicationList) {
        this.applicant = applicant;
        this.withdrawalList = withdrawalList;
        this.applicationList = applicationList;
        this.scanner = new Scanner(System.in);
    }

    public void withdrawApplication() {
        Application application = applicationList.getApplicationByApplicant(applicant);
        if (application == null) {
            System.out.println("You do not have an active application to withdraw.");
            return;
        }

        System.out.print("Are you sure you want to withdraw? (yes/no): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            Withdrawal withdrawal = new Withdrawal(application);
            withdrawalList.addWithdrawal(withdrawal);
            applicationList.removeApplication(application);
            System.out.println("Application withdrawn successfully.");
        } else {
            System.out.println("Withdrawal cancelled.");
        }
    }
}