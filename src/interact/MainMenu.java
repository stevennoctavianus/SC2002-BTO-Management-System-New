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
        ClearScreen.clear();
        while (true) {
            System.out.println("+----------------------------------------+");
            System.out.println("|      Welcome to the BTO Management     |");
            System.out.println("+----------------------------------------+");
            System.out.println("|  1) Applicant Login                    |");
            System.out.println("|  2) Officer Login                      |");
            System.out.println("|  3) Manager Login                      |");
            System.out.println("|  4) Exit                               |");
            System.out.println("+----------------------------------------+");
            int choice;
            String nric, password;
            System.out.print("Enter choice: ");
            try{
                choice = scanner.nextInt();
                if (choice == 4) {

                    DataSyncUtil syncUtil = new DataSyncUtil(
                        DataInitializer.getApplicantList(),
                        projectList,
                        DataInitializer.getManagerList(),
                        DataInitializer.getOfficerList(),
                        applicationList,
                        registrationList,
                        withdrawalList,
                        enquiryList
                    );
                    syncUtil.saveAll(); 
                    ClearScreen.clear();
                    System.out.println("Bye Bye!");
                    break;
                    }
                scanner.nextLine();
            }
            catch(InputMismatchException e){
                ClearScreen.clear();
                System.out.println("Please input an integer!");
                scanner.nextLine();
                continue;
            }
            // Input and validate NRIC:
            System.out.print("Enter NRIC: ");
            nric = scanner.nextLine().trim();
            if(!AuthenticationService.validNRIC(nric)){ 
                ClearScreen.clear();
                System.out.println("Invalid NRIC\n");
                BackButton.goBack();
                continue;
            }

            // Input password:
            System.out.print("Enter Password: ");
            password = scanner.nextLine().trim();

            // Check account's availability:
            User user = AuthenticationService.authenticate(nric, password,choice);
            if (user != null) {
                UserSession.setCurrentUser(user);
                ClearScreen.clear();
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
                ClearScreen.clear();
                System.out.println("Invalid credentials. Please try again.\n");
                BackButton.goBack();
            }
        }
        scanner.close();
    }
}
