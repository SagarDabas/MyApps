package core.newslo;

import core.FileInterface;

/**
 * Newsletter class defines the properties of Newsletter and implemennts the
 * Serializable and FileInterface Interface.
 *
 * @author Sagar
 */
public class Compose extends FileInterface{

    /**
     *
     * @param title
     */
    public Compose(String title) {
        setTitle(title);
    }
    /**
     *
     */
    private String body;
    /**
     * 
     */
    private int[] groupIds;

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }
    public void setGroupId(int[] groupIDs) {
        this.groupIds = groupIDs;
    }

    public int[] getGroupId() {
        return groupIds;
    }

}