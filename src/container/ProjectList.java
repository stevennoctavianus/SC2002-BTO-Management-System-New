package container;

import entity.*;
import utils.CSVReader;
import utils.CSVWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Manages a list of BTO {@link Project} instances.
 * <p>
 * Responsible for loading project data from CSV, linking with managers and officers,
 * providing search and update operations, and saving data back to CSV.
 */
public class ProjectList {
    private List<Project> projectList;
    private ManagerList managerList;
    private OfficerList officerList;

    /**
     * Constructs a {@code ProjectList} and loads project data from CSV.
     *
     * @param filePath     path to the CSV file
     * @param managerList  list of all managers to associate with projects
     * @param officerList  list of all officers to associate with projects
     */
    public ProjectList(String filePath, ManagerList managerList, OfficerList officerList) {
        this.projectList = new ArrayList<>();
        this.managerList = managerList;
        this.officerList = officerList;
        loadProjects(filePath);
    }

    /**
     * Loads project data from CSV, links managers and officers by name.
     *
     * @param filePath the CSV file path
     */
    private void loadProjects(String filePath) {
        List<String[]> data = CSVReader.readCSV(filePath);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date today = new Date();

        for (String[] row : data) {
            if (row.length < 12) {
                System.out.println("Skipping invalid row: " + String.join(",", row));
                continue;
            }

            try {
                String projectName = row[0];
                String neighborhood = row[1];
                int availableTwoRoom = Integer.parseInt(row[3]);
                int sellingPriceTwoRoom = Integer.parseInt(row[4]);
                int availableThreeRoom = Integer.parseInt(row[6]);
                int sellingPriceThreeRoom = Integer.parseInt(row[7]);
                Date openingDate = dateFormat.parse(row[8]);
                Date closingDate = dateFormat.parse(row[9]);
                String managerName = row[10];
                int maxOfficer = Integer.parseInt(row[11]);

                Manager manager = findManagerByName(managerName);
                if (manager == null) {
                    System.out.println("Manager not found: " + managerName);
                    continue;
                }

                Project project = new Project(
                    projectName, neighborhood,
                    availableTwoRoom, sellingPriceTwoRoom,
                    availableThreeRoom, sellingPriceThreeRoom,
                    openingDate, closingDate, maxOfficer
                );

                project.setManager(manager);
                manager.addManagedProject(project);

                if (!today.before(openingDate) && !today.after(closingDate)) {
                    manager.setActiveProject(project);
                }

                // Handle assigned officers
                if (row.length > 12) {
                    String[] officerNames = row[12].split(",");
                    for (String officerName : officerNames) {
                        Officer officer = findOfficerByName(officerName.trim());
                        if (officer != null) {
                            project.addOfficers(officer);
                            officer.setAssignedProject(
                                (!today.before(openingDate) && !today.after(closingDate)) ? project : null
                            );
                            officer.getManagedProjects().add(project);
                        } else {
                            System.out.println("Officer not found: " + officerName);
                        }
                    }
                }

                projectList.add(project);
            } catch (ParseException | NumberFormatException e) {
                System.out.println("Error parsing row: " + String.join(",", row));
                e.printStackTrace();
            }
        }
    }

    /**
     * Finds a manager by name (case-insensitive).
     *
     * @param name the manager's name
     * @return the matching {@code Manager} or {@code null} if not found
     */
    private Manager findManagerByName(String name) {
        for (Manager manager : managerList.getManagerList()) {
            if (manager.getName().equalsIgnoreCase(name)) {
                return manager;
            }
        }
        return null;
    }

    /**
     * Finds an officer by name (case-insensitive).
     *
     * @param name the officer's name
     * @return the matching {@code Officer} or {@code null} if not found
     */
    private Officer findOfficerByName(String name) {
        for (Officer officer : officerList.getOfficerList()) {
            if (officer.getName().equalsIgnoreCase(name)) {
                return officer;
            }
        }
        return null;
    }

    /**
     * Returns the list of all projects.
     *
     * @return list of {@link Project}
     */
    public List<Project> getProjectList() {
        return projectList;
    }

    /**
     * Adds a new project to the list.
     *
     * @param project the project to add
     */
    public void addProject(Project project) {
        projectList.add(project);
    }

    /**
     * Retrieves a project by name (case-insensitive).
     *
     * @param projectName name of the project
     * @return the matching {@code Project} or {@code null} if not found
     */
    public Project getProjectByName(String projectName) {
        for (Project project : projectList) {
            if (project.getProjectName().equalsIgnoreCase(projectName)) {
                return project;
            }
        }
        return null;
    }

    /**
     * Removes a project from the list.
     *
     * @param project the project to remove
     */
    public void removeProject(Project project) {
        if (projectList.remove(project)) {
            System.out.println("Project '" + project.getProjectName() + "' removed successfully.");
        } else {
            System.out.println("Project not found in the list.");
        }
    }

    /**
     * Saves all project data to a CSV file.
     * The CSV includes project details, assigned manager, max officers, and officer names.
     */
    public void saveToCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        List<String[]> data = new ArrayList<>();

        data.add(new String[]{
            "Project Name", "Neighborhood", "Type 1", "Number of units for Type 1",
            "Selling price for Type 1", "Type 2", "Number of units for Type 2",
            "Selling price for Type 2", "Application opening date", "Application closing date",
            "Manager", "Officer Slot", "Officer"
        });

        for (Project p : projectList) {
            String officerNames = p.getOfficers()
                .stream()
                .map(Officer::getName)
                .reduce((a, b) -> a + "," + b)
                .orElse("");

            data.add(new String[]{
                p.getProjectName(),
                p.getNeighborhood(),
                "2-Room",
                String.valueOf(p.getAvailableTwoRoom()),
                String.valueOf(p.getSellingPriceTwoRoom()),
                "3-Room",
                String.valueOf(p.getAvailableThreeRoom()),
                String.valueOf(p.getSellingPriceThreeRoom()),
                sdf.format(p.getOpeningDate()),
                sdf.format(p.getClosingDate()),
                p.getManager().getName(),
                String.valueOf(p.getMaxOfficer()),
                officerNames
            });
        }

        CSVWriter.writeCSV("../data/ProjectList.csv", data);
    }
}
