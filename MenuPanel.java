package projectmyra;
//real
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class MenuPanel extends JPanel {
    private Image bg;
    private JButton startBtn, exitBtn;
    private JButton forestBtn, mountainBtn;
    private JButton startGameBtn,backBtn;
    private String selectedMap;

    public MenuPanel(JFrame frame) {
        setLayout(null);
        loadBackground("images/menu bg.png");

        // Start Button
        startBtn = new JButton("Start");
        startBtn.setIcon(new ImageIcon(getClass().getResource("images/startbtn.png")));
        startBtn.setBounds(750, 400, 320, 100);
        startBtn.setBorderPainted(false);
        startBtn.setContentAreaFilled(false);
        startBtn.setFocusPainted(false);
        startBtn.addActionListener(e -> showMapButtons(frame));
        add(startBtn);
        
        exitBtn = new JButton("Exit");
        exitBtn.setIcon(new ImageIcon(getClass().getResource("images/exitbtn.png")));
        exitBtn.setBounds(750, 500, 320, 100);
        exitBtn.setBorderPainted(false);
        exitBtn.setContentAreaFilled(false);
        exitBtn.setFocusPainted(false);
        exitBtn.addActionListener(e -> System.exit(0));
        add(exitBtn);
        
        //Back Button (Hide)
        ImageIcon backIcon = new ImageIcon(getClass().getResource("images/backbtn.png"));
        Image scaledBack = backIcon.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
        backBtn = new JButton(new ImageIcon(scaledBack));
        backBtn.setBounds(50, 30, 150, 100);
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setFocusPainted(false);
        backBtn.setVisible(false); // here
        add(backBtn);
    }

    private void loadBackground(String path) {
        URL imageURL = ProjectMyra.class.getResource(path);
        bg = new ImageIcon(imageURL).getImage();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }

    private void showMapButtons(JFrame frame) {
        // Start/Exit
        startBtn.setVisible(false);
        exitBtn.setVisible(false);

        // Forest Button
        forestBtn = new JButton("Forest");
        forestBtn.setIcon(new ImageIcon(getClass().getResource("images/Forestbtn.png")));
        forestBtn.setBounds(500, 400, 330, 130);
        forestBtn.setBorderPainted(false);
        forestBtn.setContentAreaFilled(false);
        forestBtn.setFocusPainted(false);
        forestBtn.addActionListener(e -> selectMap("forest",frame));
        add(forestBtn);

        // Mountain Button
        mountainBtn = new JButton("Mountain");
        mountainBtn.setIcon(new ImageIcon(getClass().getResource("images/Mountainbtn.png")));
        mountainBtn.setBounds(1000, 400, 330, 130);
        mountainBtn.setBorderPainted(false);
        mountainBtn.setContentAreaFilled(false);
        mountainBtn.setFocusPainted(false);
        mountainBtn.addActionListener(e -> selectMap("mountain",frame));
        add(mountainBtn);
        
        backBtn.setVisible(true);
        // Update backBtn action
        for (ActionListener al : backBtn.getActionListeners()) {
            backBtn.removeActionListener(al);
        }
        backBtn.addActionListener(e -> backToMenu(frame));;

        repaint();
    }

    private void selectMap(String mapName,JFrame frame) {
        this.selectedMap = mapName;

        //background preview map
        if (mapName.equals("forest")) loadBackground("images/game bg.jpg");
        else if (mapName.equals("mountain")) loadBackground("images/mountains bg.jpg");

        // button disappear 
        forestBtn.setVisible(false);
        mountainBtn.setVisible(false);

        // Start Game button
        startGameBtn = new JButton("Start Game");
        startGameBtn.setIcon(new ImageIcon(getClass().getResource("images/StartGamebtn.png")));
        startGameBtn.setBounds(650, 500, 500, 120);
        startGameBtn.setBorderPainted(false);
        startGameBtn.setContentAreaFilled(false);
        startGameBtn.setFocusPainted(false);
        startGameBtn.addActionListener(e -> startGame((JFrame) getTopLevelAncestor(), selectedMap));
        add(startGameBtn);
        
        backBtn.setVisible(true);
        // Update backBtn action
        for (ActionListener al : backBtn.getActionListeners()) {
            backBtn.removeActionListener(al);
        }
        backBtn.addActionListener(e -> backToMapSelection(frame));
            repaint();
        }

    private void backToMapSelection(JFrame frame) {        
        loadBackground("images/menu bg.png");
        startGameBtn.setVisible(false);
        forestBtn.setVisible(true);
        mountainBtn.setVisible(true);

        for (ActionListener al : backBtn.getActionListeners()) backBtn.removeActionListener(al);
        backBtn.addActionListener(e -> backToMenu(frame));
        backBtn.setVisible(true);
        repaint();
    }
    
    private void backToMenu(JFrame frame) {
        if (forestBtn != null) 
            forestBtn.setVisible(false);
        if (mountainBtn != null) 
            mountainBtn.setVisible(false);
        if (startGameBtn != null) 
            startGameBtn.setVisible(false);

        loadBackground("images/menu bg.png");
        startBtn.setVisible(true);
        exitBtn.setVisible(true);
        backBtn.setVisible(false);

        repaint();
    }
    private void startGame(JFrame frame, String mapName) {
        frame.getContentPane().removeAll();
        DrawArea drawArea; //  abstract type

        if (mapName.equals("forest")) {
            drawArea = new DrawForest(frame);
        } else {
            drawArea = new DrawMountain(frame);
        }
        frame.add(drawArea);
        drawArea.setFocusable(true);
        drawArea.requestFocusInWindow();
        frame.revalidate();
        frame.repaint();
    }
}