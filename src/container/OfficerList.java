package container;

import entity.*;
import utils.CSVReader;
import utils.CSVWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of {@link Officer} entities in the system.
 * <p>
 * Supports loading officer data from CSV, querying by NRIC,
 * adding new officers, and saving the list back to a file.
 */
public class OfficerList {
    private ArrayList<Officer> officerList;

    /**
     * Constructs the {@code OfficerList} and loads officer data from a CSV file.
     *
     * @param filePath the path to the officer data CSV file
     */
    public OfficerList(String filePath) {
        officerList = new ArrayList<>();
        loadOfficers(filePath);
    }

    /**
     * Loads officer data from a CSV file.
     * Assumes CSV structure: name, NRIC, age, marital status, password.
     *
     * @param filePath the file to load officers from
     */
    private void loadOfficers(String filePath) {
        List<String[]> data = CSVReader.readCSV(filePath);
        for (String[] row : data) {
            String name = row[0];
            String nric = row[1];
            int age = Integer.parseInt(row[2]);
            String maritalStatus = row[3];
            String password = row[4];

            officerList.add(new Officer(name, nric, age, maritalStatus, password));
        }
    }

    /**
     * Returns the list of all officers.
     *
     * @return the list of {@link Officer} instances
     */
    public ArrayList<Officer> getOfficerList() {
        return this.officerList;
    }

    /**
     * Retrieves an officer by NRIC.
     *
     * @param nric the NRIC to search for
     * @return the matching {@code Officer}, or {@code null} if not found
     */
    public Officer getOfficerByNric(String nric) {
        for (Officer officer : officerList) {
            if (officer.getNric().equalsIgnoreCase(nric)) {
                return officer;
            }
        }
        return null;
    }

    /**
     * Adds an officer to the list.
     *
     * @param officer the officer to add
     */
    public void addOfficer(Officer officer) {
        this.officerList.add(officer);
    }

    /**
     * Saves the officer list to a CSV file.
     * The CSV structure includes: name, NRIC, age, marital status, and password.
     */
    public void saveToCSV() {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"name", "nric", "age", "maritalStatus", "password"});

        for (Officer o : this.officerList) {
            data.add(new String[]{
                o.getName(),
                o.getNric(),
                String.valueOf(o.getAge()),
                o.getMaritalStatus().name(),
                o.getPassword()
            });
        }

        CSVWriter.writeCSV("../data/OfficerList.csv", data);
    }
}

