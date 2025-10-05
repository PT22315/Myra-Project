
package projectmyra;
//real
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public abstract class Actor {
    protected Image image;
    protected int x, y, width, height;

    public Actor(String path, int x, int y, int width, int height) {
        URL url = this.getClass().getResource(path);
        this.image = new ImageIcon(url).getImage();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g, JPanel panel) {
        g.drawImage(image, x, y, width, height, panel);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public abstract void update();

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
