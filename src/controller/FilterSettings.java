package controller;

import entity.Application;

/**
 * Models user-defined filters for browsing BTO projects.
 * <p>
 * These filters include location and flat type, and can be used by both applicants and officers
 * to narrow down project listings.
 */
public class FilterSettings {
    private String location;
    private Application.FlatType flatType;

    /**
     * Creates an empty filter with no location or flat type set.
     */
    public FilterSettings() {
        this.location = null;
        this.flatType = null;
    }

    /**
     * Gets the currently selected location filter.
     *
     * @return the location filter, or {@code null} if not set
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location filter.
     *
     * @param location the neighborhood or area to filter by
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the currently selected flat type filter.
     *
     * @return the flat type, or {@code null} if not set
     */
    public Application.FlatType getFlatType() {
        return flatType;
    }

    /**
     * Sets the flat type filter.
     *
     * @param flatType the flat type (e.g., {@code TWOROOM}, {@code THREEROOM})
     */
    public void setFlatType(Application.FlatType flatType) {
        this.flatType = flatType;
    }

    /**
     * Clears both the location and flat type filters.
     */
    public void clearFilters() {
        this.location = null;
        this.flatType = null;
    }

    /**
     * Returns a string representation of the currently selected filters.
     *
     * @return a human-readable string summarizing active filters
     */
    @Override
    public String toString() {
        return "Filters: [Location=" + (location != null ? location : "None") +
               ", FlatType=" + (flatType != null ? flatType : "None") + "]";
    }
}
