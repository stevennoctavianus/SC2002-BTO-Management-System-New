package controller;
import container.*;
import entity.*;
public class AuthenticationService {
    public static User authenticate(String nric, String password) {
        // Get lists from DataInitializer (avoid static call on non-static methods)
        for (Applicant a : DataInitializer.getApplicantList().getApplicantList()) {
            if (a.getNric().equals(nric) && a.getPassword().equals(password)) return a;
        }
        for (Officer o : DataInitializer.getOfficerList().getOfficerList()) {
            if (o.getNric().equals(nric) && o.getPassword().equals(password)) return o;
        }
        for (Manager m : DataInitializer.getManagerList().getManagerList()) {
            if (m.getNric().equals(nric) && m.getPassword().equals(password)) return m;
        }
        return null;
    }
}