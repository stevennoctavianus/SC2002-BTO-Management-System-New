import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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

    //Constructor
    public Project(){
        this.officers = new ArrayList<>();
        this.applications = new ArrayList<>();
    }

    public Project(String projectName, String neighborhood, int availableTwoRoom, int sellingPriceTwoRoom, int availableThreeRoom, int sellingPriceThreeRoom, Date openingDate, Date closingDate, int maxOfficer) {
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

    //Getter setter
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

    public void setMaxOfficer(int maxOfficer){
        this.maxOfficer = maxOfficer;
    }

    public int getMaxOfficer(){
        return this.maxOfficer;
    }

    public ArrayList<Officer> getOfficers() {
        return officers;
    }

    public void setOfficers(ArrayList<Officer> officers) {
        this.officers = officers;
    }

    public void addOfficers(Officer officer){
        this.officers.add(officer);
    }

    public ArrayList<Application> getApplication() {
        return applications;
    }

    public void setApplication(ArrayList<Application> applications) {
        this.applications = applications;
    }

    public void addApplication(Application application){
        this.applications.add(application);
    }

    public boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public boolean hasApplicant(Applicant applicant) {
        for (Application application : applications) {
            if (application.getApplicant().equals(applicant)) {
                return true;
            }
        }
        return false;
    }

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
