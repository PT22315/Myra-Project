package projectmyra;



import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class ProjectMyra extends JFrame {

    public ProjectMyra() {
        // bg
        URL imageURL = this.getClass().getResource("images/game bg.jpg");
        Image imageBg = new ImageIcon(imageURL).getImage();

        // actor Myra
        URL actorMyraURL = this.getClass().getResource("images/myra.png");
        Image imageActorMyra = new ImageIcon(actorMyraURL).getImage();
        
        // actor Turtle
        URL actorTurtleURL = this.getClass().getResource("images/turtle.png");
        Image imageActorTurtle = new ImageIcon(actorTurtleURL).getImage();
        
        // coin
        URL coinURL = this.getClass().getResource("images/coin.png");
        Image imageCoin = new ImageIcon(coinURL).getImage();
        
        // full heart
        URL fullheartURL = this.getClass().getResource("images/full heart.png");
        Image imageFullheart = new ImageIcon(fullheartURL).getImage();
        
        // empty heart
        URL emptyheartURL = this.getClass().getResource("images/empty heart.png");
        Image imageEmptyheart = new ImageIcon(emptyheartURL).getImage();

        // DrawArea
        add(new DrawArea(imageBg, imageActorMyra , imageActorTurtle , imageCoin , imageFullheart , imageEmptyheart));
    }

    // inner class
    public static class DrawArea extends JPanel {
        private Image imageBg;
        private Image imageMyra;
        private Image imageTurtle;
        private Image imageCoin;
        private Image imageFullheart;
        private Image imageEmptyheart;
        int xBg = 0, yBg = 0; //bg
        int xMyra = 150, yMyra = 550; //actor Myra
        int xTurtle = 1000 , yTurtle = 550; //actor Turtle
        int xCoin = 1500 , yCoin = 575; //coin
        int xFullheart = 20 , yFullheart = 10; //fullheart

        DrawArea(Image bg, Image actorMyra , Image actorTurtle , Image coin , Image Fullheart, Image Emptyheart) {
            this.imageBg = bg;
            this.imageMyra = actorMyra;
            this.imageTurtle = actorTurtle;
            this.imageCoin = coin;
            this.imageFullheart = Fullheart;
            this.imageEmptyheart = Emptyheart;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            // bg
            g.drawImage(imageBg, xBg, yBg, getWidth() , getHeight(), this);

            // actor Myra
            g.drawImage(imageMyra, xMyra, yMyra, 200 , 200, this);
            
            //actor Turtle
            g.drawImage(imageTurtle, xTurtle, yTurtle, 200 , 210, this);
            
            //coin
            g.drawImage(imageCoin, xCoin, yCoin, 150 , 150, this);
            
            //fullheart
            g.drawImage(imageFullheart, xFullheart, yFullheart,75,60 , this);
            g.drawImage(imageFullheart, xFullheart + 60, yFullheart,75,60 , this);
            g.drawImage(imageFullheart, xFullheart + 120, yFullheart,75,60 , this);
            
            /*emptyheart 
            (draw when myra hit turtle)
            when hit turtle -> the most right fullheart will be replace by the emptyheart
            */
            //use this parameter for first time die g.drawImage(imageEmptyheart, xFullheart + 130, yFullheart + 7 ,58,52 , this);
            //use this parameter for second time die g.drawImage(imageEmptyheart, xFullheart + 70, yFullheart + 7 ,58,52 , this);
            //use this parameter when lost the last life g.drawImage(imageEmptyheart, xFullheart + 10, yFullheart + 7 ,58,52 , this);
            
            //g.drawImage(imageEmptyheart, xFullheart + 130, yFullheart + 7 ,57,52 , this);
            //g.drawImage(imageEmptyheart, xFullheart + 70, yFullheart + 7 ,58,52 , this);
            //g.drawImage(imageEmptyheart, xFullheart + 10, yFullheart + 7 ,58,52 , this);
            
        }
    }

    public static void main(String[] args) {
        JFrame demo = new ProjectMyra();
        demo.setTitle("Demo");
        demo.setSize(1800, 1080);
        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demo.setVisible(true);
    }
}
//test