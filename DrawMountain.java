
package projectmyra;
//real
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;


public class DrawMountain extends DrawArea{
    private Turtle turtle1,turtle2;
    private Coin coin;
    private Image bg, fullHeart, emptyHeart;
    private Tile[] platform1, platform2; //platform

    public DrawMountain(JFrame frame) {
        super(frame);
        initActors();
        initTimers();
    }
    @Override
    protected void initActors() {
        bg = new ImageIcon(getClass().getResource("images/mountains bg.jpg")).getImage();

        myra = new Myra("images/myra1.png", 150,740, 150, 150);
        myra.setGroundY(740);
        
        turtle1 = new Turtle("images/turtle1.png", 1000, 740, 150, 160);
        turtle2 = new Turtle("images/turtle1.png", 500, 740, 150, 160);
        
        coin = new Coin("images/coin.png", 1550, 300, 100, 100);

        fullHeart = new ImageIcon(getClass().getResource("images/full heart.png")).getImage();
        emptyHeart = new ImageIcon(getClass().getResource("images/empty heart.png")).getImage();
        // Platform 1: long 5 tiles
        platform1 = new Tile[5];
        for (int i = 0; i < 5; i++) {
            platform1[i] = new Tile("images/brick.png", 500 + i * 50, 600, 50);
        }

        // Platform 2: long 4 tiles
        platform2 = new Tile[4];
        for (int i = 0; i < 4; i++) {
            platform2[i] = new Tile("images/brick.png", 900 + i * 50, 500, 50);
        }
    }
    @Override
    protected void initTimers() {
        frameTimer = new Timer(20, e -> {
            myra.update();
            turtle1.update();
            turtle2.update();
            coin.update();

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

            // Collision Turtle1
            if (myra.getBounds().intersects(turtle1.getBounds())) {
                checkTurtleCollision(turtle1);
            }
            // Collision Turtle2
            if (myra.getBounds().intersects(turtle2.getBounds())) {
                checkTurtleCollision(turtle2);
            }
            
            // Collision with platform
            boolean onPlatform = false;
            Tile currentTile = null;

            // ตรวจสอบ platform1
            for (Tile t : platform1) {
                if (myra.getX() + myra.getWidth() > t.getX() && myra.getX() < t.getX() + t.getWidth()) {
                    if (myra.getY() + myra.getHeight() <= t.getY() + 15) {
                        myra.setGroundY(t.getY() - myra.getHeight());
                        onPlatform = true;
                        currentTile = t;
                    }
                }
            }

            // ตรวจสอบ platform2
            for (Tile t : platform2) {
                if (myra.getX() + myra.getWidth() > t.getX() && myra.getX() < t.getX() + t.getWidth()) {
                    if (myra.getY() + myra.getHeight() <= t.getY() + 15) {
                        myra.setGroundY(t.getY() - myra.getHeight());
                        onPlatform = true;
                        currentTile = t;
                    }
                }
            }

            // Platform1 range
            int p1Left = 400;
            int p1Right = 850;

            // Platform2 range
            int p2Left = 800;
            int p2Right = 1200;

            // เช็คว่าอยู่บน platform ไหน
            boolean onPlatform1 = myra.getY() + myra.getHeight() == 600; // platform1 y=600
            boolean onPlatform2 = myra.getY() + myra.getHeight() == 500; // platform2 y=500

            if (!myra.isJumping()) {
                if (onPlatform1) {
                    if (myra.getX() < p1Left) myra.setX(p1Left);
                    if (myra.getX() + myra.getWidth() > p1Right) 
                        myra.setX(p1Right - myra.getWidth());
                } else if (onPlatform2) {
                    if (myra.getX() < p2Left) myra.setX(p2Left);
                    if (myra.getX() + myra.getWidth() > p2Right) 
                        myra.setX(p2Right - myra.getWidth());
                }
            }

            // ถ้าไม่อยู่บน platform → พื้น
            if (!onPlatform) {
                myra.setGroundY(740);
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
        myra.resetPosition(150, 740);
        turtle1.setX(1000);
        turtle1.setY(740);
        turtle2.setX(500);
        turtle2.setY(740);
        coin.setX(1500);
        coin.setY(765);

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
        turtle1.draw(g, this);
        turtle2.draw(g, this);
        coin.draw(g, this);
        for (Tile t : platform1) t.draw(g, this);
        for (Tile t : platform2) t.draw(g, this);

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
    private void checkTurtleCollision(Turtle t) {
        if (myra.getY() + myra.getHeight() <= t.getY() + 50) {
            // ข้ามหัวเต่า → ไม่ลดชีวิต
        } else {
            myra.life--;
            if (myra.life <= 0) {
                stopAllTimers();
                int choice = JOptionPane.showOptionDialog(
                    this,
                    "You Lose!",
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
                myra.resetPosition(150, 740);
            }
        }
    }
}

