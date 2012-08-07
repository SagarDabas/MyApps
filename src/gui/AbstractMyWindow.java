package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import utility.Extensions;
import utility.MainPanel;

/**
 *
 * @author Sagar
 */
public abstract class AbstractMyWindow extends JPanel {

    private static final int BIG_PANEL_WIDTH = (int) (0.18 * MainPanel.screenSize.width) + 5;
    private static final int SMALL_PANEL_WIDTH = (int) (0.09 * MainPanel.screenSize.width);
    private static final int PANEL_HEIGHT = (int) (0.09 * MainPanel.screenSize.width);
    private String buttonName;
    private int coordX;
    private int coordY;
    private int counter = 0;
    private Dimension dimension = new Dimension();
    private static final int OFFSET = 5;
    private ArrayList<String> stringList = new ArrayList<>();

    public abstract String[] getString();

    public AbstractMyWindow() {
        super();
        setBackground(Color.black);
        setLayout(null);
        setBounds(20, 0, MainPanel.screenSize.width, MainPanel.screenSize.height);
        coordX = this.getX() + OFFSET;
        coordY = this.getY() + OFFSET;
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

    public final JPanel createBigPanel(final String heading, final String content, final String myWindow, final Color color) {
        dimension.setSize(BIG_PANEL_WIDTH, PANEL_HEIGHT);
        counter += 2;
        return createPanel(heading, content, myWindow, color);
    }

    public final JPanel createSmallPanel(final String heading, final String content, final String myWindow, final Color color) {
        dimension.setSize(SMALL_PANEL_WIDTH, PANEL_HEIGHT);
        counter++;
        return createPanel(heading, content, myWindow, color);
    }

    private void updateBounds() {
        if (coordX + dimension.width > this.getX() + this.getWidth()) {
            coordY = coordY + dimension.height + OFFSET;
            coordX = this.getX() + OFFSET;
            if (dimension.width == BIG_PANEL_WIDTH) {
                counter = 2;
            } else if (dimension.width == SMALL_PANEL_WIDTH) {
                counter = 1;
            }
        } else {
            if (counter % 4 == 0) {
                coordX = coordX + dimension.width + OFFSET + 30;
                counter = 0;
            } else {
                coordX = coordX + dimension.width + OFFSET;
            }
        }
    }

    /**
     *
     * @param heading
     * @param content
     * @param myWindow
     * @param color
     * @return
     */
    private JPanel createPanel(final String heading, final String content, final String myWindow, final Color color) {
        
        stringList.add(myWindow);
        
        final JPanel panel = new JPanel();

        //when there is 1 small(1), 1 big(2) and 1 big(2) panel is  there
        if (counter % 5 == 0) {
            coordX = coordX + SMALL_PANEL_WIDTH + OFFSET + 30;
            counter = 2;
        }

        //Before settiing the bounds for the panel check whether it is overflowing the screen or not.
        if (coordX + dimension.width > this.getX() + this.getWidth()) {
            updateBounds();
        }

        //bounds of panel is set after updating.
        panel.setBounds(coordX, coordY, dimension.width, dimension.height);

        //updates the coordinates for the next panel.
        updateBounds();

        panel.setBackground(color);
        panel.setLayout(new BorderLayout());
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                Extensions.windowByName(myWindow);
                mouseExited(me);
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                panel.setBounds(panel.getX() - 1, panel.getY() - 1, panel.getWidth() + 2, panel.getHeight() + 2);
                panel.setBorder(BorderFactory.createLineBorder(Color.white));
            }

            @Override
            public void mouseExited(MouseEvent me) {
                panel.setBounds(panel.getX() + 1, panel.getY() + 1, panel.getWidth() - 2, panel.getHeight() - 2);
                final Color brighterColor = color.brighter();
                panel.setBorder(BorderFactory.createLineBorder(new Color(brighterColor.getRed(), brighterColor.getGreen(), brighterColor.getBlue())));
                panel.setBorder(BorderFactory.createLineBorder(brighterColor));
            }
        });
        final JPanel headingPanel = new JPanel();
        final JLabel headingLabel = new JLabel(heading);
        headingLabel.setFont(MainPanel.font);
        headingLabel.setForeground(Color.white);
        headingPanel.setBackground(null);
        headingPanel.add(headingLabel);
        panel.add(headingPanel, BorderLayout.NORTH);
        final JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        final JLabel area = new JLabel("<html>" + content + "</html>");
        area.setVerticalAlignment(SwingConstants.NORTH);
        area.setFont(MainPanel.font);
        area.setForeground(Color.white);
        area.setPreferredSize(dimension);
        contentPanel.add(area);
        contentPanel.setBackground(null);
        final Color brighterColor = color.brighter();
        panel.setBorder(BorderFactory.createLineBorder(new Color(brighterColor.getRed(), brighterColor.getGreen(), brighterColor.getBlue())));
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return panel;
    }

    /**
     * @return the stringList
     */
    public ArrayList<String> getStringList() {
        return (ArrayList<String>) stringList.clone();
    }
}
