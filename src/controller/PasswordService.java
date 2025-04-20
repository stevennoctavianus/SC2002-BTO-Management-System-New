package controller;

import entity.User;
import java.util.Scanner;
import utils.ClearScreen;

/**
 * Utility service for handling password changes across different types of users.
 */
public class PasswordService {

    /**
     * Allows a user to change their password through console input.
     * Clears the screen, prompts the user for a new password, updates it,
     * and notifies the user that the change was successful.
     *
     * @param u the {@link User} whose password is being changed
     */
    public static void changePassWord(User u) {
        ClearScreen.clear();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter new Password: ");
        String newPassWord = sc.nextLine();
        u.changePassword(newPassWord);
        System.out.println("Change Password Successfully! Please log in again");
    }
}
