package interact;

/**
 * An abstract base class that defines a common contract for role-based user interfaces.
 * All user roles (e.g., Applicant, Officer, Manager) that require a main menu must implement this class.
 */
public abstract class RoleInterface {

    /**
     * Displays the role-specific main menu and handles user interaction.
     * This method must be implemented by each subclass to present relevant options and logic.
     */
    public abstract void showMenu();
}
