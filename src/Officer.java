import java.util.ArrayList;

public class Officer extends Applicant {
    private Project assignedProject;
    private ArrayList<Registration> registeredProject;
    private boolean eligibleForRegistration;

    public Officer(){
        this.registeredProject = new ArrayList<>();
    }

    public Officer(String name, String nric, int age, String maritalStatus, String password){
        setName(name);
        setNric(nric);
        setAge(age);
        setMaritalStatus(maritalStatus);
        setPassword(password);
    }

    //Getter and Setter
    public Project getAssignedProject() {
        return assignedProject;
    }
    
    public void setAssignedProject(Project assignedProject) {
        this.assignedProject = assignedProject;
    }
    
    public ArrayList<Registration> getRegisteredProject() {
        return registeredProject;
    }
    
    public void setRegisteredProject(ArrayList<Registration> registeredProject) {
        this.registeredProject = registeredProject;
    }
    
    public void addRegisteredProject(Registration registration) {
        if (this.registeredProject == null) {
            this.registeredProject = new ArrayList<>();
        }
        this.registeredProject.add(registration);
    }
    
    public boolean isEligibleForRegistration() {
        return eligibleForRegistration;
    }
    
    public void setEligibleForRegistration(boolean eligibleForRegistration) {
        this.eligibleForRegistration = eligibleForRegistration;
    }
    

}
