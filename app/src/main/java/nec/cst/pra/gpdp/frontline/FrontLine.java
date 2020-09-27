package nec.cst.pra.gpdp.frontline;

import java.io.Serializable;

/**
 * Created by SanAji on 20-12-2018.
 */

public class FrontLine implements Serializable {

    String frontgramName;
    String frontuserName;
    String frontdesignation;
    String frontmobileNo;
    String frontEmailId;
    String frontdeptname;
    String frontfrontLineWorker;

    public FrontLine(String frontgramName, String frontuserName, String frontdesignation, String frontmobileNo, String frontEmailId, String frontdeptname, String frontfrontLineWorker) {
        this.frontgramName = frontgramName;
        this.frontuserName = frontuserName;
        this.frontdesignation = frontdesignation;
        this.frontmobileNo = frontmobileNo;
        this.frontEmailId = frontEmailId;
        this.frontdeptname = frontdeptname;
        this.frontfrontLineWorker = frontfrontLineWorker;
    }

    public String getFrontgramName() {
        return frontgramName;
    }

    public void setFrontgramName(String frontgramName) {
        this.frontgramName = frontgramName;
    }

    public String getFrontuserName() {
        return frontuserName;
    }

    public void setFrontuserName(String frontuserName) {
        this.frontuserName = frontuserName;
    }

    public String getFrontdesignation() {
        return frontdesignation;
    }

    public void setFrontdesignation(String frontdesignation) {
        this.frontdesignation = frontdesignation;
    }

    public String getFrontmobileNo() {
        return frontmobileNo;
    }

    public void setFrontmobileNo(String frontmobileNo) {
        this.frontmobileNo = frontmobileNo;
    }

    public String getFrontEmailId() {
        return frontEmailId;
    }

    public void setFrontEmailId(String frontEmailId) {
        this.frontEmailId = frontEmailId;
    }

    public String getFrontdeptname() {
        return frontdeptname;
    }

    public void setFrontdeptname(String frontdeptname) {
        this.frontdeptname = frontdeptname;
    }

    public String getFrontfrontLineWorker() {
        return frontfrontLineWorker;
    }

    public void setFrontfrontLineWorker(String frontfrontLineWorker) {
        this.frontfrontLineWorker = frontfrontLineWorker;
    }
}
