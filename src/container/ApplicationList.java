package container;

import entity.*;
import utils.CSVReader;
import utils.CSVWriter;
import utils.Colour;

import java.util.*;

/**
 * Manages the list of applications in the system.
 * Supports operations such as adding, removing, filtering by project or status,
 * and persisting application data via CSV.
 */
public class ApplicationList {
    private ArrayList<Application> applicationList;

    private ApplicantList applicantList;
    private ProjectList projectList;

    /**
     * Constructs an {@code ApplicationList} and loads data from a CSV file.
     *
     * @param filepath       the file path to load application data from
     * @param applicantList  reference to the list of all applicants
     * @param projectList    reference to the list of all projects
     */
    public ApplicationList(String filepath, ApplicantList applicantList, ProjectList projectList) {
        this.applicationList = new ArrayList<>();
        this.applicantList = applicantList;
        this.projectList = projectList;
        loadApplications(filepath);
    }

    /**
     * Loads application data from a CSV file and populates the list.
     *
     * @param filePath the CSV file path
     */
    private void loadApplications(String filePath) {
        List<String[]> data = CSVReader.readCSV(filePath);
        for (String[] row : data) {
            if (row[0].equalsIgnoreCase("applicantNric")) continue; // Skip header

            String nric = row[0];
            String projectName = row[1];
            String statusStr = row[2];

            Applicant applicant = applicantList.getApplicantByNric(nric);
            Project project = projectList.getProjectByName(projectName);
            Application.ApplicationStatus status = Application.ApplicationStatus.valueOf(statusStr);

            if (applicant != null && project != null) {
                Application application = new Application(project, applicant);
                application.setApplicationStatus(status);
                this.applicationList.add(application);
                applicant.setCurrentApplication(application); // Link to applicant
            } else {
                System.out.println(Colour.RED + "Could not find Applicant or Project for: " + nric + Colour.RESET + " / " + Colour.RED + projectName + Colour.RESET);
            }
        }
    }

    /**
     * Adds a new application to the list.
     *
     * @param application the application to add
     */
    public void addApplication(Application application) {
        this.applicationList.add(application);
    }

    /**
     * Removes an existing application from the list.
     *
     * @param application the application to remove
     */
    public void removeApplication(Application application) {
        if (applicationList.remove(application)) {
            System.out.println(Colour.GREEN + "Application removed successfully." + Colour.RESET);
        } else {
            System.out.println(Colour.RED + "Application not found." + Colour.RESET);
        }
    }

    /**
     * Returns the full list of applications.
     *
     * @return list of {@code Application}
     */
    public ArrayList<Application> getApplicationList() {
        return applicationList;
    }

    /**
     * Retrieves the application submitted by a specific applicant.
     *
     * @param applicant the applicant to search for
     * @return the matching {@code Application}, or {@code null} if none found
     */
    public Application getApplicationByApplicant(Applicant applicant) {
        for (Application application : applicationList) {
            if (application.getApplicant().equals(applicant)) {
                return application;
            }
        }
        return null;
    }

    /**
     * Retrieves all applications submitted for a specific project.
     *
     * @param project the project to filter by
     * @return a list of matching applications
     */
    public ArrayList<Application> getApplicationsByProject(Project project) {
        ArrayList<Application> projectApplication = new ArrayList<>();
        for (Application application : applicationList) {
            if (application.getProject().equals(project)) {
                projectApplication.add(application);
            }
        }
        return projectApplication;
    }

    /**
     * Returns all pending applications for a given project.
     *
     * @param project the project to filter by
     * @return list of pending applications
     */
    public ArrayList<Application> getPendingApplicationsByProject(Project project) {
        ArrayList<Application> pendingApplications = new ArrayList<>();
        for (Application application : applicationList) {
            if (application.getProject().equals(project) &&
                application.getApplicationStatus() == Application.ApplicationStatus.PENDING) {
                pendingApplications.add(application);
            }
        }
        return pendingApplications;
    }

    /**
     * Removes all applications tied to a specific project.
     * Also clears any references held by the applicants.
     *
     * @param project the project whose applications should be removed
     */
    public void removeApplicationsByProject(Project project) {
        for (Application application : new ArrayList<>(applicationList)) {
            if (application.getProject().equals(project)) {
                Applicant applicant = application.getApplicant();
                if (applicant != null) {
                    applicant.setCurrentApplication(null);
                }
                this.removeApplication(application);
            }
        }
    }

    /**
     * Returns a list of all successful applications.
     *
     * @return list of applications with status {@code SUCCESSFUL}
     */
    public ArrayList<Application> getSuccessfulApplications() {
        ArrayList<Application> successful = new ArrayList<>();
        for (Application application : applicationList) {
            if (application.getApplicationStatus() == Application.ApplicationStatus.SUCCESSFUL) {
                successful.add(application);
            }
        }
        return successful;
    }

    /**
     * Saves the current state of the application list to a CSV file.
     * The file includes the applicant NRIC, project name, and application status.
     */
    public void saveToCSV() {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"applicantNric", "projectName", "status"});

        for (Application a : this.applicationList) {
            data.add(new String[]{
                a.getApplicant().getNric(),
                a.getProject().getProjectName(),
                a.getApplicationStatus().name(),
                
            });
        }

        CSVWriter.writeCSV("../data/ApplicationList.csv", data);
    }
}
