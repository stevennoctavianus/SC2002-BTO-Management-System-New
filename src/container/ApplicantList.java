package container;

import java.util.*;
import utils.CSVReader;
import utils.CSVWriter;
import entity.*;

/**
 * Represents the central list of all applicants in the system.
 * Handles reading from and writing to the CSV file, and provides access
 * and search functionalities for managing applicant data.
 */
public class ApplicantList {
    private ArrayList<Applicant> applicantList;

    /**
     * Constructs an empty {@code ApplicantList}.
     */
    public ApplicantList() {
        this.applicantList = new ArrayList<>();
    }

    /**
     * Constructs an {@code ApplicantList} and loads applicant data from a given CSV file.
     *
     * @param filepath the file path of the CSV file to load from
     */
    public ApplicantList(String filepath) {
        this.applicantList = new ArrayList<>();
        loadApplicants(filepath);
    }

    /**
     * Loads applicant data from the specified CSV file into the list.
     * This method assumes the CSV format is consistent with expected columns.
     *
     * @param filepath the CSV file path
     */
    private void loadApplicants(String filepath) {
        List<String[]> data = CSVReader.readCSV(filepath);
        for (String[] row : data) {
            String name = row[0];
            String nric = row[1];
            int age = Integer.parseInt(row[2]);
            String maritalStatus = row[3];
            String password = row[4];

            applicantList.add(new Applicant(name, nric, age, maritalStatus, password));
        }
    }

    /**
     * Returns the list of all applicants.
     *
     * @return a list of {@code Applicant} objects
     */
    public List<Applicant> getApplicantList() {
        return applicantList;
    }

    /**
     * Adds a new applicant to the list.
     *
     * @param applicant the applicant to add
     */
    public void addApplicant(Applicant applicant) {
        this.applicantList.add(applicant);
    }

    /**
     * Retrieves an applicant based on their NRIC.
     *
     * @param nric the NRIC of the applicant
     * @return the matching {@code Applicant}, or {@code null} if not found
     */
    public Applicant getApplicantByNric(String nric) {
        for (Applicant applicant : applicantList) {
            if (applicant.getNric().equalsIgnoreCase(nric)) {
                return applicant;
            }
        }
        return null;
    }

    /**
     * Saves all applicants to a CSV file for persistence.
     * Overwrites the existing CSV file with the current in-memory data.
     */
    public void saveToCSV() {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"name", "nric", "age", "maritalStatus", "password"});

        for (Applicant a : this.applicantList) {
            data.add(new String[]{
                a.getName(),
                a.getNric(),
                String.valueOf(a.getAge()),
                a.getMaritalStatus().name(),
                a.getPassword()
            });
        }

        CSVWriter.writeCSV("../data/ApplicantList.csv", data);
    }
}
