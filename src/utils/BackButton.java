package utils;
import java.util.Scanner;
public class BackButton {
    public static void goBack(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nPress ENTER to return ...");
        sc.nextLine();
        ClearScreen.clear();
    }
}
