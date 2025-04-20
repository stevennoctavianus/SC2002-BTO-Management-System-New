package interact;

import container.*;
import entity.*;
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
 * Entry point for the BTO Management System.
 * Handles user authentication, session routing based on role,
 * and ensures that all data is saved both on normal exit and unexpected termination.
 */
public class MainMenu {

    /**
     * Main method that runs the BTO CLI program.
     * It initializes data, authenticates users, and delegates control to respective role controllers.
     *
     * @param args not used
     */
    public static void main(String[] args) {

        // Catch any uncaught exceptions and attempt to save data before exiting
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            System.out.println("An unexpected error occurred: " + throwable.getMessage());
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
                System.out.println("All data saved before crash.");
            } catch (Exception e) {
                System.out.println("Failed to save data during crash: " + e.getMessage());
            }
            System.exit(1);
        });

        // Load all user and system data
        DataInitializer.loadData();
        ApplicantList applicantList = DataInitializer.getApplicantList();
        ManagerList managerList = DataInitializer.getManagerList();
        OfficerList officerList = DataInitializer.getOfficerList();
        ProjectList projectList = DataInitializer.getProjectList();
        ApplicationList applicationList = DataInitializer.getApplicationList();
        RegistrationList registrationList = DataInitializer.getRegistrationList();
        WithdrawalList withdrawalList = DataInitializer.getWithdrawalList();
        EnquiryList enquiryList = DataInitializer.getEnquiryList();

        // Save data when the user manually terminates the program (Ctrl+C or Exit)
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DataSyncUtil syncUtil = new DataSyncUtil(
                applicantList, projectList, managerList, officerList,
                applicationList, registrationList, withdrawalList, enquiryList
            );
            syncUtil.saveAll();
            System.out.println("All data saved before shutdown.");
        }));

        Scanner scanner = new Scanner(System.in);
        ClearScreen.clear();

        while (true) {
            // Display main menu banner
            System.out.println("  ____ _______ ____    __  __                                                   _   ");
            System.out.println(" |  _ \\__   __/ __ \\  |  \\/  |                                                 | |  ");
            System.out.println(" | |_) | | | | |  | | | \\  / | __ _ _ __   __ _  __ _  ___ _ __ ___   ___ _ __ | |_ ");
            System.out.println(" |  _ <  | | | |  | | | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '_ ` _ \\ / _ \\ '_ \\| __|");
            System.out.println(" | |_) | | | | |__| | | |  | | (_| | | | | (_| | (_| |  __/ | | | | |  __/ | | | |_ ");
            System.out.println(" |____/  |_|  \\____/  |_|  |_|\\__,_|_| |_|\\__,_|\\__,_|\\___|_| |_| |_|\\___|_| |_|\\__|");
            System.out.println("                                                __/ |                               ");
            System.out.println("                                               |___/                                ");
            System.out.println("                    +----------------------------------------+");
            System.out.println("                    |  1) Applicant Login                    |");
            System.out.println("                    |  2) Officer Login                      |");
            System.out.println("                    |  3) Manager Login                      |");
            System.out.println("                    |  4) Exit                               |");
            System.out.println("                    +----------------------------------------+");

            int choice;
            String nric, password;
            System.out.print("Enter choice: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (choice == 4) {
                    // Save all data on clean exit
                    DataSyncUtil syncUtil = new DataSyncUtil(
                        applicantList, projectList, managerList, officerList,
                        applicationList, registrationList, withdrawalList, enquiryList
                    );
                    syncUtil.saveAll();
                    ClearScreen.clear();
                    System.out.println("Bye Bye!");
                    break;
                }
            } catch (InputMismatchException e) {
                ClearScreen.clear();
                System.out.println("Please input an integer!");
                BackButton.goBack();
                scanner.nextLine(); // clear the invalid input
                continue;
            }

            // Input NRIC
            System.out.print("Enter NRIC: ");
            nric = scanner.nextLine().trim();
            if (!AuthenticationService.validNRIC(nric)) {
                ClearScreen.clear();
                System.out.println("Invalid NRIC\n");
                BackButton.goBack();
                continue;
            }

            // Input password
            System.out.print("Enter Password: ");
            password = scanner.nextLine().trim();

            // Authenticate user based on role
            User user = AuthenticationService.authenticate(nric, password, choice);
            if (user != null) {
                UserSession.setCurrentUser(user);
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
                System.out.println("Invalid credentials. Please try again.\n");
                BackButton.goBack();
            }
        }

        scanner.close();
    }
}

