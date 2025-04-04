import java.util.ArrayList;

public class Manager extends User {
    private ArrayList<Project> managedProjects;
    private Project activeProject;

    public Manager(){
        this.managedProjects = new ArrayList<>();
    }

    public Manager(String name, String nric, int age, String maritalStatus, String password){
        setName(name);
        setNric(nric);
        setAge(age);
        setMaritalStatus(maritalStatus);
        setPassword(password);
    }

    public Project getActiveProject(){
        return this.activeProject;
    }

    public void setActiveProject(Project project){
        this.activeProject = project;
    }

    public ArrayList<Project> getManagedProjects(){
        return this.managedProjects;
    }

    public void addManagedProject(Project project){
        this.managedProjects.add(project);
    }

}
