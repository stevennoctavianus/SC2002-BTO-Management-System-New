import java.util.Scanner;

public class ApplicantMakeWithdrawal {
    private Applicant applicant;
    private WithdrawalList withdrawalList;
    private Scanner scanner;

    public ApplicantMakeWithdrawal(Applicant applicant, WithdrawalList withdrawalList, ApplicationList applicationList) {
        this.applicant = applicant;
        this.withdrawalList = withdrawalList;
        this.scanner = new Scanner(System.in);
    }

    public void withdrawApplication() {
        Application application = applicant.getCurrentApplication();
    
        if (application == null) {
            System.out.println("You do not have an active application to withdraw.");
            return;
        }
    
        System.out.print("Are you sure you want to request a withdrawal? (yes/no): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            Withdrawal withdrawal = new Withdrawal(application);
            withdrawalList.addWithdrawal(withdrawal);
    
            System.out.println("Withdrawal request submitted. Awaiting manager approval.");
        } else {
            System.out.println("Withdrawal cancelled.");
        }
    }
}