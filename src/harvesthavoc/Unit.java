package harvesthavoc;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Unit extends ImageView {
    
    protected double height, width, deltaX, deltaY;
    
    public Unit() {
        
    }
    
    public Unit(String imageFile) {
        setImage(new Image(imageFile));
    }
    
    // Constructor if you want different dimensions than the picture itsels
    public Unit(String imageFile, double width, double height) {
        setImage(new Image(imageFile, width, height, false, false));
        this.width = width;
        this.height = height;
    }
    
    public abstract void act();
}
