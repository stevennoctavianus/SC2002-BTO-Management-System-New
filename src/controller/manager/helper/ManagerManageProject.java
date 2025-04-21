package controller.manager.helper;

import entity.*;
import utils.BackButton;
import utils.ClearScreen;
import utils.Colour;
import container.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import controller.manager.template.IManagerManageProject;

/**
 * Provides managers with tools to create, edit, delete, and manage visibility for BTO projects.
 * Also handles cleanup of related data such as applications and registrations when deleting a project.
 */
public class ManagerManageProject implements IManagerManageProject {

    private Manager manager;
    private ProjectList projectList;
    private ApplicationList applicationList;
    private RegistrationList registrationList;
    private Scanner scanner;

    /**
     * Constructs the project manager controller.
     *
     * @param manager           the logged-in manager
     * @param projectList       the list of all BTO projects
     * @param applicationList   the list of all applications
     * @param registrationList  the list of all officer registrations
     */
    public ManagerManageProject(Manager manager, ProjectList projectList,
                                ApplicationList applicationList, RegistrationList registrationList) {
        this.manager = manager;
        this.projectList = projectList;
        this.applicationList = applicationList;
        this.registrationList = registrationList;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Allows the manager to create a new project.
     * Includes input validation and ensures the manager has no currently active project.
     */
    public void createProject() {
        if (manager.getActiveProject() != null) {
            System.out.println(Colour.RED + "You are already handling an active project: " + manager.getActiveProject().getProjectName());
            System.out.println("You must finish managing the current project before creating a new one!" + Colour.RESET);
            return;
        }

        String name;
        while (true) {
            System.out.print(Colour.BLUE + "Enter Project Name: " + Colour.RESET);
            name = scanner.nextLine();
            if (projectList.getProjectByName(name) != null) {
                System.out.println(Colour.RED + "A project with this name already exists. Please enter a different name." + Colour.RESET);
            } else {
                break;
            }
        }

        System.out.print(Colour.BLUE + "Enter Neighbourhood: " + Colour.RESET);
        String neighborhood = scanner.nextLine();

        int twoRoom = getValidInt(Colour.BLUE + "Enter number of 2-Room Flats: " + Colour.RESET);
        int sellingPriceTwoRoom = getValidInt(Colour.BLUE + "Enter the selling price of 2-Room Flats: " + Colour.RESET);
        int threeRoom = getValidInt(Colour.BLUE + "Enter number of 3-Room Flats: " + Colour.RESET);
        int sellingPriceThreeRoom = getValidInt(Colour.BLUE + "Enter the selling price of 3-Room Flats: " + Colour.RESET);
        Date openDate = getValidDate(Colour.BLUE + "Enter Opening Date (yyyy-MM-dd): " + Colour.RESET);
        Date closeDate = getValidDate(Colour.BLUE + "Enter Closing Date (yyyy-MM-dd): " + Colour.RESET);
        int maxOfficer = getValidInt(Colour.BLUE + "Enter Max Officer Slot: " + Colour.RESET);

        Project project = new Project(name, neighborhood, twoRoom, sellingPriceTwoRoom, threeRoom, sellingPriceThreeRoom, openDate, closeDate, maxOfficer);
        project.setManager(manager);

        manager.addManagedProject(project);
        if (project.getVisibility()) {
            manager.setActiveProject(project);
        }

        projectList.addProject(project);
        System.out.println(Colour.GREEN + "Project created successfully." + Colour.RESET);
    }

    /**
     * Allows the manager to edit attributes of an existing project they created.
     */
    public void editProject() {
        System.out.print(Colour.BLUE + "Enter project name to edit:  " + Colour.RESET);
        String name = scanner.nextLine();

        Project project = projectList.getProjectByName(name);
        if (project == null || !manager.getManagedProjects().contains(project)) {
            System.out.println(Colour.RED + "Project not found or not owned by you." + Colour.RESET);
            return;
        }

        while (true) {
            System.out.println("            +------------------------------------------------+");
            System.out.println("            |              Select What to Edit               |");
            System.out.println("            +------------------------------------------------+");
            System.out.println("            |  1) Neighbourhood                              |");
            System.out.println("            |  2) 2-Room Units                               |");
            System.out.println("            |  3) Selling Price of 2-Room Units              |");
            System.out.println("            |  4) 3-Room Units                               |");
            System.out.println("            |  5) Selling Price of 3-Room Units              |");
            System.out.println("            |  6) Open Date                                  |");
            System.out.println("            |  7) Close Date                                 |");
            System.out.println("            |  8) Max Officer Slots                          |");
            System.out.println("            |  9) Exit                                       |");
            System.out.println("            +------------------------------------------------+\n\n");
            System.out.print(Colour.BLUE + "Enter choice: " + Colour.RESET);

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                ClearScreen.clear();
                System.out.println(Colour.RED + "Please input an integer!" + Colour.RESET);
                BackButton.goBack();
                scanner.nextLine();
                continue;
            }

            scanner.nextLine();
            ClearScreen.clear();

            switch (choice) {
                case 1:
                    System.out.print(Colour.BLUE + "New Neighbourhood: " + Colour.RESET);
                    project.setNeighborhood(scanner.nextLine());
                    break;
                case 2:
                    project.setAvailableTwoRoom(getValidInt(Colour.BLUE + "New 2-Room Units: " + Colour.RESET));
                    break;
                case 3:
                    project.setSellingPriceTwoRoom(getValidInt(Colour.BLUE + "New 2-Room Units Selling Price: " + Colour.RESET));
                    break;
                case 4:
                    project.setAvailableThreeRoom(getValidInt(Colour.BLUE + "New 3-Room Units: " + Colour.RESET));
                    break;
                case 5:
                    project.setSellingPriceThreeRoom(getValidInt(Colour.BLUE + "New 3-Room Units Selling Price: " + Colour.RESET));
                    break;
                case 6:
                    project.setOpeningDate(getValidDate(Colour.BLUE + "New Opening Date (yyyy-MM-dd): " + Colour.RESET));
                    break;
                case 7:
                    project.setClosingDate(getValidDate(Colour.BLUE + "New Closing Date (yyyy-MM-dd): " + Colour.RESET));
                    break;
                case 8:
                    project.setMaxOfficer(getValidInt(Colour.BLUE + "New Max Officer Slots: " + Colour.RESET));
                    break;
                case 9:
                    System.out.println(Colour.GREEN + "Edit complete." + Colour.RESET);
                    return;
                default:
                    System.out.println(Colour.RED + "Invalid choice! Please enter a valid option." + Colour.RESET);
            }
        }
    }

    /**
     * Deletes a project and performs cleanup of related applications, officer assignments, and manager records.
     */
    public void deleteProject() {
        System.out.print(Colour.BLUE + "Enter project name to delete: " + Colour.RESET);
        String name = scanner.nextLine();

        Project project = projectList.getProjectByName(name);
        if (project == null || !manager.getManagedProjects().contains(project)) {
            System.out.println(Colour.RED + "Project not found or not managed by you." + Colour.RESET);
            return;
        }

        applicationList.removeApplicationsByProject(project);

        ArrayList<Registration> registrationsToRemove = registrationList.getRegistrations();
        for (Registration reg : registrationsToRemove) {
            if (reg.getProject().equals(project)) {
                Officer officer = reg.getOfficer();
                officer.getRegistrations().remove(reg);
            }
        }
        registrationList.removeRegistrationByProject(project);
        registrationList.saveToCSV();

        for (Officer officer : project.getOfficers()) {
            if (officer.getAssignedProject() != null && officer.getAssignedProject().equals(project)) {
                officer.setAssignedProject(null);
            }
            officer.getManagedProjects().remove(project);
        }

        manager.getManagedProjects().remove(project);
        if (manager.getActiveProject() != null && manager.getActiveProject().equals(project)) {
            manager.setActiveProject(null);
        }

        projectList.removeProject(project);
    }

    /**
     * Toggles the visibility of a managed project (visible ↔ hidden).
     */
    public void changeProjectVisibility() {
        System.out.print(Colour.BLUE + "Enter project name to change visibility: " + Colour.RESET);
        String name = scanner.nextLine();

        Project project = projectList.getProjectByName(name);
        if (project == null || !manager.getManagedProjects().contains(project)) {
            System.out.println(Colour.RED + "Project not found or not managed by you." + Colour.RESET);
            return;
        }

        project.setVisibility(!project.getVisibility());
        System.out.println(Colour.GREEN + "Visibility updated: " + (project.getVisibility() ? "Visible" : "Hidden") + Colour.RESET);
    }

    /**
     * Displays all projects that the manager has created or managed.
     */
    public void viewOwnProject() {
        ArrayList<Project> myProjects = manager.getManagedProjects();
        if (myProjects.isEmpty()) {
            System.out.println(Colour.RED + "You have not created any projects." + Colour.RESET);
            return;
        }

        System.out.println(Colour.BLUE_UNDERLINED + "\nYour Projects:" + Colour.RESET);
        for (Project project : myProjects) {
            System.out.println(project.toString());
        }
    }

    /**
     * Displays all projects in the system, regardless of ownership.
     */
    public void viewAllCreatedProject() {
        System.out.println(Colour.BLUE_UNDERLINED + "\nAll Projects in the System: " + Colour.RESET);
        for (Project project : projectList.getProjectList()) {
            System.out.println(project.toString());
        }
    }

    /**
     * Helper method to repeatedly prompt for and return a valid integer input.
     */
    private int getValidInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(Colour.RED + "Please input a valid integer!" + Colour.RESET);
            }
        }
    }

    /**
     * Helper method to repeatedly prompt for and return a valid date input.
     */
    private Date getValidDate(String prompt) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return sdf.parse(input);
            } catch (ParseException e) {
                System.out.println(Colour.RED + "Invalid date format. Please use yyyy-MM-dd." + Colour.RESET);
            }
        }
    }
}
