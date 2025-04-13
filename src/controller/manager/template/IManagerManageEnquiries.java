package controller.manager.template;

import entity.Project;

public interface IManagerManageEnquiries {
    public void viewEnquiry();
    public void viewHandledProjectEnquiry(Project currentProject);
    public void replyHandledProjectEnquiry(Project currentProject);
}
