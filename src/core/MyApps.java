package core;

import gui.AbstractMyWindow;
import java.util.ArrayList;
import javax.swing.*;
import utility.CollapsiblePanel;
import utility.Extensions;
import utility.MainPanel;

/**
 * MyApps.class is the frame for GUI.
 *
 * @author Sagar
 */
public class MyApps extends JFrame {

    static {
        // enable anti-aliased text:
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
    }
    
    public MyApps() {

        setSize(MainPanel.screenSize.width, MainPanel.screenSize.height);
        setUndecorated(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MyApps");
        setFont(MainPanel.font);

        JPanel initMainPanel = MainPanel.initMainPanel();
        add(initMainPanel);

        JLayeredPane layeredPane = CollapsiblePanel.setHiddenPanel();

        //Default window when program is started.
        final ArrayList<AbstractMyWindow> myWindows = Extensions.getMyWindows();
        MainPanel.changePanel(myWindows.get(0), myWindows.get(0).getString());

        getLayeredPane().setLayer(layeredPane, 400);
        getLayeredPane().add(layeredPane);
    }
}
