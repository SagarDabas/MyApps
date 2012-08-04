package gui.newslo;

import core.FileInterface;
import core.newslo.Group;
import gui.AbstractEditWindow;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import utility.Validator;

/**
 *
 * @author Sagar
 */
public class GroupEditWindow<T extends FileInterface> extends AbstractEditWindow {

    private JTextField field1;
    private JTextArea field2;

    public GroupEditWindow(final ArrayList<T> list, int index) {
        super(list, index, Group.class);
        super.addCustomGUI(null);
//        if (index != -1) {
//            this.data = (Group) list.get(index);
//            setFields();
//        }
    }

    @Override
    public FileInterface getFields() {
        Group data = new Group(field1.getText());
        data.setDescription(field2.getText());
        return data;
    }

    @Override
    public final void setFields(FileInterface record) {
        Group data = (Group)record;
        field1.setText(data.getTitle());
        field2.setText(data.getDescription());
    }

    @Override
    public HashMap getTextFields() {
        field1 = new JTextField();
        final JLabel label1 = new JLabel("Name :");
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
    public String[] getString() {
        return new String[]{"Complete the all fields.", "Group Edit"};
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
        Validator[] constants = {Validator.VAL_NOT_NULL, Validator.VAL_DUPLICATE};
        map.put(field2, constants);
        return map;
    }
}
