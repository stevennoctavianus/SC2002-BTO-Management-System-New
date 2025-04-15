package controller.officer.helper;
import container.*;
import entity.*;
import java.util.Scanner;
import java.util.ArrayList;
import controller.officer.template.IOfficerManageEnquiries;
public class OfficerManageEnquiries implements IOfficerManageEnquiries{
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
    
        ArrayList<Enquiry> pendingEnquiries = enquiryList.getPendingEnquiriesByProject(assignedProject);
        if (pendingEnquiries.isEmpty()) {
            System.out.println("No pending enquiries to reply to.");
            return;
        }
    
        System.out.println("\n-- Pending Enquiries for Project: " + assignedProject.getProjectName() + " --");
        for (int i = 0; i < pendingEnquiries.size(); i++) {
            Enquiry enquiry = pendingEnquiries.get(i);
            System.out.println((i + 1) + ") " + enquiry.toString());
        }
    
        System.out.print("Select enquiry number to reply: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }
    
        if (choice < 1 || choice > pendingEnquiries.size()) {
            System.out.println("Invalid selection.");
            return;
        }
    
        Enquiry selectedEnquiry = pendingEnquiries.get(choice - 1);
        System.out.println("Enquiry: " + selectedEnquiry.getMessage());
        System.out.print("Enter your reply: ");
        String reply = scanner.nextLine();
    
        selectedEnquiry.setReply(reply);
        selectedEnquiry.setStatus(Enquiry.EnquiryStatus.RESPONDED);
        System.out.println("✅ Reply sent successfully.");
    }
}
