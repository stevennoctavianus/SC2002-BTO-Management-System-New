package controller.manager.helper;

import java.util.ArrayList;
import java.util.Scanner;
import entity.*;
import utils.Colour;
import container.*;
import controller.manager.template.IManagerManageEnquiries;

/**
 * Provides functionality for managers to view and reply to enquiries submitted by applicants. Managers can access all enquiries or limit their view to those related to their own project.
 */
public class ManagerManageEnquiries implements IManagerManageEnquiries {

    private EnquiryList enquiryList;
    private Scanner scanner;

    /**
     * Constructs the enquiry handler for a manager using the global enquiry list.
     *
     * @param enquiryList the list of all enquiries submitted in the system
     */
    public ManagerManageEnquiries(EnquiryList enquiryList) {
        this.enquiryList = enquiryList;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays all enquiries in the system, regardless of project ownership.
     */
    public void viewEnquiry() {
        ArrayList<Enquiry> enquiries = enquiryList.getEnquiries();
        if (enquiries.isEmpty()) {
            System.out.println(Colour.RED + "No enquiries found." + Colour.RESET);
            return;
        }

        System.out.println(Colour.BLUE + "===== All Enquiries =====" + Colour.RESET);
        for (Enquiry enquiry : enquiries) {
            System.out.println(enquiry);
        }
    }

    /**
     * Displays all enquiries submitted for the project currently managed by the manager.
     *
     * @param currentProject the manager's active project
     */
    public void viewHandledProjectEnquiry(Project currentProject) {
        ArrayList<Enquiry> enquiries = enquiryList.getEnquiriesByProject(currentProject);
        if (enquiries.isEmpty()) {
            System.out.println(Colour.RED + "No enquiries for your project." + Colour.RESET);
            return;
        }

        System.out.println(Colour.BLUE + "===== Enquiries for Managed Project: " + currentProject.getProjectName() + " =====" + Colour.RESET);
        for (Enquiry enquiry : enquiries) {
            System.out.println(enquiry);
        }
    }

    /**
     * Allows the manager to reply to a pending enquiry for their currently managed project.
     * Marks the enquiry as responded after a reply is submitted.
     *
     * @param currentProject the manager's active project
     */
    public void replyHandledProjectEnquiry(Project currentProject) {
        ArrayList<Enquiry> pendingEnquiries = enquiryList.getPendingEnquiriesByProject(currentProject);
        if (pendingEnquiries.isEmpty()) {
            System.out.println(Colour.RED + "No pending enquiries for your project." + Colour.RESET);
            return;
        }

        System.out.println(Colour.BLUE_UNDERLINED + "Pending Enquiries for Project: " + currentProject.getProjectName() + Colour.RESET);
        for (int i = 0; i < pendingEnquiries.size(); i++) {
            System.out.println((i + 1) + ". " + pendingEnquiries.get(i));
        }

        System.out.println(Colour.BLUE + "Select an enquiry to reply (enter number): " + Colour.RESET);
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println(Colour.RED + "Invalid input." + Colour.RESET);
            return;
        }

        if (choice < 0 || choice >= pendingEnquiries.size()) {
            System.out.println(Colour.RED + "Invalid choice." + Colour.RESET);
            return;
        }

        Enquiry enquiry = pendingEnquiries.get(choice);
        System.out.println(Colour.BLUE + "Enter your reply: " + Colour.RESET);
        String reply = scanner.nextLine();
        enquiry.setReply(reply);
        enquiry.setStatus(Enquiry.EnquiryStatus.RESPONDED);

        System.out.println(Colour.GREEN + "Enquiry replied successfully." + Colour.RESET);
    }
}
