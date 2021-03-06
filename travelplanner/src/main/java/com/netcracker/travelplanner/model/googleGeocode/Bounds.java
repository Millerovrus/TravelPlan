package com.netcracker.travelplanner.model.googleGeocode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bounds {
    @SerializedName("northeast")
    @Expose
    private Northeast northeast;
    @SerializedName("southwest")
    @Expose
    private Southwest southwest;

    public Northeast getNortheast() { return this.northeast; }

    public void setNortheast(Northeast northeast) { this.northeast = northeast; }

    public Southwest getSouthwest() { return this.southwest; }

    public void setSouthwest(Southwest southwest) { this.southwest = southwest; }
}
