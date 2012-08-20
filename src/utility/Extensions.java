package utility;

import gui.AbstractGUI;
import gui.AbstractMyWindow;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sagar
 */
public class Extensions {

    public final static HashMap<String,AbstractMyWindow> myWindows = new HashMap<>();

    /**
     *
     * @return
     */
    public static void initWindows() {
        File myWindowsClassesFile = new File("Extensions.txt");
        Scanner reader = null;
        try {
            reader = new Scanner(myWindowsClassesFile);
            while (reader.hasNextLine()) {
                String appName = reader.nextLine();
                Class myWindow = Class.forName(appName.toLowerCase() + "." + appName);
                myWindows.put(appName,(AbstractMyWindow) myWindow.newInstance());
            }
        } catch (FileNotFoundException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        reader.close();
    }

    /**
     *
     * @param data
     * @return
     */
    public static void windowByName(String name) {
        String[] appName = name.split(":");
        try {
            if (appName.length == 3) {
                Class app = Class.forName(appName[0].toLowerCase() + "." + appName[2]);
                AbstractGUI window = (AbstractGUI) app.newInstance();
                MainPanel.changePanel(window, window.getString());
            } else {
                Class app = Class.forName(name.toLowerCase() + "." + name);
                AbstractMyWindow myWindow = (AbstractMyWindow) app.newInstance();
                MainPanel.changePanel(myWindow, myWindow.getString());
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param data
     * @return
     */
    public static void windowByName(String name, Object... parameters) {

        Class[] parameterTypes = new Class[parameters.length];
        int index = 0;
        for (Object parameter : parameters) {
            parameterTypes[index] = parameter.getClass();
            if (parameter instanceof Number) {
                String className = parameterTypes[index].getSimpleName();
                switch (className) {
                    case "Integer":
                        parameterTypes[index] = int.class;
                        break;
                    case "Float":
                        parameterTypes[index] = float.class;
                        break;
                    case "Double":
                        parameterTypes[index] = double.class;
                        break;
                    case "Long":
                        parameterTypes[index] = long.class;
                        break;
                    case "Short":
                        parameterTypes[index] = short.class;
                        break;
                    case "Boolean":
                        parameterTypes[index] = boolean.class;
                        break;
                }
            }
            index++;
        }

        String[] appName = name.split(":");

        AbstractGUI window = null;
        try {
            Class app = Class.forName(appName[0].toLowerCase() + "." + appName[2]);
            window = (AbstractGUI) app.getDeclaredConstructor(parameterTypes).newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | InvocationTargetException | ClassNotFoundException ex) {
            Logger.getLogger(Extensions.class.getName()).log(Level.SEVERE, null, ex);
        }
        MainPanel.changePanel(window, window.getString());
    }

    public static File getMyFile(String name) {
        return new File(name);
    }
}
