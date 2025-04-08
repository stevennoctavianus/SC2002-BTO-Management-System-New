package controller;
import java.util.Scanner;
import entity.*;
import container.*;

public class ManagerController {
    private Manager manager;
    private ProjectList projectList;
    private ApplicationList applicationList;
    private RegistrationList registrationList;
    private WithdrawalList withdrawalList;
    private EnquiryList enquiryList;

    private ManagerManageProject projectManager;
    private ManagerManageApplication applicationManager;
    private ManagerManageRegistration registrationManager;
    private ManagerManageWithdrawal withdrawalManager;
    private ManagerGenerateReport reportGenerator;
    private ManagerManageEnquiries enquiryManager;

    public ManagerController(Manager manager, ProjectList projectList, ApplicationList applicationList,
                             RegistrationList registrationList, WithdrawalList withdrawalList, EnquiryList enquiryList) {
        this.manager = manager;
        this.projectList = projectList;
        this.applicationList = applicationList;
        this.registrationList = registrationList;
        this.withdrawalList = withdrawalList;
        this.enquiryList = enquiryList;

        this.projectManager = new ManagerManageProject(manager, projectList, applicationList, registrationList);
        this.applicationManager = new ManagerManageApplication(applicationList);
        this.registrationManager = new ManagerManageRegistration(registrationList);
        this.withdrawalManager = new ManagerManageWithdrawal(withdrawalList, applicationList);
        this.reportGenerator = new ManagerGenerateReport(applicationList);
        this.enquiryManager = new ManagerManageEnquiries(enquiryList);
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Manager Menu ===");
            System.out.println("1. Create Project");
            System.out.println("2. Edit Project");
            System.out.println("3. Delete Project");
            System.out.println("4. Change Project Visibility");
            System.out.println("5. View Own Projects");
            System.out.println("6. View All Created Projects");
            System.out.println("7. View Applicant Applications");
            System.out.println("8. Manage Applicant Application");
            System.out.println("9. View Officer Registrations");
            System.out.println("10. Manage Officer Registrations");
            System.out.println("11. Manage Applicant Withdrawal");
            System.out.println("12. Generate Report");
            System.out.println("13. View All Enquiries");
            System.out.println("14. View Enquiries for Managed Projects");
            System.out.println("15. Reply to Enquiry");
            System.out.println("16. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: projectManager.createProject(); break;
                case 2: projectManager.editProject(); break;
                case 3: projectManager.deleteProject(); break;
                case 4: projectManager.changeProjectVisibility(); break;
                case 5: projectManager.viewOwnProject(); break;
                case 6: projectManager.viewAllCreatedProject(); break;
                case 7: applicationManager.viewApplication(manager.getActiveProject()); break;
                case 8: applicationManager.manageApplication(manager.getActiveProject()); break;
                case 9: registrationManager.viewRegistration(); break;
                case 10:
                    Project activeProject = manager.getActiveProject();
                    if (activeProject == null) {
                        System.out.println("You are not currently handling any active project.");
                    } else {
                        registrationManager.manageRegistration(activeProject);
                    }
                    break;
                case 11: withdrawalManager.manageWithdrawal(manager.getActiveProject()); break;
                case 12: reportGenerator.generateReport(); break;
                case 13: enquiryManager.viewEnquiry(); break;
                case 14: enquiryManager.viewHandledProjectEnquiry(manager.getActiveProject()); break;
                case 15: enquiryManager.replyHandledProjectEnquiry(manager.getActiveProject()); break;
                case 16:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}