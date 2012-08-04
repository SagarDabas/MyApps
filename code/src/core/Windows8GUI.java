package core;

import gui.AbstractMyWindow;
import java.util.ArrayList;
import javax.swing.*;
import utility.CollapsiblePanel;
import utility.Extensions;
import utility.MainPanel;
import utility.NewsEdit;

/**
 * Windows8GUI.class is the frame for GUI.
 *
 * @author Sagar
 */
public class Windows8GUI extends JFrame {

    public Windows8GUI() {
        setSize(MainPanel.screenSize.width, MainPanel.screenSize.height);
        setUndecorated(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Newslo");
        setFont(MainPanel.font);

        JPanel initMainPanel = MainPanel.initMainPanel();
        add(initMainPanel);

        //setHiddenPanel();
        JLayeredPane layeredPane = CollapsiblePanel.setHiddenPanel();

        //Default window when program is started.
        final ArrayList<AbstractMyWindow> myWindows = Extensions.getMyWindows();
        MainPanel.changePanel(myWindows.get(0), myWindows.get(0).getString());

        getLayeredPane().setLayer(layeredPane, 400);
        getLayeredPane().add(layeredPane);
    }

    public static void main(String args[]) {
        
        // enable anti-aliased text:
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
        //reade all files. Should be changes to specific application files.
        NewsEdit.readFiles();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Windows8GUI().setVisible(true);
            }
        });
    }
}
