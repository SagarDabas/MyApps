package gui.newslo;

import core.FileInterface;
import core.newslo.Subscriber;
import gui.AbstractListWindow;
import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import utility.MainPanel;
import utility.NewsEdit;

/**
 * SubscriberListWindow is the panel for manipulating subscribers.
 *
 * @author Sagar
 */
public final class SubscriberListWindow extends AbstractListWindow {

    /**
     * Modify the list GUI created by AbstractListWindow.
     */
    public SubscriberListWindow() {
        super(NewsEdit.subsList, Subscriber.class);
        super.addCustomGUI(null);
    }

    /**
     *
     * @param l
     * @return
     */
    @Override
    public final JPanel getRightPanel(FileInterface data) {
        Subscriber subs = (Subscriber)data;
        JPanel buttonsPanel2 = new JPanel();
        ArrayList<String> gname = new ArrayList<>();
        for (int a : subs.getGroupId()) {
            gname.add(NewsEdit.groupList.get(a-1).getTitle());
        }
        String gnam[] = new String[gname.size()];
        gnam = gname.toArray(gnam);
        JComboBox jComboBox = new JComboBox();
        jComboBox.setBorder(null);
        jComboBox.setFont(MainPanel.font);
        jComboBox.setModel(new DefaultComboBoxModel(gnam));
        buttonsPanel2.add(jComboBox);
        return buttonsPanel2;
    }

    /**
     * Overrides the deleteButtonListener method for passing the specific parameters to the
     * arguments.
     *
     * @param index index of the entry in the arraylist, to be deleted.
     */
    @Override
    public boolean deleteButtonListener(final int index) {
        boolean flag = super.deleteButtonListener(index);
        //Make sure the logs for the user exist.
        if (NewsEdit.logList.size() > index) {
            NewsEdit.delete(index, new File("Logfile"), NewsEdit.logList, 1);
        }
        return flag;
    }

    /**
     * Returns the Array of string describing Settings Window.
     *
     * @return the Array of string describing Settings Window.
     */
    @Override
    public String[] getString() {
        return new String[]{"Click on the name to see the Subscriber's info","Subscribers"};
    }
}
