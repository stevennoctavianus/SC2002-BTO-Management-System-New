package interact;
import container.*;
import entity.*;
import controller.*;
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
        
        projectList = new ProjectList("ProjectList.csv", managerList, officerList);
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("Welcome to the BTO Management System");
            System.out.println("1) Applicant Login");
            System.out.println("2) Officer Login");
            System.out.println("3) Manager Login");
            System.out.println("4) Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            if (choice == 4) break;
            
            System.out.print("Enter NRIC: ");
            String nric = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();
            
            User user = AuthenticationService.authenticate(nric, password);
            if (user != null) {
                UserSession.setCurrentUser(user);
                
                if (user instanceof Officer) {
                    new OfficerController((Officer) user, projectList, applicationList, enquiryList, withdrawalList, registrationList).showMenu();
                } else if (user instanceof Applicant) {
                    new ApplicantController((Applicant) user, projectList, applicationList, enquiryList, withdrawalList).showMenu();
                } else if (user instanceof Manager) {
                    new ManagerUI().showMenu();
                }                
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
        scanner.close();
    }
}
