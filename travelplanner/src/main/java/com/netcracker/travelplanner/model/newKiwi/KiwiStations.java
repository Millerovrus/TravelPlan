
package com.netcracker.travelplanner.model.newKiwi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class KiwiStations {

    @SerializedName("locations")
    @Expose
    private List<Airport> locations=null;
//    @SerializedName("meta")
//    @Expose
//    private Meta meta;

    public List<Airport> getLocations() {
        return locations;
    }

    public void setLocations(List<Airport> locations) {
        this.locations = locations;
    }

//    public Meta getMeta() {
//        return meta;
//    }
//
//    public void setMeta(Meta meta) {
//        this.meta = meta;
//    }

}
