package nec.cst.pra.gpdp.userdetail;

import java.io.Serializable;

/**
 * Created by SanAji on 14-12-2018.
 */

public class UserDetail implements Serializable {


     String id;
     String userserialno;
     String userstateName;
     String useruserName;
     String usermobileNo;
     String userdesignation;
     String userentityType;
     String userentityName;
     String userEmailId;

    public UserDetail(String name, String userstateName, String useruserName, String usermobileNo, String userdesignation, String userentityType, String userentityName, String userEmailId) {
        this.userstateName = userstateName;
        this.useruserName = useruserName;
        this.usermobileNo = usermobileNo;
        this.userdesignation = userdesignation;
        this.userentityType = userentityType;
        this.userentityName = userentityName;
        this.userEmailId = userEmailId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserserialno() {
        return userserialno;
    }

    public void setUserserialno(String userserialno) {
        this.userserialno = userserialno;
    }

    public String getUserstateName() {
        return userstateName;
    }

    public void setUserstateName(String userstateName) {
        this.userstateName = userstateName;
    }

    public String getUseruserName() {
        return useruserName;
    }

    public void setUseruserName(String useruserName) {
        this.useruserName = useruserName;
    }

    public String getUsermobileNo() {
        return usermobileNo;
    }

    public void setUsermobileNo(String usermobileNo) {
        this.usermobileNo = usermobileNo;
    }

    public String getUserdesignation() {
        return userdesignation;
    }

    public void setUserdesignation(String userdesignation) {
        this.userdesignation = userdesignation;
    }

    public String getUserentityType() {
        return userentityType;
    }

    public void setUserentityType(String userentityType) {
        this.userentityType = userentityType;
    }

    public String getUserentityName() {
        return userentityName;
    }

    public void setUserentityName(String userentityName) {
        this.userentityName = userentityName;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }
}
