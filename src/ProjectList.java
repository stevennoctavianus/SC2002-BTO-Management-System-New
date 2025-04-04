import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;


public class ProjectList {
    private ArrayList<Project> projectList;

    //Constructor
    public ProjectList(){
        this.projectList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date openingDate = null;
        Date closingDate = null;
        try {
            openingDate = sdf.parse("2025-02-15"); // Parse outside ProjectList
            closingDate = sdf.parse("2025-03-20");
        } catch (Exception e) {
            e.printStackTrace(); // Handle error here, if needed
        }

        //temp data
        Project acaciaBreeze = new Project();
        acaciaBreeze.setNeighborhood("Yishun");
        acaciaBreeze.setProjectName("Acacia Breeze");
        acaciaBreeze.setAvailableTwoRoom(2);
        acaciaBreeze.setSellingPriceTwoRoom(350000);
        acaciaBreeze.setAvailableThreeRoom(3);
        acaciaBreeze.setSellingPriceThreeRoom(450000);
        acaciaBreeze.setOpeningDate(openingDate);
        acaciaBreeze.setClosingDate(closingDate);
        acaciaBreeze.setMaxOfficer(3);


        this.addProject(acaciaBreeze);
    }

    public void addProject(Project project){
        this.projectList.add(project);
    }

    public ArrayList<Project> getProject(){
        return this.projectList;
    }

    public Project getProjectByName(String projectName) {
        for (Project project : projectList) {
            if (project.getProjectName().equalsIgnoreCase(projectName)) {
                return project;
            }
        }
        return null; // Return null if no project is found
    }
    


}
