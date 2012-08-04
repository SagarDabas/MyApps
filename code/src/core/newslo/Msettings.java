package core.newslo;

import core.FileInterface;
import java.io.Serializable;

/**
 * Msettings class defines the properties of settings that is email-id and
 * password. And implemennts the Serializable and FileInterface Interface.
 *
 * @author Sagar
 */
public class Msettings extends FileInterface implements Serializable {

    public Msettings(String title) {
        setTitle(title);
    }
    /**
     *
     */
    private String emailID;
    /**
     *
     */
    private String password;

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

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}