
package ru.netcracker.travelPlanner.entities.yandex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Carrier {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("contacts")
    @Expose
    private String contacts;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("logo_svg")
    @Expose
    private String logoSvg;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("codes")
    @Expose
    private Codes codes;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("email")
    @Expose
    private String email;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogoSvg() {
        return logoSvg;
    }

    public void setLogoSvg(String logoSvg) {
        this.logoSvg = logoSvg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Codes getCodes() {
        return codes;
    }

    public void setCodes(Codes codes) {
        this.codes = codes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("code", code).append("contacts", contacts).append("url", url).append("logoSvg", logoSvg).append("title", title).append("phone", phone).append("codes", codes).append("address", address).append("logo", logo).append("email", email).toString();
    }

}
