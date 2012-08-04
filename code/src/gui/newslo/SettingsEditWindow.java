package gui.newslo;

import core.FileInterface;
import core.newslo.Msettings;
import gui.AbstractEditWindow;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
//import java.awt.event.ItemEvent;
//import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import utility.Validator;

/**
 *
 * @author Sagar
 */
public final class SettingsEditWindow<T extends FileInterface> extends AbstractEditWindow {

    private JTextField field1;
    private JPasswordField field2;
    private JRadioButton[] radioButtons;

    public SettingsEditWindow(final ArrayList<T> list, int index) {
        super(list, index, Msettings.class);
        super.addCustomGUI(customPanel());
        this.add(buttonsPanel(), BorderLayout.SOUTH);
        //adds the default buttons Panel along the customPanel.
        //setting fields for the customPanel. No need to invoke this method for defaultWindow.
        if (index != -1) {
            setFields(list.get(index));
        }
    }

    private JPanel customPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 20));

        final JPanel center = new JPanel();
        center.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        center.setPreferredSize(new Dimension(200, 300));

        final JLabel mail = new JLabel("Enter your Email ID and Password.");
        center.add(mail);

        final JSeparator separator1 = new JSeparator();
        separator1.setPreferredSize(new Dimension(200, 2));
        center.add(separator1);

        final JLabel name1 = new JLabel("Email ID :    ");
        center.add(name1);
        field1 = new JTextField();
        field1.setPreferredSize(new Dimension(120, 20));
        center.add(field1);
        final JLabel password = new JLabel("Password : ");
        center.add(password);
        field2 = new JPasswordField();
        field2.setPreferredSize(new Dimension(120, 20));
        center.add(field2);

        final JLabel server = new JLabel("<html><br><br><strong>Select your"
                + " mailing server.</strong></html>");
        center.add(server);
        final JSeparator separator2 = new JSeparator();
        separator2.setPreferredSize(new Dimension(200, 2));
        center.add(separator2);

        center.add(addRadioButtons());
        panel.add(center);

//        saveButton.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(final ActionEvent event) {
//                final String str = field1.getText();
//                final char[] password = field2.getPassword();
//                final String pass = new String(password);
//                String ser = "";
//                if (buttons[0].isSelected()) {
//                    ser = "smtp.gmail.com";
//                } else if (buttons[1].isSelected()) {
//                    ser = "smtp.mail.yahoo.com";
//                } else if (buttons[2].isSelected()) {
//                    ser = "smtp.live.com";
//                } else if (buttons[3].isSelected()) {
//                    ser = "smtp.rediffmail.com";
//                }
//                save();
//            }
//        });
        return panel;
    }

    /**
     * Method adds the radio buttons to select an email server and enables the
     * save button if an radio button is selected.
     *
     * @param update the save button to save setting.
     * @return returns the array of radio buttons added.
     */
    private JPanel addRadioButtons() {
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        boxPanel.setPreferredSize(new Dimension(100, 150));
//        final JButton saveButton = getSaveButton();
//        saveButton.setEnabled(false);
        ButtonGroup group = new ButtonGroup();
        radioButtons = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            radioButtons[i] = new JRadioButton();
//            final int index = i;
//            radioButtons[i].addItemListener(new ItemListener() {
//
//                @Override
//                public void itemStateChanged(final ItemEvent event) {
//                    if (radioButtons[index].isSelected()) {
//                        saveButton.setEnabled(true);
//                    }
//                }
//            });
            group.add(radioButtons[i]);
            boxPanel.add(radioButtons[i]);
        }
        radioButtons[0].setText("Gmail");
        radioButtons[1].setText("Yahoo");
        radioButtons[2].setText("Rediff");
        radioButtons[3].setText("Hotmail");
        return boxPanel;
    }

    @Override
    public FileInterface getFields() {
        Msettings data = new Msettings(field1.getText());
        data.setPassword(String.valueOf(field2.getPassword()));
        return data;
    }

    @Override
    public final void setFields(FileInterface record) {
        Msettings data = (Msettings) record;
        field1.setText(data.getTitle());
        field2.setText(data.getPassword());
    }

    @Override
    public String[] getString() {
        return new String[]{"Enter email-ID and Password & Select one mailing server.", "Edit Email and Password"};
    }

    @Override
    public HashMap getFieldsValidator() {
        HashMap<JTextField, Validator[]> map = new HashMap<>();
        Validator[] constants = {Validator.VAL_NOT_NULL, Validator.VAL_DUPLICATE};
        map.put(field1, constants);
        map.put(field2, constants);
        return map;
    }
}
