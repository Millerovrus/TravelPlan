
package com.netcracker.travelplanner.entities.kiwi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Seats {

    @SerializedName("infants")
    @Expose
    private Integer infants;
    @SerializedName("passengers")
    @Expose
    private Integer passengers;
    @SerializedName("adults")
    @Expose
    private Integer adults;
    @SerializedName("children")
    @Expose
    private Integer children;

    public Integer getInfants() {
        return infants;
    }

    public void setInfants(Integer infants) {
        this.infants = infants;
    }

    public Integer getPassengers() {
        return passengers;
    }

    public void setPassengers(Integer passengers) {
        this.passengers = passengers;
    }

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("infants", infants).append("passengers", passengers).append("adults", adults).append("children", children).toString();
    }

}
