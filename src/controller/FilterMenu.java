package controller;
import entity.*;
import utils.ClearScreen;
import java.util.InputMismatchException;
import java.util.Scanner;
public class FilterMenu {
    private Scanner scanner;

    public FilterMenu() {
        this.scanner = new Scanner(System.in);
    }

    public void manageFilters() {
        FilterSettings filters = UserSession.getFilterSettings();
        System.out.println("\nCurrent " + filters);

        System.out.println("\nFilter Options:");
        System.out.println("1) Set Location Filter");
        System.out.println("2) Set Flat Type Filter");
        System.out.println("3) Clear Filters");
        System.out.println("4) Continue to Main Menu");
        System.out.print("Enter choice (1-4): ");
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            ClearScreen.clear();
            System.out.println("Please input an integer!");
            scanner.nextLine();
            return;
        }

        ClearScreen.clear();
        switch (choice) {
            case 1:
                System.out.print("Enter Location (Neighborhood, e.g., Yishun): ");
                String location = scanner.nextLine();
                filters.setLocation(location);
                System.out.println("Location filter set to: " + location);
                manageFilters();
                break;
            case 2:
                System.out.println("Select Flat Type:");
                System.out.println("1) 2-Room");
                System.out.println("2) 3-Room");
                System.out.print("Enter choice (1-2): ");
                int flatChoice;
                try {
                    flatChoice = scanner.nextInt();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    ClearScreen.clear();
                    System.out.println("Please input an integer!");
                    scanner.nextLine();
                    return;
                }
                Application.FlatType flatType = null;
                if (flatChoice == 1) {
                    flatType = Application.FlatType.TWOROOM;
                    System.out.println("Flat Type filter set to: 2-Room");
                } else if (flatChoice == 2) {
                    flatType = Application.FlatType.THREEROOM;
                    System.out.println("Flat Type filter set to: 3-Room");
                } else {
                    System.out.println("Invalid choice. Filter not changed.");
                }
                filters.setFlatType(flatType);
                manageFilters();
                break;
            case 3:
                filters.clearFilters();
                System.out.println("Filters cleared.");
                manageFilters();
                break;
            case 4:
                return; // Proceed to role-specific menu
            default:
                System.out.println("Invalid choice. Try again.");
                manageFilters();
        }
    }
}