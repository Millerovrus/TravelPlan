package com.netcracker.travelplanner.model.googleGeocode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddressComponent {
    @SerializedName("long_name")
    @Expose
    private String long_name;
    @SerializedName("short_name")
    @Expose
    private String short_name;
    @SerializedName("types")
    @Expose
    private ArrayList<String> types;

    public String getLongName() { return this.long_name; }

    public void setLongName(String long_name) { this.long_name = long_name; }

    public String getShortName() { return this.short_name; }

    public void setShortName(String short_name) { this.short_name = short_name; }

    public ArrayList<String> getTypes() { return this.types; }

    public void setTypes(ArrayList<String> types) { this.types = types; }
}
