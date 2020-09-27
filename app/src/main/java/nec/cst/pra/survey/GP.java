package nec.cst.pra.survey;

public class GP {

    String village;
    String district;
    String state;

    public GP(String village, String district, String state) {
        this.village = village;
        this.district = district;
        this.state = state;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
