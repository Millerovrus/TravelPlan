
package com.netcracker.travelplanner.model.kiwi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Route {

    @SerializedName("bags_recheck_required")
    @Expose
    private Boolean bagsRecheckRequired;
    @SerializedName("mapIdfrom")
    @Expose
    private String mapIdfrom;
    @SerializedName("flight_no")
    @Expose
    private Integer flightNo;
    @SerializedName("original_return")
    @Expose
    private Integer originalReturn;
    @SerializedName("lngFrom")
    @Expose
    private Double lngFrom;
    @SerializedName("flyTo")
    @Expose
    private String flyTo;
    @SerializedName("guarantee")
    @Expose
    private Boolean guarantee;
    @SerializedName("latTo")
    @Expose
    private Double latTo;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("combination_id")
    @Expose
    private String combinationId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("latFrom")
    @Expose
    private Double latFrom;
    @SerializedName("lngTo")
    @Expose
    private Double lngTo;
    @SerializedName("dTimeUTC")
    @Expose
    private Integer dTimeUTC;
    @SerializedName("aTimeUTC")
    @Expose
    private Integer aTimeUTC;
    @SerializedName("return")
    @Expose
    private Integer _return;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("cityTo")
    @Expose
    private String cityTo;
    @SerializedName("vehicle_type")
    @Expose
    private String vehicleType;
    @SerializedName("flyFrom")
    @Expose
    private String flyFrom;
    @SerializedName("mapIdto")
    @Expose
    private String mapIdto;
    @SerializedName("dTime")
    @Expose
    private Integer dTime;
    @SerializedName("found_on")
    @Expose
    private String foundOn;
    @SerializedName("airline")
    @Expose
    private String airline;
    @SerializedName("cityFrom")
    @Expose
    private String cityFrom;
    @SerializedName("aTime")
    @Expose
    private Integer aTime;

    public Boolean getBagsRecheckRequired() {
        return bagsRecheckRequired;
    }

    public void setBagsRecheckRequired(Boolean bagsRecheckRequired) {
        this.bagsRecheckRequired = bagsRecheckRequired;
    }

    public String getMapIdfrom() {
        return mapIdfrom;
    }

    public void setMapIdfrom(String mapIdfrom) {
        this.mapIdfrom = mapIdfrom;
    }

    public Integer getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(Integer flightNo) {
        this.flightNo = flightNo;
    }

    public Integer getOriginalReturn() {
        return originalReturn;
    }

    public void setOriginalReturn(Integer originalReturn) {
        this.originalReturn = originalReturn;
    }

    public Double getLngFrom() {
        return lngFrom;
    }

    public void setLngFrom(Double lngFrom) {
        this.lngFrom = lngFrom;
    }

    public String getFlyTo() {
        return flyTo;
    }

    public void setFlyTo(String flyTo) {
        this.flyTo = flyTo;
    }

    public Boolean getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(Boolean guarantee) {
        this.guarantee = guarantee;
    }

    public Double getLatTo() {
        return latTo;
    }

    public void setLatTo(Double latTo) {
        this.latTo = latTo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCombinationId() {
        return combinationId;
    }

    public void setCombinationId(String combinationId) {
        this.combinationId = combinationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLatFrom() {
        return latFrom;
    }

    public void setLatFrom(Double latFrom) {
        this.latFrom = latFrom;
    }

    public Double getLngTo() {
        return lngTo;
    }

    public void setLngTo(Double lngTo) {
        this.lngTo = lngTo;
    }

    public Integer getDTimeUTC() {
        return dTimeUTC;
    }

    public void setDTimeUTC(Integer dTimeUTC) {
        this.dTimeUTC = dTimeUTC;
    }

    public Integer getATimeUTC() {
        return aTimeUTC;
    }

    public void setATimeUTC(Integer aTimeUTC) {
        this.aTimeUTC = aTimeUTC;
    }

    public Integer getReturn() {
        return _return;
    }

    public void setReturn(Integer _return) {
        this._return = _return;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCityTo() {
        return cityTo;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getFlyFrom() {
        return flyFrom;
    }

    public void setFlyFrom(String flyFrom) {
        this.flyFrom = flyFrom;
    }

    public String getMapIdto() {
        return mapIdto;
    }

    public void setMapIdto(String mapIdto) {
        this.mapIdto = mapIdto;
    }

    public Integer getDTime() {
        return dTime;
    }

    public void setDTime(Integer dTime) {
        this.dTime = dTime;
    }

    public String getFoundOn() {
        return foundOn;
    }

    public void setFoundOn(String foundOn) {
        this.foundOn = foundOn;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public Integer getATime() {
        return aTime;
    }

    public void setATime(Integer aTime) {
        this.aTime = aTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("bagsRecheckRequired", bagsRecheckRequired).append("mapIdfrom", mapIdfrom).append("flightNo", flightNo).append("originalReturn", originalReturn).append("lngFrom", lngFrom).append("flyTo", flyTo).append("guarantee", guarantee).append("latTo", latTo).append("source", source).append("combinationId", combinationId).append("id", id).append("latFrom", latFrom).append("lngTo", lngTo).append("dTimeUTC", dTimeUTC).append("aTimeUTC", aTimeUTC).append("_return", _return).append("price", price).append("cityTo", cityTo).append("vehicleType", vehicleType).append("flyFrom", flyFrom).append("mapIdto", mapIdto).append("dTime", dTime).append("foundOn", foundOn).append("airline", airline).append("cityFrom", cityFrom).append("aTime", aTime).toString();
    }

}
