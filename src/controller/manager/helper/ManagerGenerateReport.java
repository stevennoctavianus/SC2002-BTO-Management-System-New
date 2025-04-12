package controller.manager.helper;
import java.util.ArrayList;
import java.util.Scanner;
import container.*;
import entity.*;
import controller.manager.template.IManagerGenerateReport;
public class ManagerGenerateReport implements IManagerGenerateReport{
    private ApplicationList applicationList;
    private Scanner scanner;

    public ManagerGenerateReport(ApplicationList applicationList) {
        this.applicationList = applicationList;
        this.scanner = new Scanner(System.in);
    }

    public void generateReport() {
        System.out.println("+-------------------------------------------------+");
        System.out.println("|          Generate Booking Report               |");
        System.out.println("+-------------------------------------------------+");
        System.out.println("|  1) View All Bookings                          |");
        System.out.println("|  2) Filter by Flat Type                        |");
        System.out.println("|  3) Filter by Marital Status                   |");
        System.out.println("|  4) Filter by Project Name                     |");
        System.out.println("+-------------------------------------------------+");
        System.out.print("Select an option: ");

        String option = scanner.nextLine();

        switch (option) {
            case "1":
                printReport(applicationList.getSuccessfulApplications());
                break;
            case "2":
                filterByFlatType();
                break;
            case "3":
                filterByMaritalStatus();
                break;
            case "4":
                filterByProjectName();
                break;
            default:
                System.out.println("Invalid option.");
        }
        
    }

    private void printReport(ArrayList<Application> applications) {
        if (applications.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("\n===== Booking Report =====");
        for (Application app : applications) {
            Applicant applicant = app.getApplicant();
            System.out.println("Name: " + applicant.getName());
            System.out.println("NRIC: " + applicant.getNric());
            System.out.println("Age: " + applicant.getAge());
            System.out.println("Marital Status: " + applicant.getMaritalStatus());
            System.out.println("Project Name: " + app.getProject().getProjectName());
            System.out.println("Flat Type: " + app.getFlatType());
            System.out.println("-------------------------------");
        }
    }

    private void filterByFlatType() {
        System.out.print("Enter Flat Type to filter (2-room or 3-room): ");
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
            System.out.println("Invalid flat type.");
        }
    }

    private void filterByMaritalStatus() {
        System.out.print("Enter Marital Status to filter (Single/Married): ");
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
            System.out.println("Invalid marital status.");
        }
        printReport(filtered);
    }

    private void filterByProjectName() {
        System.out.print("Enter Project Name: ");
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