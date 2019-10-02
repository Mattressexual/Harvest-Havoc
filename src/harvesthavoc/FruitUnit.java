package harvesthavoc;

public class FruitUnit extends Unit {
    
    private double SCREEN_X = 600;
    private double SCREEN_Y = 500;
    private double FRUIT_DELTAX = 0;
    private double FRUIT_DELTAY = 1;
    double xStart;
    double yStart;
    
    public FruitUnit() {
        deltaX = FRUIT_DELTAX;
        deltaY = FRUIT_DELTAY;
        
        xStart = Math.random() * SCREEN_X - width;
        yStart = 0 - height;
        
        setX(xStart);
        setY(yStart);
    }
    
    public FruitUnit(String imageFile) {
        super(imageFile);
        deltaX = FRUIT_DELTAX;
        deltaY = FRUIT_DELTAY;
        
        xStart = Math.random() * SCREEN_X - width;
        yStart = 0 - height;
        
        setX(xStart);
        setY(yStart);
    }
    
    public FruitUnit(String imageFile, double width, double height) {
        super(imageFile, width, height);
        deltaX = FRUIT_DELTAX;
        deltaY = FRUIT_DELTAY;
        
        xStart = Math.random() * SCREEN_X - width;
        if (xStart < 0)
            xStart += width;
        
        yStart = 0 - height;
        
        setX(xStart);
        setY(yStart);
    }
    
    public void fall() {
        setY(getY() + deltaY);
        setRotate(getRotate() + 1);
    }
    
    @Override
    public void act() {
        fall();
    }
}
