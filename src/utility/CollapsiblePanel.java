package utility;

import gui.AbstractMyWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Sagar
 */
public class CollapsiblePanel {

    private static final JPanel buttonsPanel = new JPanel();
    private static MyMouseListener mouseListener = new MyMouseListener();
    private static int frameHeight = MainPanel.screenSize.height;
    private static final int frameWidth = MainPanel.screenSize.width;
    private static final int hidden_width = (int) (0.15 * frameWidth);
    private final static JLayeredPane layeredPane = new JLayeredPane();
    private static boolean showAlways;
    private static int height;

    /**
     *
     * @return
     */
    public static JLayeredPane setHiddenPanel() {
        buttonsPanel.setBounds(frameWidth, 0, hidden_width, frameHeight);
        buttonsPanel.setBackground(new Color(20, 20, 20));
        buttonsPanel.setOpaque(true);
        layeredPane.add(buttonsPanel);
        return layeredPane;
    }

    /**
     *
     */
    public static void hideAlways() {
        showAlways = false;
        height = 0;
        buttonsPanel.removeAll();
        //Adds the menu items in the sidebar.

        for (Map.Entry<String, AbstractMyWindow> entry : Extensions.myWindows.entrySet()) {
            AbstractMyWindow myWindow = entry.getValue();
            JButton button = getButton(myWindow.getButtonName(), myWindow);
            button.setFont(MainPanel.font.deriveFont(20f));
            buttonsPanel.add(button);
            height += 40;
        }

        addDefaultButtons();

        layeredPane.setBounds(frameWidth - hidden_width, 0, hidden_width, frameHeight);
        layeredPane.addMouseListener(mouseListener);
        buttonsPanel.setVisible(false);

    }

    /**
     *
     * @param stringList
     */
    public static void showAlways(ArrayList<String> stringList) {
        showAlways = true;
        buttonsPanel.removeAll();
        height = 0;
        if (!stringList.isEmpty()) {
            boolean flag = true;
            for (final String string : stringList) {
                String[] strings = string.split(":");
                if (flag) {
                    flag = false;
                    final String appName = strings[0];
                    AbstractMyWindow myWindow = Extensions.myWindows.get(appName);
                    JButton button = getButton(myWindow.getButtonName(), myWindow);
                    button.setBackground(new Color(50, 50, 50));
                    button.setForeground(Color.white);
                    button.setFont(MainPanel.font.deriveFont(20f));
                    buttonsPanel.add(button);
                    height += 40;
                }
                JButton button = getButton(strings[1], null);
                button.removeActionListener(button.getActionListeners()[0]);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        Extensions.windowByName(string);
                    }
                });
                buttonsPanel.add(button);
                height += 35;
            }
        }

        addDefaultButtons();

        buttonsPanel.setBounds(0, 0, hidden_width, frameHeight);
        layeredPane.removeMouseListener(mouseListener);
        buttonsPanel.setVisible(true);
    }

    /**
     * Method creates the buttons in the top that is the buttons in the menu and
     * their listeners.
     *
     * @param jButton instance of JButton.
     * @param text Text displayed on the Buttons.
     * @param buttonID IDs of the buttons.
     * @return returns the JButton.
     */
    private static JButton getButton(final String text, final AbstractMyWindow myWindow) {

        JButton jButton = new JButton(text);
        jButton.setBackground(new Color(20, 20, 20));
        jButton.setForeground(Color.white);
        jButton.setFont(MainPanel.font);
        jButton.setFocusPainted(false);
        jButton.setBorderPainted(false);
        jButton.setContentAreaFilled(false);
        jButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButton.setOpaque(true);
        jButton.setPreferredSize(new Dimension(hidden_width, 30));
        jButton.addMouseListener(mouseListener);
        jButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent event) {
                MainPanel.changePanel(myWindow, myWindow.getString());
            }
        });
        return jButton;

    }

    /**
     *
     */
    private static void addDefaultButtons() {
        MouseAdapterImpl mouseAdapterImpl = new MouseAdapterImpl();

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(20, 20, 20));
        bottomPanel.setPreferredSize(new Dimension(hidden_width, frameHeight - height));
        bottomPanel.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(hidden_width, 30));
        panel.setLayout(new GridLayout(1, 2));

        //close button to exit the application.
        final JButton closeButton = MainPanel.closeButton();
        closeButton.addMouseListener(mouseAdapterImpl);
        //Adds the close button in the sidebar.
        panel.add(closeButton);

        //the last window button in the sidebar.
        JButton lastWindowButton = MainPanel.lastWindowButton();
        lastWindowButton.addMouseListener(mouseAdapterImpl);
        panel.add(lastWindowButton);


        panel.setBackground(new Color(50, 50, 50));
        bottomPanel.add(panel, BorderLayout.SOUTH);
        buttonsPanel.add(bottomPanel);
    }

    /**
     *
     */
    private static class MyActionListener implements ActionListener {

        private static final int frames = 10;
        int count = 0;
        private final Timer timer = new Timer(10, this);

        public void start() {
            timer.start();
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (count >= frames) {
                timer.stop();
            } else {
                count++;
                buttonsPanel.setBounds(hidden_width - hidden_width * count / frames, 0, hidden_width, frameHeight);
            }
        }
    }

    /**
     *
     */
    private static class MyMouseListener extends MouseAdapter {

        private MyActionListener timerListener;

        @Override
        public void mouseEntered(final MouseEvent event) {

            final Color color = new Color(50, 50, 50);
            if (event.getSource() instanceof JButton) {
                final JButton button = (JButton) event.getSource();
                button.setBackground(color);
                buttonsPanel.setVisible(true);
            } else if (!buttonsPanel.isVisible()) {
                timerListener = new MyActionListener();
                timerListener.start();
                buttonsPanel.setVisible(true);
            }
        }

        @Override
        public void mouseExited(final MouseEvent event) {
            Point point = new Point(0, 0);
            if (event.getSource() instanceof JButton) {
                final JButton button = (JButton) event.getSource();
                point = button.getLocation();
                button.setBackground(new Color(20, 20, 20));
            }
            if (!showAlways && !layeredPane.contains(point.x + event.getX(), point.y + event.getY())) {
                buttonsPanel.setVisible(false);
                buttonsPanel.setBounds(frameWidth, 0, hidden_width, frameHeight);
            }
        }
    }

    /**
     *
     */
    private static class MouseAdapterImpl extends MouseAdapter {

        JButton button;

        @Override
        public void mouseEntered(MouseEvent event) {
            if (event.getSource() instanceof JButton) {
                button = (JButton) event.getSource();
                button.setBackground(new Color(20, 20, 20));
            }
        }

        @Override
        public void mouseExited(MouseEvent event) {
            if (event.getSource() instanceof JButton) {
                button.setBackground(null);
            }
        }
    }
}
