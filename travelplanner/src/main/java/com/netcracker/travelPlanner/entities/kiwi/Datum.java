
package com.netcracker.travelPlanner.entities.kiwi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

public class Datum {

    @SerializedName("mapIdfrom")
    @Expose
    private String mapIdfrom;
    @SerializedName("duration")
    @Expose
    private Duration duration;
    @SerializedName("flyTo")
    @Expose
    private String flyTo;
    @SerializedName("conversion")
    @Expose
    private Conversion conversion;
    @SerializedName("deep_link")
    @Expose
    private String deepLink;
    @SerializedName("mapIdto")
    @Expose
    private String mapIdto;
    @SerializedName("nightsInDest")
    @Expose
    private Object nightsInDest;
    @SerializedName("airlines")
    @Expose
    private List<String> airlines = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("facilitated_booking_available")
    @Expose
    private Boolean facilitatedBookingAvailable;
    @SerializedName("pnr_count")
    @Expose
    private Integer pnrCount;
    @SerializedName("fly_duration")
    @Expose
    private String flyDuration;
    @SerializedName("countryTo")
    @Expose
    private CountryTo countryTo;
    @SerializedName("baglimit")
    @Expose
    private Baglimit baglimit;
    @SerializedName("aTimeUTC")
    @Expose
    private Integer aTimeUTC;
    @SerializedName("p3")
    @Expose
    private Integer p3;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("type_flights")
    @Expose
    private List<String> typeFlights = null;
    @SerializedName("bags_price")
    @Expose
    private BagsPrice bagsPrice;
    @SerializedName("cityTo")
    @Expose
    private String cityTo;
    @SerializedName("transfers")
    @Expose
    private List<Object> transfers = null;
    @SerializedName("flyFrom")
    @Expose
    private String flyFrom;
    @SerializedName("dTimeUTC")
    @Expose
    private Integer dTimeUTC;
    @SerializedName("p2")
    @Expose
    private Integer p2;
    @SerializedName("countryFrom")
    @Expose
    private CountryFrom countryFrom;
    @SerializedName("p1")
    @Expose
    private Integer p1;
    @SerializedName("dTime")
    @Expose
    private Integer dTime;
    @SerializedName("found_on")
    @Expose
    private List<String> foundOn = null;
    @SerializedName("booking_token")
    @Expose
    private String bookingToken;
    @SerializedName("routes")
    @Expose
    private List<List<String>> routes = null;
    @SerializedName("cityFrom")
    @Expose
    private String cityFrom;
    @SerializedName("aTime")
    @Expose
    private Integer aTime;
    @SerializedName("route")
    @Expose
    private List<Route> route = null;
    @SerializedName("distance")
    @Expose
    private Double distance;

    public String getMapIdfrom() {
        return mapIdfrom;
    }

    public void setMapIdfrom(String mapIdfrom) {
        this.mapIdfrom = mapIdfrom;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getFlyTo() {
        return flyTo;
    }

    public void setFlyTo(String flyTo) {
        this.flyTo = flyTo;
    }

    public Conversion getConversion() {
        return conversion;
    }

    public void setConversion(Conversion conversion) {
        this.conversion = conversion;
    }

    public String getDeepLink() {
        return deepLink;
    }

    public void setDeepLink(String deepLink) {
        this.deepLink = deepLink;
    }

    public String getMapIdto() {
        return mapIdto;
    }

    public void setMapIdto(String mapIdto) {
        this.mapIdto = mapIdto;
    }

    public Object getNightsInDest() {
        return nightsInDest;
    }

    public void setNightsInDest(Object nightsInDest) {
        this.nightsInDest = nightsInDest;
    }

    public List<String> getAirlines() {
        return airlines;
    }

    public void setAirlines(List<String> airlines) {
        this.airlines = airlines;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getFacilitatedBookingAvailable() {
        return facilitatedBookingAvailable;
    }

    public void setFacilitatedBookingAvailable(Boolean facilitatedBookingAvailable) {
        this.facilitatedBookingAvailable = facilitatedBookingAvailable;
    }

    public Integer getPnrCount() {
        return pnrCount;
    }

    public void setPnrCount(Integer pnrCount) {
        this.pnrCount = pnrCount;
    }

    public String getFlyDuration() {
        return flyDuration;
    }

    public void setFlyDuration(String flyDuration) {
        this.flyDuration = flyDuration;
    }

    public CountryTo getCountryTo() {
        return countryTo;
    }

    public void setCountryTo(CountryTo countryTo) {
        this.countryTo = countryTo;
    }

    public Baglimit getBaglimit() {
        return baglimit;
    }

    public void setBaglimit(Baglimit baglimit) {
        this.baglimit = baglimit;
    }

    public Integer getATimeUTC() {
        return aTimeUTC;
    }

    public void setATimeUTC(Integer aTimeUTC) {
        this.aTimeUTC = aTimeUTC;
    }

    public Integer getP3() {
        return p3;
    }

    public void setP3(Integer p3) {
        this.p3 = p3;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<String> getTypeFlights() {
        return typeFlights;
    }

    public void setTypeFlights(List<String> typeFlights) {
        this.typeFlights = typeFlights;
    }

    public BagsPrice getBagsPrice() {
        return bagsPrice;
    }

    public void setBagsPrice(BagsPrice bagsPrice) {
        this.bagsPrice = bagsPrice;
    }

    public String getCityTo() {
        return cityTo;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    public List<Object> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Object> transfers) {
        this.transfers = transfers;
    }

    public String getFlyFrom() {
        return flyFrom;
    }

    public void setFlyFrom(String flyFrom) {
        this.flyFrom = flyFrom;
    }

    public Integer getDTimeUTC() {
        return dTimeUTC;
    }

    public void setDTimeUTC(Integer dTimeUTC) {
        this.dTimeUTC = dTimeUTC;
    }

    public Integer getP2() {
        return p2;
    }

    public void setP2(Integer p2) {
        this.p2 = p2;
    }

    public CountryFrom getCountryFrom() {
        return countryFrom;
    }

    public void setCountryFrom(CountryFrom countryFrom) {
        this.countryFrom = countryFrom;
    }

    public Integer getP1() {
        return p1;
    }

    public void setP1(Integer p1) {
        this.p1 = p1;
    }

    public Integer getDTime() {
        return dTime;
    }

    public void setDTime(Integer dTime) {
        this.dTime = dTime;
    }

    public List<String> getFoundOn() {
        return foundOn;
    }

    public void setFoundOn(List<String> foundOn) {
        this.foundOn = foundOn;
    }

    public String getBookingToken() {
        return bookingToken;
    }

    public void setBookingToken(String bookingToken) {
        this.bookingToken = bookingToken;
    }

    public List<List<String>> getRoutes() {
        return routes;
    }

    public void setRoutes(List<List<String>> routes) {
        this.routes = routes;
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

    public List<Route> getRoute() {
        return route;
    }

    public void setRoute(List<Route> route) {
        this.route = route;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("mapIdfrom", mapIdfrom).append("duration", duration).append("flyTo", flyTo).append("conversion", conversion).append("deepLink", deepLink).append("mapIdto", mapIdto).append("nightsInDest", nightsInDest).append("airlines", airlines).append("id", id).append("facilitatedBookingAvailable", facilitatedBookingAvailable).append("pnrCount", pnrCount).append("flyDuration", flyDuration).append("countryTo", countryTo).append("baglimit", baglimit).append("aTimeUTC", aTimeUTC).append("p3", p3).append("price", price).append("typeFlights", typeFlights).append("bagsPrice", bagsPrice).append("cityTo", cityTo).append("transfers", transfers).append("flyFrom", flyFrom).append("dTimeUTC", dTimeUTC).append("p2", p2).append("countryFrom", countryFrom).append("p1", p1).append("dTime", dTime).append("foundOn", foundOn).append("bookingToken", bookingToken).append("routes", routes).append("cityFrom", cityFrom).append("aTime", aTime).append("route", route).append("distance", distance).toString();
    }

}
