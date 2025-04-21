package utils;

import java.util.Scanner;

/**
 * Utility class that provides a simple way to pause the program and wait for user input
 * before returning to the previous menu.
 * Often used to simulate a "Back" button in console-based navigation flows.
 */
public class BackButton {

    /**
     * Waits for the user to press ENTER before continuing.
     * This is commonly used after displaying messages or completing actions.
     */
    public static void goBack() {
        Scanner sc = new Scanner(System.in);
        System.out.println(Colour.BLUE + "\nPress ENTER to return ..." + Colour.RESET);
        sc.nextLine();
        ClearScreen.clear();
    }
}

