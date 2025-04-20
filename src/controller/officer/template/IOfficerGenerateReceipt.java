package controller.officer.template;

/**
 * A functional interface that defines the receipt generation capability
 * for an officer in the BTO system.
 * This is a pure interface with no implementation. It is intended to be implemented by helper classes.
 */

public interface IOfficerGenerateReceipt {

    /**
     * Generates and displays a receipt for a successful flat booking.
     */

    public void generateReceipt();
}
