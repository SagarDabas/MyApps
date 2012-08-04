package gui.newslo;

import core.FileInterface;
import core.newslo.Newsletter;
import gui.AbstractInfoWindow;
import java.util.ArrayList;

/**
 * 
 * @author Sagar
 * @param <T> 
 */
public final class NewsInfoWindow<T extends FileInterface> extends AbstractInfoWindow {

    /**
     * 
     * @param list
     * @param index 
     */
    public NewsInfoWindow(final ArrayList<T> list, final int index) {
        super(list, index, Newsletter.class);
        super.addCustomGUI(null);
    }

    @Override
    public String[] getString() {
        return new String[]{"Name and Description", "Newsletter Info"};
    }
}
