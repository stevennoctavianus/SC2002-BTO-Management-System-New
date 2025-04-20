package controller.manager.template;

/**
 * A functional interface that defines the report generation capability
 * for a manager in the BTO system.
 * This is a pure interface with no implementation. It is intended to be implemented by helper classes.
 */
public interface IManagerGenerateReport {

    /**
     * Generates a report of all successful applications (bookings),
     * with options to filter by flat type, marital status, or project.
     */
    public void generateReport();
}

