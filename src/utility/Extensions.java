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

    private static HashMap<String, Integer> myApps;
    private static ArrayList<AbstractMyWindow> myWindows;
    private static HashMap<String, HashMap<String, HashMap<String, Class>>> myClassesByApp;

    /**
     *
     * @return
     */
    public static ArrayList<AbstractMyWindow> getMyWindows() {
        myWindows = new ArrayList<>();
        myClassesByApp = new HashMap<>();
        myApps = new HashMap<>();
        HashMap<String, HashMap<String, Class>> myClassesByName = new HashMap<>();

        File myWindowsClassesFile = new File("Extensions.txt");

        Scanner reader = null;
        try {
            reader = new Scanner(myWindowsClassesFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Class<? extends AbstractMyWindow> myWindow;
            String appName = null;
            int count = 0;
            while (reader.hasNextLine()) {
                String[] appNameCumClasses = reader.nextLine().split(":");
                switch (appNameCumClasses[0]) {
                    case "ApplicationStart":
                        appName = appNameCumClasses[1];
                        myApps.put(appName, count);
                        count++;
                        myWindow = (Class<? extends AbstractMyWindow>) Class.forName(appName.toLowerCase() + "." + appName);
                        try {
                            myWindows.add((AbstractMyWindow) myWindow.newInstance());
                        } catch (InstantiationException | IllegalAccessException ex) {
                            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "ApplicationEnd":
                        myClassesByApp.put(appName, myClassesByName);
                        break;
                    default:
                        String[] classes = appNameCumClasses[1].split(",");

                        HashMap<String, Class> classNameClasses = new HashMap<>();
                        for (String className : classes) {
                            classNameClasses.put(className, Class.forName(appName.toLowerCase() + "." + className));
                        }
                        myClassesByName.put(appNameCumClasses[0], classNameClasses);
                        break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        reader.close();
        return myWindows;
    }

    /**
     *
     * @param data
     * @return
     */
    public static void windowByName(String name) {
        String[] appName = name.split(":");
        if (appName.length == 3) {
            AbstractGUI window = null;
            try {
                //Example : When name is 'Newslo:Subscriber:SubscriberListWindow' then Appname is 'newslo.Newslo' and SubItem is 'Subscriber'.
                window = (AbstractGUI) myClassesByApp.get(appName[0]).get(appName[1]).get(appName[2]).newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            MainPanel.changePanel(window, window.getString());
        } else {
            AbstractMyWindow myWindow = myWindows.get(myApps.get(name));
            MainPanel.changePanel(myWindow, myWindow.getString());
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
        System.out.println(name+"   "+myClassesByApp.get(appName[0]).get(appName[1]).get(appName[2]));

        AbstractGUI window = null;
        try {
            window = (AbstractGUI) myClassesByApp.get(appName[0]).get(appName[1]).get(appName[2]).getDeclaredConstructor(parameterTypes).newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
            Logger.getLogger(Extensions.class.getName()).log(Level.SEVERE, null, ex);
        }
        MainPanel.changePanel(window, window.getString());
    }

    public static File getMyFile(String name) {
        return new File(name);
    }

}
