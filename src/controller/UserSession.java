package controller;

import entity.*;

/**
 * A simple session manager for tracking the currently logged-in user during runtime.
 * This is used globally to determine user identity and role across different modules.
 */
public class UserSession {

    /**
     * The current user of the system (could be an Applicant, Officer, or Manager).
     */
    private static User currentUser;

    /**
     * Sets the current user for the session.
     *
     * @param user the {@link User} object representing the logged-in user
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Retrieves the user currently logged into the session.
     *
     * @return the {@link User} object representing the current session user, or {@code null} if no user is logged in
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Logs the current user out by clearing the session.
     */
    public static void logout() {
        currentUser = null;
    }
}

