package container;

import entity.*;
import utils.CSVReader;
import utils.CSVWriter;
import utils.Colour;

import java.util.*;

/**
 * Manages all officer registration records for BTO projects in the system.
 * <p>
 * Supports loading from and saving to CSV, and provides methods to add, remove,
 * and query registrations based on officer or project.
 */
public class RegistrationList {
    private ArrayList<Registration> registrationList;
    private OfficerList officerList;
    private ProjectList projectList;

    /**
     * Constructs an empty {@code RegistrationList}.
     */
    public RegistrationList() {
        this.registrationList = new ArrayList<>();
    }

    /**
     * Constructs a {@code RegistrationList} and loads registrations from the specified CSV file.
     *
     * @param filepath     path to the registration CSV file
     * @param officerList  reference to the list of officers (for mapping)
     * @param projectList  reference to the list of projects (for mapping)
     */
    public RegistrationList(String filepath, OfficerList officerList, ProjectList projectList) {
        this.registrationList = new ArrayList<>();
        this.officerList = officerList;
        this.projectList = projectList;
        loadRegistrations(filepath);
    }

    /**
     * Loads registration records from a CSV file and maps them to Officer and Project objects.
     *
     * @param filePath the CSV file path
     */
    private void loadRegistrations(String filePath) {
        List<String[]> data = CSVReader.readCSV(filePath);
        for (String[] row : data) {
            String officerNric = row[0];
            String projectName = row[1];
            String status = row[2];

            Officer officer = officerList.getOfficerByNric(officerNric);
            Project project = projectList.getProjectByName(projectName);

            if (officer != null && project != null) {
                Registration registration = new Registration(officer, project);
                registration.setStatus(Registration.RegistrationStatus.valueOf(status));
                registrationList.add(registration);
            }
        }
    }

    /**
     * Adds a new registration to the list.
     *
     * @param registration the registration to add
     */
    public void addRegistration(Registration registration) {
        registrationList.add(registration);
    }

    /**
     * Returns all registrations in the system.
     *
     * @return list of all registrations
     */
    public ArrayList<Registration> getRegistrations() {
        return registrationList;
    }

    /**
     * Returns all registrations submitted by a specific officer.
     *
     * @param officer the officer to search by
     * @return list of registrations for that officer
     */
    public ArrayList<Registration> getRegistrationsByOfficer(Officer officer) {
        ArrayList<Registration> result = new ArrayList<>();
        for (Registration reg : registrationList) {
            if (reg.getOfficer().equals(officer)) {
                result.add(reg);
            }
        }
        return result;
    }

    /**
     * Returns the registration made by a specific officer for a specific project, if any.
     *
     * @param officer the officer involved
     * @param project the project applied to
     * @return matching registration, or {@code null} if not found
     */
    public Registration getRegistrationByOfficerAndProject(Officer officer, Project project) {
        for (Registration reg : registrationList) {
            if (reg.getOfficer().equals(officer) && reg.getProject().equals(project)) {
                return reg;
            }
        }
        return null;
    }

    /**
     * Removes all registrations associated with a given project.
     *
     * @param project the project whose registrations should be removed
     */
    public void removeRegistrationByProject(Project project) {
        registrationList.removeIf(reg -> reg.getProject().equals(project));
        System.out.println(Colour.GREEN + "All registrations for project '" + project.getProjectName() + "' have been removed." + Colour.RESET);
    }

    /**
     * Returns a list of pending registrations for a given project.
     *
     * @param project the project to filter by
     * @return list of pending registrations
     */
    public ArrayList<Registration> getPendingRegistrationsByProject(Project project) {
        ArrayList<Registration> pendingList = new ArrayList<>();
        for (Registration registration : registrationList) {
            if (registration.getProject().equals(project) &&
                registration.getStatus() == Registration.RegistrationStatus.PENDING) {
                pendingList.add(registration);
            }
        }
        return pendingList;
    }

    /**
     * Saves all registration data to a CSV file.
     */
    public void saveToCSV() {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"officerNric", "projectName", "status"});

        for (Registration r : this.registrationList) {
            data.add(new String[]{
                r.getOfficer().getNric(),
                r.getProject().getProjectName(),
                r.getStatus().name()
            });
        }

        CSVWriter.writeCSV("../data/RegistrationList.csv", data);
    }
}
