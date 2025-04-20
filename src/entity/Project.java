package entity;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Models a BTO project managed within the system.
 * Contains details such as available flats, pricing, associated manager, assigned officers,
 * application period, and registration visibility.
 */
public class Project {

    private String projectName;
    private String neighborhood;
    private int availableTwoRoom;
    private int sellingPriceTwoRoom;
    private int availableThreeRoom;
    private int sellingPriceThreeRoom;
    private Date openingDate;
    private Date closingDate;
    private Manager manager;
    private ArrayList<Application> applications;
    private int maxOfficer;
    private ArrayList<Officer> officers;
    private boolean visibility;

    /**
     * Default constructor that initializes empty officer and application lists.
     */
    public Project() {
        this.officers = new ArrayList<>();
        this.applications = new ArrayList<>();
    }

    /**
     * Creates a project with all relevant details and default visibility set to true.
     *
     * @param projectName         the name of the project
     * @param neighborhood        the neighborhood where the project is located
     * @param availableTwoRoom    number of 2-room flats available
     * @param sellingPriceTwoRoom price for a 2-room flat
     * @param availableThreeRoom  number of 3-room flats available
     * @param sellingPriceThreeRoom price for a 3-room flat
     * @param openingDate         the application opening date
     * @param closingDate         the application closing date
     * @param maxOfficer          the number of officer slots available
     */
    public Project(String projectName, String neighborhood, int availableTwoRoom, int sellingPriceTwoRoom,
                   int availableThreeRoom, int sellingPriceThreeRoom, Date openingDate, Date closingDate,
                   int maxOfficer) {
        this.projectName = projectName;
        this.neighborhood = neighborhood;
        this.availableTwoRoom = availableTwoRoom;
        this.sellingPriceTwoRoom = sellingPriceTwoRoom;
        this.availableThreeRoom = availableThreeRoom;
        this.sellingPriceThreeRoom = sellingPriceThreeRoom;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.maxOfficer = maxOfficer;
        this.officers = new ArrayList<>();
        this.applications = new ArrayList<>();
        this.visibility = true;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public int getAvailableTwoRoom() {
        return availableTwoRoom;
    }

    public void setAvailableTwoRoom(int availableTwoRoom) {
        this.availableTwoRoom = availableTwoRoom;
    }

    public int getSellingPriceTwoRoom() {
        return sellingPriceTwoRoom;
    }

    public void setSellingPriceTwoRoom(int sellingPriceTwoRoom) {
        this.sellingPriceTwoRoom = sellingPriceTwoRoom;
    }

    public int getAvailableThreeRoom() {
        return availableThreeRoom;
    }

    public void setAvailableThreeRoom(int availableThreeRoom) {
        this.availableThreeRoom = availableThreeRoom;
    }

    public int getSellingPriceThreeRoom() {
        return sellingPriceThreeRoom;
    }

    public void setSellingPriceThreeRoom(int sellingPriceThreeRoom) {
        this.sellingPriceThreeRoom = sellingPriceThreeRoom;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void setMaxOfficer(int maxOfficer) {
        this.maxOfficer = maxOfficer;
    }

    public int getMaxOfficer() {
        return this.maxOfficer;
    }

    /**
     * Returns the list of officers assigned to this project.
     *
     * @return list of assigned officers
     */
    public ArrayList<Officer> getOfficers() {
        return officers;
    }

    public void setOfficers(ArrayList<Officer> officers) {
        this.officers = officers;
    }

    /**
     * Assigns an officer to the project.
     *
     * @param officer the officer to add
     */
    public void addOfficers(Officer officer) {
        this.officers.add(officer);
    }

    /**
     * Returns the list of applications submitted for this project.
     *
     * @return list of applications
     */
    public ArrayList<Application> getApplication() {
        return applications;
    }

    public void setApplication(ArrayList<Application> applications) {
        this.applications = applications;
    }

    /**
     * Adds a new application to this project.
     *
     * @param application the application to add
     */
    public void addApplication(Application application) {
        this.applications.add(application);
    }

    /**
     * Indicates whether the project is visible to applicants.
     *
     * @return true if visible, false if hidden
     */
    public boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * Checks whether a given applicant has already applied to this project.
     *
     * @param applicant the applicant to check
     * @return true if the applicant has submitted an application
     */
    public boolean hasApplicant(Applicant applicant) {
        for (Application application : applications) {
            if (application.getApplicant().equals(applicant)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a formatted string summarizing the project details.
     *
     * @return project information as a string
     */
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Project Name: " + projectName +
               "\nNeighborhood: " + neighborhood +
               "\nAvailable 2-Room Flats: " + availableTwoRoom +
               "\nSelling Price (2-Room): $" + sellingPriceTwoRoom +
               "\nAvailable 3-Room Flats: " + availableThreeRoom +
               "\nSelling Price (3-Room): $" + sellingPriceThreeRoom +
               "\nOpening Date: " + (openingDate != null ? sdf.format(openingDate) : "N/A") +
               "\nClosing Date: " + (closingDate != null ? sdf.format(closingDate) : "N/A") +
               "\nMax Officers: " + maxOfficer +
               "\nVisibility: " + (visibility ? "Visible" : "Hidden") +
               "\n-------------------------------------";
    }
}

