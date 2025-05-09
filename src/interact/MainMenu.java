package interact;

import container.*;
import entity.*;
import utils.Colour;
import utils.BackButton;
import utils.ClearScreen;
import utils.DataSyncUtil;
import controller.*;
import controller.applicant.ApplicantController;
import controller.officer.OfficerController;
import controller.manager.ManagerController;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Entry point of the BTO Management System.
 * <p>
 * Presents a main menu for users to log in based on their role (Applicant, Officer, Manager),
 * or register as a new applicant. Also includes graceful shutdown hooks and error recovery mechanisms.
 * All session-related user data is managed through {@link UserSession}, and persistent data is stored
 * using the {@link DataSyncUtil}.
 */
public class MainMenu {

    /**
     * Starts the BTO system, handles user authentication and dispatches users to their respective dashboards.
     * <p>
     * This method sets up:
     * <ul>
     *   <li>Uncaught exception handler for emergency data saving</li>
     *   <li>Shutdown hook for normal termination</li>
     *   <li>Main menu for login and registration</li>
     * </ul>
     *
     * Users can:
     * <ul>
     *   <li>Login as Applicant, Officer, or Manager</li>
     *   <li>Register as a new Applicant</li>
     *   <li>Exit the program safely</li>
     * </ul>
     *
     * All data is persisted to CSV files on exit or crash.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            System.out.println(Colour.RED + "An unexpected error occurred: " + throwable.getMessage() + Colour.RESET);
            try {
                DataSyncUtil syncUtil = new DataSyncUtil(
                        DataInitializer.getApplicantList(),
                        DataInitializer.getProjectList(),
                        DataInitializer.getManagerList(),
                        DataInitializer.getOfficerList(),
                        DataInitializer.getApplicationList(),
                        DataInitializer.getRegistrationList(),
                        DataInitializer.getWithdrawalList(),
                        DataInitializer.getEnquiryList()
                );
                syncUtil.saveAll();
                System.out.println(Colour.GREEN + "All data saved before crash." + Colour.RESET);
            } catch (Exception e) {
                System.out.println(Colour.RED + "Failed to save data during crash: " + e.getMessage() + Colour.RESET);
            }
            System.exit(1);
        });

        DataInitializer.loadData();
        ApplicantList applicantList = DataInitializer.getApplicantList();
        ManagerList managerList = DataInitializer.getManagerList();
        OfficerList officerList = DataInitializer.getOfficerList();
        ProjectList projectList = DataInitializer.getProjectList();
        ApplicationList applicationList = DataInitializer.getApplicationList();
        RegistrationList registrationList = DataInitializer.getRegistrationList();
        WithdrawalList withdrawalList = DataInitializer.getWithdrawalList();
        EnquiryList enquiryList = DataInitializer.getEnquiryList();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DataSyncUtil syncUtil = new DataSyncUtil(
                    applicantList,
                    projectList,
                    managerList,
                    officerList,
                    applicationList,
                    registrationList,
                    withdrawalList,
                    enquiryList
            );
            syncUtil.saveAll();
            System.out.println(Colour.GREEN + "All data saved before shutdown." + Colour.RESET);
        }));

        Scanner scanner = new Scanner(System.in);
        ClearScreen.clear();

        while (true) {
            System.out.println(Colour.BLUE + "  ____ _______ ____    __  __                                                   _   ");
            System.out.println(" |  _ \\__   __/ __ \\  |  \\/  |                                                 | |  ");
            System.out.println(" | |_) | | | | |  | | | \\  / | __ _ _ __   __ _  __ _  ___ _ __ ___   ___ _ __ | |_ ");
            System.out.println(" |  _ <  | | | |  | | | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '_ ` _ \\ / _ \\ '_ \\| __|");
            System.out.println(" | |_) | | | | |__| | | |  | | (_| | | | | (_| | (_| |  __/ | | | | |  __/ | | | |_ ");
            System.out.println(" |____/  |_|  \\____/  |_|  |_|\\__,_|_| |_|\\__,_|\\__,_|\\___|_| |_| |_|\\___|_| |_|\\__|");
            System.out.println("                                                __/ |                               ");
            System.out.println("                                               |___/                                " + Colour.RESET);
            System.out.println("                    +----------------------------------------+");
            System.out.println("                    |  1) Applicant Login                    |");
            System.out.println("                    |  2) Officer Login                      |");
            System.out.println("                    |  3) Manager Login                      |");
            System.out.println("                    |  4) Register as Applicant              |");
            System.out.println("                    |  5) Exit                               |");
            System.out.println("                    +----------------------------------------+\n\n");

            int choice;
            String nric, password;
            System.out.print(Colour.BLUE + "Enter choice: " + Colour.RESET);
            try {
                choice = scanner.nextInt();
                if (choice == 4) {
                    RegistrationService regService = new RegistrationService(applicantList);
                    regService.registerNewApplicant();
                    BackButton.goBack();
                    continue;
                }
                if (choice == 5) {
                    DataSyncUtil syncUtil = new DataSyncUtil(
                            applicantList,
                            projectList,
                            managerList,
                            officerList,
                            applicationList,
                            registrationList,
                            withdrawalList,
                            enquiryList
                    );
                    syncUtil.saveAll();
                    ClearScreen.clear();
                    System.out.println(Colour.BLUE + "Bye Bye!" + Colour.RESET);
                    break;
                }
                scanner.nextLine(); // consume newline
            } catch (InputMismatchException e) {
                ClearScreen.clear();
                System.out.println(Colour.RED + "Please input an integer!" + Colour.RESET);
                BackButton.goBack();
                scanner.nextLine();
                continue;
            }

            System.out.print(Colour.BLUE + "Enter NRIC: " + Colour.RESET);
            nric = scanner.nextLine().trim().toUpperCase();
            if (!AuthenticationService.validNRIC(nric)) {
                ClearScreen.clear();
                System.out.println(Colour.RED + "Invalid NRIC\n" + Colour.RESET);
                BackButton.goBack();
                continue;
            }

            System.out.print(Colour.BLUE + "Enter Password: " + Colour.RESET);
            password = scanner.nextLine().trim();

            User user = AuthenticationService.authenticate(nric, password, choice);
            if (user != null) {
                UserSession.setCurrentUser(user);
                ClearScreen.clear();
                new FilterMenu().manageFilters();  // Optionally ask for filters
                ClearScreen.clear();
                if (user instanceof Officer) {
                    new OfficerController((Officer) user, projectList, applicationList, enquiryList, withdrawalList, registrationList).showMenu();
                } else if (user instanceof Applicant) {
                    new ApplicantController((Applicant) user, projectList, applicationList, enquiryList, withdrawalList).showMenu();
                } else if (user instanceof Manager) {
                    new ManagerController((Manager) user, projectList, applicationList, registrationList, withdrawalList, enquiryList).showMenu();
                }
            } else {
                ClearScreen.clear();
                System.out.println(Colour.RED + "Invalid credentials. Please try again.\n" + Colour.RESET);
                BackButton.goBack();
            }
        }
        scanner.close();
    }
}
