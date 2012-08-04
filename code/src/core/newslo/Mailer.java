package core.newslo;

import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {

     // The javamail session object.
    protected Session session;
    protected String from;
    /**
     * The sender's email address
     */
    protected String subject;
    /**
     * The subject of the message.
     */
    protected ArrayList<String> toList = new ArrayList<>();
    /**
     * The recipient ("To:"), as Strings.
     */
    protected String body;
    /**
     * The text of the message.
     */
    protected boolean verbose;

    /**
     * The verbosity setting
     */
    public String getFrom() {
        return from;
    }

    public void setFrom(String fm) {
        from = fm;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subj) {
        subject = subj;
    }

    public ArrayList<String> getToList() //Get tolist, as an array of Strings
    {
        return toList;
    }

    public void setToList(ArrayList<String> to) // Set to list to an ArrayList of Strings
    {
        toList = to;
    }

    public void addTo(String to) /**
     * Add one "to" recipient
     */
    {
        toList.add(to);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String text) {
        body = text;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean v) {
        verbose = v;
    }

    public boolean isComplete() {
        if (from == null || from.length() == 0) {
            // no FROM
            return false;
        }
        if (subject == null || subject.length() == 0) {
            // no SUBJECT
            return false;
        }
        if (toList.isEmpty()) {
            //no recipients
            return false;
        }
        if (body == null || body.length() == 0) {
           // no body
            return false;
        }

        return true;
    }

    public void doSend(String password) throws MessagingException {
        if (!isComplete()) {
            throw new IllegalArgumentException("doSend called before message was complete");
        }

        /**
         * Properties object used to pass props into the MAIL API
         */
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.auth", "true");


// Create the Session object
        Session session1 = Session.getDefaultInstance(props);
        session1.setDebug(true);

// create a message
        final Message mesg = new MimeMessage(session1);
        InternetAddress[] addresses;

// TO Address list
        addresses = new InternetAddress[toList.size()];
        for (int i = 0; i < addresses.length; i++) {
            addresses[i] = new InternetAddress((String) toList.get(i));
        }
        mesg.setRecipients(Message.RecipientType.TO, addresses);

// From Address
        mesg.setFrom(new InternetAddress(from));
// The Subject
        mesg.setSubject(subject);
        mesg.setContent(body, "text/plain");
// Now the message body.
        mesg.setText(body);

        Transport transportSSL = session1.getTransport();
        transportSSL.connect("smtp.gmail.com", 465, from, password); // account used

// Finally, send the message! (use static Transport method)
// Do this in a Thread as it sometimes is too slow for JServ
        transportSSL.sendMessage(mesg, mesg.getAllRecipients());
        //Done
    }

    public static void send(ArrayList<String> recipient, String sender,String Password, String subject, String message) throws MessagingException {
        Mailer m = new Mailer();
        m.setToList(recipient);
        m.setFrom(sender);
        m.setSubject(subject);
        m.setBody(message);
        m.doSend(Password);
    }
}