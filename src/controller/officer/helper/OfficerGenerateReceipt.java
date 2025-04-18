package controller.officer.helper;
import entity.*;
import java.util.List;

import container.ApplicationList;
import controller.officer.template.IOfficerGenerateReceipt;
public class OfficerGenerateReceipt implements IOfficerGenerateReceipt{
    private Officer officer;
    protected ApplicationList applicationList;

    public OfficerGenerateReceipt(Officer officer, ApplicationList applicationList) {
        this.officer = officer;
        this.applicationList = applicationList;
    }

    public void generateReceipt() {
        Project assignedProject = officer.getAssignedProject();

        if (assignedProject == null) {
            System.out.println("You are not currently assigned to any project.");
            return;
        }

        List<Application> applications = applicationList.getApplicationsByProject(assignedProject);
        boolean hasReceipt = false;

        System.out.println("\n=== Flat Booking Receipt for Project: " + assignedProject.getProjectName() + " ===");

        for (Application application : applications) {
            if (application.getApplicationStatus() == Application.ApplicationStatus.BOOKED) {
                Applicant applicant = application.getApplicant();

                System.out.println("-----------------------------------------");
                System.out.println("Name           : " + applicant.getName());
                System.out.println("NRIC           : " + applicant.getNric());
                System.out.println("Age            : " + applicant.getAge());
                System.out.println("Marital Status : " + applicant.getMaritalStatus());
                System.out.println("Flat Type      : " + application.getFlatType());
                System.out.println("Project Name   : " + assignedProject.getProjectName());
                System.out.println("Neighborhood   : " + assignedProject.getNeighborhood());
                System.out.println("-----------------------------------------");
                hasReceipt = true;
            }
        }

        if (!hasReceipt) {
            System.out.println("No BOOKED applications found under your project.");
        }
    }
}
