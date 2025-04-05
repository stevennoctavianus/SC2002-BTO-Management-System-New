package container;
import entity.*;
import java.util.ArrayList;

public class RegistrationList {
    private ArrayList<Registration> registrations;

    public RegistrationList() {
        this.registrations = new ArrayList<>();
    }

    public void addRegistration(Registration registration) {
        registrations.add(registration);
    }

    public ArrayList<Registration> getRegistrations() {
        return registrations;
    }

    public ArrayList<Registration> getRegistrationsByOfficer(Officer officer) {
        ArrayList<Registration> result = new ArrayList<>();
        for (Registration reg : registrations) {
            if (reg.getOfficer().equals(officer)) {
                result.add(reg);
            }
        }
        return result;
    }

    public Registration getRegistrationByOfficerAndProject(Officer officer, Project project) {
        for (Registration reg : registrations) {
            if (reg.getOfficer().equals(officer) && reg.getProject().equals(project)) {
                return reg;
            }
        }
        return null;
    }
}
