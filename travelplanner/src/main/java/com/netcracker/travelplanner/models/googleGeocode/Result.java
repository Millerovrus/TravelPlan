package com.netcracker.travelplanner.models.googleGeocode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Result {
    @SerializedName("address_components")
    @Expose
    private ArrayList<AddressComponent> address_components;
    @SerializedName("formatted_address")
    @Expose
    private String formatted_address;
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("place_id")
    @Expose
    private String place_id;
    @SerializedName("types")
    @Expose
    private ArrayList<String> types;

    public ArrayList<AddressComponent> getAddressComponents() { return this.address_components; }

    public void setAddressComponents(ArrayList<AddressComponent> address_components) { this.address_components = address_components; }

    public String getFormattedAddress() { return this.formatted_address; }

    public void setFormattedAddress(String formatted_address) { this.formatted_address = formatted_address; }

    public Geometry getGeometry() { return this.geometry; }

    public void setGeometry(Geometry geometry) { this.geometry = geometry; }

    public String getPlaceId() { return this.place_id; }

    public void setPlaceId(String place_id) { this.place_id = place_id; }

    public ArrayList<String> getTypes() { return this.types; }

    public void setTypes(ArrayList<String> types) { this.types = types; }
}
