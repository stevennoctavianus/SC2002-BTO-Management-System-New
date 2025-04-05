import java.util.ArrayList;
import java.util.List;

public class ManagerList {
    private ArrayList <Manager> managerList;

    public ManagerList(String filePath) {
        managerList = new ArrayList<>();
        loadManagers(filePath);
    }

    private void loadManagers(String filePath) {
        List<String[]> data = CSVReader.readCSV(filePath);
        for (String[] row : data) {
            String name = row[0];
            String nric = row[1];
            int age = Integer.parseInt(row[2]);
            String maritalStatus = row[3];
            String password = row[4];

            managerList.add(new Manager(name, nric, age, maritalStatus, password));
        }
    }

    public ArrayList<Manager> getManagerList(){
        return this.managerList;
    }

    public void addManager(Manager manager){
        this.managerList.add(manager);
    }
}
