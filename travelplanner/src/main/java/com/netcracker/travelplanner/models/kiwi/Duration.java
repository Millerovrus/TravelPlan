
package com.netcracker.travelplanner.models.kiwi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Duration {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("return")
    @Expose
    private Integer _return;
    @SerializedName("departure")
    @Expose
    private Integer departure;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getReturn() {
        return _return;
    }

    public void setReturn(Integer _return) {
        this._return = _return;
    }

    public Integer getDeparture() {
        return departure;
    }

    public void setDeparture(Integer departure) {
        this.departure = departure;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("total", total).append("_return", _return).append("departure", departure).toString();
    }

}
