import java.util.Scanner;

public class ApplicantUI extends RoleInterface {
    private ApplicantController applicantController;
    private Scanner scanner;

    public ApplicantUI(Applicant applicant) {
        this.applicantController = new ApplicantController(applicant);
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n===== Applicant Dashboard =====");
            System.out.println("1) View BTO Project List");
            System.out.println("2) Apply for a BTO Project");
            System.out.println("3) View My Application");
            System.out.println("4) Withdraw Application");
            System.out.println("5) Submit an Enquiry");
            System.out.println("6) View My Enquiries");
            System.out.println("7) Edit an Enquiry");
            System.out.println("8) Delete an Enquiry");
            System.out.println("9) Logout");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    applicantController.viewProjectList();
                    break;
                case 2:
                    applicantController.applyForProject();
                    break;
                case 3:
                    applicantController.viewApplication();
                    break;
                case 4:
                    applicantController.withdrawApplication();
                    break;
                case 5:
                    applicantController.submitEnquiries();
                    break;
                case 6:
                    applicantController.viewEnquiries();
                    break;
                case 7:
                    applicantController.editEnquiries();
                    break;
                case 8:
                    applicantController.deleteEnquiries();
                    break;
                case 9:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
        } while (choice != 9);
    }
}
