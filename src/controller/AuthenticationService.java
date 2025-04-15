package controller;
import entity.*;
public class AuthenticationService {
    public static User authenticate(String nric, String password, int choice) {
        // Get lists from DataInitializer (avoid static call on non-static methods)
        if(choice == 1){
            for (Applicant a : DataInitializer.getApplicantList().getApplicantList()) {
                if (a.getNric().equals(nric) && a.getPassword().equals(password)) return a;
            }
        }
        else if(choice == 2){
            for (Officer o : DataInitializer.getOfficerList().getOfficerList()) {
                if (o.getNric().equals(nric) && o.getPassword().equals(password)) return o;
            }
        }
        else{
            for (Manager m : DataInitializer.getManagerList().getManagerList()) {
                if (m.getNric().equals(nric) && m.getPassword().equals(password)) return m;
            }
        }
        return null;
    }
    public static boolean validNRIC(String nric){
        return nric != null && nric.matches("^[ST]\\d{7}[A-Z]$");
    }
}