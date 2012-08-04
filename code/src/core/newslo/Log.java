package core.newslo;

import core.FileInterface;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Log class defines the properties of a log and implements the Serializable and
 * FileInterface Interface.
 *
 * @author Sagar
 */
public class Log extends FileInterface implements Serializable {

    public Log(String title) {
        setTitle(title);
    }
    /**
     * List of newsletters id.
     */
    private ArrayList<Integer> newsId;

    /**
     * @return the newsid
     */
    public ArrayList<Integer> getNewsId() {
        return newsId;
    }

    /**
     * @param newsid the newsid to set
     */
    public void setNewsId(ArrayList<Integer> newsId) {
        this.newsId = newsId;
    }
}