package container;
import entity.*;
import utils.CSVReader;
import utils.CSVWriter;

import java.util.ArrayList;
import java.util.List;

public class OfficerList {
    private ArrayList<Officer> officerList;

    public OfficerList(String filePath) {
        officerList = new ArrayList<>();
        loadOfficers(filePath);
    }

    private void loadOfficers(String filePath) {
        List<String[]> data = CSVReader.readCSV(filePath);
        for (String[] row : data) {
            String name = row[0];
            String nric = row[1];
            int age = Integer.parseInt(row[2]);
            String maritalStatus = row[3];
            String password = row[4];

            officerList.add(new Officer(name, nric, age, maritalStatus, password));
        }
    }

    public ArrayList<Officer> getOfficerList(){
        return this.officerList;
    }

    public void addOfficer(Officer officer){
        this.officerList.add(officer);
    }
    
    public void saveToCSV() {
    List<String[]> data = new ArrayList<>();
    data.add(new String[]{"name", "nric", "age", "maritalStatus", "password"});

    for (Officer o : this.officerList) {
        data.add(new String[]{
            o.getName(),
            o.getNric(),
            String.valueOf(o.getAge()),
            o.getMaritalStatus().name(),
            o.getPassword()
        });
    }

    CSVWriter.writeCSV("../data/OfficerList.csv", data);
}

}
