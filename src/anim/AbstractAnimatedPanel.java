package anim;

/**
 *
 * @author Sagar
 */
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 *
 * @author Sagar
 */
public abstract class AbstractAnimatedPanel extends JLayeredPane {

    private MyMouseListener mouseListener = new MyMouseListener();
    protected JPanel animatedLayer;

    protected static interface MyActionListener extends ActionListener {
        void start();
    }

    abstract MyActionListener getMouseEnteredListener();

    abstract MyActionListener getMouseExitedListener();

    public AbstractAnimatedPanel(final JPanel layerOne) {
        setLayout(new BorderLayout());
        adjustDimension(layerOne);
        add(layerOne);
    }

    //Should be called in the constructor of Subclasses after modifying animatedLayer.
    protected void addAnimatedLayer(JPanel layerTwo) {
        animatedLayer = layerTwo;
        add(animatedLayer);
        hideAlways();
    }

    /**
     *
     */
    private class MyMouseListener extends MouseAdapter {

        private MyActionListener timerListenerOne;
        private MyActionListener timerListenerTwo;

        @Override
        public void mouseClicked(MouseEvent e) {
            animatedLayer.setVisible(false);
        }

        @Override
        public void mouseEntered(MouseEvent event) {

            if (!animatedLayer.isVisible()) {
                timerListenerOne = getMouseEnteredListener();
                timerListenerOne.start();
            }
        }

        @Override
        public void mouseExited(MouseEvent event) {
            if (animatedLayer.isVisible()) {
                timerListenerTwo = getMouseExitedListener();
                timerListenerTwo.start();
            }
        }
    }

    /**
     *
     */
    public final void hideAlways() {
        addMouseListener(mouseListener);
        animatedLayer.setVisible(false);
    }

    /**
     *
     *
     */
    public final void showAlways() {
        removeMouseListener(mouseListener);
    }

    private void adjustDimension(final JPanel layerOne) {
        addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                if (AbstractAnimatedPanel.this.getLayout() instanceof BorderLayout) {
                    int height = getHeight();
                    int width = getWidth();
                    AbstractAnimatedPanel.this.setLayout(null);
                    AbstractAnimatedPanel.this.setBounds(0, 0, width, height);
                    layerOne.setBounds(0, 0, width, height);
                    animatedLayer.setBounds(0, 0, width, height);
                    AbstractAnimatedPanel.this.removeAll();
                    AbstractAnimatedPanel.this.add(layerOne, new Integer(0), 0);
                    AbstractAnimatedPanel.this.add(animatedLayer, new Integer(1), 0);
                    AbstractAnimatedPanel.this.revalidate();
                    AbstractAnimatedPanel.this.removeAncestorListener(getAncestorListeners()[0]);
                }
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
            }
        });
    }
}
