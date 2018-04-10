package com.netcracker.travelplanner.models.googleGeocode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geometry {
    @SerializedName("bounds")
    @Expose
    private Bounds bounds;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("location_type")
    @Expose
    private String location_type;
    @SerializedName("viewport")
    @Expose
    private Viewport viewport;

    public Bounds getBounds() { return this.bounds; }

    public void setBounds(Bounds bounds) { this.bounds = bounds; }

    public Location getLocation() { return this.location; }

    public void setLocation(Location location) { this.location = location; }

    public String getLocationType() { return this.location_type; }

    public void setLocationType(String location_type) { this.location_type = location_type; }

    public Viewport getViewport() { return this.viewport; }

    public void setViewport(Viewport viewport) { this.viewport = viewport; }
}
