package controller.officer.helper;

import container.*;
import entity.*;
import utils.ClearScreen;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import controller.officer.template.IOfficerManageEnquiries;

/**
 * Allows officers to manage enquiries submitted for the BTO project they are assigned to.
 * Officers can view and respond to enquiries tied to their current project.
 */
public class OfficerManageEnquiries implements IOfficerManageEnquiries {

    private Officer officer;
    private EnquiryList enquiryList;
    private Scanner scanner;

    /**
     * Constructs the enquiry manager for an officer.
     *
     * @param officer     the logged-in officer
     * @param enquiryList the global list of all enquiries
     */
    public OfficerManageEnquiries(Officer officer, EnquiryList enquiryList) {
        this.officer = officer;
        this.enquiryList = enquiryList;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays all enquiries submitted for the project the officer is currently assigned to.
     * Each enquiry includes applicant details, message, reply, and status.
     */
    public void viewEnquiries() {
        Project assignedProject = officer.getAssignedProject();
        if (assignedProject == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }

        ArrayList<Enquiry> enquiries = enquiryList.getEnquiriesByProject(assignedProject);
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries found for your project.");
            return;
        }

        System.out.println("\n-- Enquiries for Project: " + assignedProject.getProjectName() + " --");
        for (int i = 0; i < enquiries.size(); i++) {
            Enquiry enquiry = enquiries.get(i);
            System.out.println((i + 1) + ") " + enquiry.toString());
        }
    }

    /**
     * Allows the officer to respond to one of the enquiries submitted to their assigned project.
     * Updates the enquiry with a reply and marks it as RESPONDED.
     */
    public void replyToEnquiry() {
        Project assignedProject = officer.getAssignedProject();
        if (assignedProject == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }

        ArrayList<Enquiry> enquiries = enquiryList.getEnquiriesByProject(assignedProject);
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries to reply to.");
            return;
        }

        viewEnquiries();

        System.out.print("Select enquiry number to reply: ");
        int choice;
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            ClearScreen.clear();
            System.out.println("Please input an integer!");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();

        if (choice < 1 || choice > enquiries.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Enquiry selectedEnquiry = enquiries.get(choice - 1);
        System.out.println("Enquiry: " + selectedEnquiry.getMessage());
        System.out.print("Enter your reply: ");
        String reply = scanner.nextLine();

        selectedEnquiry.setReply(reply);
        selectedEnquiry.setStatus(Enquiry.EnquiryStatus.RESPONDED);
        System.out.println("Reply sent successfully.");
    }
}

