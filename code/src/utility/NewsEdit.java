package utility;

import core.FileInterface;
import core.FileInterface;
import core.newslo.Group;
import core.newslo.Log;
import core.newslo.Msettings;
import core.newslo.Newsletter;
import core.newslo.Subscriber;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class  NewsEdit {

    public static ArrayList<Newsletter> letterList;
    public static ArrayList<Group> groupList;
    public static ArrayList<Subscriber> subsList;
    public static ArrayList<Log> logList;
    public static ArrayList<Msettings> settingsList;

    public static void readFiles() {
        letterList = readFile(new File("Letterfile"));
        groupList = readFile(new File("Grfile"));        //(ArrayList<Group>)(ArrayList<T>)readFile(new File("Grfile")); also works?? Why??
        subsList = readFile(new File("Subsfile"));
        logList = readFile(new File("Logfile"));
        settingsList = readFile(new File("Settingsfile"));
    }

    public static ArrayList readFile(File file) {
        ArrayList List = new ArrayList();
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file))) {
            List = (ArrayList) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }
        return List;
    }

    public static <T> void createNew(File file, ArrayList<T> ins) {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file))) {
            writer.writeObject(ins);
        } catch (IOException e) {
        }
    }

    public static <T extends FileInterface> boolean delete(int row, File file, ArrayList<T> ins, int notValidate) {
        int value;
        boolean flag = false;
        if (notValidate != 1) {
            value = JOptionPane.showConfirmDialog(null,
                    "Do you really want to delete " + ins.get(row).getTitle() + " ?",
                    "Warning",
                    JOptionPane.OK_CANCEL_OPTION);
        } else {
            value = JOptionPane.OK_OPTION;
        }
        if (value == JOptionPane.OK_OPTION) {
            ins.remove(row);
            if (ins.isEmpty()) {
                boolean delete = file.delete();
                if (!delete) {
                    JOptionPane.showMessageDialog(null,
                            "Something went wrong file was not deleted",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                createNew(file, ins);
            }
            flag = true;
        }
        return flag;
    }

    public static <T extends FileInterface> int edit(int row, File file, ArrayList<T> ins, T type) {
        if (row != -1) {
            ins.set(row, type);
        } else {
            ins.add(type);
            row = ins.size()-1;
        }
        createNew(file, ins);
        return row;
    }

}