
package projectmyra;
//real
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class DrawForest extends DrawArea {
    private Turtle turtle;
    private Coin coin;
    private Image bg, fullHeart, emptyHeart;
    //resettime item
    private Item timeResetItem;

    public DrawForest(JFrame frame) {
        super(frame);
        initActors();
        initTimers();
    }
    @Override
    protected void initActors() {
        bg = new ImageIcon(getClass().getResource("images/game bg.jpg")).getImage();

        myra = new Myra("images/myra1.png", 150, 600, 150, 150);
        myra.setGroundY(600);
        
        turtle = new Turtle("images/turtle1.png", 1000, 600, 150, 160);
        coin = new Coin("images/coin.png", 1500, 625, 100, 100);
        
        timeResetItem = new Item("images/item.png", 500, 200, 100, 120);

        fullHeart = new ImageIcon(getClass().getResource("images/full heart.png")).getImage();
        emptyHeart = new ImageIcon(getClass().getResource("images/empty heart.png")).getImage();
    }
    @Override
    protected void initTimers() {
        frameTimer = new Timer(20, e -> {
            myra.update();
            turtle.update();
            coin.update();
            
            //Collision Item
            if(timeResetItem.isActive() && myra.getBounds().intersects(timeResetItem.getBounds())) {
                timeResetItem.setActive(false); 
                timeLeft = 60;  
                score = 1000;  
            }
            // Collision Coin
            if (myra.getBounds().intersects(coin.getBounds())) {
                stopAllTimers();
                int choice = JOptionPane.showOptionDialog(
                        this,
                        "You got the coin!\nYour score: " + score,
                        "Victory!",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Exit", "Play Again"},
                        "Exit"
                );
                if (choice == JOptionPane.YES_OPTION) System.exit(0);
                else {
                    resetGame();
                    backToMenu();
                }
            }

            // Collision Turtle
            if (myra.getBounds().intersects(turtle.getBounds())) {
                if (myra.getY() + myra.getHeight() <= turtle.getY() + 50) {
                    // ข้ามหัวเต่า → ไม่ลดชีวิต
                } else {
                    myra.life--;
                    if (myra.life <= 0) {
                        stopAllTimers();
                        int choice = JOptionPane.showOptionDialog(
                                this,
                                "You Lose! \nYour score: " + score,
                                "Game Over",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.ERROR_MESSAGE,
                                null,
                                new String[]{"Exit", "Play Again"},
                                "Exit"
                        );
                        if (choice == JOptionPane.YES_OPTION) System.exit(0);
                        else {
                            resetGame();
                            backToMenu();
                        }
                    } else {
                        myra.resetPosition(150, 600);
                    }
                }
            }

            repaint();
        });
        frameTimer.start();

        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            score -= 1000 / 60;
            if (timeLeft <= 0) {
                stopAllTimers();
                int choice = JOptionPane.showOptionDialog(
                        this,
                        "Time’s up!\nYou Lose!",
                        "Game Over",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        new String[]{"Exit", "Play Again"},
                        "Exit"
                );
                if (choice == JOptionPane.YES_OPTION) System.exit(0);
                else {
                    resetGame();
                    backToMenu();
                }
            }
        });
        gameTimer.start();
    }
    @Override
    protected void resetGame() {
        myra.life = 3;
        myra.resetPosition(150, 600);
        turtle.setX(1000);
        turtle.setY(600);
        coin.setX(1500);
        coin.setY(625);

        timeLeft = 60;
        score = 1000;
        gameTimer.start();
    }
    @Override
    protected void backToMenu() {
        frame.getContentPane().removeAll();
        frame.add(new MenuPanel(frame));
        frame.revalidate();
        frame.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        myra.draw(g, this);
        turtle.draw(g, this);
        coin.draw(g, this);
        if(timeResetItem.isActive()) {
            timeResetItem.draw(g, this);
        }

        // Hearts
        int xH = 20, yH = 10;
        for (int i = 0; i < 3; i++) {
            if (i < myra.life) g.drawImage(fullHeart, xH + i * 60, yH, 75, 60, this);
            else g.drawImage(emptyHeart, xH + i * 60 + 8, yH + 7, 58, 52, this);
        }

        // Draw time and score
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.setColor(Color.BLACK);
        g.drawString("Time: " + timeLeft + "s", 1300, 70);
        g.drawString("Score: " + score, 1300, 130);
    }
}


