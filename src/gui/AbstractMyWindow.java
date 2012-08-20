package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import utility.Extensions;
import utility.MainPanel;

/**
 *
 * @author Sagar
 */
public abstract class AbstractMyWindow extends JPanel {

    private HashMap<Integer, String> map = new HashMap<>();
    private String buttonName;
    private int counter = 0;
    private JPanel animatingPanels[];
    private ArrayList<String> stringList = new ArrayList<>();
    private JPanel container = new JPanel();
    private Dimension dimension = MainPanel.screenSize;

    public abstract String[] getString();

    public AbstractMyWindow() {
        super();
        setBackground(Color.black);
        setLayout(new BorderLayout());
        int inset = (int) (0.03*dimension.width);
        setBorder(BorderFactory.createEmptyBorder(inset, inset, inset, inset));
        container.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        container.setBackground(Color.black);
    }

    /**
     * @return the buttonName
     */
    public String getButtonName() {
        return buttonName;
    }

    /**
     * @param buttonName the buttonName to set
     */
    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public final void setMenuItem(String windowName) {
        stringList.add(windowName);
        map.put(counter, windowName);
        counter++;
    }

    public final void showMenu(boolean flag) {
        if (flag) {
            initMenu();
        }
    }

    private void initMenu() {
        int inset = (int) (0.01*dimension.width);
        GridLayout grid = new GridLayout(3,0,inset,inset);
        container.setLayout(grid);
        animatingPanels = new JPanel[counter];
        for (int i = 0; i < counter; i++) {
            animatingPanels[i] = drawRect(map.get(i)); 
        }
    }

    private JPanel drawRect(final String windowName) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                Extensions.windowByName(windowName);
            }
        });
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        container.add(panel);
        add(container);
        return panel;
    }

    /**
     * @return the stringList
     */
    public ArrayList<String> getStringList() {
        return (ArrayList<String>) stringList.clone();
    }

    protected JPanel[] getMenuItems() {
        return animatingPanels;
    }
}
