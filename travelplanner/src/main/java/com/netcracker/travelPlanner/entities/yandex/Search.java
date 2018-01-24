
package com.netcracker.travelPlanner.entities.yandex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Search {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("to")
    @Expose
    private To_ to;
    @SerializedName("from")
    @Expose
    private From_ from;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public To_ getTo() {
        return to;
    }

    public void setTo(To_ to) {
        this.to = to;
    }

    public From_ getFrom() {
        return from;
    }

    public void setFrom(From_ from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("date", date).append("to", to).append("from", from).toString();
    }

}
