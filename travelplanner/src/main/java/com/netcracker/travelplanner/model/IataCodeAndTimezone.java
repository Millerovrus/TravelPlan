package com.netcracker.travelplanner.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class IataCodeAndTimezone {
    private String iataCode;
    private String timezone;

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public IataCodeAndTimezone(String iataCode, String timezone) {
        this.iataCode = iataCode;
        this.timezone = timezone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("iataCode", iataCode)
                .append("timezone", timezone)
                .toString();
    }
}
