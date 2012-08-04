package utility;

import core.FileInterface;
import gui.AbstractEditWindow;
import gui.AbstractInfoWindow;
import gui.AbstractListWindow;
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

    /**
     *
     * @return
     */
    public static ArrayList<AbstractMyWindow> getMyWindows() {
        myWindows = new ArrayList<>();
        myClassesByApp = new HashMap<>();
        myClassesByFileInterface = new HashMap<>();
        myApps = new HashMap<>();
        HashMap<String, MyType> myClassesByName = new HashMap<>();
        File myWindowsClassesFile = new File("Extensions.txt");
        Scanner reader = null;
        try {
            reader = new Scanner(myWindowsClassesFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Class<? extends AbstractMyWindow> myWindow = null;
            String appName = null;
            int count = 0;
            while (reader.hasNextLine()) {
                String[] appNameCumClasses = reader.nextLine().split(":");
                switch (appNameCumClasses[0]) {
                    case "ApplicationStart":
                        appName = appNameCumClasses[1];
                        myApps.put(appName, count);
                        count++;
                        myWindow = (Class<? extends AbstractMyWindow>) Class.forName("gui." + appName.toLowerCase() + "." + appName);
                        try {
                            myWindows.add((AbstractMyWindow) myWindow.newInstance());
                        } catch (InstantiationException | IllegalAccessException ex) {
                            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "ApplicationEnd":
                        myClassesByApp.put(myWindow.getName().substring(4), myClassesByName);
                        break;
                    default:
                        String[] classes = appNameCumClasses[1].split(",");
                        Class<? extends FileInterface> data = (Class<? extends FileInterface>) Class.forName("core." + appName.toLowerCase() + "." + classes[0]);
                        Class<? extends AbstractListWindow> listClass = (Class<? extends AbstractListWindow>) Class.forName("gui." + appName.toLowerCase() + "." + classes[1]);
                        Class<? extends AbstractInfoWindow> infoClass = (Class<? extends AbstractInfoWindow>) Class.forName("gui." + appName.toLowerCase() + "." + classes[2]);
                        Class<? extends AbstractEditWindow> editClass = (Class<? extends AbstractEditWindow>) Class.forName("gui." + appName.toLowerCase() + "." + classes[3]);
                        String file = classes[4];
                        MyType myType = new MyType();
                        myType.setData(data);
                        myType.setListWindow(listClass);
                        myType.setInfoWindow(infoClass);
                        myType.setEditWindow(editClass);
                        myType.setFile(file);
                        myClassesByName.put(appNameCumClasses[0], myType);
                        myClassesByFileInterface.put(data, myType);
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
    public static void listWindowByName(String name) {
        String[] appName = name.split(":");
        if (appName.length == 2) {
            AbstractListWindow listWindow = null;
            try {
                //Example : When name is 'Newslo:Subscriber' then Appname is 'newslo.Newslo' and SubItem is 'Subscriber'.
                listWindow = myClassesByApp.get(appName[0].toLowerCase() + "." + appName[0]).get(appName[1]).getListWindow().newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            MainPanel.changePanel(listWindow, listWindow.getString());
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
    public static <T extends FileInterface> void infoWindowByDataClass(Class<T> fileClass, ArrayList<T> list, int index) {
        AbstractInfoWindow infoWindow = null;
        try {
            infoWindow = myClassesByFileInterface.get(fileClass).getInfoWindow().getDeclaredConstructor(ArrayList.class, int.class).newInstance(list, index);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        MainPanel.changePanel(infoWindow, infoWindow.getString());
    }

    /**
     *
     * @param data
     * @return
     */
    public static File myFileByDataClass(Class<? extends FileInterface> fileClass) {
        String fileName = myClassesByFileInterface.get(fileClass).getFile();
        return new File(fileName);
    }

    /**
     *
     * @param data
     * @return
     */
    public static void listWindowByDataClass(Class<? extends FileInterface> fileClass) {
        AbstractListWindow listWindow = null;
        try {
            listWindow = myClassesByFileInterface.get(fileClass).getListWindow().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        MainPanel.changePanel(listWindow, listWindow.getString());
    }

    /**
     *
     * @param <T>
     * @param fileClass
     * @param list
     * @param index
     */
    public static <T extends FileInterface> void editWindowByDataClass(Class<T> fileClass, ArrayList<T> list, int index) {
        AbstractEditWindow editWindow = null;
        try {
            editWindow = myClassesByFileInterface.get(fileClass).getEditWindow().getDeclaredConstructor(ArrayList.class, int.class).newInstance(list, index);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        MainPanel.changePanel(editWindow, editWindow.getString());
    }

    /**
     *
     */
    private final static class MyType {

        private Class<? extends FileInterface> data;
        private Class<? extends AbstractListWindow> listWindow;
        private Class<? extends AbstractInfoWindow> infoWindow;
        private Class<? extends AbstractEditWindow> editWindow;
        private String file;

        public Class<? extends AbstractListWindow> getListWindow() {
            return listWindow;
        }

        public void setListWindow(Class<? extends AbstractListWindow> listWindow) {
            this.listWindow = listWindow;
        }

        public Class<? extends AbstractInfoWindow> getInfoWindow() {
            return infoWindow;
        }

        public void setInfoWindow(Class<? extends AbstractInfoWindow> infoWindow) {
            this.infoWindow = infoWindow;
        }

        public Class<? extends FileInterface> getData() {
            return data;
        }

        public void setData(Class<? extends FileInterface> data) {
            this.data = data;
        }

        public Class<? extends AbstractEditWindow> getEditWindow() {
            return editWindow;
        }

        public void setEditWindow(Class<? extends AbstractEditWindow> editWindow) {
            this.editWindow = editWindow;
        }

        /**
         * @return the file
         */
        public String getFile() {
            return file;
        }

        /**
         * @param file the file to set
         */
        public void setFile(String file) {
            this.file = file;
        }
    }
    /**
     *
     */
    private static HashMap<Class<? extends FileInterface>, MyType> myClassesByFileInterface;
    private static HashMap<String, HashMap<String, MyType>> myClassesByApp;
}
