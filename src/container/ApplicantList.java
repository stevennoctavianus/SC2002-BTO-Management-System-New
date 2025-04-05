import java.util.*;

public class ApplicantList {
    private ArrayList<Applicant> applicantList;

    public ApplicantList(){
        this.applicantList = new ArrayList<>();
    }

    public ApplicantList(String filepath){
        this.applicantList = new ArrayList<>();
        loadApplicants(filepath);
    }

    private void loadApplicants(String filepath){
        List<String[]> data = CSVReader.readCSV(filepath);
        for(String[] row : data){
            String name = row[0];
            String nric = row[1];
            int age = Integer.parseInt(row[2]);
            String maritalStatus = row[3];
            String password = row[4];

            applicantList.add(new Applicant(name, nric, age, maritalStatus, password));
        }
    }

    public List<Applicant> getApplicantList() {
        return applicantList;
    }

    public void addApplicant(Applicant applicant){
        this.applicantList.add(applicant);
    }

}
