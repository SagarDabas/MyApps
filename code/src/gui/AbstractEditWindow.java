package gui;

import core.FileInterface;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import utility.Extensions;
import utility.MainPanel;
import utility.NewsEdit;
import utility.SearchFiles;
import utility.Validator;

/**
 *
 * @author Sagar
 */
public abstract class AbstractEditWindow<T extends FileInterface> extends JPanel {

    private Class<T> fileClass;

    /**
     *
     * @param file
     * @param list
     * @param index
     */
    public AbstractEditWindow(final ArrayList<T> list, int index, Class<T> fileClass) {
        super();
        this.fileClass = fileClass;
        this.list = list;
        this.index = index;
    }

    /**
     *
     * @param yourEditWindow
     */
    protected final void addCustomGUI(final JComponent yourEditWindow) {
        setLayout(new BorderLayout());
        if (yourEditWindow == null) {
            this.add(buttonsPanel(), BorderLayout.SOUTH);
            this.add(defaultWindow(), BorderLayout.CENTER);
        } else {
            this.add(yourEditWindow, BorderLayout.CENTER);
        }
    }

    /**
     *
     * @return
     */
    private JPanel defaultWindow() {

        defaultWindow = new JPanel();
        defaultWindow.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        HashMap<JLabel, JTextField> textFields = getTextFields();
        if (textFields != null) {
            for (Map.Entry<JLabel, JTextField> entry : textFields.entrySet()) {
                final JPanel labelField = new JPanel();
                labelField.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
                labelField.setPreferredSize(new Dimension((int) (MainPanel.panelSize.width), 30));
                final JLabel label = entry.getKey();
                label.setFont(MainPanel.font);
                labelField.add(label);
                final JTextField field = entry.getValue();
                field.setPreferredSize(new Dimension((int) (0.9 * MainPanel.panelSize.width), 20));
                labelField.add(field);
                defaultWindow.add(labelField);
            }
        }
        HashMap<JLabel, JTextArea> textAreas = getTextAreas();
        if (textAreas != null) {
            for (Map.Entry<JLabel, JTextArea> entry : textAreas.entrySet()) {
                final JLabel label = entry.getKey();
                label.setFont(MainPanel.font);
                label.setPreferredSize(new Dimension((int) (MainPanel.panelSize.width), 30));
                defaultWindow.add(label);
                final JTextArea area = entry.getValue();
                area.setLineWrap(true);
                area.setPreferredSize(new Dimension((int) (0.96*MainPanel.panelSize.width), 100));
                JScrollPane jScrollPane = new JScrollPane();
                jScrollPane.setViewportView(area);
                defaultWindow.add(jScrollPane);

            }
        }

        //After initializing fields set the fields if index is not -1 that is
        //when a record is edited not newly created.
        if (index != -1) {
            setFields(list.get(index));
        }

        return defaultWindow;
    }

    /**
     *
     */
    protected JComponent buttonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        buttonsPanel.setBackground(new Color(92, 126, 230));
        JButton saveButton = new JButton("Save");
        saveButton.setFont(MainPanel.font);
        saveButton.setBackground(new Color(92, 126, 230));
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                if (Validator.validateFields(getFieldsValidator(), list) && Validator.validateAreas(getAreasValidator(), list)) {
                    save();
                }
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(MainPanel.font);
        cancelButton.setBackground(new Color(92, 126, 230));
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                if (index != -1) {
                    Extensions.infoWindowByDataClass(fileClass, list, index);
                } else {
                    Extensions.listWindowByDataClass(fileClass);
                }
            }
        });
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);
        return buttonsPanel;
    }

    /**
     *
     * @param file
     */
    private void save() {
        final T data = initData();
        final File file = Extensions.myFileByDataClass(fileClass);
        index = NewsEdit.edit(index, file, list, data);
        Extensions.infoWindowByDataClass(fileClass, list, index);
    }

    /**
     *
     * @return
     */
    private T initData() {
        final T data = getFields();
        if (index == -1) {
            data.setId(list.size() + 1);
            data.setDate(SearchFiles.getDate());
        } else {
            final String date = list.get(index).getDate();
            data.setId(index + 1);
            data.setDate(date);
        }
        return data;
    }

    public HashMap<JLabel, JTextField> getTextFields() {
        return null;
    }

    public HashMap<JLabel, JTextArea> getTextAreas() {
        return null;
    }

    public HashMap<JTextField, Validator[]> getFieldsValidator() {
        return null;
    }

    public HashMap<JTextArea, Validator[]> getAreasValidator() {
        return null;
    }

//    public final JPanel getButtonsPanel() {
//        return buttonsPanel;
//    }
//
//    public final JButton getSaveButton() {
//        return saveButton;
//    }
//
//    public final JButton getCancelButton() {
//        return cancelButton;
//    }
    public final JPanel getDefaultWindow() {
        return defaultWindow;
    }
    private JPanel defaultWindow;
    private final ArrayList<T> list;
    private int index;

    /**
     * Sets the textfields, textareas and other inputs from the FileInterface
     * object.
     */
    public abstract void setFields(T data);

    /**
     * Updates or edits the FileInterface object with fields value and returns
     * the updated object.
     *
     * @return FileInterface object.
     */
    public abstract T getFields();

    /**
     *
     * @return Strings for the top and the sidebar.
     */
    public abstract String[] getString();
}