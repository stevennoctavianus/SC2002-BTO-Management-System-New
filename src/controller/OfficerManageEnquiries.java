package controller;
import container.*;
import entity.*;
import java.util.Scanner;
import java.util.ArrayList;

public class OfficerManageEnquiries {
    private Officer officer;
    private EnquiryList enquiryList;
    private Scanner scanner;

    public OfficerManageEnquiries(Officer officer, EnquiryList enquiryList) {
        this.officer = officer;
        this.enquiryList = enquiryList;
        this.scanner = new Scanner(System.in);
    }

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
        int choice = scanner.nextInt();
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
