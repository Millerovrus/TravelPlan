
package com.netcracker.travelplanner.model.kiwiFlights;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SearchParams {

    @SerializedName("to_type")
    @Expose
    private String toType;
    @SerializedName("flyFrom_type")
    @Expose
    private String flyFromType;
    @SerializedName("seats")
    @Expose
    private Seats seats;

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public String getFlyFromType() {
        return flyFromType;
    }

    public void setFlyFromType(String flyFromType) {
        this.flyFromType = flyFromType;
    }

    public Seats getSeats() {
        return seats;
    }

    public void setSeats(Seats seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("toType", toType).append("flyFromType", flyFromType).append("seats", seats).toString();
    }

}
