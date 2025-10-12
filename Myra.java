package projectmyra;
//chatgpt
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class Myra extends Actor {
    private boolean isJumping = false;
    private int jumpVelocity = 0;
    private int groundY;
    private final int gravity = 2;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    public int life = 3;
    //for animation
    private Image[] walkRightFrames; // myra1,2,3
    private Image[] walkLeftFrames;  // myra4,5,6
    private Image[] currentFrames;   // 
    private int currentFrame = 0;
    private int frameDelay = 5; // update every 5 update
    private int frameCount = 0;
    private boolean facingRight = true; // true = right, false = left

    
    public Myra(String path, int x, int y, int width, int height) {
        super(path, x, y, width, height);
        this.groundY=y;
        walkRightFrames = new Image[3];
        walkRightFrames[0] = new ImageIcon(getClass().getResource("images/myra1.png")).getImage();
        walkRightFrames[1] = new ImageIcon(getClass().getResource("images/myra2.png")).getImage();
        walkRightFrames[2] = new ImageIcon(getClass().getResource("images/myra3.png")).getImage();

        walkLeftFrames = new Image[3];
        walkLeftFrames[0] = new ImageIcon(getClass().getResource("images/myra4.png")).getImage();
        walkLeftFrames[1] = new ImageIcon(getClass().getResource("images/myra5.png")).getImage();
        walkLeftFrames[2] = new ImageIcon(getClass().getResource("images/myra6.png")).getImage();

        currentFrames = walkRightFrames;
        image = currentFrames[0];
    }
    public void setGroundY(int groundY) {
        this.groundY = groundY;
    }
    public void resetPosition(int x, int y) {
        setX(x);
        setY(y);
        groundY = y;
        isJumping = false;
        jumpVelocity = 0;
    }


    @Override
    public void update() {
        //start jump til hit ground
        //start if loop when use jump() 
        if (isJumping) {
            setY(getY() - jumpVelocity);
            jumpVelocity -= gravity;
            if (getY() >= groundY) {
                setY(groundY);
                isJumping = false;
            }
        }
        //go left-right
        if (leftPressed) setX(getX() - 10);
        if (rightPressed) setX(getX() + 10);
        //stay in frame
        if (getX() < 0) setX(0);
        if (getX() > 1640) setX(1640);
        
        if (leftPressed || rightPressed) {
            if (rightPressed && !facingRight) {
                facingRight = true;
                currentFrames = walkRightFrames;
                currentFrame = 0;
            }
            if (leftPressed && facingRight) {
                facingRight = false;
                currentFrames = walkLeftFrames;
                currentFrame = 0;
            }

            frameCount++;
            if (frameCount >= frameDelay) {
                frameCount = 0;
                currentFrame = (currentFrame + 1) % currentFrames.length;
                image = currentFrames[currentFrame];
            }
        } else {
            //if not moving stay facing that direction
            image = currentFrames[0];
        }

    }
    

    public void jump() {
        //dont activate if still jumping
        if (!isJumping) {
            isJumping = true;
            jumpVelocity = 50;
        }
    }

    public void setLeftPressed(boolean value) { 
        leftPressed = value; 
    }
    public void setRightPressed(boolean value) {
        rightPressed = value; 
    }
}
