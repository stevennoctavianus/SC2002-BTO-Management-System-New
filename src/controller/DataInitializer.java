package controller;

import container.*;

/**
 * Loads and provides access to all core container lists used in the system,
 * including applicants, officers, managers, projects, and various transactional records.
 * All lists are initialized from their respective CSV files during system startup.
 */
public class DataInitializer {

    private static ApplicantList applicantList;
    private static OfficerList officerList;
    private static ManagerList managerList;
    private static ProjectList projectList;
    private static ApplicationList applicationList;
    private static RegistrationList registrationList;
    private static WithdrawalList withdrawals;
    private static EnquiryList enquiries;

    /**
     * Loads all container data by reading from their respective CSV files.
     * This method should be called once at the beginning of the program to populate in-memory data.
     */
    public static void loadData() {
        applicantList = new ApplicantList("../data/ApplicantList.csv");
        officerList = new OfficerList("../data/OfficerList.csv");
        managerList = new ManagerList("../data/ManagerList.csv");
        projectList = new ProjectList("../data/ProjectList.csv", managerList, officerList);
        applicationList = new ApplicationList("../data/ApplicationList.csv", applicantList, projectList); 
        registrationList = new RegistrationList("../data/RegistrationList.csv", officerList, projectList);
        withdrawals = new WithdrawalList("../data/WithdrawalList.csv", applicationList, applicantList, projectList);
        enquiries = new EnquiryList("../data/EnquiryList.csv", applicantList, projectList);
    }

    /**
     * @return the list of all applicants loaded from CSV
     */
    public static ApplicantList getApplicantList() {
        return applicantList;
    }

    /**
     * @return the list of all officers loaded from CSV
     */
    public static OfficerList getOfficerList() {
        return officerList;
    }

    /**
     * @return the list of all managers loaded from CSV
     */
    public static ManagerList getManagerList() {
        return managerList;
    }

    /**
     * @return the list of all BTO projects
     */
    public static ProjectList getProjectList() {
        return projectList;
    }

    /**
     * @return the list of all applications made by users
     */
    public static ApplicationList getApplicationList() {
        return applicationList;
    }

    /**
     * @return the list of officer registration requests
     */
    public static RegistrationList getRegistrationList() {
        return registrationList;
    }

    /**
     * @return the list of application withdrawal requests
     */
    public static WithdrawalList getWithdrawalList() {
        return withdrawals;
    }

    /**
     * @return the list of all enquiries submitted to the system
     */
    public static EnquiryList getEnquiryList() {
        return enquiries;
    }
}
