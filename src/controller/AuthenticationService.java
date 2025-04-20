package controller;

import entity.*;

/**
 * Provides authentication and NRIC validation services for all types of users Applicants, Officers, and Managers.
 */
public class AuthenticationService {

    /**
     * Authenticates a user based on their NRIC, password, and login role choice.
     *
     * @param nric    the user's NRIC (e.g., S1234567A)
     * @param password the user's password
     * @param choice   the user's login role (1 = Applicant, 2 = Officer, 3 = Manager)
     * @return the authenticated {@link User} if credentials match, otherwise {@code null}
     */
    public static User authenticate(String nric, String password, int choice) {
        // Get lists from DataInitializer (avoid static call on non-static methods)
        if (choice == 1) {
            for (Applicant a : DataInitializer.getApplicantList().getApplicantList()) {
                if (a.getNric().equals(nric) && a.getPassword().equals(password)) return a;
            }
        } else if (choice == 2) {
            for (Officer o : DataInitializer.getOfficerList().getOfficerList()) {
                if (o.getNric().equals(nric) && o.getPassword().equals(password)) return o;
            }
        } else {
            for (Manager m : DataInitializer.getManagerList().getManagerList()) {
                if (m.getNric().equals(nric) && m.getPassword().equals(password)) return m;
            }
        }
        return null;
    }

    /**
     * Validates the NRIC format using a regular expression.
     * Valid NRICs start with 'S' or 'T', followed by 7 digits and a capital letter.
     *
     * @param nric the NRIC string to validate
     * @return {@code true} if the NRIC is in a valid format, {@code false} otherwise
     */
    public static boolean validNRIC(String nric) {
        return nric != null && nric.matches("^[ST]\\d{7}[A-Z]$");
    }
}
