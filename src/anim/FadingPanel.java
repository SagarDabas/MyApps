/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package anim;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Sagar
 */
public class FadingPanel extends AbstractAnimatedPanel {

    private float opacity = 0;

    public FadingPanel(JPanel layerOne, JPanel layerTwo) {
        super(layerOne);
        FadingLayer layer = new FadingLayer(layerTwo);
        addAnimatedLayer(layer);
    }

    @Override
    MyActionListener getMouseEnteredListener() {
        return new MouseEnteredListener();
    }

    @Override
    MyActionListener getMouseExitedListener() {
        return new MouseExitedListener();
    }

    /**
     *
     */
    private class MouseEnteredListener implements MyActionListener {

        {
         opacity = 0;
        }
        private Timer timer = new Timer(50, this);

        @Override
        public void start() {
            timer.start();
            animatedLayer.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            opacity += 0.1;
            if (opacity > 1) {
                opacity = 1;
                timer.stop();
            }
            animatedLayer.repaint();
        }
    }

    /**
     *
     */
    private class MouseExitedListener implements MyActionListener {

        {
         opacity = 1;
        }
        private Timer timer = new Timer(50, this);

        @Override
        public void start() {
            timer.start();
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            opacity -= 0.1;
            if (opacity < 0) {
                opacity = 0;
                timer.stop();
                animatedLayer.setVisible(false);
            }
            animatedLayer.repaint();
        }
    }

    public class FadingLayer extends JPanel {

        JPanel panel;
        public FadingLayer(JPanel panel) {
            this.panel = panel;
            setLayout(new BorderLayout());
            add(panel);
            setOpaque(false);
            panel.setOpaque(false);
        }

        @Override
        public void paintComponent(Graphics arg0) {
            Graphics2D graphics = (Graphics2D) arg0;
            AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
            graphics.setComposite(alpha);
            graphics.setColor(panel.getBackground());
            graphics.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
