package container;
import entity.*;
import java.util.ArrayList;

public class EnquiryList {
    private ArrayList<Enquiry> enquiries;

    public EnquiryList() {
        this.enquiries = new ArrayList<>();
    }

    public void addEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
    }

    public ArrayList<Enquiry> getEnquiries() {
        return enquiries;
    }

    public void removeEnquiry(Enquiry enquiry) {
        enquiries.remove(enquiry);
    }

    public ArrayList<Enquiry> getEnquiriesByProject(Project project) {
        ArrayList<Enquiry> result = new ArrayList<>();
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getProject().equals(project)) {
                result.add(enquiry);
            }
        }
        return result;
    }

    public ArrayList<Enquiry> getPendingEnquiriesByProject(Project project) {
        ArrayList<Enquiry> pendingEnquiries = new ArrayList<>();
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getProject().equals(project) && enquiry.getStatus() == Enquiry.EnquiryStatus.PENDING) {
                pendingEnquiries.add(enquiry);
            }
        }
        return pendingEnquiries;
    }
}
