package gui.newslo;

import core.FileInterface;
import core.newslo.Compose;
import gui.AbstractListWindow;
import java.util.ArrayList;
import javax.swing.JComponent;
import utility.Extensions;
import utility.NewsEdit;

/**
 * ComposeListWindow is the panel for manipulating and sending mails.
 *
 * @author Sagar
 */
public class ComposeListWindow<T extends FileInterface> extends AbstractListWindow {

    /**
     * Modify the list GUI created by AbstractListWindow.
     */
    public ComposeListWindow() {
        //Casting is not required here, but it is required in nameButtonListener, Why???
        super(NewsEdit.letterList, Compose.class);
        super.addCustomGUI(null);
    }

    @Override
    protected JComponent buttonsPanel() {
        JComponent buttonsPanel = super.buttonsPanel();
        if (!NewsEdit.letterList.isEmpty()) {
        buttonsPanel.remove(1);
        buttonsPanel.revalidate();
        }
        return  buttonsPanel;
    }

    
    @Override
    public final String[] getString() {
        return new String[]{"Select a Newsletter to send.", "Select Newsletter"};
    }

    @Override
    protected void nameButtonListener(int index) {
        //Why casting is required here??? But not in the super class.
        Extensions.editWindowByDataClass((Class<T>) Compose.class, (ArrayList<T>) NewsEdit.letterList, index);
    }
}
