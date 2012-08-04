package gui.newslo;

import core.FileInterface;
import core.newslo.Compose;
import gui.AbstractInfoWindow;
import java.util.ArrayList;

/**
 * 
 * @author Sagar
 * @param <T> 
 */
public final class ComposeInfoWindow <T extends FileInterface> extends AbstractInfoWindow {

    /**
     * 
     * @param list
     * @param index 
     */
    public ComposeInfoWindow(final ArrayList<T> list, final int index) {
        super(list, index, Compose.class);
        super.addCustomGUI(null);
    }

    @Override
    public String[] getString() {
        return new String[]{"Name and Description", "Newsletter Info"};
    }
}
