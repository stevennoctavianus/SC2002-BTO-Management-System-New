package entity;

/**
 * An abstract base class modelling a user in the BTO system.
 * Users have basic identity information such as name, NRIC, age, marital status, and login credentials.
 * This class is extended by the specific user roles of Applicant, Officer, and Manager.
 */
public abstract class User {

    private String name;
    private String nric;
    private String password;
    private int age;

    /**
     * Enum to represent the marital status of a user.
     */
    public enum MaritalStatus {
        SINGLE,
        MARRIED
    }

    private MaritalStatus maritalStatus;

    /**
     * Default constructor.
     */
    public User() {}

    /**
     * Changes the user's password.
     *
     * @param password the new password
     */
    public void changePassword(String password) {
        this.setPassword(password);
    }

    /**
     * Placeholder method for login functionality.
     * Currently unused but can be overridden if needed.
     */
    public void login() {
        // To be implemented if needed
    }

    // --- Getters and Setters ---

    /**
     * Sets the user's name.
     *
     * @param name the user's full name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the user's name.
     *
     * @return the name of the user
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the user's NRIC.
     *
     * @param nric the national identification number
     */
    public void setNric(String nric) {
        this.nric = nric;
    }

    /**
     * Returns the user's NRIC.
     *
     * @return the NRIC
     */
    public String getNric() {
        return this.nric;
    }

    /**
     * Sets the user's password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user's password.
     *
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the user's age.
     *
     * @param age the user's age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns the user's age.
     *
     * @return the age
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Sets the user's marital status from a string input.
     * The input is case-insensitive and will be matched against enum values.
     *
     * @param input the marital status as a string (e.g., "single" or "married")
     */
    public void setMaritalStatus(String input) {
        try {
            this.maritalStatus = MaritalStatus.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid marital status: " + input);
            this.maritalStatus = null; // Can also default to MaritalStatus.SINGLE if preferred
        }
    }

    /**
     * Returns the user's marital status.
     *
     * @return the marital status
     */
    public MaritalStatus getMaritalStatus() {
        return this.maritalStatus;
    }
}

