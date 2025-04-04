import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        DataInitializer.loadData(); // Load users from CSV
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
                
                if (user instanceof Applicant) new ApplicantUI().showMenu();
                else if (user instanceof Officer) new OfficerUI().showMenu();
                else if (user instanceof Manager) new ManagerUI().showMenu();
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
        scanner.close();
    }
}