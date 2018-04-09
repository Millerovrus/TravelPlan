package com.netcracker.travelplanner.models;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("algorithm")
public class AlgorithmProperties {
    private Integer coefsCount;
    private Integer routesByCoefCount;
    private Integer planePauseHours;
    private Integer busPauseMinutes;
    private Integer defaultPauseHours;

    public Integer getCoefsCount() {
        return coefsCount;
    }

    public void setCoefsCount(Integer coefsCount) {
        this.coefsCount = coefsCount;
    }

    public Integer getRoutesByCoefCount() {
        return routesByCoefCount;
    }

    public void setRoutesByCoefCount(Integer routesByCoefCount) {
        this.routesByCoefCount = routesByCoefCount;
    }

    public Integer getPlanePauseHours() {
        return planePauseHours;
    }

    public void setPlanePauseHours(Integer planePauseHours) {
        this.planePauseHours = planePauseHours;
    }

    public Integer getBusPauseMinutes() {
        return busPauseMinutes;
    }

    public void setBusPauseMinutes(Integer busPauseMinutes) {
        this.busPauseMinutes = busPauseMinutes;
    }

    public Integer getDefaultPauseHours() {
        return defaultPauseHours;
    }

    public void setDefaultPauseHours(Integer defaultPauseHours) {
        this.defaultPauseHours = defaultPauseHours;
    }
}
