package core.newslo;

import java.io.File;
import java.util.ArrayList;
import javax.mail.MessagingException;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import utility.MainPanel;
import utility.NewsEdit;
import utility.SearchFiles;

/**
 * Mail class provides the API for sending mails.
 *
 * @author Sagar
 */
public class Mail {

    /**
     * List of recipients email-ids.
     */
    private ArrayList<String> recipients;
    /**
     * List of recipients ids.
     */
    private ArrayList<Integer> recipientsid;
    /**
     * List of checkboxes represents selected groups for sending mails.
     */
    private ArrayList<JCheckBox> checkList;

    /**
     * Contructor initializes list of checkboxes that is checkList.
     *
     * @param checks
     */
    public Mail(ArrayList<JCheckBox> checks) {
        checkList = checks;
    }

    /**
     * Method sends the mail.
     *
     * @param newsID ID of the Subscriber.
     * @param buttons ArrayList of Radiobuttons. These buttons are used to get
     * the email-id and password of the sender.
     * @param name Subject of the Newsletter.
     * @param body Body of the Newsletter.
     */
    public final void sendMail(final int newsID, final ArrayList<JRadioButton> buttons, final String name, final String body) {
        final int check = getRecepients(0);
        if (check != 0) {
            String sender = "";
            String password = "";
            for (JRadioButton b : buttons) {
                if (b.isSelected()) {
                    final int j = buttons.indexOf(b);
                    sender = NewsEdit.settingsList.get(j).getTitle();
                    password = NewsEdit.settingsList.get(j).getPassword();
                    break;
                }
            }
            final JPanel report = new JPanel();
            JLabel status;
            try {
                Mailer.send(recipients, sender, password, name, body);
                status = new JLabel("Your message successfully sent.");
                updateLogFile(newsID);

            } catch (MessagingException e) {
                status = new JLabel("Error Occured");
            }
            report.add(status);
            final String[] editString = {"<html><center>Mail Status<br>"
                + "<font size='-2'>Click any tab from the top<br>"
                + "to go back.</font></center></html>",
                "Mail Status"};
            MainPanel.changePanel(report, editString);
        }
    }

    /**
     * Method initializes the ArrayList of recipients. It finds the subscribers
     * in the groups selected and adds the email-ids of the subscribers in the
     * recipients list and the id's of the recipients in the recipientsid list.
     *
     * @param check flag variable to indicate whether the selected group have
     * any subscribers or not.
     * @return 1 if the group selected have subscribers.
     */
    private int getRecepients(int check) {
        recipients = new ArrayList<>();
        recipientsid = new ArrayList<>();
        for (JCheckBox chk : checkList) {
            final int j = checkList.indexOf(chk);
            if (chk.isSelected()) {
                final Group gr = NewsEdit.groupList.get(j);
                for (Subscriber subs : NewsEdit.subsList) {
                    for (int a : subs.getGroupId()) {
                        if (gr.getId() == a) {
                            recipients.add(subs.getEmailID());
                            recipientsid.add(subs.getId());
                            check = 1;
                            break;
                        }
                    }
                }
            }
        }
        return check;
    }

    /**
     * Method updates the logs for a particular subscriber when a mail is sent
     * to him.
     *
     * @param newsID Newsletter's ID
     */
    private void updateLogFile(int newsID) {
        for (int recipientID : recipientsid) {
            ArrayList<Integer> newsIDs = new ArrayList<>();
            if (!NewsEdit.logList.isEmpty()) {
                int j = 0;
                for (int i = 0; i < NewsEdit.logList.size(); i++) {
                    Log log = NewsEdit.logList.get(i);
                    if (log.getId() == recipientID) {
                        newsIDs = log.getNewsId();
                        createLog(newsIDs, newsID, recipientID, i);
                        j = 101;
                        break;
                    }
                }
                if (j != 101) {
                    createLog(newsIDs, newsID, recipientID, -1);
                }
            } else {
                createLog(newsIDs, newsID, recipientID, -1);
            }
        }
    }

    private void createLog(ArrayList<Integer> newsIDs, int newsID, int recipientID, int index) {
        Log newLog;
        newsIDs.add(newsID);
        String date = SearchFiles.getDate();
        newLog = new Log(date);
        newLog.setId(recipientID);
        newLog.setNewsId(newsIDs);
        newLog.setDate(date);
        NewsEdit.edit(index, new File("Logfile"), NewsEdit.logList, newLog);
    }
}
