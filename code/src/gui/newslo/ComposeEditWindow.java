package gui.newslo;

import core.FileInterface;
import core.newslo.Compose;
import core.newslo.Group;
import core.newslo.Mail;
import core.newslo.Msettings;
import core.newslo.Newsletter;
import gui.AbstractEditWindow;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import utility.MainPanel;
import utility.NewsEdit;
import utility.Validator;

/**
 *
 * @author Sagar
 */
public class ComposeEditWindow<T extends FileInterface> extends AbstractEditWindow {

    private JTextField field1;
    private JTextArea field2;
    private ArrayList<JCheckBox> checkBoxes;
    private JButton saveButton;
    private ArrayList<JRadioButton> buttons;
    private int index;
    private ArrayList<T> list;

    /**
     *
     * @param list
     * @param index
     */
    public ComposeEditWindow(final ArrayList<T> list, final int index) {
        super(list, index, Compose.class);
        this.index = index;
        this.list = list;
        //all of the fields have to be instantiated first since addCustomGUI calls the setFields()
        //which makes  use of these fields. Otherwise nullPointerException would be thrown.
        JPanel checkBoxesPanel = checkBoxes();
        JPanel radioButtonsPanel = radioButtons();
        //initiates the defaultWindow object.
        super.addCustomGUI(null);
        //gets the defaultWindow and then checkBoxesPanel is added it.
        super.getDefaultWindow().add(checkBoxesPanel);
        super.getDefaultWindow().add(radioButtonsPanel);      
    }

    @Override
    protected final JComponent buttonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        buttonsPanel.setBackground(new Color(92, 126, 230));
        saveButton = new JButton();
        saveButton.setText("Send");
        saveButton.setFont(MainPanel.font);
        saveButton.setBackground(new Color(92, 126, 230));
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.setEnabled(false);
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (Validator.validateFields(getFieldsValidator(), list) && Validator.validateAreas(getAreasValidator(), list)) {
                    new Mail(checkBoxes).sendMail(index + 1, buttons, field1.getText(), field2.getText());
                }
            }
        });
        buttonsPanel.add(saveButton);
        return buttonsPanel;
    }

    
    /**
     *
     */
    private JPanel checkBoxes() {
        checkBoxes = new ArrayList<>();
        final JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(MainPanel.panelSize.width, 30));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        final JLabel label = new JLabel("Groups : ");
        label.setFont(MainPanel.font);
        panel.add(label);
        JCheckBox checkBox;
        for (Group group : NewsEdit.groupList) {
            checkBox = new JCheckBox();
            checkBox.setText(group.getTitle());
            panel.add(checkBox);
            checkBoxes.add(checkBox);
        }
        return panel;
    }

    /**
     * Method adds the radio buttons to select an email ID to send mails and
     * enables the send button if an radio button is selected.
     *
     * @param update the send button to send mails.
     * @return returns the array of radio buttons added.
     */
    private JPanel radioButtons() {
        final JLabel sender = new JLabel("Sender : ");
        sender.setFont(MainPanel.font);
        final JPanel senders = new JPanel();
        senders.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        senders.add(sender);
        buttons = new ArrayList<>();
        final ButtonGroup group = new ButtonGroup();
        for (Msettings g : NewsEdit.settingsList) {
            final JRadioButton radio = new JRadioButton();
            radio.setCursor(new Cursor(Cursor.HAND_CURSOR));
            radio.setBorderPainted(false);
            radio.setContentAreaFilled(false);
            radio.setFocusPainted(false);
            radio.setText(g.getTitle());
            buttons.add(radio);
            group.add(radio);
            radio.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(final ItemEvent event) {
                    if (radio.isSelected()) {
                        saveButton.setEnabled(true);
                    }
                }
            });
            senders.add(radio);
        }
        return senders;
    }

    /**
     *
     * @param groupIDs
     */
    private void setSelectedCheckBoxes(int[] groupIDs) {
        for (int groupID : groupIDs) {
            checkBoxes.get(groupID - 1).setSelected(true);
        }
    }

    /**
     *
     * @return
     */
    private int[] getSelectedCheckBoxes() {
        ArrayList<Integer> groupIds = new ArrayList<>();
        int count = 0;
        for (JCheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                groupIds.add(++count);
            }
        }
        int[] groupIDs = new int[count];
        for (int i = 0; i < count; i++) {
            groupIDs[i] = groupIds.get(i);
        }
        return groupIDs;
    }

    @Override
    public FileInterface getFields() {
        Compose data = new Compose(field1.getText());
        data.setBody(field2.getText());
        data.setGroupId(getSelectedCheckBoxes());
        return data;
    }

    @Override
    public final void setFields(FileInterface record) {
        Newsletter data = (Newsletter)record;
        field1.setText(data.getTitle());
        field2.setText(data.getBody());
        setSelectedCheckBoxes(data.getGroupId());
    }

    @Override
    public HashMap getTextFields() {
        field1 = new JTextField();
        final JLabel label1 = new JLabel("Subject :");
        HashMap<JLabel, JTextField> map = new HashMap<>();
        map.put(label1, field1);
        return map;
    }

    @Override
    public HashMap getTextAreas() {
        field2 = new JTextArea();
        final JLabel label2 = new JLabel("Body :");
        HashMap<JLabel, JTextArea> map = new HashMap<>();
        map.put(label2, field2);
        return map;
    }

    @Override
    public String[] getString() {
        return  new String[]{"Edit Newsletter and Select Recipient.", "Edit Newsletter"};
    }

    @Override
    public HashMap getFieldsValidator() {
        HashMap<JTextField, Validator[]> map = new HashMap<>();
        Validator[] constants = {Validator.VAL_NOT_NULL};
        map.put(field1, constants);
        return map;
    }

    @Override
    public HashMap getAreasValidator() {
        HashMap<JTextArea, Validator[]> map = new HashMap<>();
        Validator[] constants = {Validator.VAL_NOT_NULL};
        map.put(field2, constants);
        return map;
    }
}
