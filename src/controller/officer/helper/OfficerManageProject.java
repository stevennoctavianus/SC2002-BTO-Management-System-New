package controller.officer.helper;

import container.*;
import entity.*;
import controller.officer.template.IOfficerManageProject;

/**
 * Provides functionality for an officer to view details of their assigned BTO project.
 * Implements the {@link IOfficerManageProject} interface.
 */
public class OfficerManageProject implements IOfficerManageProject {

    private Officer officer;
    private ProjectList projectList;

    /**
     * Constructs the project management handler for an officer.
     *
     * @param officer     the officer currently logged in
     * @param projectList the list of all available projects
     */
    public OfficerManageProject(Officer officer, ProjectList projectList) {
        this.officer = officer;
        this.projectList = projectList;
    }

    /**
     * Displays detailed information about the project currently assigned to the officer.
     * If the officer is not assigned to any project, a message is shown instead.
     */
    public void viewProjectDetails() {
        Project assignedProject = officer.getAssignedProject();

        if (assignedProject == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }

        System.out.println("\n===== Project Details =====");
        System.out.println(assignedProject.toString());
    }
}

