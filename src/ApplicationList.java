import java.util.ArrayList;

public class ApplicationList {
    private ArrayList<Application> applicationList;

    public ApplicationList(){
        this.applicationList = new ArrayList<>();
    }

    public void addApplication(Application application){
        this.applicationList.add(application);
    }
}
