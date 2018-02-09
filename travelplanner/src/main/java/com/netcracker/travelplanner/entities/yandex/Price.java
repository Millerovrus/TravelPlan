
package com.netcracker.travelplanner.entities.yandex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Price {

    @SerializedName("cents")
    @Expose
    private Integer cents;
    @SerializedName("whole")
    @Expose
    private Integer whole;

    public Integer getCents() {
        return cents;
    }

    public void setCents(Integer cents) {
        this.cents = cents;
    }

    public Integer getWhole() {
        return whole;
    }

    public void setWhole(Integer whole) {
        this.whole = whole;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("cents", cents).append("whole", whole).toString();
    }

}
