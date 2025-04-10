package controller;
import container.*;
public class DataInitializer {
    // Declare at the class level (accessible in all methods)
    private static ApplicantList applicantList;
    private static OfficerList officerList;
    private static ManagerList managerList;

    public static void loadData() {
        // Initialize lists by loading from CSV
        applicantList = new ApplicantList("../data/ApplicantList.csv");
        officerList = new OfficerList("../data/OfficerList.csv");
        managerList = new ManagerList("../data/ManagerList.csv");
    }

    // Getter methods to retrieve data
    public static ApplicantList getApplicantList() {
        return applicantList;
    }

    public static OfficerList getOfficerList() {
        return officerList;
    }

    public static ManagerList getManagerList() {
        return managerList;
    }

    public static void saveData(
        ProjectList projectList,
        ApplicationList applicationList,
        RegistrationList registrationList,
        WithdrawalList withdrawalList,
        EnquiryList enquiryList
        ) {
            if (applicantList != null) applicantList.saveToCSV();
            if (officerList != null) officerList.saveToCSV();
            if (managerList != null) managerList.saveToCSV();
            if (projectList != null) projectList.saveToCSV();
            if (applicationList != null) applicationList.saveToCSV();
            if (registrationList != null) registrationList.saveToCSV();
            if (withdrawalList != null) withdrawalList.saveToCSV();
            if (enquiryList != null) enquiryList.saveToCSV();
        }
}