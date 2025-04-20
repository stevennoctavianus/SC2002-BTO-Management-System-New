package controller.officer.helper;

import container.*;
import entity.*;
import utils.ClearScreen;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import controller.officer.template.IOfficerMakeEnquiry;
import controller.applicant.helper.ApplicantMakeEnquiry;

/**
 * Allows officers to submit, view, edit, and delete enquiries about BTO projects.
 * Inherits functionality from but excludes projects
 * that the officer is currently managing.
 */
public class OfficerMakeEnquiry extends ApplicantMakeEnquiry implements IOfficerMakeEnquiry {

    private Officer officer;
    private ProjectList projectList;
    private EnquiryList enquiryList;
    private Scanner sc;

    /**
     * Constructs an enquiry handler for officers.
     *
     * @param officer      the officer making enquiries
     * @param projectList  the list of all projects
     * @param enquiryList  the global list of enquiries
     */
    public OfficerMakeEnquiry(Officer officer, ProjectList projectList, EnquiryList enquiryList) {
        super(officer, projectList, enquiryList); // Officer extends Applicant
        this.officer = officer;
        this.projectList = projectList;
        this.enquiryList = enquiryList;
        this.sc = new Scanner(System.in);
    }

    /**
     * Allows the officer to submit an enquiry, excluding projects they are managing.
     */
    @Override
    public void makeEnquiry() {
        List<Project> allProjects = projectList.getProjectList();
        ArrayList<Project> managedProjects = officer.getManagedProjects();

        List<Project> availableProjects = allProjects.stream()
            .filter(p -> !managedProjects.contains(p))
            .toList();

        if (availableProjects.isEmpty()) {
            System.out.println("You cannot make enquiries because you are managing all the projects.");
            return;
        }

        int index = 1;
        System.out.println("Which project do you want to enquire?");
        for (Project project : availableProjects) {
            System.out.println(index + ") " + project.getProjectName());
            index++;
        }

        System.out.print("Enter project number: ");
        int choice;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException e) {
            ClearScreen.clear();
            System.out.println("Please input an integer!");
            sc.nextLine();
            return;
        }
        sc.nextLine();

        if (choice < 1 || choice > availableProjects.size()) {
            System.out.println("Invalid project choice.");
            return;
        }

        Project selectedProject = availableProjects.get(choice - 1);

        System.out.print("Enter your message: ");
        String message = sc.nextLine();

        Enquiry enquiry = new Enquiry(officer, selectedProject, message);
        enquiryList.addEnquiry(enquiry);

        System.out.println("Success! Your enquiry has been submitted.");
    }

    /**
     * Displays all enquiries submitted by the officer for projects they are not managing.
     */
    @Override
    public void viewEnquiry() {
        System.out.println("\n===== My Enquiries (Excluding Managed Projects) =====");
        boolean found = false;
        int index = 1;

        for (Enquiry enquiry : enquiryList.getEnquiries()) {
            if (enquiry.getApplicant().equals(officer) &&
                !officer.getManagedProjects().contains(enquiry.getProject())) {
                System.out.println(index + ") " + enquiry);
                found = true;
                index++;
            }
        }

        if (!found) {
            System.out.println("No enquiries found.");
        }
    }

    /**
     * Utility method to check if the officer is managing the specified project.
     *
     * @param project the project to check
     * @return true if the officer is managing the project, false otherwise
     */
    public boolean isManagingProject(Project project) {
        for (Registration reg : officer.getRegistrations()) {
            if (reg.getProject().equals(project) && reg.getStatus() == Registration.RegistrationStatus.APPROVED) {
                return true;
            }
        }
        return false;
    }

    /**
     * Allows the officer to edit a pending enquiry submitted to a project they do not manage.
     */
    @Override
    public void editEnquiry() {
        List<Enquiry> editableEnquiries = new ArrayList<>();
        int index = 1;

        System.out.println("\n===== Select Enquiry to Edit (Excluding Managed Projects) =====");
        for (Enquiry enquiry : enquiryList.getEnquiries()) {
            if (enquiry.getApplicant().equals(officer) &&
                enquiry.getStatus() == Enquiry.EnquiryStatus.PENDING &&
                !officer.isManagingProject(enquiry.getProject())) {
                System.out.println(index + ") " + enquiry);
                editableEnquiries.add(enquiry);
                index++;
            }
        }

        if (editableEnquiries.isEmpty()) {
            System.out.println("No pending enquiries to edit.");
            return;
        }

        System.out.print("Enter enquiry number to edit: ");
        int choice;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException e) {
            ClearScreen.clear();
            System.out.println("Please input an integer!");
            sc.nextLine();
            return;
        }
        sc.nextLine();

        if (choice < 1 || choice > editableEnquiries.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        System.out.print("Enter new message: ");
        String message = sc.nextLine();
        editableEnquiries.get(choice - 1).setMessage(message);

        System.out.println("Enquiry updated.");
    }

    /**
     * Allows the officer to delete a pending enquiry they submitted to a project they are not managing.
     */
    @Override
    public void deleteEnquiry() {
        List<Enquiry> deletableEnquiries = new ArrayList<>();
        int index = 1;

        System.out.println("\n===== Select Enquiry to Delete (Excluding Managed Projects) =====");
        for (Enquiry enquiry : enquiryList.getEnquiries()) {
            if (enquiry.getApplicant().equals(officer) &&
                enquiry.getStatus() == Enquiry.EnquiryStatus.PENDING &&
                !officer.getManagedProjects().contains(enquiry.getProject())) {

                System.out.println(index + ") " + enquiry);
                deletableEnquiries.add(enquiry);
                index++;
            }
        }

        if (deletableEnquiries.isEmpty()) {
            System.out.println("No pending enquiries to delete.");
            return;
        }

        System.out.print("Enter enquiry number to delete: ");
        int choice;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException e) {
            ClearScreen.clear();
            System.out.println("Please input an integer!");
            sc.nextLine();
            return;
        }
        sc.nextLine();

        if (choice < 1 || choice > deletableEnquiries.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        enquiryList.removeEnquiry(deletableEnquiries.get(choice - 1));
        System.out.println("Enquiry deleted.");
    }
}

