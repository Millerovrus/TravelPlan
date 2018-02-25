package com.netcracker.travelplanner.entities.newKiwi;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "airports")
public class MyPoint {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "type")
    private String type;

    @Column(name = "latitude")
    private Double lat;

    @Column(name = "longitude")
    private Double lon;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "city_code")
    private String cityCode;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setSityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public MyPoint(String id, String code, String name, String timezone, String type, Double lat, Double lon, String countryName, String countryCode, String cityName, String cityCode) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.timezone = timezone;
        this.type = type;
        this.lat = lat;
        this.lon = lon;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.cityName = cityName;
        this.cityCode = cityCode;
    }

    private MyPoint() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("code", code)
                .append("name", name)
                .append("timezone", timezone)
                .append("type", type)
                .append("lat", lat)
                .append("lon", lon)
                .append("countryName", countryName)
                .append("countryCode", countryCode)
                .append("cityName", cityName)
                .append("cityCode", cityCode)
                .toString();
    }
}
