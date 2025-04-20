package utils;

import container.*;

/**
 * Utility class that provides a centralized way to save all system data
 * to their corresponding CSV files.
 * Typically used during program shutdown or unexpected termination
 * to persist all in-memory data to storage.
 */
public class DataSyncUtil {
    private ApplicantList applicantList;
    private ProjectList projectList;
    private ManagerList managerList;
    private OfficerList officerList;
    private ApplicationList applicationList;
    private RegistrationList registrationList;
    private WithdrawalList withdrawalList;
    private EnquiryList enquiryList;

    /**
     * Constructs a new {@code DataSyncUtil} instance with references to all core data containers.
     *
     * @param applicantList    the list of all applicants
     * @param projectList      the list of all projects
     * @param managerList      the list of all managers
     * @param officerList      the list of all officers
     * @param applicationList  the list of all applications
     * @param registrationList the list of all officer registrations
     * @param withdrawalList   the list of all withdrawals
     * @param enquiryList      the list of all enquiries
     */
    public DataSyncUtil(ApplicantList applicantList, ProjectList projectList, ManagerList managerList,
                        OfficerList officerList, ApplicationList applicationList, RegistrationList registrationList,
                        WithdrawalList withdrawalList, EnquiryList enquiryList) {
        this.applicantList = applicantList;
        this.projectList = projectList;
        this.managerList = managerList;
        this.officerList = officerList;
        this.applicationList = applicationList;
        this.registrationList = registrationList;
        this.withdrawalList = withdrawalList;
        this.enquiryList = enquiryList;
    }

    /**
     * Saves all in-memory container data to their respective CSV files.
     * This method is typically called during normal exits or crash recovery.
     */
    public void saveAll() {
        applicantList.saveToCSV();
        projectList.saveToCSV();
        managerList.saveToCSV();
        officerList.saveToCSV();
        applicationList.saveToCSV();
        registrationList.saveToCSV();
        withdrawalList.saveToCSV();
        enquiryList.saveToCSV();
    }
}

