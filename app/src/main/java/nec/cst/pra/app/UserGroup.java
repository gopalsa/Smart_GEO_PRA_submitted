package nec.cst.pra.app;

/**
 * Created at Magora Systems (http://magora-systems.com) on 08.07.16
 *
 * @author Stanislav S. Borzenko
 */
public class UserGroup {
    private String name;

    public UserGroup() {
    }

    public UserGroup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
