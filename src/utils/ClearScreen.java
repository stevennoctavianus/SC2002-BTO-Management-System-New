package utils;

/**
 * Utility class to simulate clearing the console screen.
 * This uses ANSI escape codes, which work in most Unix-based terminals.
 * May not be effective in some Windows environments or IDE consoles.
 */
public class ClearScreen {

    /**
     * Clears the console output by printing ANSI escape sequences.
     * This is useful for refreshing the interface in a text-based UI.
     */
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

