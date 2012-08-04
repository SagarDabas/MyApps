package gui.newslo;

import core.FileInterface;
import core.newslo.Group;
import core.newslo.Newsletter;
import gui.AbstractEditWindow;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
public final class NewsEditWindow<T extends FileInterface> extends AbstractEditWindow {

    private JTextField field1;
    private JTextArea field2;
    private ArrayList<JCheckBox> checkBoxes;

    public NewsEditWindow(final ArrayList<T> list, int index) {
        super(list, index, Newsletter.class);
        JPanel checkBoxesPanel = checkBoxes();
        super.addCustomGUI(null);
        super.getDefaultWindow().add(checkBoxesPanel);
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
        final JLabel label2 = new JLabel("Description :");
        HashMap<JLabel, JTextArea> map = new HashMap<>();
        map.put(label2, field2);
        return map;
    }

    @Override
    public FileInterface getFields() {
        Newsletter data = new Newsletter(field1.getText());
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
    public HashMap getFieldsValidator() {
        HashMap<JTextField, Validator[]> map = new HashMap<>();
        Validator[] constants = {Validator.VAL_NOT_NULL, Validator.VAL_DUPLICATE};
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

    @Override
    public String[] getString() {
        return new String[]{"Complete the all fields.", "Edit Newsletter"};
    }

    private JPanel checkBoxes() {
        checkBoxes = new ArrayList<>();
        JCheckBox checkBox;
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(550, 30));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        final JLabel label = new JLabel("Groups : ");
        label.setFont(MainPanel.font);
        panel.add(label);

        for (Group group : NewsEdit.groupList) {
            checkBox = new JCheckBox();
            checkBox.setText(group.getTitle());
            panel.add(checkBox);
            checkBoxes.add(checkBox);
        }
        return panel;
    }

    private void setSelectedCheckBoxes(int[] groupIDs) {
        for (int groupID : groupIDs) {
            checkBoxes.get(groupID - 1).setSelected(true);
        }
    }

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
}
