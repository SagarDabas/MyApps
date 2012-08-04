package gui.newslo;

import core.newslo.Msettings;
import gui.AbstractListWindow;
import utility.NewsEdit;

/**
 * SettingsListWindow is the panel for manipulating various settings.
 *
 * @author Sagar
 */
public final class SettingsListWindow extends AbstractListWindow {

    /**
     * Modify the list GUI created by AbstractListWindow.
     */
    public SettingsListWindow() {
        super(NewsEdit.settingsList, Msettings.class);
        super.addCustomGUI(null);
    }

    @Override
    public String[] getString() {
        return new String[]{"Set up as many emails ids as you want. You can choose"
                + " one, when you'll be sending mails.", "Emails IDs"};
    }
}