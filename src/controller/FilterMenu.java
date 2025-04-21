package controller;

import entity.*;
import utils.ClearScreen;
import utils.Colour;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A controller class that manages user-defined filters (location and flat type)
 * for browsing BTO projects.
 * <p>
 * This class interacts with the {@link FilterSettings} stored in {@link UserSession}
 * and provides a simple menu to apply or reset filters before returning to the main flow.
 */
public class FilterMenu {
    private Scanner scanner;

    /**
     * Constructs a {@code FilterMenu} with a new input scanner.
     */
    public FilterMenu() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the filter management menu and allows users to:
     * <ul>
     *     <li>Set a location filter</li>
     *     <li>Set a flat type filter</li>
     *     <li>Clear all filters</li>
     *     <li>Return to the main menu</li>
     * </ul>
     * The selected filters are saved in the current {@link UserSession}.
     */
    public void manageFilters() {
        FilterSettings filters = UserSession.getFilterSettings();
        System.out.println(Colour.BLUE + "\nCurrent " + filters + Colour.RESET);

        System.out.println(Colour.BLUE_UNDERLINED + "\nFilter Options:" + Colour.RESET);
        System.out.println("1) Set Location Filter");
        System.out.println("2) Set Flat Type Filter");
        System.out.println("3) Clear Filters");
        System.out.println("4) Continue to Main Menu");
        System.out.println(Colour.BLUE + "\nEnter choice (1-4): " + Colour.RESET);

        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            ClearScreen.clear();
            System.out.println(Colour.RED + "Please input an integer!" + Colour.RESET);
            scanner.nextLine();
            return;
        }

        ClearScreen.clear();
        switch (choice) {
            case 1:
                System.out.println(Colour.BLUE + "Enter Location (Neighbourhood, e.g., Yishun): " + Colour.RESET);
                String location = scanner.nextLine();
                filters.setLocation(location);
                System.out.println(Colour.GREEN + "Location filter set to: " + location + Colour.RESET);
                manageFilters();
                break;

            case 2:
                System.out.println(Colour.BLUE_UNDERLINED + "Select Flat Type:" + Colour.RESET);
                System.out.println("1) 2-Room");
                System.out.println("2) 3-Room");
                System.out.print(Colour.BLUE + "Enter choice (1-2): ");
                int flatChoice;
                try {
                    flatChoice = scanner.nextInt();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    ClearScreen.clear();
                    System.out.println(Colour.RED + "Please input an integer!" + Colour.RESET);
                    scanner.nextLine();
                    return;
                }

                Application.FlatType flatType = null;
                if (flatChoice == 1) {
                    flatType = Application.FlatType.TWOROOM;
                    System.out.println(Colour.GREEN + "Flat Type filter set to: 2-Room" + Colour.RESET);
                } else if (flatChoice == 2) {
                    flatType = Application.FlatType.THREEROOM;
                    System.out.println(Colour.GREEN + "Flat Type filter set to: 3-Room" + Colour.RESET);
                } else {
                    System.out.println(Colour.RED + "Invalid choice. Filter not changed." + Colour.RESET);
                }
                filters.setFlatType(flatType);
                manageFilters();
                break;

            case 3:
                filters.clearFilters();
                System.out.println(Colour.GREEN + "Filters cleared." + Colour.RESET);
                manageFilters();
                break;

            case 4:
                return;

            default:
                System.out.println(Colour.RED + "Invalid choice. Try again." + Colour.RESET);
                manageFilters();
        }
    }
}
