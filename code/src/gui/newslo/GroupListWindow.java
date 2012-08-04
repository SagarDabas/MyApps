package gui.newslo;

import core.newslo.Group;
import core.newslo.Newsletter;
import core.newslo.Subscriber;
import gui.AbstractListWindow;
import javax.swing.JOptionPane;
import utility.NewsEdit;

/**
 * Newsletter is the panel for manipulating newsletters.
 *
 * @author Sagar
 */
public final class GroupListWindow extends AbstractListWindow {

    /**
     * Modify the list GUI created by AbstractListWindow.
     */
    public GroupListWindow() {
        super(NewsEdit.groupList, Group.class);
        super.addCustomGUI(null);
    }

    @Override
    public boolean deleteButtonListener(final int index) {
        boolean flag = false;
        if (!validateGroupDel(index + 1)) {
             flag = super.deleteButtonListener(index);
        }
        return flag;
    }

    @Override
    public String[] getString() {
        return new String[]{"Click on Group name to see the Subscribers list", "Groups"};
    }

    /**
     * Method to validate whether the group is empty or not before deleting it.
     *
     * @param index ID of the group to be deleted.
     * @return returns false if the group is empty else true.
     */
    private boolean validateGroupDel(final int index) {
        boolean flag = false;
        //Label to ensure only one warning is displayed and also
        //loops for only one matched group.
        A:
        {
            for (Newsletter letter : NewsEdit.letterList) {
                final int[] groupIds = letter.getGroupId();
                for (int id : groupIds) {
                    if (id == index) {
                        flag = true;
                        JOptionPane.showMessageDialog(null,
                                "Please delete or move all the newsletters in "
                                + NewsEdit.groupList.get(index - 1).getTitle()
                                + " group to another group.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        break A;
                    }
                }
            }

            for (Subscriber letter : NewsEdit.subsList) {
                final int[] groupIds = letter.getGroupId();
                for (int id : groupIds) {
                    if (id == index) {
                        flag = true;
                        JOptionPane.showMessageDialog(null,
                                "Please delete or move all the subscribers in "
                                + NewsEdit.groupList.get(index - 1).getTitle()
                                + " group to another group.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        break A;
                    }
                }
            }
        }
        return flag;
    }
}