package core.newslo;

import core.FileInterface;
import java.io.Serializable;

/**
 * Subscriber class defines the properties of a Subscriber and implemennts the
 * Serializable and FileInterface Interface.
 *
 * @author Sagar
 */
public class Subscriber extends FileInterface implements Serializable {

    /**
     *
     */
    private String emailID;
    /**
     * 
     */
    private int[] groupIds;
    /**
     * 
     * @param title 
     */
    public Subscriber(String title) {
        setTitle(title);
    }
    
    /**
     * @return the emailID
     */
    public String getEmailID() {
        return emailID;
    }

    /**
     * @param emailID the emailID to set
     */
    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }
    public void setGroupId(int[] groupIDs) {
        this.groupIds = groupIDs;
    }

    public int[] getGroupId() {
        return groupIds;
    }

}