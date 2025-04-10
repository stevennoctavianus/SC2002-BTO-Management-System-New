package controller;
import controller.applicant.*;
import controller.applicant.template.*;
import container.*;
import entity.*;
import utils.ClearScreen;

import java.util.Scanner;

public class ApplicantController {
    private Applicant applicant;
    private ProjectList projectList;
    private ApplicationList applicationList;
    private EnquiryList enquiryList;
    private WithdrawalList withdrawalList;

    private Scanner scanner;
    private IApplicantMakeEnquiry enquiryHandler;
    private IApplicantViewProjects projectHandler;
    private IApplicantViewApplication applicationHandler;
    private IApplicantMakeWithdrawal withdrawalHandler;

    public ApplicantController(Applicant applicant, ProjectList projectList, ApplicationList applicationList, EnquiryList enquiryList, WithdrawalList withdrawalList) {
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
                    ClearScreen.clear();
                    projectHandler.viewProjectList();
                    break;
                case 2:
                    ClearScreen.clear();
                    projectHandler.applyForProject();
                    break;
                case 3:
                    ClearScreen.clear();
                    applicationHandler.viewApplicationStatus();
                    break;
                case 4:
                    ClearScreen.clear();
                    withdrawalHandler.withdrawApplication();
                    break;
                case 5:
                    ClearScreen.clear();
                    enquiryHandler.makeEnquiry();
                    break;
                case 6:
                    ClearScreen.clear();
                    enquiryHandler.viewEnquiry();
                    break;
                case 7:
                    ClearScreen.clear();
                    enquiryHandler.editEnquiry();
                    break;
                case 8:
                    ClearScreen.clear();
                    enquiryHandler.deleteEnquiry();
                    break;
                case 9:
                    ClearScreen.clear();
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
        } while (choice != 9);
    }
}
