import java.util.Scanner;

public class ApplicantMakeEnquiry {
    private Applicant applicant;
    private ProjectList projectList;
    private EnquiryList enquiryList;
    private Scanner sc;

    public ApplicantMakeEnquiry(Applicant applicant, ProjectList projectList, EnquiryList enquiryList){
        this.applicant = applicant;
        this.projectList = projectList;
        this.enquiryList = enquiryList;
        this.sc = new Scanner(System.in);
    }

    public void makeEnquiry(){
        int index = 1;
        System.out.println("What project you want to enquire?");
        for (Project project : projectList.getProject()){
            System.out.println(index + ")" + project.getProjectName());
            index++;
        }

        System.out.println("Enter the project numbner: ");
        int choice = sc.nextInt();
        sc.nextLine();

        Project project = projectList.getProject().get(choice-1);

        System.out.println("Enter your message: ");
        String message = sc.nextLine();

        Enquiry enquiry = new Enquiry(applicant, project, message);
        enquiryList.addEnquiry(enquiry);

        System.out.println("Success");
    }

    public void viewEnquiry(){
        enquiryList.viewEnquiriesByApplicant(applicant);
    }

    public void editEnquiry(){
        System.out.println("Enter enq");
    }
}
