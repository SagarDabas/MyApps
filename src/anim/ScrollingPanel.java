package anim;

import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Sagar
 */
public class ScrollingPanel extends AbstractAnimatedPanel {

    private byte constant;
    public final byte TO_LEFT = 0;
    public final byte TO_RIGHT = 1;
    public final byte UPWARDS = 2;
    public final byte DOWNWARDS = 3;

    public ScrollingPanel(JPanel layerOne, JPanel layerTwo) {
        super(layerOne);
        addAnimatedLayer(layerTwo);
    }

    @Override
    MyActionListener getMouseEnteredListener() {
        return new MouseEnteredListener();
    }

    @Override
    MyActionListener getMouseExitedListener() {
        return new MouseExitedListener();
    }

    //Should throw exception if the user does not uses the predefined constants in this class.
    public final void setDirection(byte constant) {
        this.constant = constant;
    }

    /**
     *
     */
    private class MouseEnteredListener implements MyActionListener {

        private int frames = 20;
        int count = 0;
        private Timer timer = new Timer(10, this);

        @Override
        public void start() {
            timer.start();
            animatedLayer.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (count >= frames) {
                timer.stop();
            } else {
                count++;
                switch (constant) {
                    case TO_LEFT:
                        animatedLayer.setLocation(getWidth() - getWidth() * count / frames, 0);
                        break;
                    case TO_RIGHT:
                        animatedLayer.setLocation(-getWidth() + getWidth() * count / frames, 0);
                        break;
                    case UPWARDS:
                        animatedLayer.setLocation(0, getHeight() - getHeight() * count / frames);
                        break;
                    case DOWNWARDS:
                        animatedLayer.setLocation(0, -getHeight() + getHeight() * count / frames);
                        break;
                    default:
                        animatedLayer.setLocation(getWidth() - getWidth() * count / frames, 0);
                        break;
                }
            }
        }
    }

    /**
     *
     */
    private class MouseExitedListener implements MyActionListener {

        private int frames = 20;
        int count = 0;
        private Timer timer = new Timer(10, this);

        @Override
        public void start() {
            timer.start();
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (count >= frames) {
                timer.stop();
                animatedLayer.setVisible(false);
            } else {
                count++;

                switch (constant) {
                    case TO_LEFT:
                        animatedLayer.setLocation(getWidth() * count / frames, 0);
                        break;
                    case TO_RIGHT:
                        animatedLayer.setLocation(-getWidth() * count / frames, 0);
                        break;
                    case UPWARDS:
                        animatedLayer.setLocation(0, getHeight() * count / frames);
                        break;
                    case DOWNWARDS:
                        animatedLayer.setLocation(0, -getHeight() * count / frames);
                        break;
                    default:
                        animatedLayer.setLocation(getWidth() * count / frames, 0);
                        break;
                }
            }
        }
    }
}
