package controller.applicant.helper;

import container.*;
import entity.*;
import utils.ClearScreen;
import utils.Colour;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import controller.applicant.template.IApplicantMakeEnquiry;

/**
 * Handles enquiry-related operations for an applicant, including creating,
 * viewing, editing, and deleting enquiries tied to BTO projects.
 */
public class ApplicantMakeEnquiry implements IApplicantMakeEnquiry {

    private Applicant applicant;
    private ProjectList projectList;
    private EnquiryList enquiryList;
    private Scanner sc;

    /**
     * Constructs the enquiry handler for a specific applicant.
     *
     * @param applicant    the logged-in applicant
     * @param projectList  the full list of BTO projects
     * @param enquiryList  the shared enquiry database
     */
    public ApplicantMakeEnquiry(Applicant applicant, ProjectList projectList, EnquiryList enquiryList) {
        this.applicant = applicant;
        this.projectList = projectList;
        this.enquiryList = enquiryList;
        this.sc = new Scanner(System.in);
    }

    /**
     * Allows the applicant to submit a new enquiry for a selected project.
     * The enquiry will be added to the global enquiry list with PENDING status.
     */
    public void makeEnquiry() {
        int index = 1;
        System.out.println(Colour.BLUE + "What project you want to enquire?" + Colour.RESET);
        for (Project project : projectList.getProjectList()) {
            System.out.println(index + ")" + project.getProjectName());
            index++;
        }

        System.out.print(Colour.BLUE + "\nEnter the project number: " + Colour.RESET);
        int choice;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException e) {
            ClearScreen.clear();
            System.out.println(Colour.RED + "Please input an integer!" + Colour.RESET);
            sc.nextLine();
            return;
        }
        sc.nextLine();

        Project project = projectList.getProjectList().get(choice - 1);

        System.out.println(Colour.BLUE + "Enter your message: " + Colour.RESET);
        String message = sc.nextLine();

        Enquiry enquiry = new Enquiry(applicant, project, message);
        enquiryList.addEnquiry(enquiry);

        System.out.println(Colour.GREEN + "\nEnquiry submitted." + Colour.RESET);
    }

    /**
     * Displays all enquiries previously made by the applicant.
     * If none exist, a message is shown.
     */
    public void viewEnquiry() {
        System.out.println(Colour.BLUE + "\n===== My Enquiries =====" + Colour.RESET);
        boolean found = false;
        int index = 1;

        for (Enquiry enquiry : enquiryList.getEnquiries()) {
            if (enquiry.getApplicant().equals(applicant)) {
                System.out.println(index + ") " + enquiry);
                found = true;
                index++;
            }
        }
        if (!found) {
            System.out.println(Colour.RED + "No enquiries found." + Colour.RESET);
        }
    }

    /**
     * Allows the applicant to edit any of their PENDING enquiries.
     * If no editable enquiries are available, a message is shown.
     */
    public void editEnquiry() {
        List<Enquiry> applicantEnquiries = new ArrayList<>();
        int index = 1;

        System.out.println(Colour.BLUE + "\n===== Select Enquiry to Edit =====" + Colour.RESET);
        for (Enquiry enquiry : enquiryList.getEnquiries()) {
            if (enquiry.getApplicant().equals(applicant) && enquiry.getStatus() == Enquiry.EnquiryStatus.PENDING) {
                System.out.println(index + ") " + enquiry);
                applicantEnquiries.add(enquiry);
                index++;
            }
        }

        if (applicantEnquiries.isEmpty()) {
            System.out.println(Colour.RED + "No pending enquiries to edit." + Colour.RESET);
            return;
        }

        System.out.println(Colour.BLUE + "Enter enquiry number to edit: " + Colour.RESET);
        int choice;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException e) {
            ClearScreen.clear();
            System.out.println(Colour.RED + "Please input an integer!" + Colour.RESET);
            sc.nextLine();
            return;
        }
        sc.nextLine();

        if (choice < 1 || choice > applicantEnquiries.size()) {
            System.out.println(Colour.RED + "Invalid choice." + Colour.RESET);
            return;
        }

        System.out.println(Colour.BLUE + "Enter new message: " + Colour.RESET);
        String message = sc.nextLine();
        applicantEnquiries.get(choice - 1).setMessage(message);

        System.out.println(Colour.GREEN + "\nEnquiry updated." + Colour.RESET);
    }

    /**
     * Lets the applicant delete one of their own PENDING enquiries.
     * Once deleted, the enquiry is removed from the system.
     */
    public void deleteEnquiry() {
        List<Enquiry> applicantEnquiries = new ArrayList<>();
        int index = 1;

        System.out.println(Colour.BLUE + "\n===== Select Enquiry to Delete =====" + Colour.RESET);
        for (Enquiry enquiry : enquiryList.getEnquiries()) {
            if (enquiry.getApplicant().equals(applicant) && enquiry.getStatus() == Enquiry.EnquiryStatus.PENDING) {
                System.out.println(index + ") " + enquiry);
                applicantEnquiries.add(enquiry);
                index++;
            }
        }

        if (applicantEnquiries.isEmpty()) {
            System.out.println(Colour.RED + "No pending enquiries to delete." + Colour.RESET);
            return;
        }

        System.out.println(Colour.BLUE + "Enter enquiry number to delete: " + Colour.RESET);
        int choice;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException e) {
            ClearScreen.clear();
            System.out.println(Colour.RED + "Please input an integer!" + Colour.RESET);
            sc.nextLine();
            return;
        }
        sc.nextLine();

        if (choice < 1 || choice > applicantEnquiries.size()) {
            System.out.println(Colour.RED + "Invalid choice." + Colour.RESET);
            return;
        }

        enquiryList.removeEnquiry(applicantEnquiries.get(choice - 1));
        System.out.println(Colour.GREEN + "Enquiry deleted." + Colour.RESET);
    }
}
