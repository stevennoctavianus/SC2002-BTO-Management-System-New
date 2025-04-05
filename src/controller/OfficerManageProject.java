public class OfficerManageProject {
    private Officer officer;
    private ProjectList projectList;

    public OfficerManageProject(Officer officer, ProjectList projectList) {
        this.officer = officer;
        this.projectList = projectList;
    }

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
