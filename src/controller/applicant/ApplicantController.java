package controller.applicant;

import controller.applicant.helper.*;
import controller.applicant.template.*;
import container.*;
import entity.*;
import utils.BackButton;
import utils.ClearScreen;
import controller.PasswordService;
import controller.FilterMenu;
import controller.UserSession;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Controls the main dashboard interaction logic for an applicant user in the BTO system.
 * <p>
 * Delegates features such as viewing and applying for projects, handling enquiries,
 * withdrawing applications, and managing user settings.
 */
public class ApplicantController {

    private Applicant applicant;
    private ProjectList projectList;
    private ApplicationList applicationList;
    private EnquiryList enquiryList;
    private WithdrawalList withdrawalList;

    private Scanner scanner;

    // Interfaces for handling individual use cases
    private IApplicantMakeEnquiry enquiryHandler;
    private IApplicantViewProjects projectHandler;
    private IApplicantViewApplication applicationHandler;
    private IApplicantMakeWithdrawal withdrawalHandler;

    /**
     * Constructs a controller for managing the interactions of an applicant.
     *
     * @param applicant        the logged-in applicant
     * @param projectList      the list of all BTO projects
     * @param applicationList  the list of all applications
     * @param enquiryList      the list of all enquiries
     * @param withdrawalList   the list of all withdrawals
     */
    public ApplicantController(Applicant applicant, ProjectList projectList,
                               ApplicationList applicationList, EnquiryList enquiryList,
                               WithdrawalList withdrawalList) {
        this.applicant = applicant;
        this.projectList = projectList;
        this.applicationList = applicationList;
        this.enquiryList = enquiryList;
        this.withdrawalList = withdrawalList;

        this.scanner = new Scanner(System.in);

        this.enquiryHandler = new ApplicantMakeEnquiry(applicant, projectList, enquiryList);
        this.projectHandler = new ApplicantViewProjects(applicant, projectList, applicationList);
        this.applicationHandler = new ApplicantViewApplication(applicant, applicationList);
        this.withdrawalHandler = new ApplicantMakeWithdrawal(applicant, withdrawalList, applicationList);
    }

    /**
     * Displays and manages the main menu options for the applicant dashboard.
     * <p>
     * Users can view/apply for projects, manage their application/enquiries,
     * update filters or password, or log out.
     */
    public void showMenu() {
        int choice = 0;
        do {
            System.out.println("            +------------------------------------------------+");
            System.out.println("            |               Applicant Dashboard              |");
            System.out.println("            +------------------------------------------------+");
            System.out.println("            |  1) View BTO Project List                      |");
            System.out.println("            |  2) Apply for a BTO Project                    |");
            System.out.println("            |  3) View My Application                        |");
            System.out.println("            |  4) Withdraw Application                       |");
            System.out.println("            |  5) Submit an Enquiry                          |");
            System.out.println("            |  6) View My Enquiries                          |");
            System.out.println("            |  7) Edit an Enquiry                            |");
            System.out.println("            |  8) Delete an Enquiry                          |");
            System.out.println("            |  9) Manage Filters                             |");
            System.out.println("            | 10) Change Password                            |");
            System.out.println("            | 11) Logout                                     |");
            System.out.println("            +------------------------------------------------+\n\n");

            System.out.print("Enter your choice: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                ClearScreen.clear();
                System.out.println("Please input an integer!");
                BackButton.goBack();
                scanner.nextLine();
                continue;
            }
            scanner.nextLine(); // consume newline
            ClearScreen.clear();

            switch (choice) {
                case 1:
                    projectHandler.viewProjectList();
                    break;
                case 2:
                    projectHandler.applyForProject();
                    break;
                case 3:
                    applicationHandler.viewApplicationStatus();
                    break;
                case 4:
                    withdrawalHandler.withdrawApplication();
                    break;
                case 5:
                    enquiryHandler.makeEnquiry();
                    break;
                case 6:
                    enquiryHandler.viewEnquiry();
                    break;
                case 7:
                    enquiryHandler.editEnquiry();
                    break;
                case 8:
                    enquiryHandler.deleteEnquiry();
                    break;
                case 9:
                    new FilterMenu().manageFilters();
                    ClearScreen.clear();
                    break;
                case 10:
                    PasswordService.changePassWord(applicant);
                    return;
                case 11:
                    System.out.println("Logging out...");
                    UserSession.logout();
                    BackButton.goBack();
                    return;
                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }

            BackButton.goBack();
        } while (choice != 11);
    }
}
