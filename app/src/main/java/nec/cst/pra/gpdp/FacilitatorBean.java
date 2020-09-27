package nec.cst.pra.gpdp;

import nec.cst.pra.gpdp.Facilitator.FacilitatorCreates;
import nec.cst.pra.gpdp.FacilitatorFeedback.FacilitatorFeedback;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by SanAji on 21-12-2018.
 */

public class FacilitatorBean implements Serializable {
    ArrayList<FacilitatorCreates> facilitatorCreates;
    ArrayList<FacilitatorFeedback> facilitatorFeedbacks;

    String id;

    public FacilitatorBean(ArrayList<FacilitatorCreates> facilitatorCreates, ArrayList<FacilitatorFeedback> facilitatorFeedbacks) {
        this.facilitatorCreates = facilitatorCreates;
        this.facilitatorFeedbacks = facilitatorFeedbacks;
    }

    public ArrayList<FacilitatorCreates> getFacilitatorCreates() {
        return facilitatorCreates;
    }

    public void setFacilitatorCreates(ArrayList<FacilitatorCreates> facilitatorCreates) {
        this.facilitatorCreates = facilitatorCreates;
    }

    public ArrayList<FacilitatorFeedback> getFacilitatorFeedbacks() {
        return facilitatorFeedbacks;
    }

    public void setFacilitatorFeedbacks(ArrayList<FacilitatorFeedback> facilitatorFeedbacks) {
        this.facilitatorFeedbacks = facilitatorFeedbacks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
