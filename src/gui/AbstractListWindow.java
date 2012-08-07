package gui;

import core.FileInterface;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import utility.Extensions;
import utility.MainPanel;
import utility.FileEdit;
import utility.SearchFiles;

/**
 *
 * @author Sagar
 * @param <T>
 */
public abstract class AbstractListWindow<T extends FileInterface> extends AbstractGUI {

    private JPanel listPanel;
    private ArrayList<T> list;
    private JCheckBox[] checkBoxes;
    private int selected;
    private MouseListener[] myMouseListeners;
    private JPanel rightPanel;
    
    /*
     * Creates the GUI for the list read from the files. @param <T> T is the
     * type parameter which represent the type of the list. @param list
     * ArrayList for storing the list read from files.
     */
    public AbstractListWindow(final ArrayList<T> list) {
        this.list = list;
    }

    @Override
    protected final JPanel defaultWindow() {
        //Paints the listPanel.
        initListGUI(list);

        //Add the list panel to the JScrollPane.
        JScrollPane jScrollPane = new JScrollPane();
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        jScrollPane.setViewportView(listPanel);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setBorder(null);
        panel.add(jScrollPane);
        return panel;
    }

    /**
     *
     * @param list
     */
    private void initListGUI(ArrayList<T> list) {
        listPanel = new JPanel();
        listPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5));
        myMouseListeners = new MouseListener[list.size()];
        checkBoxes = new JCheckBox[list.size()];
        JLabel index;
        int dataIndex = 0;
        if (!list.isEmpty()) {

            //adds the select all checkbox.
            addSelectAllButton();

            int height = 320;
            for (T data : list) {
                dataIndex++;
                if (dataIndex >= 8) {
                    height = height + 52;
                }
                index = new JLabel();
                index.setFont(MainPanel.font);
                index.setText(data.getId() + ".");
                JPanel indexPanel = new JPanel();
                indexPanel.setPreferredSize(new Dimension(30, 40));
                indexPanel.add(index);
                listPanel.add(indexPanel);

                //Paints and returns the names on the list.
                JPanel buttonsPanel = addName(data, dataIndex - 1);
                listPanel.add(buttonsPanel);


                //Panels are sent to the Mouselisteners.
                myMouseListeners[dataIndex - 1] = myMouseListener(indexPanel, buttonsPanel, rightPanel);

                //Mouselisteners are added to the panels.
                index.addMouseListener(myMouseListeners[dataIndex - 1]);
                buttonsPanel.addMouseListener(myMouseListeners[dataIndex - 1]);

                //listPanel size is updated if the content overflows the available height.
                listPanel.setPreferredSize(new Dimension((int) (MainPanel.panelSize.width), height));
                listPanel.revalidate();
            }
        } else {
            index = new JLabel();
            index.setText("File is empty");
            listPanel.add(index);
        }
    }

    /**
     * Adds the checkbox at the top which when selected selects all checkboxes.
     */
    private void addSelectAllButton() {
        JPanel checkPanel = new JPanel();
        checkPanel.setPreferredSize(new Dimension((int) (MainPanel.panelSize.width), 20));
        checkPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JLabel label = new JLabel("No.");
        label.setFont(MainPanel.font);
        label.setPreferredSize(new Dimension(25, 20));
        checkPanel.add(label);
        JCheckBox selectAll = new JCheckBox();
        selectAll.setPreferredSize(new Dimension(40, 20));
        selectAll.addActionListener(new ActionListener() {
            boolean flag = false;

            @Override
            public void actionPerformed(ActionEvent ae) {
                flag = !flag;
                for (JCheckBox checkBox : checkBoxes) {
                    checkBox.setSelected(flag);
                }
            }
        });
        checkPanel.add(selectAll);
        JLabel name = new JLabel("Name");
        name.setFont(MainPanel.font);
        checkPanel.add(name);
        listPanel.add(checkPanel);
    }

    /**
     * Returns the names to be added on the window.
     *
     * @param data FileInterface object.
     * @param index
     * @return
     */
    private JPanel addName(T data, final int index) {
        JPanel buttonsPanel;
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        buttonsPanel.setPreferredSize(new Dimension((int) (MainPanel.panelSize.width) - 30, 40));

        checkBoxes[index] = new JCheckBox();
        buttonsPanel.add(checkBoxes[index]);
        final JButton nameButton = new JButton();
        nameButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nameButton.setBorderPainted(false);
        nameButton.setContentAreaFilled(false);
        nameButton.setFocusPainted(false);
        nameButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nameButton.setText(data.getTitle());
        nameButton.setFont(MainPanel.font);

        class MouseAdapterImpl extends MouseAdapter {

            @Override
            public void mouseEntered(MouseEvent evt) {
                Color color = new Color(92, 126, 230);
                nameButton.setForeground(color);
                myMouseListeners[index].mouseEntered(evt);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                nameButton.setForeground(Color.black);
                myMouseListeners[index].mouseExited(evt);
            }
        }
        MouseAdapterImpl mouseListener = new MouseAdapterImpl();
        checkBoxes[index].addMouseListener(mouseListener);
        nameButton.addMouseListener(mouseListener);
        nameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                nameButtonListener(index);
            }
        });
        buttonsPanel.add(nameButton);

        //Optional right panel , displayed along the names on the list.
        rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 7));
        rightPanel.setPreferredSize(new Dimension(MainPanel.panelSize.width - nameButton.getPreferredSize().width - 70, 40));
        //Optional right panel , displayed along the names on the list.
        JPanel panel = getRightPanel(data);
        if (panel != null) {
            panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            rightPanel.add(panel);
        }
        buttonsPanel.add(rightPanel);

        return buttonsPanel;
    }

    /**
     *
     * @param panel1
     * @param panel2
     * @param panel3
     * @return
     */
    private MouseListener myMouseListener(final JPanel panel1, final JPanel panel2, final JPanel panel3) {
        MouseListener myMouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent fe) {
                listPanel.scrollRectToVisible(panel2.getBounds());
                panel1.setBackground(Color.white);
                panel2.setBackground(Color.white);
                if (panel3 != null) {
                    panel3.setBackground(Color.white);
                }
            }

            @Override
            public void mouseExited(MouseEvent fe) {
                panel1.setBackground(null);
                panel2.setBackground(null);
                if (panel3 != null) {
                    panel3.setBackground(null);
                }
            }
        };
        return myMouseListener;
    }

    /**
     *
     * @param data
     * @return
     */
    public JPanel getRightPanel(final FileInterface data) {
        return null;
    }

    /**
     *
     * @param fileClass
     * @return
     */
    @Override
    protected JComponent buttonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        buttonsPanel.setBackground(new Color(92, 126, 230));
        JButton createButton = new JButton();
        createButton.setText("Create New");
        createButton.setFont(MainPanel.font);
        createButton.setBackground(new Color(92, 126, 230));
        createButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                createButtonListener();
            }
        });
        buttonsPanel.add(createButton);
        if (!list.isEmpty()) {
            JButton deleteButton = new JButton();
            deleteButton.setText("Delete");
            deleteButton.setFont(MainPanel.font);
            deleteButton.setBackground(new Color(92, 126, 230));
            deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ev) {
                    if (deleteMulti() == 0) {
                        int fileid = SearchFiles.searchByName(list);
                        if (fileid != -1) {
                            if (deleteButtonListener(fileid)) {
                                Extensions.windowByName(listWindow);
                            }
                        }
                    }
                }
            });
            buttonsPanel.add(deleteButton);
            JButton searchButton = new JButton();
            searchButton.setText("Search");
            searchButton.setFont(MainPanel.font);
            searchButton.setBackground(new Color(92, 126, 230));
            searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ev) {
                    int fileid = SearchFiles.searchByName(list);
                    System.out.println(fileid);
                    if (fileid != -1) {
                        myMouseListeners[fileid].mouseEntered(null);
                    }
                }
            });
            buttonsPanel.add(searchButton);
        }
        return buttonsPanel;
    }

    /**
     *
     */
    protected void createButtonListener() {
//        if (!FileEdit.groupList.isEmpty() || (AbstractListWindow.this instanceof GroupListWindow)) {
        Extensions.windowByName(editWindow,list,-1);
//        } else {
//            JOptionPane.showMessageDialog(null, "Please, first create a group.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
    }

    /**
     *
     * @param index
     */
    protected boolean deleteButtonListener(int index) {
        File file = Extensions.getMyFile(fileName);
        return FileEdit.delete(index, file, list, selected);
    }

    /**
     *
     * @param index
     */
    protected void nameButtonListener(int index) {
        Extensions.windowByName(infoWindow,list,index);
    }

    /**
     *
     * @return
     */
    private int deleteMulti() {
        int confirm;
        for (JCheckBox checkbox : checkBoxes) {
            if (checkbox.isSelected()) {
                selected = 1;
                break;
            }
        }
        if (selected == 1) {
            confirm = JOptionPane.showConfirmDialog(null,
                    "Do you really want to delete the selected records ?",
                    "Warning",
                    JOptionPane.OK_CANCEL_OPTION);

            if (confirm == JOptionPane.OK_OPTION) {
                int x = 0;
                for (int i = 0; i < checkBoxes.length; i++) {
                    if (checkBoxes[i].isSelected()) {
                        //arraylist index is updated after each deleteButtonListener operation.
                        deleteButtonListener(i - x);
                        x++;
                    }
                }
                Extensions.windowByName(listWindow);
            } else {
                selected = 2;
            }
        }
        return selected;
    }

}
