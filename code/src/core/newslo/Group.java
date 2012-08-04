package core.newslo;

import core.FileInterface;
import java.io.Serializable;

/**
 * Group class defines the properties of a group and implemennts the
 * Serializable and FileInterface Interface.
 *
 * @author Sagar
 */
public class Group extends FileInterface implements Serializable {

    public Group(String title){
        setTitle(title);
    }
    /**
     * 
     */
    private String description;

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}