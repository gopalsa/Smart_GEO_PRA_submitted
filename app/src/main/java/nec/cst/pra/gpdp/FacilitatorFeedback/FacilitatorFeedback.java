package nec.cst.pra.gpdp.FacilitatorFeedback;

import java.io.Serializable;

/**
 * Created by SanAji on 20-12-2018.
 */

public class FacilitatorFeedback implements Serializable {

    String gramName;
    String date;
    String people;
    String sc;
    String st;
    String shg;
    String women;
    String department;
    String frontLineWorker;
    String whetherAvailable;
    String whetherdelivered;
    String fund;
    String resource;
    String gaps;
    String resolution;

    public FacilitatorFeedback(String gramName, String date, String people, String sc, String st, String shg, String women, String department, String frontLineWorker, String whetherAvailable, String whetherdelivered, String fund, String resource, String gaps, String resolution) {
        this.gramName = gramName;
        this.date = date;
        this.people = people;
        this.sc = sc;
        this.st = st;
        this.shg = shg;
        this.women = women;
        this.department = department;
        this.frontLineWorker = frontLineWorker;
        this.whetherAvailable = whetherAvailable;
        this.whetherdelivered = whetherdelivered;
        this.fund = fund;
        this.resource = resource;
        this.gaps = gaps;
        this.resolution = resolution;
    }

    public String getGramName() {
        return gramName;
    }

    public void setGramName(String gramName) {
        this.gramName = gramName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getShg() {
        return shg;
    }

    public void setShg(String shg) {
        this.shg = shg;
    }

    public String getWomen() {
        return women;
    }

    public void setWomen(String women) {
        this.women = women;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFrontLineWorker() {
        return frontLineWorker;
    }

    public void setFrontLineWorker(String frontLineWorker) {
        this.frontLineWorker = frontLineWorker;
    }

    public String getWhetherAvailable() {
        return whetherAvailable;
    }

    public void setWhetherAvailable(String whetherAvailable) {
        this.whetherAvailable = whetherAvailable;
    }

    public String getWhetherdelivered() {
        return whetherdelivered;
    }

    public void setWhetherdelivered(String whetherdelivered) {
        this.whetherdelivered = whetherdelivered;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getGaps() {
        return gaps;
    }

    public void setGaps(String gaps) {
        this.gaps = gaps;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}
