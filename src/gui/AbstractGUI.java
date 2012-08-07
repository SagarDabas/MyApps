package gui;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Sagar
 */
abstract public class AbstractGUI extends JPanel {

    //Use exceptions to force the class implementors to assign these values first.
    protected static String editWindow;
    protected static String infoWindow;
    protected static String listWindow;
    protected static String fileName;

    /**
     *
     * @param component
     */
    public final void addCustomGUI(final JComponent component) {
        this.setLayout(new BorderLayout());

        if (component == null) {
            //Adds the buttonsPanel
            this.add(buttonsPanel(), BorderLayout.SOUTH);

            this.add(defaultWindow(), BorderLayout.CENTER);
        } else {
            this.add(component, BorderLayout.CENTER);
        }
    }

    protected abstract JComponent buttonsPanel();

    protected abstract JPanel defaultWindow();

    public abstract String[] getString();
}
