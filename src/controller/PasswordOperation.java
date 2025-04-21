package controller;

import java.util.regex.Pattern;
import utils.Colour;

/**
 * Utility class for validating password strength and displaying password requirements.
 * <p>
 * This class uses regular expressions to check for the presence of uppercase, lowercase,
 * numeric, and special characters in a password.
 */
public class PasswordOperation {

    private static final Pattern HAS_UPPER = Pattern.compile("[A-Z]");
    private static final Pattern HAS_LOWER = Pattern.compile("[a-z]");
    private static final Pattern HAS_NUMBER = Pattern.compile("\\d");
    private static final Pattern HAS_SPECIAL = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");

    /**
     * Validates whether a given password meets the defined strength criteria:
     * <ul>
     *     <li>At least 8 characters long</li>
     *     <li>Contains an uppercase letter</li>
     *     <li>Contains a lowercase letter</li>
     *     <li>Contains a digit</li>
     *     <li>Contains a special character</li>
     * </ul>
     *
     * @param password the password string to validate
     * @return true if the password meets all criteria; false otherwise
     */
    public static boolean isPasswordValid(String password) {
        if (password == null || password.length() < 8) return false;

        boolean hasUpper = HAS_UPPER.matcher(password).find();
        boolean hasLower = HAS_LOWER.matcher(password).find();
        boolean hasNumber = HAS_NUMBER.matcher(password).find();
        boolean hasSpecial = HAS_SPECIAL.matcher(password).find();

        return hasUpper && hasLower && hasNumber && hasSpecial;
    }

    /**
     * Returns a user-friendly string describing the password rules.
     *
     * @return the password requirement message formatted with colour for emphasis
     */
    public static String getPasswordRequirements() {
        return Colour.BLUE_UNDERLINED + "Password Requirements:" + Colour.RESET + "\n" +
                "- Be at least 8 characters long\n" +
                "- Contain at least one uppercase letter\n" +
                "- Contain at least one lowercase letter\n" +
                "- Contain at least one number\n" +
                "- Contain at least one special character\n";
    }
}

