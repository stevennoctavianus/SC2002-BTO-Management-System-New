package utils;
import container.*;

public class DataSyncUtil {
    private ApplicantList applicantList;
    private ProjectList projectList;
    private ManagerList managerList;
    private OfficerList officerList;
    private ApplicationList applicationList;
    private RegistrationList registrationList;
    private WithdrawalList withdrawalList;
    private EnquiryList enquiryList;

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
