package gui.newslo;

import core.FileInterface;
import core.newslo.Group;
import core.newslo.Subscriber;
import gui.AbstractEditWindow;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import utility.MainPanel;
import utility.NewsEdit;
import utility.Validator;

/**
 *
 * @author Sagar
 */
public final class SubscriberEditWindow<T extends FileInterface> extends AbstractEditWindow {

    private JTextField field1;
    private JTextField field2;
    private ArrayList<JCheckBox> checkBoxes;

    public SubscriberEditWindow(final ArrayList<T> list, int index) {
        super(list, index, Subscriber.class);
        super.addCustomGUI(null);
        super.getDefaultWindow().add(addCheckBoxes());
//        if (index != -1) {
//            this.data = (Subscriber) list.get(index);
//            setFields();
//        }
    }

   private JPanel addCheckBoxes() {
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

    @Override
    public FileInterface getFields() {
        Subscriber data = new Subscriber(field1.getText());
        data.setEmailID(field2.getText());
        data.setGroupId(getSelectedCheckBoxes());
        return data;
    }

    @Override
    public final void setFields(FileInterface record) {
        Subscriber data = (Subscriber)record;
        field1.setText(data.getTitle());
        field2.setText(data.getEmailID());
        setSelectedCheckBoxes(data.getGroupId());
    }

    @Override
    public HashMap getTextFields() {
        field1 = new JTextField();
        final JLabel label1 = new JLabel("Name :");
        label1.setFont(MainPanel.font);
        field2 = new JTextField();
        field2.setPreferredSize(new Dimension(450, 20));
        final JLabel label2 = new JLabel("EmailID :");
        label2.setFont(MainPanel.font);
        HashMap<JLabel, JTextField> map = new HashMap<>();
        map.put(label1, field1);
        map.put(label2, field2);
        return map;
    }

    @Override
    public String[] getString() {
        return new String[]{"Complete the all fields. Select atleast one group.", "Subscriber Update"};
    }

    @Override
    public HashMap getFieldsValidator() {
        HashMap<JTextField, Validator[]> map = new HashMap<>();
        Validator[] constants = {Validator.VAL_NOT_NULL, Validator.VAL_DUPLICATE};
        map.put(field1, constants);
        map.put(field2, constants);
        return map;
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
