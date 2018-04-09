
package com.netcracker.travelplanner.models.newKiwi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Airport {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("timezone")
    @Expose
    private String timezone;

    @SerializedName("city")
    @Expose
    private City city=null;

    @SerializedName("country")
    @Expose
    private Country country=null;


    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("location")
    @Expose
    private Location_ location=null;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Location_ getLocation() {
        return location;
    }

    public void setLocation(Location_ location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
