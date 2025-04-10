package container;
import entity.*;
import utils.CSVReader;
import utils.CSVWriter;

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

                // Find manager
                Manager manager = findManagerByName(managerName);
                if (manager == null) {
                    System.out.println("Manager not found: " + managerName);
                    continue;
                }

                Project project = new Project(
                        projectName, neighborhood,
                        availableTwoRoom, sellingPriceTwoRoom,
                        availableThreeRoom, sellingPriceThreeRoom,
                        openingDate, closingDate, maxOfficer);

                project.setManager(manager);
                manager.addManagedProject(project);

                if (!today.before(openingDate) && !today.after(closingDate)) {
                    manager.setActiveProject(project);
                }

                // Handle officers
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

    public List<Project> getProjectList() {
        return projectList;
    }

    public void addProject(Project project){
        projectList.add(project);
    }

    public Project getProjectByName(String projectName) {
        for (Project project : projectList) {
            if (project.getProjectName().equalsIgnoreCase(projectName)) {
                return project;
            }
        }
        return null;
    }

    public void removeProject(Project project) {
        if (projectList.remove(project)) {
            System.out.println("Project '" + project.getProjectName() + "' removed successfully.");
        } else {
            System.out.println("Project not found in the list.");
        }
    }

    public void saveToCSV() {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    List<String[]> data = new ArrayList<>();
    data.add(new String[]{
        "projectName", "neighborhood",
        "availableTwoRoom", "priceTwoRoom",
        "availableThreeRoom", "priceThreeRoom",
        "openDate", "closeDate",
        "managerName", "maxOfficers"
    });

    for (Project p : projectList) {
        data.add(new String[]{
            p.getProjectName(),
            p.getNeighborhood(),
            String.valueOf(p.getAvailableTwoRoom()),
            String.valueOf(p.getSellingPriceTwoRoom()),
            String.valueOf(p.getAvailableThreeRoom()),
            String.valueOf(p.getSellingPriceThreeRoom()),
            sdf.format(p.getOpeningDate()),
            sdf.format(p.getClosingDate()),
            p.getManager().getName(),
            String.valueOf(p.getMaxOfficer())
        });
    }

    CSVWriter.writeCSV("data/ProjectList.csv", data);
}

}