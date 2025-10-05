package projectmyra;
//real
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class ProjectMyra extends JFrame {
    public ProjectMyra() {
        setTitle("Myra");
        setSize(1800, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Show menu first
        add(new MenuPanel(this));

        setVisible(true);
    }

    // Static method to create game panel
    public static DrawArea createGamePanel() {
        // Load background
        URL imageURL = ProjectMyra.class.getResource("images/game bg.jpg");
        Image imageBg = new ImageIcon(imageURL).getImage();

        // Load actors
        Myra myra = new Myra("images/myra.png", 150, 600, 150, 150);
        Turtle turtle = new Turtle("images/turtle.png", 1000, 600, 150, 160);
        Coin coin = new Coin("images/coin.png", 1500, 625, 100, 100);

        // Load hearts
        URL fullheartURL = ProjectMyra.class.getResource("images/full heart.png");
        Image fullHeart = new ImageIcon(fullheartURL).getImage();
        URL emptyheartURL = ProjectMyra.class.getResource("images/empty heart.png");
        Image emptyHeart = new ImageIcon(emptyheartURL).getImage();

        return new DrawArea(imageBg, myra, turtle, coin, fullHeart, emptyHeart);
    }

    // ============================
    // DrawArea
    // ============================
    static class DrawArea extends JPanel implements KeyListener {
        private Image bg, fullHeart, emptyHeart;
        private Myra myra;
        private Turtle turtle;
        private Coin coin;
        
        // Timer and score variables
        private int timeLeft = 60;  // seconds
        private int score = 1000;   // starting score
        private Timer gameTimer;
        
        DrawArea(Image bg, Myra myra, Turtle turtle, Coin coin, Image fullHeart, Image emptyHeart) {
            this.bg = bg;
            this.myra = myra;
            this.turtle = turtle;
            this.coin = coin;
            this.fullHeart = fullHeart;
            this.emptyHeart = emptyHeart;

            Timer frametimer = new Timer(20, e -> {
                myra.update();
                turtle.update();
                coin.update();

                // Collision Coin
                if (myra.getBounds().intersects(coin.getBounds())) {
                    int choice = JOptionPane.showOptionDialog(
                        this,
                        "Victory! You got the coin!\nYour score: " + score,
                        "Game Over",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Exit", "Play Again"},
                        "Exit"
                    );
                    if (choice == JOptionPane.YES_OPTION) System.exit(0);
                    else {
                        resetGame();
                    
                    }
                }

                // Collision Turtle
                if (myra.getBounds().intersects(turtle.getBounds())) {
                    if (myra.getY() + myra.getHeight() <= turtle.getY() + 50) {
                        // ข้ามหัวเต่า → ไม่ลดชีวิต
                    } else {
                        myra.life--;
                        if (myra.life <= 0) {
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
                            }
                        } else {
                            // ตายครั้งแรก/ครั้งสอง → รีเซ็ตตำแหน่ง Myra
                            myra.setX(150);
                            myra.setY(600);
                        }
                    }
                }

                repaint();
            });
            frametimer.start();
            
            // Countdown timer and score reduction
            gameTimer = new Timer(1000, e -> {
                timeLeft--;
                score -= 1000 / 60; // reduce score gradually
                if (timeLeft <= 0) {
                    gameTimer.stop();
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
                    else resetGame();
                }
            });
            gameTimer.start();
            addKeyListener(this);
        }
        // Reset game state
        private void resetGame() {
            myra.life = 3;
            myra.setX(150);
            myra.setY(600);
            turtle.setX(1000);
            coin.setX(1500);
            coin.setY(625);
            timeLeft = 60;
            score = 1000;
            gameTimer.start();
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            myra.draw(g, this);
            turtle.draw(g, this);
            coin.draw(g, this);

            // Hearts
            int xH = 20, yH = 10;
            for (int i = 0; i < 3; i++) {
                if (i < myra.life) g.drawImage(fullHeart, xH + i*60, yH, 75, 60, this);
                else g.drawImage(emptyHeart, xH + i*60 + 8, yH+7, 58, 52, this);
            }
            // Draw time and score
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(Color.WHITE);
            g.drawString("Time: " + timeLeft + "s", 1300, 70);
            g.drawString("Score: " + score, 1300, 130);
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
        @Override public void keyTyped(KeyEvent e) {}
    }

    // ============================
    // Main
    // ============================
    public static void main(String[] args) {
        new ProjectMyra();
    }
}
//test 123
