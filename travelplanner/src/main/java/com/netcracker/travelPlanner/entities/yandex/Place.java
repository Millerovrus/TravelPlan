
package com.netcracker.travelPlanner.entities.yandex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Place {

    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("price")
    @Expose
    private Price price;
    @SerializedName("name")
    @Expose
    private String name;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("currency", currency).append("price", price).append("name", name).toString();
    }

}
