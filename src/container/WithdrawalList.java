package container;

import entity.*;
import utils.CSVReader;
import utils.CSVWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a container for withdrawal requests in the BTO system.
 * <p>
 * Handles loading from and saving to CSV, as well as logic for filtering and
 * managing withdrawal entries tied to specific applicants or projects.
 */
public class WithdrawalList {
    private List<Withdrawal> withdrawals;
    private ApplicationList applicationList;
    private ApplicantList applicantList;
    private ProjectList projectList;

    /**
     * Default constructor initializes an empty withdrawal list.
     */
    public WithdrawalList() {
        this.withdrawals = new ArrayList<>();
    }

    /**
     * Constructs the list and loads data from a CSV file.
     *
     * @param filepath        path to the withdrawal CSV
     * @param applicationList reference to application data
     * @param applicantList   reference to applicant data
     * @param projectList     reference to project data
     */
    public WithdrawalList(String filepath, ApplicationList applicationList, ApplicantList applicantList, ProjectList projectList){
        this.withdrawals = new ArrayList<>();
        this.applicationList = applicationList;
        this.applicantList = applicantList;
        this.projectList = projectList;
        loadWithdrawals(filepath);
    }

    /**
     * Loads withdrawal requests from a CSV file and maps them to existing applications.
     *
     * @param filePath the path to the CSV file
     */
    private void loadWithdrawals(String filePath) {
        List<String[]> data = CSVReader.readCSV(filePath);
        for(String[] row : data) {
            String applicantNric = row[0];
            String projectName = row[1];
            String status = row[2]; 

            Applicant applicant = applicantList.getApplicantByNric(applicantNric);
            Project project = projectList.getProjectByName(projectName);

            if (applicant != null && project != null) {
                Application application = applicationList.getApplicationByApplicant(applicant);
                if (application != null && application.getProject().equals(project)) {
                    Withdrawal withdrawal = new Withdrawal(application);
                    withdrawal.setWithdrawalStatus(Withdrawal.WithdrawalStatus.valueOf(status));
                    withdrawals.add(withdrawal);
                }
            }
        }
    }

    /**
     * Adds a new withdrawal to the list.
     *
     * @param withdrawal the withdrawal object to add
     */
    public void addWithdrawal(Withdrawal withdrawal) {
        withdrawals.add(withdrawal);
        System.out.println("Withdrawal request submitted.");
    }

    /**
     * Displays all withdrawals associated with the given applicant.
     *
     * @param applicant the applicant whose withdrawals are to be viewed
     */
    public void viewWithdrawalsByApplicant(Applicant applicant) {
        System.out.println("\n===== My Withdrawal Requests =====");
        for (Withdrawal withdrawal : withdrawals) {
            if (withdrawal.getApplication().getApplicant().equals(applicant)) {
                System.out.println(withdrawal);
            }
        }
    }

    /**
     * Gets all pending withdrawal requests for a specific project.
     *
     * @param project the project to filter by
     * @return list of withdrawals with status {@code PENDING}
     */
    public ArrayList<Withdrawal> getPendingWithdrawalsByProject(Project project) {
        ArrayList<Withdrawal> pending = new ArrayList<>();
        for (Withdrawal withdrawal : withdrawals) {
            Application app = withdrawal.getApplication();
            if (app.getProject().equals(project) &&
                withdrawal.getStatus() == Withdrawal.WithdrawalStatus.PENDING) {
                pending.add(withdrawal);
            }
        }
        return pending;
    }

    /**
     * Saves all withdrawals to a CSV file.
     * Each entry contains applicant NRIC, project name, and status.
     */
    public void saveToCSV() {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"applicantNric", "projectName", "status"});

        for (Withdrawal w : this.withdrawals) {
            data.add(new String[]{
                w.getApplication().getApplicant().getNric(),
                w.getApplication().getProject().getProjectName(),
                w.getStatus().name()
            });
        }

        CSVWriter.writeCSV("../data/WithdrawalList.csv", data);
    }
}
