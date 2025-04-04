import java.util.ArrayList;
import java.util.List;

public class EnquiryList {
    private List<Enquiry> enquiries;

    public EnquiryList() {
        this.enquiries = new ArrayList<>();
    }

    public void addEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
        System.out.println("Enquiry submitted.");
    }

    public void viewEnquiriesByApplicant(Applicant applicant) {
        System.out.println("\n===== My Enquiries =====");
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getApplicant().equals(applicant)) {
                System.out.println(enquiry);
            }
        }
    }

    public void editEnquiry(int enquiryId, String newMessage) {
        if (enquiryId < 0 || enquiryId >= enquiries.size()) {
            System.out.println("Invalid enquiry ID.");
            return;
        }
        Enquiry enquiry = enquiries.get(enquiryId);
        enquiry.setMessage(newMessage);
        System.out.println("Enquiry updated.");
    }

    public void deleteEnquiry(int enquiryId) {
        if (enquiryId < 0 || enquiryId >= enquiries.size()) {
            System.out.println("Invalid enquiry ID.");
            return;
        }
        enquiries.remove(enquiryId);
        System.out.println("Enquiry deleted.");
    }

}
