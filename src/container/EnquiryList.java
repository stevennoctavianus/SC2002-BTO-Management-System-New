package container;
import entity.*;
import utils.CSVReader;
import utils.CSVWriter;

import java.util.*;

public class EnquiryList {
    private ArrayList<Enquiry> enquiries;
    private ApplicantList applicantList;
    private ProjectList projectList;

    public EnquiryList() {
        this.enquiries = new ArrayList<>();
    }

    public EnquiryList(String filepath, ApplicantList applicantList, ProjectList projectList){
        this.enquiries = new ArrayList<>();
        this.applicantList = applicantList;
        this.projectList = projectList;
        loadEnquiries(filepath);
    }

    private void loadEnquiries(String filePath) {
        List<String[]> data = CSVReader.readCSV(filePath);
        for(String[] row : data) {
            String applicantNric = row[0];
            String projectName = row[1];
            String message = row[2];
            String status = row[3];
            
            Applicant applicant = applicantList.getApplicantByNric(applicantNric);
            Project project = projectList.getProjectByName(projectName);

            if (applicant != null && project != null) {
                Enquiry enquiry = new Enquiry(applicant, project, message);
                enquiry.setStatus(Enquiry.EnquiryStatus.valueOf(status)); 
                enquiries.add(enquiry);
        }
        }
    }

    public void addEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
    }

    public ArrayList<Enquiry> getEnquiries() {
        return enquiries;
    }

    public void removeEnquiry(Enquiry enquiry) {
        enquiries.remove(enquiry);
    }

    public ArrayList<Enquiry> getEnquiriesByProject(Project project) {
        ArrayList<Enquiry> result = new ArrayList<>();
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getProject().equals(project)) {
                result.add(enquiry);
            }
        }
        return result;
    }

    public ArrayList<Enquiry> getPendingEnquiriesByProject(Project project) {
        ArrayList<Enquiry> pendingEnquiries = new ArrayList<>();
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getProject().equals(project) && enquiry.getStatus() == Enquiry.EnquiryStatus.PENDING) {
                pendingEnquiries.add(enquiry);
            }
        }
        return pendingEnquiries;
    }

    public void saveToCSV() {
    List<String[]> data = new ArrayList<>();
    data.add(new String[]{"applicantNric", "projectName", "message", "status"});

    for (Enquiry e : this.enquiries) {
        data.add(new String[]{
            e.getApplicant().getNric(),
            e.getProject().getProjectName(),
            e.getMessage(),
            e.getStatus().name()
        });
    }

    CSVWriter.writeCSV("../data/EnquiryList.csv", data);
}

}
