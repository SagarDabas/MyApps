package gui.newslo;

import gui.AbstractMyWindow;
import java.awt.Color;

/**
 *
 * @author Sagar
 */
public final class Newslo extends AbstractMyWindow {

    /**
     *
     */
    public Newslo() {
        super();
        setButtonName("Newslo");
        add(createBigPanel("Groups", "Manage groups and add newsletters,"
                + " subscribers to the <br/>groups.", "Newslo:Group", new Color(162, 0, 255)));
        add(createBigPanel("Subscribers", "Manage subscribers.",
                "Newslo:Subscriber", new Color(240, 150, 9)));
        add(createBigPanel("Newsletters", "Manage newsletters, add,"
                + " delete, search, edit newsletter to the<br/> groups.",
                "Newslo:Newsletter", new Color(51, 153, 51)));
        add(createBigPanel("Mail", "Send newsletter or create a new"
                + " mail to send it to the groups.", "Newslo:Compose", new Color(216, 0, 115)));
        add(createBigPanel("Settings", "Manage your accounts for"
                + " sending mails to the groups.", "Newslo:Settings", new Color(27, 161, 226)));
    }

    @Override
    public String[] getString() {
        return new String[]{"Manage Newsletters, Subscribers,"
                + " Groups, Email IDs, Autoresponders, Send mails or Schedule Mails", "Newslo"};
    }
}
