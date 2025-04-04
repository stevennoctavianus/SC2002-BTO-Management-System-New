public class DataInitializer {
    // Declare at the class level (accessible in all methods)
    private static ApplicantList applicantList;
    private static OfficerList officerList;
    private static ManagerList managerList;

    public static void loadData() {
        // Initialize lists by loading from CSV
        applicantList = new ApplicantList("applicants.csv");
        officerList = new OfficerList("officers.csv");
        managerList = new ManagerList("managers.csv");
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
}