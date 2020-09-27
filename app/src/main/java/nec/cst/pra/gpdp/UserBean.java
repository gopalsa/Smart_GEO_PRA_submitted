package nec.cst.pra.gpdp;

import nec.cst.pra.gpdp.frontline.FrontLine;
import nec.cst.pra.gpdp.userdetail.UserDetail;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by SanAji on 20-12-2018.
 */

public class UserBean implements Serializable {
    ArrayList<UserDetail> userDetails;
    ArrayList<FrontLine> frontLines;

    String id;
    String deptname;
    String blockName;

    public UserBean(ArrayList<UserDetail> userDetails, ArrayList<FrontLine> frontLines, String deptname, String blockName) {
        this.userDetails = userDetails;
        this.frontLines = frontLines;
        this.deptname = deptname;
        this.blockName = blockName;
    }

    public UserBean(String s, String s1, ArrayList<UserDetail> userDetailArrayList, ArrayList<FrontLine> frontLineArrayList) {

    }

    public ArrayList<UserDetail> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(ArrayList<UserDetail> userDetails) {
        this.userDetails = userDetails;
    }

    public ArrayList<FrontLine> getFrontLines() {
        return frontLines;
    }

    public void setFrontLines(ArrayList<FrontLine> frontLines) {
        this.frontLines = frontLines;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }
}
