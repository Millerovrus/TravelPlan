
package com.netcracker.travelplanner.entities.kiwi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

public class KiwiFlights {

    @SerializedName("search_params")
    @Expose
    private SearchParams searchParams;
    @SerializedName("_results")
    @Expose
    private Integer results;
    @SerializedName("connections")
    @Expose
    private List<Object> connections = null;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("currency_rate")
    @Expose
    private Double currencyRate;
    @SerializedName("all_stopover_airports")
    @Expose
    private List<String> allStopoverAirports = null;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("ref_tasks")
    @Expose
    private List<Object> refTasks = null;
    @SerializedName("refresh")
    @Expose
    private List<Object> refresh = null;
    @SerializedName("del")
    @Expose
    private Object del;
    @SerializedName("all_airlines")
    @Expose
    private List<String> allAirlines = null;
    @SerializedName("time")
    @Expose
    private Integer time;

    public SearchParams getSearchParams() {
        return searchParams;
    }

    public void setSearchParams(SearchParams searchParams) {
        this.searchParams = searchParams;
    }

    public Integer getResults() {
        return results;
    }

    public void setResults(Integer results) {
        this.results = results;
    }

    public List<Object> getConnections() {
        return connections;
    }

    public void setConnections(List<Object> connections) {
        this.connections = connections;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(Double currencyRate) {
        this.currencyRate = currencyRate;
    }

    public List<String> getAllStopoverAirports() {
        return allStopoverAirports;
    }

    public void setAllStopoverAirports(List<String> allStopoverAirports) {
        this.allStopoverAirports = allStopoverAirports;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public List<Object> getRefTasks() {
        return refTasks;
    }

    public void setRefTasks(List<Object> refTasks) {
        this.refTasks = refTasks;
    }

    public List<Object> getRefresh() {
        return refresh;
    }

    public void setRefresh(List<Object> refresh) {
        this.refresh = refresh;
    }

    public Object getDel() {
        return del;
    }

    public void setDel(Object del) {
        this.del = del;
    }

    public List<String> getAllAirlines() {
        return allAirlines;
    }

    public void setAllAirlines(List<String> allAirlines) {
        this.allAirlines = allAirlines;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("searchParams", searchParams).append("results", results).append("connections", connections).append("currency", currency).append("currencyRate", currencyRate).append("allStopoverAirports", allStopoverAirports).append("data", data).append("refTasks", refTasks).append("refresh", refresh).append("del", del).append("allAirlines", allAirlines).append("time", time).toString();
    }

}
