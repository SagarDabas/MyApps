package utility;

import core.FileInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Validator class providdes the methods for validating various inputs by the
 * user.
 *
 * @author Sagar
 */
public enum Validator {

    VAL_NOT_NULL{
       
        @Override
        public boolean validate() {
            return validateNotNull();
        }
    
    },
    VAL_CHAR {

        @Override
        public boolean validate() {
            return true;
        }
    },
    VAL_DATE {

        @Override
        public boolean validate() {
            return true;
        }
    },
    VAL_DUPLICATE {

        @Override
        public boolean validate() {
            return validateNotDuplicate();
        }
    },
    VAL_EMAIL {

        @Override
        public boolean validate() {
            return true;
        }
    },
    VAL_LENGTH() {

        @Override
        public boolean validate() {
            return true;
        }
    },
    VAL_NUMERIC {

        @Override
        public boolean validate() {
            return true;
        }
    },
    VAL_STRING {

        @Override
        public boolean validate() {
            return true;
        }
    };

    /**
     * Method prompt user to enter the data again if the field is left empty.
     *
     * @return return true if the field is not empty.
     */
    public final boolean validateNotNull() {
        boolean flag = true;
        if ("".equals(field)) {
            JOptionPane.showMessageDialog(null,
                    "Please enter the field",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            flag = false;
        }
        return flag;
    }

    /**
     * Method prompts the user to enter the the data again if the value entered
     * by user already exist.
     *
     * @return true if the entry is not duplicate.
     */
    public final boolean validateNotDuplicate() {
        boolean flag = true;
        if (!validateNotNull()) {
            flag = false;
        } else {
            for (int index = 0; index < list.size(); index++) {
                
                if (field.equals(list.get(index).getTitle())) {

                    JOptionPane.showMessageDialog(null,
                            "Name or ID is already in use."
                            + " Please choose another one",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    flag = false;

                }
            }
        }
        return flag;
    }

    /**
     * Method prompts the user to select atleast one group if none is selected.
     *
     * @param check check is 0 if any group is not selected, 1 if any one group
     * is selected.
     * @return return true if atleast one group is selected.
     */
    public final boolean validateGroupSelected(final int check) {
        boolean flag;
        if (check == 0) {
            JOptionPane.showMessageDialog(null, "Select atleast one Group", "Error", JOptionPane.ERROR_MESSAGE);
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * 
     * @param textFieldMap
     * @param dataList
     * @return 
     */
    public static boolean validateFields(final HashMap<JTextField, Validator[]> textFieldMap, final ArrayList<? extends FileInterface> dataList) {
        list = dataList;
        boolean flag = true;
        if (textFieldMap != null) {
            for (Map.Entry<JTextField, Validator[]> entry : textFieldMap.entrySet()) {
                final JTextField textField = entry.getKey();
                field = textField.getText();
                for (Validator constant : entry.getValue()) {
                    flag = flag && constant.validate();  //If constant.validate() return false first time, it will not be called again second time.
               }
            }
        }
        return flag;
    }

    /**
     *
     * @param textFieldMap
     * @return
     */
    public static boolean validateAreas(final HashMap<JTextArea, Validator[]> textAreaMap, final ArrayList<? extends FileInterface> dataList) {
        list = dataList;
        boolean flag = true;
        if (textAreaMap != null) {
            for (Map.Entry<JTextArea, Validator[]> entry : textAreaMap.entrySet()) {
                final JTextArea area = entry.getKey();
                field = area.getText();
                for (Validator constant : entry.getValue()) {
                    flag = flag && constant.validate();
                }
            }
        }
        return flag;
    }
    /**
     * Array of strings to validate.
     */
    private static String field;
    /**
     * Data is stored in this list. Fields are validated against this data.
     */
    private static ArrayList<? extends FileInterface> list;

    /**
     *
     * @return
     */
    public abstract boolean validate();
}
