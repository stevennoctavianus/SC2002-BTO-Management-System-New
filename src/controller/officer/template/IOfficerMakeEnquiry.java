package controller.officer.template;
import entity.Project;
public interface IOfficerMakeEnquiry {
    public void makeEnquiry();
    public void viewEnquiry();
    public boolean isManagingProject(Project p);
    public void editEnquiry();
    public void deleteEnquiry();
}
