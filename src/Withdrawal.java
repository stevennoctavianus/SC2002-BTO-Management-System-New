public class Withdrawal {
    private Application application;
    private WithdrawalStatus status;

    public enum WithdrawalStatus {
        PENDING, APPROVED, REJECTED;
    }

    public Withdrawal(Application application) {
        this.application = application;
        this.status = WithdrawalStatus.PENDING;
    }

    public Application getApplication() {
        return application;
    }

    public WithdrawalStatus getStatus() {
        return status;
    }

    public void setWithdrawalStatus(WithdrawalStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Withdrawal [Application=" + application.getApplicant().getName() + 
               ", Project=" + application.getProject().getProjectName() + 
               ", Status=" + status + "]";
    }
}
