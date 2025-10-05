/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmyra;
//real
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class MenuPanel extends JPanel {
    private Image bg;

    public MenuPanel(JFrame frame) {
        setLayout(null);
        // Load background
        URL imageURL = ProjectMyra.class.getResource("images/game bg.jpg");
        bg = new ImageIcon(imageURL).getImage();

        // Start Button
        JButton startBtn = new JButton("Start");
        startBtn.setBounds(800, 400, 200, 80);
        startBtn.setFont(new Font("Arial", Font.BOLD, 30));
        startBtn.addActionListener(e -> startGame(frame));
        add(startBtn);

        // Exit Button
        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(800, 500, 200, 80);
        exitBtn.setFont(new Font("Arial", Font.BOLD, 30));
        exitBtn.addActionListener(e -> System.exit(0));
        add(exitBtn);
    }
     // Paint background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }
        
    public void startGame(JFrame frame) {
        // Remove menu panel
        frame.getContentPane().removeAll();

        // Create game panel
        ProjectMyra.DrawArea drawArea = ProjectMyra.createGamePanel();
        frame.add(drawArea);
        drawArea.setFocusable(true);
        drawArea.requestFocusInWindow();

        frame.revalidate();
        frame.repaint();
    }
}

