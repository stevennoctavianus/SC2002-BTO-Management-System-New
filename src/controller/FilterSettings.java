package controller;
import entity.Application;

public class FilterSettings {
    private String location;
    private Application.FlatType flatType;

    public FilterSettings() {
        this.location = null;
        this.flatType = null;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Application.FlatType getFlatType() {
        return flatType;
    }

    public void setFlatType(Application.FlatType flatType) {
        this.flatType = flatType;
    }

    public void clearFilters() {
        this.location = null;
        this.flatType = null;
    }

    @Override
    public String toString() {
        return "Filters: [Location=" + (location != null ? location : "None") + ", FlatType=" + (flatType != null ? flatType : "None") + "]";
    }
}