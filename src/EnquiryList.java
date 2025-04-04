import java.util.ArrayList;
import java.util.List;

public class EnquiryList {
    private List<Enquiry> enquiries;

    public EnquiryList() {
        this.enquiries = new ArrayList<>();
    }

    public void addEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
    }

    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    public void removeEnquiry(Enquiry enquiry) {
        enquiries.remove(enquiry);
    }
}