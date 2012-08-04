package gui.newslo;

import core.FileInterface;
import core.newslo.Newsletter;
import gui.AbstractListWindow;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import utility.MainPanel;
import utility.NewsEdit;

/**
 * Newsletter is the panel for manipulating newsletters.
 *
 * @author Sagar
 */
public final class NewsListWindow extends AbstractListWindow {

    /**
     * Modify the list GUI created by AbstractListWindow.
     */
    public NewsListWindow() {
        super(NewsEdit.letterList, Newsletter.class);
        super.addCustomGUI(null);
    }

    @Override
    public String[] getString() {
        return new String[]{"Click on Newsletter name to see it's body.", "Newsletters"};
    }

    @Override
    public JPanel getRightPanel(FileInterface data) {
        Newsletter letter = (Newsletter) data;
        JPanel buttonsPanel2 = new JPanel();
        ArrayList<String> gname = new ArrayList<>();
        for (int a : letter.getGroupId()) {
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
}
