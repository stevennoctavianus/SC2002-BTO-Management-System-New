package controller.officer.helper;
import entity.*;
import java.util.List;
import controller.officer.template.IOfficerGenerateReceipt;
public class OfficerGenerateReceipt implements IOfficerGenerateReceipt{
    private Officer officer;
    private Application application;
    public OfficerGenerateReceipt(Officer officer, Application application) {
        this.officer = officer;
        this.application = application;
    }

    public void generateReceipt() {
        Project assignedProject = officer.getAssignedProject();

        if (assignedProject == null) {
            System.out.println("You are not currently assigned to any project.");
            return;
        }

        if (application == null || application.getApplicationStatus() != Application.ApplicationStatus.BOOKED) {
            System.out.println("No valid BOOKED application provided for receipt generation.");
            return;
        }

        Applicant applicant = application.getApplicant();
        if (applicant == null) {
            System.out.println("Error: Applicant information is missing.");
            return;
        }

        System.out.println("\n=== Flat Booking Receipt for Project: " + assignedProject.getProjectName() + " ===");
        System.out.println("-----------------------------------------");
        System.out.println("Name           : " + applicant.getName());
        System.out.println("NRIC           : " + applicant.getNric());
        System.out.println("Age            : " + applicant.getAge());
        System.out.println("Marital Status : " + applicant.getMaritalStatus());
        System.out.println("Flat Type      : " + application.getFlatType());
        System.out.println("Project Name   : " + assignedProject.getProjectName());
        System.out.println("Neighborhood   : " + assignedProject.getNeighborhood());
        System.out.println("-----------------------------------------");
    }
}
