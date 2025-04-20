package entity;

/**
 * Models a withdrawal request made by an applicant to withdraw their BTO application.
 * Each withdrawal has a status that reflects whether it is pending, approved, or rejected.
 */
public class Withdrawal {

    private Application application;
    private WithdrawalStatus status;

    /**
     * Enum representing the current status of the withdrawal request.
     * PENDING: awaiting approval, APPROVED: withdrawal accepted, REJECTED: withdrawal denied.
     */
    public enum WithdrawalStatus {
        PENDING, APPROVED, REJECTED
    }

    /**
     * Creates a new withdrawal request linked to a specific application.
     * The status is set to PENDING by default.
     *
     * @param application the application the applicant wishes to withdraw from
     */
    public Withdrawal(Application application) {
        this.application = application;
        this.status = WithdrawalStatus.PENDING;
    }

    /**
     * Returns the application associated with this withdrawal.
     *
     * @return the linked application
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Returns the current status of the withdrawal.
     *
     * @return the withdrawal status
     */
    public WithdrawalStatus getStatus() {
        return status;
    }

    /**
     * Updates the status of the withdrawal request.
     *
     * @param status the new status to assign
     */
    public void setWithdrawalStatus(WithdrawalStatus status) {
        this.status = status;
    }

    /**
     * Returns a string summary of the withdrawal request,
     * including applicant name, project name, and current status.
     *
     * @return a formatted summary string
     */
    @Override
    public String toString() {
        return "Withdrawal [Application=" + application.getApplicant().getName() +
               ", Project=" + application.getProject().getProjectName() +
               ", Status=" + status + "]";
    }
}
