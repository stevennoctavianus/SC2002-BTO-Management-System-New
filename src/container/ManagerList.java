package container;

import entity.*;
import utils.CSVReader;
import utils.CSVWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the storage, loading, and saving of {@link Manager} objects in the system.
 * Provides utilities for importing manager data from CSV, accessing the manager list,
 * and saving updates back to file.
 */
public class ManagerList {
    private ArrayList<Manager> managerList;

    /**
     * Constructs the {@code ManagerList} and loads data from a CSV file.
     *
     * @param filePath the path to the CSV file containing manager data
     */
    public ManagerList(String filePath) {
        managerList = new ArrayList<>();
        loadManagers(filePath);
    }

    /**
     * Loads manager records from a CSV file.
     * Each record must follow the structure: name, NRIC, age, marital status, password.
     *
     * @param filePath the CSV file to load from
     */
    private void loadManagers(String filePath) {
        List<String[]> data = CSVReader.readCSV(filePath);
        for (String[] row : data) {
            String name = row[0];
            String nric = row[1];
            int age = Integer.parseInt(row[2]);
            String maritalStatus = row[3];
            String password = row[4];

            managerList.add(new Manager(name, nric, age, maritalStatus, password));
        }
    }

    /**
     * Returns the list of all managers.
     *
     * @return list of {@link Manager} instances
     */
    public ArrayList<Manager> getManagerList() {
        return this.managerList;
    }

    /**
     * Adds a new manager to the list.
     *
     * @param manager the manager to add
     */
    public void addManager(Manager manager) {
        this.managerList.add(manager);
    }

    /**
     * Saves the manager list to a CSV file.
     * The output includes name, NRIC, age, marital status, and password.
     */
    public void saveToCSV() {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"name", "nric", "age", "maritalStatus", "password"});

        for (Manager m : this.managerList) {
            data.add(new String[]{
                m.getName(),
                m.getNric(),
                String.valueOf(m.getAge()),
                m.getMaritalStatus().name(),
                m.getPassword()
            });
        }

        CSVWriter.writeCSV("../data/ManagerList.csv", data);
    }
}


