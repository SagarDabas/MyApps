package gui;

import core.FileInterface;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import utility.Extensions;
import utility.MainPanel;
import utility.NewsEdit;

/**
 *
 * @author Sagar
 */
public abstract class AbstractInfoWindow<T extends FileInterface> extends JPanel {

    /**
     *
     */
    private final FileInterface data;
    private ArrayList<T> list;
    private final int index;
    private final Class<T> fileClass;

    /**
     *
     * @param file
     * @param yourInfoWindow
     * @param editButton
     * @param deleteButton
     */
    public AbstractInfoWindow(final ArrayList<T> list, int index, Class<T> fileClass) {
        super();
        this.fileClass = fileClass;
        data = list.get(index);
        this.list = list;
        this.index = index;
    }
    
    
    /**
     *
     * @param yourInfoWindow
     */
    public final void addCustomGUI(final JPanel yourInfoWindow) {
        this.setLayout(new BorderLayout());
        
        if (yourInfoWindow == null) {
             //Adds the buttonsPanel
            this.add(buttonsPanel(), BorderLayout.SOUTH);
        
            this.add(defaultWindow(), BorderLayout.CENTER);
        } else {
            this.add(yourInfoWindow, BorderLayout.CENTER);
        }
    }

    
     /**
     *
     */
    private JPanel defaultWindow() {
       
        //'panel' would be added in the defaultPanel so that setPreferred method can be effective on the 'panel'.
        final JPanel defaultPanel = new JPanel();
        defaultPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        final JPanel top = new JPanel();
        top.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        top.setPreferredSize(new Dimension((int) (MainPanel.panelSize.width-10), 60));
        final JLabel topLabel = new JLabel(data.getTitle());
        topLabel.setFont(MainPanel.font);
        top.add(topLabel);
        final JSeparator separator1 = new JSeparator();
        separator1.setPreferredSize(new Dimension((int) (MainPanel.panelSize.width - 10), 2));
        top.add(separator1);
        panel.add(top, BorderLayout.NORTH);
        final JPanel center = new JPanel();
        center.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        final JLabel centerLabel = new JLabel(data.getDate());
        centerLabel.setFont(MainPanel.font);
        centerLabel.setPreferredSize(new Dimension((int) (MainPanel.panelSize.width-10), 20));
        center.add(centerLabel);
        panel.add(center, BorderLayout.CENTER);
        defaultPanel.add(panel);
        return defaultPanel;
    }


    /**
     *
     * @return
     */
    protected JPanel buttonsPanel() {
        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        buttonsPanel.setBackground(new Color(92, 126, 230));
        final JButton editButton = new JButton("Edit");
        editButton.setFont(MainPanel.font);
        editButton.setBackground(new Color(92, 126, 230));
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent event) {
                Extensions.editWindowByDataClass(fileClass, list, index);
            }
        });
        buttonsPanel.add(editButton);
        final JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(MainPanel.font);
        deleteButton.setBackground(new Color(92, 126, 230));
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent event) {
                final File file = Extensions.myFileByDataClass(fileClass);
                if(NewsEdit.delete(index, file, list, 2)) {
                    Extensions.listWindowByDataClass(fileClass);
                }
            }
        });
        buttonsPanel.add(deleteButton);
        return buttonsPanel;
    }


    /**
     *
     * @return Strings for the top and the sidebar.
     */
    public abstract String[] getString();
}
