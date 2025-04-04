import java.util.ArrayList;
import java.util.Scanner;

public class OfficerRegistration {
    private Officer officer;
    private ProjectList projectList;
    private RegistrationList registrationList;
    private Scanner scanner;

    public OfficerRegistration(Officer officer, ProjectList projectList, RegistrationList registrationList) {
        this.officer = officer;
        this.projectList = projectList;
        this.registrationList = registrationList;
        this.scanner = new Scanner(System.in);
    }

    public void registerForProject() {
        // If officer is already assigned or has a pending/approved registration
        if (officer.getAssignedProject() != null || !officer.getPendingRegistrations().isEmpty() || !officer.getApprovedRegistrations().isEmpty()) {
            System.out.println("You are already managing or registering for a project. Cannot register again.");
            return;
        }

        // Filter available projects
        ArrayList<Project> availableProjects = new ArrayList<>();
        for (Project project : projectList.getProjectList()) {
            boolean hasSlot = project.getMaxOfficer() > project.getOfficers().size();
            boolean hasNotApplied = !project.hasApplicant(officer);

            if (hasSlot && hasNotApplied) {
                availableProjects.add(project);
            }
        }

        if (availableProjects.isEmpty()) {
            System.out.println(" No projects are currently available for registration.");
            return;
        }

        // Display and let officer choose
        System.out.println("\nAvailable Projects for Officer Registration:");
        for (int i = 0; i < availableProjects.size(); i++) {
            System.out.println((i + 1) + ") " + availableProjects.get(i).getProjectName());
        }

        System.out.print("Select a project to register (0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > availableProjects.size()) {
            System.out.println("Registration cancelled.");
            return;
        }

        Project selectedProject = availableProjects.get(choice - 1);
        Registration registration = new Registration(officer, selectedProject);
        registrationList.addRegistration(registration);
        officer.addRegistration(registration);

        System.out.println("Registration submitted successfully for project: " + selectedProject.getProjectName());
    }

    public void viewRegistrationStatus() {
        ArrayList<Registration> registrations = officer.getRegistrations();
        if (registrations.isEmpty()) {
            System.out.println("No registration history found.");
            return;
        }

        System.out.println("\nRegistration History:");
        for (Registration r : registrations) {
            System.out.println("- Project: " + r.getProject().getProjectName() +
                               " | Status: " + r.getStatus());
        }
    }
}
