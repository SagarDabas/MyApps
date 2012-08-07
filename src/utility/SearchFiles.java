package utility;

import core.FileInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.JOptionPane;

/**
 *
 * @author Sagar
 * @param <T>
 */
public class SearchFiles {

    public static <T extends FileInterface> int searchByName(final ArrayList<T> lists) {

        int fileid = -1;
        String succeed = "";
        A:
        while (true) {
            final String name = (String) JOptionPane.showInputDialog(null,
                    succeed + "Enter the " + lists.get(0).getClass().getName().substring(5) + "'s name.",
                    "Enter",
                    JOptionPane.INFORMATION_MESSAGE,
                    null, null, null);
            if (name != null) {
                for (FileInterface file : lists) {
                    if (file.getTitle().equals(name)) {
                        fileid = file.getId() - 1;
                        break A;
                    } else {
                        succeed = "No such record exist.\n";
                    }
                }
            } else {
                break;
            }
        }
        return fileid;

    }

    /**
     *
     */
    public static <T extends FileInterface> ArrayList<Integer> searchByDate(final ArrayList<T> lists) {

        ArrayList<Integer> ids = new ArrayList<>();
        String succeed = "";
        A:
        while (true) {
            final String date = (String) JOptionPane.showInputDialog(null,
                    succeed + "Select a date on which " + lists.get(0).getClass().getName().substring(5) + "s are created.",
                    "Search",
                    JOptionPane.INFORMATION_MESSAGE,
                    null, null, null);
            if (date != null) {
                for (FileInterface file : lists) {
                    if (file.getDate().equals(date)) {
                        ids.add(file.getId() - 1);
                    } else {
                        succeed = "No group was created on this date.\n";
                    }
                }
            } else {
                break;
            }
        }
        return ids;
    }

    /**
     *
     */
    public static <T extends FileInterface> ArrayList<Integer> searchByMonth(final ArrayList<T> lists) {

        ArrayList<Integer> ids = new ArrayList<>();
        String succeed = "";
        A:
        while (true) {
            final String date = (String) JOptionPane.showInputDialog(null,
                    succeed + "Select a month during which " + lists.get(0).getClass().getName().substring(5) + "s are created.",
                    "Search",
                    JOptionPane.INFORMATION_MESSAGE,
                    null, null, null);
            if (date != null) {
                for (FileInterface file : lists) {
                    if (file.getDate().equals(date)) {
                        ids.add(file.getId() - 1);
                    } else {
                        succeed = "No entry was made during this month.\n";
                    }
                }
            } else {
                break;
            }
        }
        return ids;
    }

    /**
     *
     */
    public static <T extends FileInterface> ArrayList<Integer> searchByYear(final ArrayList<T> lists) {

        ArrayList<Integer> ids = new ArrayList<>();
        String succeed = "";
        A:
        while (true) {
            final String date = (String) JOptionPane.showInputDialog(null,
                    succeed + "Select a year during which  " + lists.get(0).getClass().getName().substring(5) + "s are created.",
                    "Search",
                    JOptionPane.INFORMATION_MESSAGE,
                    null, null, null);
            if (date != null) {
                for (FileInterface file : lists) {
                    if (file.getDate().equals(date)) {
                        ids.add(file.getId() - 1);
                    } else {
                        succeed = "No entry was made during this year.\n";
                    }
                }
            } else {
                break;
            }
        }
        return ids;
    }
    
    public static String getDate() {
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMM yyyy", Locale.ENGLISH);
        final String date = sdf.format(cal.getTime());
        return date;
    }
}
