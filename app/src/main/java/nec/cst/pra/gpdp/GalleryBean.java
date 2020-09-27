package nec.cst.pra.gpdp;

/**
 * Created by SanAji on 22-12-2018.
 */

public class GalleryBean {

    String id;
    String images;
    String profileImage;
    String remark;
    String testmonial;

    public GalleryBean( String images, String profileImage, String remark, String testmonial) {

        this.images = images;
        this.profileImage = profileImage;
        this.remark = remark;
        this.testmonial = testmonial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTestmonial() {
        return testmonial;
    }

    public void setTestmonial(String testmonial) {
        this.testmonial = testmonial;
    }

}
