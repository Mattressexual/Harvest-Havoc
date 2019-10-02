package harvesthavoc;

import com.sun.javafx.scene.traversal.Direction;

public class PlayerUnit extends Unit {
    private double SCREEN_X = 600;
    private double SCREEN_Y = 500;
    
    private double PLAYER_DELTAX = 3;
    private double PLAYER_DELTAY = 1;
    
    private boolean movingLeft = false;
    private boolean movingRight = false;
    
    public PlayerUnit() {
        deltaX = PLAYER_DELTAX;
        deltaY = PLAYER_DELTAY;
    }
    
    public PlayerUnit(String imageFile) {
        super(imageFile);
        deltaX = PLAYER_DELTAX;
        deltaY = PLAYER_DELTAY;
    }
    
    public PlayerUnit(String imageFile, double width, double height) {
        super(imageFile, width, height);
        deltaX = PLAYER_DELTAX;
        deltaY = PLAYER_DELTAY;
    }
    
    public void startMove(Direction direction) {
        switch(direction) {
            case LEFT:
                movingLeft = true;
                setRotate(-10);
                if (movingRight)
                    setRotate(0);
                break;
                
            case RIGHT:
                movingRight = true;
                setRotate(10);
                if (movingLeft)
                    setRotate(0);
                break;
        }
    } 
    
    public void stopMove(Direction direction) {
        switch(direction) {
            case LEFT:
                movingLeft = false;
                if (movingRight)
                    setRotate(10);
                else
                    setRotate(0);
                break;
            case RIGHT:
                movingRight = false;
                if (movingLeft)
                    setRotate(-10);
                else
                    setRotate(0);
                break;
        }
    }
    
    public void move() {
        
        
        
        if (movingLeft && !movingRight) {
            if (getX() > 0) {
                setX(getX() - deltaX);
            }
        }
        if (movingRight && !movingLeft) {
            if (getX() < SCREEN_X - width) {
                setX(getX() + deltaX);
            }
        }
    }
    
    @Override
    public void act() {
        move();
    }
}
