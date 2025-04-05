import java.util.ArrayList;
import java.util.List;

public class WithdrawalList {
    private List<Withdrawal> withdrawals;

    public WithdrawalList() {
        this.withdrawals = new ArrayList<>();
    }

    public void addWithdrawal(Withdrawal withdrawal) {
        withdrawals.add(withdrawal);
        System.out.println("Withdrawal request submitted.");
    }

    public void viewWithdrawalsByApplicant(Applicant applicant) {
        System.out.println("\n===== My Withdrawal Requests =====");
        for (Withdrawal withdrawal : withdrawals) {
            if (withdrawal.getApplication().getApplicant().equals(applicant)) {
                System.out.println(withdrawal);
            }
        }
    }
}
