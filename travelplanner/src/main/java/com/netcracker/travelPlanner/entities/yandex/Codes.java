
package com.netcracker.travelPlanner.entities.yandex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Codes {

    @SerializedName("icao")
    @Expose
    private Object icao;
    @SerializedName("sirena")
    @Expose
    private String sirena;
    @SerializedName("iata")
    @Expose
    private String iata;

    public Object getIcao() {
        return icao;
    }

    public void setIcao(Object icao) {
        this.icao = icao;
    }

    public String getSirena() {
        return sirena;
    }

    public void setSirena(String sirena) {
        this.sirena = sirena;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("icao", icao).append("sirena", sirena).append("iata", iata).toString();
    }

}
