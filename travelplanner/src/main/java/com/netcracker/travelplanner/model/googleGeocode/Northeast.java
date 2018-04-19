package com.netcracker.travelplanner.model.googleGeocode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Northeast {
    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("lng")
    @Expose
    private double lng;

    public double getLat() { return this.lat; }

    public void setLat(double lat) { this.lat = lat; }

    public double getLng() { return this.lng; }

    public void setLng(double lng) { this.lng = lng; }
}
