package controller;
import entity.User;
import java.util.Scanner;
import utils.ClearScreen;
public class PasswordService {
    public static boolean changePassWord(User u){
        ClearScreen.clear();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter new Password: ");
        String newPassWord = sc.nextLine();
        u.changePassword(newPassWord);
        System.out.println("Password changed successfully.");

        // Invalidate session (force re-login)
        UserSession.setCurrentUser(null);

        System.out.println("You have been logged out. Please login again with your new password.\n");
        return true;
    }
}
