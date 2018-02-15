
package com.netcracker.travelplanner.entities.newKiwi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Country {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;
//    @SerializedName("slug")
//    @Expose
//    private String slug;

    @SerializedName("code")
    @Expose
    private String code;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getSlug() {
//        return slug;
//    }
//
//    public void setSlug(String slug) {
//        this.slug = slug;
//    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
