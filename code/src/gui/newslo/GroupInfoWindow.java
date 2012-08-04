package gui.newslo;

import core.FileInterface;
import core.newslo.Group;
import gui.AbstractInfoWindow;
import java.util.ArrayList;

/**
 * 
 * @author Sagar
 * @param <T> 
 */
public class GroupInfoWindow<T extends FileInterface> extends AbstractInfoWindow {

    /**
     * 
     * @param list
     * @param index 
     */
    public GroupInfoWindow(final ArrayList<T> list, final int index) {
        super(list, index, Group.class);
        super.addCustomGUI(null);
    }

    @Override
    public final String[] getString() {
        return new String[]{"Name and Description", "Groupletter Info"};
    }
}
