package controller.manager.helper;
import entity.*;
import container.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import controller.manager.template.IManagerManageProject;
public class ManagerManageProject implements IManagerManageProject{
    private Manager manager;
    private ProjectList projectList;
    private ApplicationList applicationList;
    private RegistrationList registrationList;

    private Scanner scanner;

    public ManagerManageProject(Manager manager, ProjectList projectList, ApplicationList applicationList, RegistrationList registrationList) {
        this.manager = manager;
        this.projectList = projectList;
        this.applicationList = applicationList;
        this.registrationList = registrationList;
        this.scanner = new Scanner(System.in);
    }

    public void createProject() {
        if (manager.getActiveProject() != null) {
            System.out.println("You are already handling an active project: " + manager.getActiveProject().getProjectName());
            System.out.println("You must finish managing the current project before creating a new one.");
            return;
        }

        System.out.print("Enter Project Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Neighborhood: ");
        String neighborhood = scanner.nextLine();

        System.out.print("Enter number of 2-Room Flats: ");
        int twoRoom = scanner.nextInt();

        System.out.print("Enter the selling price of 2-Room Flats: ");
        int sellingPriceTwoRoom = scanner.nextInt();

        System.out.print("Enter number of 3-Room Flats: ");
        int threeRoom = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter the selling price of 3-Room Flats: ");
        int sellingPriceThreeRoom = scanner.nextInt();
        scanner.nextLine();

        Date openDate;
        Date closeDate;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            System.out.print("Enter Opening Date (yyyy-MM-dd): ");
            openDate = sdf.parse(scanner.nextLine());

            System.out.print("Enter Closing Date (yyyy-MM-dd): ");
            closeDate = sdf.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        System.out.print("Enter Max Officer Slot: ");
        int maxOfficer = scanner.nextInt();
        scanner.nextLine();

        Project project = new Project(name, neighborhood, twoRoom, sellingPriceTwoRoom, threeRoom, sellingPriceThreeRoom, openDate, closeDate, maxOfficer);
        project.setManager(manager);

        // Assign manager's responsibility
        manager.addManagedProject(project);
        if (project.getVisibility()) {
            manager.setActiveProject(project);
        }

        projectList.addProject(project);
        System.out.println("Project created successfully.");
    }

    public void editProject() {
        System.out.print("Enter project name to edit: ");
        String name = scanner.nextLine();

        Project project = projectList.getProjectByName(name);
        if (project == null || !manager.getManagedProjects().contains(project)) {
            System.out.println("Project not found or not owned by you.");
            return;
        }

        while (true) {
            System.out.println("\nSelect what to edit:");
            System.out.println("1) Neighborhood");
            System.out.println("2) 2-Room Units");
            System.out.println("3) Selling price of 2-room Units");
            System.out.println("4) 3-Room Units");
            System.out.println("5) Selling price of 3-room Units");
            System.out.println("6) Open Date");
            System.out.println("7) Close Date");
            System.out.println("8) Max Officer Slots");
            System.out.println("0) Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("New Neighborhood: ");
                    project.setNeighborhood(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("New 2-Room Units: ");
                    project.setAvailableTwoRoom(scanner.nextInt());
                    scanner.nextLine();
                    break;
                case 3:
                    System.out.print("New 2-Room Units Selling Price: ");
                    project.setSellingPriceTwoRoom(scanner.nextInt());
                    scanner.nextLine();
                    break;
                case 4:
                    System.out.print("New 3-Room Units: ");
                    project.setAvailableThreeRoom(scanner.nextInt());
                    scanner.nextLine();
                    break;
                case 5:
                    System.out.print("New 3-Room Units Selling Price: ");
                    project.setSellingPriceThreeRoom(scanner.nextInt());
                    scanner.nextLine();
                    break;

                case 6:
                    try {
                        System.out.print("New Opening Date (yyyy-MM-dd): ");
                        String openDateStr = scanner.nextLine();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date newOpenDate = sdf.parse(openDateStr);
                        project.setOpeningDate(newOpenDate);
                        System.out.println("Opening date updated.");
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                    }
                    break;

                case 7:
                    try {
                        System.out.print("New Closing Date (yyyy-MM-dd): ");
                        String closeDateStr = scanner.nextLine();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date newCloseDate = sdf.parse(closeDateStr);
                        project.setClosingDate(newCloseDate);
                        System.out.println("Closing date updated.");
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                    }
                    break;

                case 8:
                    System.out.print("New Max Officer Slots: ");
                    project.setMaxOfficer(scanner.nextInt());
                    scanner.nextLine();
                    break;
                case 0:
                    System.out.println("Edit complete.");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    public void deleteProject() {
        System.out.print("Enter project name to delete: ");
        String name = scanner.nextLine();

        Project project = projectList.getProjectByName(name);
        if (project == null || !manager.getManagedProjects().contains(project)) {
            System.out.println("Project not found or not managed by you.");
            return;
        }

        // 1. Remove related applications
        applicationList.removeApplicationsByProject(project);

        // 2. Remove related registrations
        registrationList.removeRegistrationByProject(project);

        // 3. Clear officer project references
        for (Officer officer : project.getOfficers()) {
            if (officer.getAssignedProject() != null && officer.getAssignedProject().equals(project)) {
                officer.setAssignedProject(null);
            }
            officer.getManagedProjects().remove(project);
        }

        // 4. Clear manager references
        Manager manager = project.getManager();
        if (manager != null) {
            manager.getManagedProjects().remove(project);
            if (manager.getActiveProject() != null && manager.getActiveProject().equals(project)) {
                manager.setActiveProject(null);
            }
        }

        // 5. Remove project from ProjectList
        projectList.removeProject(project);
    }

    public void changeProjectVisibility() {
        System.out.print("Enter project name to change visibility: ");
        String name = scanner.nextLine();

        Project project = projectList.getProjectByName(name);
        if (project == null || !manager.getManagedProjects().contains(project)) {
            System.out.println("Project not found or not managed by you.");
            return;
        }

        project.setVisibility(!project.getVisibility());
        System.out.println("Visibility updated: " + (project.getVisibility() ? "Visible" : "Hidden"));
    }

    public void viewOwnProject() {
        ArrayList<Project> myProjects = manager.getManagedProjects();
        if (myProjects.isEmpty()) {
            System.out.println("You have not created any projects.");
            return;
        }

        System.out.println("\nYour Projects:");
        for (Project project : myProjects) {
            System.out.println(project.toString());
        }
    }

    public void viewAllCreatedProject() {
        System.out.println("\nAll Projects in the System:");
        for (Project project : projectList.getProjectList()) {
            System.out.println(project.toString());
        }
    }
}
