package utility;

import gui.AbstractMyWindow;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Class MainPanel represents the main GUI of the Newslo. SidePanel, TopPanel
 * and BottonPanel is fixed. CenterPanel is updated in accordance with the menu
 * item selected. changePanel(javax.swing.JPanel,String[]) updates the
 * CenterPanel.
 *
 * @author Sagar
 */
public final class MainPanel {

    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final Dimension panelSize = new Dimension(screenSize.width - (int) (0.15 * screenSize.width), screenSize.height);
    public static final Font font = getLightFont();
    private static JPanel lastWindow;
    public static JPanel presentWindow;
    private static String[] lastString;
    private static String[] presentString;
    private static boolean flag = false;
    private static ArrayList<String> stringList = new ArrayList<>();
    
    /**
     *
     * @return
     */
    public static JPanel initMainPanel() {

        topLabel = new JLabel();
        topLabel.setHorizontalAlignment(SwingConstants.LEFT);
        topLabel.setOpaque(true);
        topLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));


        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        bottomLabel = new JLabel();
        bottomLabel.setBackground(new Color(255, 255, 255));
        bottomLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        bottomLabel.setFont(font);
        bottomLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomLabel.setOpaque(true);

        container = new JPanel();
        container.setLayout(new BorderLayout());
        container.add(topLabel, BorderLayout.NORTH);
        container.add(centerPanel, BorderLayout.CENTER);
        container.add(bottomLabel, BorderLayout.SOUTH);
        return container;
    }

    private static Font getLightFont() {
        Font font1 = null;
        try {
            font1 = Font.createFont(Font.TRUETYPE_FONT, MainPanel.class.getResourceAsStream("/core/SegoeWP-Semilight.ttf"));
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return font1.deriveFont(16f);
    }

    /**
     * Replaces the center panel and labels on the sidebar and below the
     * menubar.
     *
     * @param select JPanel by which to change the center panel.
     * @param side array of String by which to change the sidebar and menubar
     * labels.
     */
    public static void changePanel(final JPanel select, final String[] side) {

        setLastWindow(select, side);

        if (presentWindow instanceof AbstractMyWindow) {

            CollapsiblePanel.hideAlways();

            stringList = ((AbstractMyWindow) presentWindow).getStringList();
            
            topLabel.setBackground(Color.BLACK);
            topLabel.setForeground(Color.white);
            topLabel.setFont(font.deriveFont(60f));
        } else {

            CollapsiblePanel.showAlways(stringList);

            topLabel.setBackground(new Color(110, 110, 110));
            topLabel.setForeground(Color.white);
            topLabel.setFont(font.deriveFont(24f));
        }
        topLabel.setText(" "+side[1]);
        bottomLabel.setText(side[0]);
        centerPanel.removeAll();
        centerPanel.add(select, BorderLayout.CENTER);
        centerPanel.repaint();
        centerPanel.revalidate();
    }

    /**
     *
     * @param panel
     * @param side
     */
    private static void setLastWindow(final JPanel panel, final String[] side) {
        if (!flag) {
            lastWindow = panel;
            lastString = side;
            presentWindow = panel;
            presentString = side;
            flag = true;
        } else {
            lastWindow = presentWindow;
            lastString = presentString;
            presentWindow = panel;
            presentString = side;
        }
    }

    public static JButton lastWindowButton() {
        JButton button = new JButton();
        button.setBackground(null);
        button.setForeground(Color.white);
        button.setText("Go Back");
        button.setFont(MainPanel.font);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                changePanel(lastWindow, lastString);
            }
        });
        return button;
    }

    public static JButton closeButton() {
        JButton button = new JButton();
        button.setBackground(null);
        button.setForeground(Color.white);
        button.setFont(MainPanel.font);
        button.setText("Exit");
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        return button;
    }

    /**
     * Private constructor to ensure nobody can instantiate objects.
     */
    private MainPanel() {
    }
    /**
     * MainPanel is the parent of all other components.
     */
    private static JPanel container;
    /**
     * Panel on the left side. Messages are displayed on this panel.
     */
    private static JLabel bottomLabel;
    /**
     * Label above the centerPanel. It displays the name of the window.
     */
    private static JLabel topLabel;
    /**
     * In this panel data is displayed by adding AbstractListWindow panel's in
     * it using changePanel() method.
     */
    private static JPanel centerPanel;
}
