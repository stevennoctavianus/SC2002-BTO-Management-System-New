package controller.applicant;
import container.*;
import entity.*;
import java.util.Scanner;

public class ApplicantViewApplication {
    private Applicant applicant;
    private ApplicationList applicationList;
    private Scanner sc;

    public ApplicantViewApplication(Applicant applicant, ApplicationList applicationList){
        this.applicant = applicant;
        this.applicationList = applicationList;
        this.sc = new Scanner(System.in);
    }

    public void viewApplicationStatus(){
        Application application = applicationList.getApplicationByApplicant(applicant);
        if (application != null){
            System.out.println("Status: " + application.getApplicationStatus());
            if(application.getApplicationStatus() == Application.ApplicationStatus.SUCCESSFUL){
                System.out.println("Please book a flat!");
                if(applicant.getMaritalStatus() == User.MaritalStatus.SINGLE){
                    System.out.println("You are only eligible for 2-flat room. Flat Booked, please wait for confirmation!");
                    application.setBookedFlat(true);
                    application.setFlatType(Application.FlatType.TWOROOM);
                } else{
                    if (application.getProject().getAvailableThreeRoom() != 0 && application.getProject().getAvailableTwoRoom() != 0){
                        System.out.println("You can book 2 room Flat or 3 room Flat. Type 2 for 2-room Flat, 3 for 3-room Flat");
                        int option = sc.nextInt();
                        if (option == 2){
                            System.out.println("Success. Please wait for confirmation!");
                            application.setBookedFlat(true);
                            application.setFlatType(Application.FlatType.TWOROOM);
                        } else if (option == 3){
                            System.out.println("Success");
                            application.setBookedFlat(true);
                            application.setFlatType(Application.FlatType.THREEROOM);
                        }
                    } else if (application.getProject().getAvailableThreeRoom() == 0){
                        System.out.println("Only 2 room Flat left. Flat Booked, please wait for confirmation!");
                        application.setBookedFlat(true);
                        application.setFlatType(Application.FlatType.TWOROOM);
                    } else if (application.getProject().getAvailableTwoRoom() == 0){
                        System.out.println("Only 3 room Flat left. Flat Booked, please wait for confirmation!");
                        application.setBookedFlat(true);
                        application.setFlatType(Application.FlatType.THREEROOM);
                    }
                }
            }
        } else{
            System.out.println("No Application found.");
        }

    }

}
