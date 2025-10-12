
package projectmyra;
//real
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class DrawArea extends JPanel implements KeyListener {
    protected Myra myra;
    protected Timer frameTimer, gameTimer;
    protected int timeLeft = 60;
    protected int score = 1000;
    protected JFrame frame;

    public DrawArea(JFrame frame) {
        this.frame = frame;
        setFocusable(true);
        addKeyListener(this);
    }

    // subclass implement
    protected abstract void initActors();   // Myra, enemy, coin
    protected abstract void initTimers();   // frameTimer gameTimer
    protected abstract void resetGame();    // reset map

    protected void stopAllTimers() {
        if (frameTimer != null && frameTimer.isRunning()) frameTimer.stop();
        if (gameTimer != null && gameTimer.isRunning()) gameTimer.stop();
    }

    protected void backToMenu() {
        frame.getContentPane().removeAll();
        frame.add(new MenuPanel(frame));
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> myra.setLeftPressed(true);
            case KeyEvent.VK_D -> myra.setRightPressed(true);
            case KeyEvent.VK_W, KeyEvent.VK_SPACE -> myra.jump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> myra.setLeftPressed(false);
            case KeyEvent.VK_D -> myra.setRightPressed(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
