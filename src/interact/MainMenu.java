package interact;
import container.*;
import entity.*;
import utils.ClearScreen;
import controller.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    private static ProjectList projectList;
    private static ApplicationList applicationList = new ApplicationList();
    private static EnquiryList enquiryList = new EnquiryList();
    private static WithdrawalList withdrawalList = new WithdrawalList();
    private static RegistrationList registrationList = new RegistrationList();

    public static void main(String[] args) {
        DataInitializer.loadData(); // Load users from CSV
        ManagerList managerList = DataInitializer.getManagerList();
        OfficerList officerList = DataInitializer.getOfficerList();

        projectList = new ProjectList("../data/ProjectList.csv", managerList, officerList);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the BTO Management System");
            System.out.println("1) Applicant Login");
            System.out.println("2) Officer Login");
            System.out.println("3) Manager Login");
            System.out.println("4) Exit");
            int choice;
            String nric, password;
            System.out.print("Enter choice: ");
            try{
                choice = scanner.nextInt();
                if (choice == 4){
                    ClearScreen.clear();
                    System.out.println("Bye Bye!");
                    break;
                }
                scanner.nextLine();
            }
            catch(InputMismatchException e){
                System.out.println("Please input an integer!");
                continue;
            }
            // Input and validate NRIC:
            System.out.print("Enter NRIC: ");
            nric = scanner.nextLine().trim();
            if(!AuthenticationService.validNRIC(nric)){ 
                System.out.println("Invalid NRIC\n");
                continue;
            }

            // Input password:
            System.out.print("Enter Password: ");
            password = scanner.nextLine().trim();

            // Check account's availability:
            User user = AuthenticationService.authenticate(nric, password,choice);
            if (user != null) {
                UserSession.setCurrentUser(user);

                if (user instanceof Officer) {
                    new OfficerController((Officer) user, projectList, applicationList, enquiryList, withdrawalList, registrationList).showMenu();
                }
                else if (user instanceof Applicant) {
                    new ApplicantController((Applicant) user, projectList, applicationList, enquiryList, withdrawalList).showMenu();
                }
                else if (user instanceof Manager) {
                    new ManagerController((Manager) user, projectList, applicationList, registrationList, withdrawalList, enquiryList).showMenu();
                }
            }
            else {
                System.out.println("Invalid credentials. Please try again.\n");
            }
        }
        scanner.close();
    }
}
