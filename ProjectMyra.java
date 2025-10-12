package projectmyra;
//C:\Users\Asus\Documents\NetBeansProjects\ProjectMyra\src\projectmyra
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
    public static DrawArea createGamePanel(JFrame frame, String mapName) {
        if (mapName.equals("forest")) {
            return new DrawForest(frame);
        } else {
            return new DrawMountain(frame);
        }
    }
    // ============================
    // Main
    // ============================
    public static void main(String[] args) {
        new ProjectMyra();
    }
}

//made time when start. Start with 1 minute
//made score start at 1000 slowly goes down when time goes down to 0 and lose
//can choose more than 1 map
//made more map 
//have ability item such as freeze time so the score dont go down and the component that not player are freeze too