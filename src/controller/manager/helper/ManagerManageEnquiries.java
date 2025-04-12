package controller.manager.helper;
import java.util.ArrayList;
import java.util.Scanner;
import entity.*;
import container.*;
import controller.manager.template.IManagerManageEnquiries;
public class ManagerManageEnquiries implements IManagerManageEnquiries{
    private EnquiryList enquiryList;
    private Scanner scanner;

    public ManagerManageEnquiries(EnquiryList enquiryList) {
        this.enquiryList = enquiryList;
        this.scanner = new Scanner(System.in);
    }

    // View all enquiries (not restricted to managed projects)
    public void viewEnquiry() {
        ArrayList<Enquiry> enquiries = enquiryList.getEnquiries();
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries found.");
            return;
        }

        System.out.println("===== All Enquiries =====");
        for (Enquiry enquiry : enquiries) {
            System.out.println(enquiry);
        }
    }

    // View enquiries only for the project the manager is currently handling
    public void viewHandledProjectEnquiry(Project currentProject) {
        ArrayList<Enquiry> enquiries = enquiryList.getEnquiriesByProject(currentProject);
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries for your project.");
            return;
        }

        System.out.println("===== Enquiries for Managed Project: " + currentProject.getProjectName() + " =====");
        for (Enquiry enquiry : enquiries) {
            System.out.println(enquiry);
        }
    }

    // Reply to pending enquiries for the project the manager is currently handling
    public void replyHandledProjectEnquiry(Project currentProject) {
        ArrayList<Enquiry> pendingEnquiries = enquiryList.getPendingEnquiriesByProject(currentProject);
        if (pendingEnquiries.isEmpty()) {
            System.out.println("No pending enquiries for your project.");
            return;
        }

        System.out.println("Pending Enquiries for Project: " + currentProject.getProjectName());
        for (int i = 0; i < pendingEnquiries.size(); i++) {
            System.out.println((i + 1) + ". " + pendingEnquiries.get(i));
        }

        System.out.print("Select an enquiry to reply (enter number): ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        if (choice < 0 || choice >= pendingEnquiries.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Enquiry enquiry = pendingEnquiries.get(choice);
        System.out.print("Enter your reply: ");
        String reply = scanner.nextLine();
        enquiry.setReply(reply);
        enquiry.setStatus(Enquiry.EnquiryStatus.RESPONDED);

        System.out.println("Enquiry replied successfully.");
    }
}