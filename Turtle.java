
package projectmyra;
//real
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

class Turtle extends Actor {
        private int dir = 1; 
        private final int minX = 500;
        private final int maxX = 1200;
        //for animation
        private Image turtleRight, turtleLeft;
        private Image currentImage;

        public Turtle(String path, int x, int y, int width, int height) {
            super(path, x, y, width, height);
            turtleRight = new ImageIcon(getClass().getResource("images/turtle1.png")).getImage();
            turtleLeft  = new ImageIcon(getClass().getResource("images/turtle2.png")).getImage();

            currentImage = turtleRight;
        }

        @Override
        public void update() {
            x = x + dir * 3;
            if (x >= maxX || x <= minX){
                dir = dir * -1;
            }
            if (dir == 1) {
                currentImage = turtleRight;
            } else {
                currentImage = turtleLeft;
            }
        }
        @Override
        public void draw(Graphics g, JPanel panel) {
            g.drawImage(currentImage, x, y, width, height, panel);
        }
}

    

