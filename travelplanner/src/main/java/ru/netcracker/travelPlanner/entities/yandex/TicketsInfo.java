
package ru.netcracker.travelPlanner.entities.yandex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class TicketsInfo {

    @SerializedName("et_marker")
    @Expose
    private Boolean etMarker;
    @SerializedName("places")
    @Expose
    private List<Place> places = new ArrayList<Place>();

    private Double averagePrice;

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Boolean getEtMarker() {
        return etMarker;
    }

    public void setEtMarker(Boolean etMarker) {
        this.etMarker = etMarker;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("etMarker", etMarker).append("places", places).toString();
    }

}
