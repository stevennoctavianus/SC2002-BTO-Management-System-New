package container;

import entity.*;
import utils.CSVReader;
import utils.CSVWriter;

import java.util.*;

/**
 * Manages a list of {@link Enquiry} objects in the BTO system.
 * Supports loading enquiries from CSV, querying by project and status,
 * and saving updates back to file.
 */
public class EnquiryList {
    private ArrayList<Enquiry> enquiries;
    private ApplicantList applicantList;
    private ProjectList projectList;

    /**
     * Default constructor that initializes an empty enquiry list.
     */
    public EnquiryList() {
        this.enquiries = new ArrayList<>();
    }

    /**
     * Constructs the enquiry list and loads data from CSV.
     *
     * @param filepath       path to the CSV file
     * @param applicantList  reference to loaded applicant list (for linking enquiries)
     * @param projectList    reference to loaded project list (for linking enquiries)
     */
    public EnquiryList(String filepath, ApplicantList applicantList, ProjectList projectList){
        this.enquiries = new ArrayList<>();
        this.applicantList = applicantList;
        this.projectList = projectList;
        loadEnquiries(filepath);
    }

    /**
     * Loads enquiry records from the CSV file and links them to corresponding
     * {@link Applicant} and {@link Project} instances.
     *
     * @param filePath the path to the CSV file
     */
    private void loadEnquiries(String filePath) {
        List<String[]> data = CSVReader.readCSV(filePath);
        for (String[] row : data) {
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

    /**
     * Adds a new enquiry to the list.
     *
     * @param enquiry the enquiry to add
     */
    public void addEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
    }

    /**
     * Returns all enquiries in the system.
     *
     * @return list of all enquiries
     */
    public ArrayList<Enquiry> getEnquiries() {
        return enquiries;
    }

    /**
     * Removes an existing enquiry from the list.
     *
     * @param enquiry the enquiry to remove
     */
    public void removeEnquiry(Enquiry enquiry) {
        enquiries.remove(enquiry);
    }

    /**
     * Retrieves all enquiries associated with a specific project.
     *
     * @param project the project to filter by
     * @return list of enquiries for the given project
     */
    public ArrayList<Enquiry> getEnquiriesByProject(Project project) {
        ArrayList<Enquiry> result = new ArrayList<>();
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getProject().equals(project)) {
                result.add(enquiry);
            }
        }
        return result;
    }

    /**
     * Retrieves all enquiries that are pending for a specific project.
     *
     * @param project the project to filter by
     * @return list of pending enquiries
     */
    public ArrayList<Enquiry> getPendingEnquiriesByProject(Project project) {
        ArrayList<Enquiry> pendingEnquiries = new ArrayList<>();
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getProject().equals(project) &&
                enquiry.getStatus() == Enquiry.EnquiryStatus.PENDING) {
                pendingEnquiries.add(enquiry);
            }
        }
        return pendingEnquiries;
    }

    /**
     * Saves the current list of enquiries to a CSV file.
     * <p>
     * Each row contains: NRIC, project name, message, and status.
     */
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
