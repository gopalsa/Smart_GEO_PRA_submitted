package nec.cst.pra.gpdp;

import java.io.Serializable;

/**
 * Created by SanAji on 20-12-2018.
 */

public class GramBean implements Serializable {

    String id;
    String districtName;
    String blockName;
    String panchayat;
    String date;
    String facilitator;
    String secretaryName;
    String secretaryMobile;
    String available;
    String sarpanchName;
    String sarpanchMobile;
    String address;

    public GramBean(String districtName, String blockName, String panchayat, String date, String facilitator, String secretaryName, String secretaryMobile, String available, String sarpanchName, String sarpanchMobile, String address) {
        this.districtName = districtName;
        this.blockName = blockName;
        this.panchayat = panchayat;
        this.date = date;
        this.facilitator = facilitator;
        this.secretaryName = secretaryName;
        this.secretaryMobile = secretaryMobile;
        this.available = available;
        this.sarpanchName = sarpanchName;
        this.sarpanchMobile = sarpanchMobile;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getPanchayat() {
        return panchayat;
    }

    public void setPanchayat(String panchayat) {
        this.panchayat = panchayat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFacilitator() {
        return facilitator;
    }

    public void setFacilitator(String facilitator) {
        this.facilitator = facilitator;
    }

    public String getSecretaryName() {
        return secretaryName;
    }

    public void setSecretaryName(String secretaryName) {
        this.secretaryName = secretaryName;
    }

    public String getSecretaryMobile() {
        return secretaryMobile;
    }

    public void setSecretaryMobile(String secretaryMobile) {
        this.secretaryMobile = secretaryMobile;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getSarpanchName() {
        return sarpanchName;
    }

    public void setSarpanchName(String sarpanchName) {
        this.sarpanchName = sarpanchName;
    }

    public String getSarpanchMobile() {
        return sarpanchMobile;
    }

    public void setSarpanchMobile(String sarpanchMobile) {
        this.sarpanchMobile = sarpanchMobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
