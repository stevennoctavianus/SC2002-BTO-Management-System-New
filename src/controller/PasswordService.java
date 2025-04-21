package controller;

import entity.User;
import java.util.Scanner;
import utils.ClearScreen;
import utils.Colour;
import utils.BackButton;

/**
 * Utility service for handling password changes across different types of users.
 */
public class PasswordService {

    /**
     * Allows a user to change their password through console input.
     * Clears the screen, prompts the user for a new password,
     * updates it once password conditions are met
     * and notifies the user that the change was successful.
     *
     * @param u the {@link User} whose password is being changed
     */
    public static void changePassWord(User u) {
        ClearScreen.clear();
        Scanner sc = new Scanner(System.in);
        
        System.out.println(PasswordOperation.getPasswordRequirements());

        String newPassWord;

        do {
            System.out.print("Enter new password: ");
            newPassWord = sc.nextLine();
            
            if (!PasswordOperation.isPasswordValid(newPassWord)) {
                ClearScreen.clear();
                System.out.print(Colour.RED + "Password does not meet the requirements!\n" + Colour.RESET);
                System.out.println(PasswordOperation.getPasswordRequirements());
            }

        } while (!PasswordOperation.isPasswordValid(newPassWord));

        String confirmPassword;
        do {
            System.out.print("Confirm new password: ");
            confirmPassword = sc.nextLine();

            if (!newPassWord.equals(confirmPassword)) {
                ClearScreen.clear();
                System.out.println(Colour.RED + "\nPasswords do not match!\n" + Colour.RESET);
                System.out.print("Please re-enter new password: ");
                newPassWord = sc.nextLine();
            }
        } while (!newPassWord.equals(confirmPassword));

        System.out.println(Colour.GREEN + "\nPassword is valid!" + Colour.RESET);

        u.changePassword(newPassWord);
        System.out.println(Colour.GREEN + "Password changed successfully! Please login again." + Colour.RESET);
        BackButton.goBack();
    }
}
