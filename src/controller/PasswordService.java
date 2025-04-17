package controller;
import entity.User;
import java.util.Scanner;
import utils.ClearScreen;
public class PasswordService {
    public static void changePassWord(User u){
        ClearScreen.clear();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter new Password: ");
        String newPassWord = sc.nextLine();
        u.changePassword(newPassWord);
        System.out.println("Change Password Successfully! Please log in again");
    }
}
