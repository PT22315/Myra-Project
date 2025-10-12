package projectmyra;
//real
import java.awt.*;
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
        loadBackground("images/menu bg.jpg");

        // Start Button
        startBtn = new JButton("Start");
        startBtn.setBounds(800, 400, 200, 80);
        startBtn.setFont(new Font("Arial", Font.BOLD, 30));
        startBtn.addActionListener(e -> showMapButtons(frame));
        add(startBtn);

        // Exit Button
        exitBtn = new JButton("Exit");
        exitBtn.setBounds(800, 500, 200, 80);
        exitBtn.setFont(new Font("Arial", Font.BOLD, 30));
        exitBtn.addActionListener(e -> System.exit(0));
        add(exitBtn);
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
        forestBtn.setBounds(475, 400, 250, 100);
        forestBtn.setFont(new Font("Arial", Font.BOLD, 30));
        forestBtn.addActionListener(e -> selectMap("forest",frame));
        add(forestBtn);

        // Mountain Button
        mountainBtn = new JButton("Mountain");
        mountainBtn.setBounds(1075, 400, 250, 100);
        mountainBtn.setFont(new Font("Arial", Font.BOLD, 30));
        mountainBtn.addActionListener(e -> selectMap("mountain",frame));
        add(mountainBtn);

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
        startGameBtn.setBounds(700, 500, 400, 80);
        startGameBtn.setFont(new Font("Arial", Font.BOLD, 30));
        startGameBtn.addActionListener(e -> startGame((JFrame) getTopLevelAncestor(), selectedMap));
        add(startGameBtn);
        
        backBtn = new JButton("Back");
        backBtn.setBounds(50, 30, 150, 60);
        backBtn.setFont(new Font("Arial", Font.BOLD, 25));
        backBtn.addActionListener(e -> backToMapSelection(frame));
        add(backBtn);
        repaint();
    }

    private void backToMapSelection(JFrame frame) {

        startGameBtn.setVisible(false);
        backBtn.setVisible(false);

        loadBackground("images/menu bg.jpg");
        forestBtn.setVisible(true);
        mountainBtn.setVisible(true);

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
