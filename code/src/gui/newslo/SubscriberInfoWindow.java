package gui.newslo;

import core.FileInterface;
import core.newslo.Log;
import core.newslo.Subscriber;
import gui.AbstractInfoWindow;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import utility.MainPanel;
import utility.NewsEdit;

/**
 *
 * @author Sagar
 * @param <T>
 */
public final class SubscriberInfoWindow<T extends FileInterface> extends AbstractInfoWindow {

    /**
     *
     */
    private final Subscriber data;

    public SubscriberInfoWindow(ArrayList<T> list, int index) {
        super(list, index, Subscriber.class);
        this.data = (Subscriber) list.get(index);
        super.addCustomGUI(customGUI());
    }

    @Override
    public String[] getString() {
        return new String[]{"See the subscribers Info here", "Newsletter Info"};
    }

    public JPanel customGUI() {
        final JPanel panel = new JPanel();
        panel.setLayout(null);


        final JLabel topLabel = new JLabel(data.getTitle());
        topLabel.setBounds(10, 20, MainPanel.panelSize.width - 20, 80);
        topLabel.setFont(MainPanel.font.deriveFont(60f));
        panel.add(topLabel);

        final JSeparator separator1 = new JSeparator();
        separator1.setBounds(10, 90, MainPanel.panelSize.width - 20, 50);
        panel.add(separator1);

        final JLabel centerLabel1 = new JLabel();
        centerLabel1.setBounds(10, 140, MainPanel.panelSize.width - 20, 50);
        centerLabel1.setText("Email-ID  : " + data.getEmailID());
        centerLabel1.setFont(MainPanel.font);
        panel.add(centerLabel1);

        final JLabel centerLabel3 = new JLabel();
        centerLabel3.setBounds(10, 190, MainPanel.panelSize.width - 20, 50);
        centerLabel3.setFont(MainPanel.font);
        panel.add(centerLabel3);

        final JLabel centerLabel4 = new JLabel();
        centerLabel4.setBounds(10, 240, MainPanel.panelSize.width - 20, 50);
        centerLabel4.setText("Registeration Date : " + data.getDate());
        centerLabel4.setFont(MainPanel.font);
        panel.add(centerLabel4);

        if (!NewsEdit.logList.isEmpty()) {
            for (Log l : NewsEdit.logList) {
                if (l.getId() == data.getId()) {
                    int index = 0;
                    JLabel centerLabel2 = new JLabel();
                    centerLabel2.setText("Mails Sent");
                    panel.add(centerLabel2);
                    for (int a : l.getNewsId()) {
                        index++;
                        centerLabel2 = new JLabel();
                        centerLabel2.setText(NewsEdit.letterList.get(a - 1).getTitle() + ",");
                        panel.add(centerLabel2);
                    }
                    centerLabel3.setText("Total number of Mails sent : " + index);
                    break;
                } else {
                    centerLabel3.setText("No mail sent yet.");
                }
            }
        } else {
            centerLabel3.setText("No mail sent yet.");
        }

//        panel.add(center, BorderLayout.CENTER);
        return panel;
    }
}
