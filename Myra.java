package projectmyra;
//chatgpt
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class Myra extends Actor {
    private boolean isJumping = false;
    private int jumpVelocity = 0;
    private final int groundY = 600;
    private final int gravity = 2;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    public int life = 3;

    public Myra(String path, int x, int y, int width, int height) {
        super(path, x, y, width, height);
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
        if (getX() > 1600) setX(1600);
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
