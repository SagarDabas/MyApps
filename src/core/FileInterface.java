package core;

import java.io.Serializable;

/**
 * This interface has to be implemented by the classes which stores the data.
 *
 * @author Sagar
 */
public abstract class FileInterface implements Serializable {

    public void setId(int dataID) {
        this.dataId = dataID;
    }

    public int getId() {
        return dataId;
        
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * ID of the log.
     */
    private int dataId;
    
    /**
     *
     */
    private String date;
    /**
     *
     */
    private String title;
}