import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectList {
    private List<Project> projectList;
    private ManagerList managerList;
    private OfficerList officerList;

    public ProjectList(String filePath, ManagerList managerList, OfficerList officerList) {
        this.projectList = new ArrayList<>();
        this.managerList = managerList;
        this.officerList = officerList;
        loadProjectsFromCSV(filePath);
    }

    private void loadProjectsFromCSV(String filePath) {
        List<String[]> data = CSVReader.readCSV(filePath);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

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

                // Find the manager from ManagerList
                Manager manager = findManagerByName(managerName);
                if (manager == null) {
                    System.out.println("Manager not found: " + managerName);
                    continue; // Skip this project if manager is missing
                }

                Project project = new Project(
                    projectName, neighborhood, 
                    availableTwoRoom, sellingPriceTwoRoom, 
                    availableThreeRoom, sellingPriceThreeRoom, 
                    openingDate, closingDate, maxOfficer
                );

                project.setManager(manager);

                // Handle Officers
                if (row.length > 12) {
                    String[] officerNames = row[12].split(",");
                    for (String officerName : officerNames) {
                        Officer officer = findOfficerByName(officerName.trim());
                        if (officer != null) {
                            project.addOfficers(officer);
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

    private Manager findManagerByName(String name) {
        for (Manager manager : managerList.getManagerList()) {
            if (manager.getName().equalsIgnoreCase(name)) {
                return manager;
            }
        }
        return null;
    }

    private Officer findOfficerByName(String name) {
        for (Officer officer : officerList.getOfficerList()) {
            if (officer.getName().equalsIgnoreCase(name)) {
                return officer;
            }
        }
        return null;
    }

    public List<Project> getProject() {
        return projectList;
    }

    public Project getProjectByName(String projectName) {
        for (Project project : projectList) {
            if (project.getProjectName().equalsIgnoreCase(projectName)) {
                return project;
            }
        }
        return null;
    }
}
