import java.util.Scanner;

public class OfficerController {
    private Officer officer;
    private ProjectList projectList;
    private ApplicationList applicationList;
    private EnquiryList enquiryList;
    private WithdrawalList withdrawalList;
    private RegistrationList registrationList;

    private Scanner scanner;

    private OfficerViewProjects projectHandler;
    private OfficerMakeEnquiry enquiryHandler;
    private ApplicantViewApplication applicationHandler;
    private ApplicantMakeWithdrawal withdrawalHandler;

    private OfficerRegistration registrationHandler;
    private OfficerManageEnquiries manageEnquiriesHandler;
    private OfficerManageProject manageProjectHandler;
    private OfficerManageApplication manageApplicationHandler;
    private OfficerGenerateReceipt receiptHandler;

    public OfficerController(Officer officer, ProjectList projectList,
                             ApplicationList applicationList, EnquiryList enquiryList,
                             WithdrawalList withdrawalList, RegistrationList registrationList) {
        this.officer = officer;
        this.projectList = projectList;
        this.applicationList = applicationList;
        this.enquiryList = enquiryList;
        this.withdrawalList = withdrawalList;
        this.registrationList = registrationList;
        this.scanner = new Scanner(System.in);

        // Officer can still do applicant-like tasks
        this.projectHandler = new OfficerViewProjects(officer, projectList, applicationList, registrationList);
        this.enquiryHandler = new OfficerMakeEnquiry(officer, projectList, enquiryList);
        this.applicationHandler = new ApplicantViewApplication(officer, applicationList);
        this.withdrawalHandler = new ApplicantMakeWithdrawal(officer, withdrawalList, applicationList);

        // Officer role-specific functionality
        this.registrationHandler = new OfficerRegistration(officer, projectList, registrationList);
        this.manageEnquiriesHandler = new OfficerManageEnquiries(officer, enquiryList);
        this.manageProjectHandler = new OfficerManageProject(officer, projectList);
        this.manageApplicationHandler = new OfficerManageApplication(officer, applicationList);
        this.receiptHandler = new OfficerGenerateReceipt(officer);
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n===== Officer Dashboard =====");
            System.out.println("1) Apply for BTO Project (as Applicant)");
            System.out.println("2) Register for Project (as Officer)");
            System.out.println("3) Manage Officer's Job");
            System.out.println("4) Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showApplicantMenu();
                    break;
                case 2:
                    showOfficerRegistrationMenu();
                    break;
                case 3:
                    showOfficerManagementMenu();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }

    private void showApplicantMenu() {
        int choice;
        do {
            System.out.println("\n-- Applicant Mode (Officer) --");
            System.out.println("1) View BTO Project List");
            System.out.println("2) Apply for a BTO Project");
            System.out.println("3) View My Application");
            System.out.println("4) Withdraw Application");
            System.out.println("5) Submit an Enquiry");
            System.out.println("6) View My Enquiries");
            System.out.println("7) Edit an Enquiry");
            System.out.println("8) Delete an Enquiry");
            System.out.println("9) Back");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: projectHandler.viewProjectList(); break;
                case 2: projectHandler.applyForProject(); break;
                case 3: applicationHandler.viewApplicationStatus(); break;
                case 4: withdrawalHandler.withdrawApplication(); break;
                case 5: enquiryHandler.makeEnquiry(); break;
                case 6: enquiryHandler.viewEnquiry(); break;
                case 7: enquiryHandler.editEnquiry(); break;
                case 8: enquiryHandler.deleteEnquiry(); break;
                case 9: return;
                default: System.out.println("Invalid choice.");
            }
        } while (true);
    }

    private void showOfficerRegistrationMenu() {
        int choice;
        do {
            System.out.println("\n-- Officer Registration --");
            System.out.println("1) Register as Officer");
            System.out.println("2) View Registration Status");
            System.out.println("3) Back");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: registrationHandler.registerForProject(); break;
                case 2: registrationHandler.viewRegistrationStatus(); break;
                case 3: return;
                default: System.out.println("Invalid choice.");
            }
        } while (true);
    }

    private void showOfficerManagementMenu() {
        if (officer.getAssignedProject() == null) {
            System.out.println("You do not have an active project. Cannot manage officer responsibilities.");
            return;
        }

        int choice;
        do {
            System.out.println("\n-- Officer Project Management --");
            System.out.println("1) View Enquiries");
            System.out.println("2) Reply Enquiry");
            System.out.println("3) View Project Details");
            System.out.println("4) View Applications");
            System.out.println("5) Update Applicant Profile");
            System.out.println("6) Generate Receipt");
            System.out.println("7) Back");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: manageEnquiriesHandler.viewEnquiries(); break;
                case 2: manageEnquiriesHandler.replyToEnquiry(); break;
                case 3: manageProjectHandler.viewProjectDetails(); break;
                case 4: manageApplicationHandler.viewApplications(); break;
                case 5: manageApplicationHandler.updateApplicationStatus(); break;
                case 6: receiptHandler.generateReceipt(); break;
                case 10: return;
                default: System.out.println("Invalid choice.");
            }
        } while (true);
    }
}
