
package com.netcracker.travelplanner.models.kiwi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Baglimit {

    @SerializedName("hand_width")
    @Expose
    private Integer handWidth;
    @SerializedName("hand_length")
    @Expose
    private Integer handLength;
    @SerializedName("hold_weight")
    @Expose
    private Integer holdWeight;
    @SerializedName("hand_height")
    @Expose
    private Integer handHeight;
    @SerializedName("hand_weight")
    @Expose
    private Integer handWeight;

    public Integer getHandWidth() {
        return handWidth;
    }

    public void setHandWidth(Integer handWidth) {
        this.handWidth = handWidth;
    }

    public Integer getHandLength() {
        return handLength;
    }

    public void setHandLength(Integer handLength) {
        this.handLength = handLength;
    }

    public Integer getHoldWeight() {
        return holdWeight;
    }

    public void setHoldWeight(Integer holdWeight) {
        this.holdWeight = holdWeight;
    }

    public Integer getHandHeight() {
        return handHeight;
    }

    public void setHandHeight(Integer handHeight) {
        this.handHeight = handHeight;
    }

    public Integer getHandWeight() {
        return handWeight;
    }

    public void setHandWeight(Integer handWeight) {
        this.handWeight = handWeight;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("handWidth", handWidth).append("handLength", handLength).append("holdWeight", holdWeight).append("handHeight", handHeight).append("handWeight", handWeight).toString();
    }

}
