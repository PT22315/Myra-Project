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
//have ability item such as reset time