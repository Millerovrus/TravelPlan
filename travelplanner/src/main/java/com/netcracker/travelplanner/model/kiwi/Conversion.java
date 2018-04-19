
package com.netcracker.travelplanner.model.kiwi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Conversion {

    @SerializedName("EUR")
    @Expose
    private Integer eUR;

    public Integer getEUR() {
        return eUR;
    }

    public void setEUR(Integer eUR) {
        this.eUR = eUR;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("eUR", eUR).toString();
    }

}
