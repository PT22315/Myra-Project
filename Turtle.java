
package projectmyra;
//real
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

class Turtle extends Actor {
        private int dir = 1; 
        private final int minX = 800;
        private final int maxX = 1200;

        public Turtle(String path, int x, int y, int width, int height) {
            super(path, x, y, width, height);
        }

        @Override
        public void update() {
            x += dir * 3;
            if (x >= maxX || x <= minX) dir *= -1;
        }
}
