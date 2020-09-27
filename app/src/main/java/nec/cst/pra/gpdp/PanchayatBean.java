package nec.cst.pra.gpdp;

import java.io.Serializable;

/**
 * Created by SanAji on 22-12-2018.
 */

public class PanchayatBean implements Serializable{
    String id;
    String userCreate;
    String userLocate;
    String userType;
    String search;

    public PanchayatBean(String userCreate, String userLocate, String userType, String search) {
        this.userCreate = userCreate;
        this.userLocate = userLocate;
        this.userType = userType;
        this.search = search;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }

    public String getUserLocate() {
        return userLocate;
    }

    public void setUserLocate(String userLocate) {
        this.userLocate = userLocate;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
