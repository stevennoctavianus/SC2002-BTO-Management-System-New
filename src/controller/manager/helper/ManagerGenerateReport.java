package controller.manager.helper;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import container.*;
import entity.*;
import utils.BackButton;
import utils.ClearScreen;
import utils.Colour;
import controller.manager.template.IManagerGenerateReport;

/**
 * Provides report generation features for managers to view or filter successful application bookings. Supports filtering by flat type, marital status, and project name.
 */
public class ManagerGenerateReport implements IManagerGenerateReport {

    private ApplicationList applicationList;
    private Scanner scanner;

    /**
     * Constructs a manager report generator using the shared application list.
     *
     * @param applicationList the list of all applications in the system
     */
    public ManagerGenerateReport(ApplicationList applicationList) {
        this.applicationList = applicationList;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the report generation menu to the manager and routes the request
     * to the appropriate report filter or view logic.
     */
    public void generateReport() {
        System.out.println("            +------------------------------------------------+");
        System.out.println("            |             Generate Booking Report            |");
        System.out.println("            +------------------------------------------------+");
        System.out.println("            |  1) View All Bookings                          |");
        System.out.println("            |  2) Filter by Flat Type                        |");
        System.out.println("            |  3) Filter by Marital Status                   |");
        System.out.println("            |  4) Filter by Project Name                     |");
        System.out.println("            +------------------------------------------------+\n\n");
        System.out.print(Colour.BLUE + "Select an option: " + Colour.RESET);

        int option;
        try {
            option = scanner.nextInt();
        } catch (InputMismatchException e) {
            ClearScreen.clear();
            System.out.println(Colour.RED + "Please input an integer!" + Colour.RESET);
            BackButton.goBack();
            scanner.nextLine();
            return;
        }

        ClearScreen.clear();

        switch (option) {
            case 1:
                printReport(applicationList.getSuccessfulApplications());
                break;
            case 2:
                filterByFlatType();
                break;
            case 3:
                filterByMaritalStatus();
                break;
            case 4:
                filterByProjectName();
                break;
            default:
                System.out.println(Colour.RED + "Invalid choice! Please enter a valid option." + Colour.RESET);
        }
    }

    /**
     * Displays a formatted list of successful applications, including applicant and project details.
     *
     * @param applications the list of applications to display
     */
    private void printReport(ArrayList<Application> applications) {
        if (applications.isEmpty()) {
            System.out.println(Colour.RED + "No bookings found." + Colour.RESET);
            return;
        }

        System.out.println(Colour.BLUE + "\n===== Booking Report =====" + Colour.RESET);
        for (Application app : applications) {
            Applicant applicant = app.getApplicant();
            System.out.println(Colour.BLUE + "Name: " + Colour.RESET + applicant.getName());
            System.out.println(Colour.BLUE + "NRIC: " + Colour.RESET + applicant.getNric());
            System.out.println(Colour.BLUE + "Age: " + Colour.RESET + applicant.getAge());
            System.out.println(Colour.BLUE + "Marital Status: " + Colour.RESET + applicant.getMaritalStatus());
            System.out.println(Colour.BLUE + "Project Name: " + Colour.RESET + app.getProject().getProjectName());
            System.out.println(Colour.BLUE + "Flat Type: " + Colour.RESET + app.getFlatType());
            System.out.println("-------------------------------");
        }
    }

    /**
     * Filters successful applications by flat type (2-room or 3-room).
     */
    private void filterByFlatType() {
        System.out.print(Colour.BLUE + "Enter Flat Type to filter (2-room or 3-room): " + Colour.RESET);
        String input = scanner.nextLine().toUpperCase();

        try {
            Application.FlatType type = Application.FlatType.valueOf(input);
            ArrayList<Application> filtered = new ArrayList<>();
            for (Application app : applicationList.getSuccessfulApplications()) {
                if (app.getFlatType() == type) {
                    filtered.add(app);
                }
            }
            printReport(filtered);
        } catch (IllegalArgumentException e) {
            System.out.println(Colour.RED + "Invalid flat type." + Colour.RESET);
        }
    }

    /**
     * Filters successful applications by applicant marital status.
     */
    private void filterByMaritalStatus() {
        System.out.print(Colour.BLUE + "Enter Marital Status to filter (Single/Married): ");
        String status = scanner.nextLine();

        ArrayList<Application> filtered = new ArrayList<>();
        try {
            User.MaritalStatus filterStatus = User.MaritalStatus.valueOf(status.toUpperCase());
            for (Application application : applicationList.getSuccessfulApplications()) {
                if (application.getApplicant().getMaritalStatus() == filterStatus) {
                    filtered.add(application);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(Colour.RED + "Invalid marital status." + Colour.RESET);
        }
        printReport(filtered);
    }

    /**
     * Filters successful applications by project name (case-insensitive).
     */
    private void filterByProjectName() {
        System.out.print(Colour.BLUE + "Enter Project Name: " + Colour.RESET);
        String projectName = scanner.nextLine();

        ArrayList<Application> filtered = new ArrayList<>();
        for (Application app : applicationList.getSuccessfulApplications()) {
            if (app.getProject().getProjectName().equalsIgnoreCase(projectName)) {
                filtered.add(app);
            }
        }
        printReport(filtered);
    }
}
