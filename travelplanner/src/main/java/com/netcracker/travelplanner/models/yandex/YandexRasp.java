
package com.netcracker.travelplanner.models.yandex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class YandexRasp {

    @SerializedName("interval_segments")
    @Expose
    private List<Object> intervalSegments = new ArrayList<Object>();
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("segments")
    @Expose
    private List<Segment> segments = new ArrayList<Segment>();
    @SerializedName("search")
    @Expose
    private Search search;

    public List<Object> getIntervalSegments() {
        return intervalSegments;
    }

    public void setIntervalSegments(List<Object> intervalSegments) {
        this.intervalSegments = intervalSegments;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("intervalSegments", intervalSegments).append("pagination", pagination).append("segments", segments).append("search", search).toString();
    }

}
