package gui.newslo;

import core.FileInterface;
import core.newslo.Msettings;
import gui.AbstractInfoWindow;
import java.util.ArrayList;

/**
 *
 * @author Sagar
 * @param <T>
 */
public final class SettingsInfoWindow<T extends FileInterface> extends AbstractInfoWindow {

    /**
     *
     * @param list
     * @param index
     */
    public SettingsInfoWindow(ArrayList<T> list, int index) {
        super(list, index, Msettings.class);
        super.addCustomGUI(null);
    }

    @Override
    public String[] getString() {
        return new String[]{"EmailID and Password", "Settingsletter Info"};
    }
}
