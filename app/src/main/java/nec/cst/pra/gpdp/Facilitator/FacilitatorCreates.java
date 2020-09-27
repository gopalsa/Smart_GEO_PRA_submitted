package nec.cst.pra.gpdp.Facilitator;

import java.io.Serializable;

/**
 * Created by SanAji on 20-12-2018.
 */

public class FacilitatorCreates implements Serializable {

    String id;
    String entityName;
    String userName;
    String designations;
    String EmailId;
    String mobileNo;

    public FacilitatorCreates(String entityName, String userName, String designations, String emailId, String mobileNo) {
        this.entityName = entityName;
        this.userName = userName;
        this.designations = designations;
        EmailId = emailId;
        this.mobileNo = mobileNo;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDesignations() {
        return designations;
    }

    public void setDesignation(String designations) {
        this.designations = designations;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setId(String id) {
        this.id = id;
    }
}
