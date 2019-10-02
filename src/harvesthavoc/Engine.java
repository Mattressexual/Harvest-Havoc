
package harvesthavoc;

import com.sun.javafx.scene.traversal.Direction;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Evan
 */
public class Engine {
    private double SCREEN_X = 600;
    private double SCREEN_Y = 500;
    private String MENU_QUIT_IMAGE = "quit.png";
    private String MENU_QUIT_H_IMAGE = "quit_h.png";
    private String MENU_BACKGROUND_IMAGE = "menu_background.png";
    private String GAME_BACKGROUND_IMAGE = "game_background.png";
    private double MENU_QUIT_HEIGHT = 88;
    private double MENU_QUIT_WIDTH = 232;
    private String GAME_PAUSE_IMAGE = "pause.png";
    private double GAME_PAUSE_HEIGHT = 88;
    private double GAME_PAUSE_WIDTH = 384;
    private String PLAYER_IMAGE = "basket.png";
    private double PLAYER_WIDTH = 80;
    private double PLAYER_HEIGHT = 110;
    private double PLAYER_DELTAX = 3;
    private double PLAYER_DELTAY = 1;
    private String FRUIT1_IMAGE = "fruit1.png";
    private String FRUIT2_IMAGE = "fruit2.png";
    private double FRUIT_WIDTH = 32;
    private double FRUIT_HEIGHT = 32;
    private double FRUIT_DELTAX = 0;
    private double FRUIT_DELTAY = 1;
    
    private Menu menu;
    
    private Stage stage;
    private Scene scene;
    private Pane gamePane;
    private StackPane pausePane;
    private VBox vbPause;
    
    Timeline mainTime;
    Timeline fruitTime;
    
    private PlayerUnit player;
    
    private ArrayList<Unit> fruitList = new ArrayList();
    private ArrayList<Unit> fruitCatchList = new ArrayList();
    private ArrayList<Unit> compostList = new ArrayList();
    
    private ImageView background;
    private ImageView pauseBackground;
    private ImageView pauseIcon;
    private ImageView pauseQuit;
    
    private Image backgroundImage;
    private Image pauseBackgroundImage;
    private Image pauseIconImage;
    private Image pauseQuitImage;
    private Image pauseQuitHImage;
    
    public Engine(Stage stage) {
        
        this.stage = stage;
        
        menu = new Menu(this.stage);
        menu.setEngine(this);
        
        stage.setScene(menu.getScene());
        
        // Game Layout
        gamePane = new Pane();
        
        backgroundImage = new Image(GAME_BACKGROUND_IMAGE, SCREEN_X, SCREEN_Y, false, false);
        background = new ImageView(backgroundImage);
        
        player = new PlayerUnit(PLAYER_IMAGE, PLAYER_WIDTH, PLAYER_HEIGHT);
        
        gamePane.getChildren().addAll(background, player);
        
        player.setX(SCREEN_X / 2 - PLAYER_WIDTH / 2);
        player.setY(330);
        
        // Pause Layout
        
        pauseBackgroundImage = new Image(MENU_BACKGROUND_IMAGE, SCREEN_X, SCREEN_Y, false, false);
        pauseBackground = new ImageView(pauseBackgroundImage);

        pauseIconImage = new Image(GAME_PAUSE_IMAGE, GAME_PAUSE_WIDTH, GAME_PAUSE_HEIGHT, false, false);
        pauseIcon = new ImageView(pauseIconImage);
        
        pauseQuitImage = new Image(MENU_QUIT_IMAGE, MENU_QUIT_WIDTH, MENU_QUIT_HEIGHT, false, false);
        pauseQuitHImage = new Image(MENU_QUIT_H_IMAGE, MENU_QUIT_WIDTH, MENU_QUIT_HEIGHT, false, false);
        pauseQuit = new ImageView(pauseQuitImage);
        
        pauseQuit.setOnMouseEntered(e -> {
            pauseQuit.setImage(pauseQuitHImage);
        });
        
        pauseQuit.setOnMouseExited(e -> {
            pauseQuit.setImage(pauseQuitImage);
        });
        
        pauseQuit.setOnMouseReleased(e -> {
            if (pauseQuit.contains(e.getX(), e.getY())) {
                mainTime.stop();
                fruitTime.stop();
                this.stage.setScene(menu.getScene());
            }
        });
        
        pauseIcon.toBack();
        pauseBackground.toBack();
        
        vbPause = new VBox(10);
        vbPause.getChildren().addAll(pauseIcon, pauseQuit);
        vbPause.setAlignment(Pos.CENTER);
        
        pausePane = new StackPane();
        pausePane.getChildren().addAll(pauseBackground, vbPause);
        
        ////////////////////////////////////////////////////////////////////////
        
        scene = new Scene(gamePane, SCREEN_X, SCREEN_Y);
        
        initializeKeyBinds();
        initializeTimelines();
    }
    
    public void initializeTimelines() {
        mainTime = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            player.act();
            
            for (Unit fruit : fruitList) {
                fruit.act();
                
                if (fruit.intersects(player.getX() + 20, player.getY(), player.width - 20, 0)) {
                    fruitCatchList.add(fruit);
                    compostList.add(fruit);
                }
                
                if (fruit.getY() >= 330 + player.height - fruit.height) {
                    compostList.add(fruit);
                }
            }
            
            for (Unit unit : compostList) {
                removeUnit(unit);
            }
        }));
        
        mainTime.setCycleCount(Timeline.INDEFINITE);
        mainTime.play();
        
        fruitTime = new Timeline(new KeyFrame(Duration.seconds(1.5), e -> {
            addUnit(new FruitUnit(FRUIT1_IMAGE, FRUIT_WIDTH, FRUIT_HEIGHT));
        }));
        
        fruitTime.setCycleCount(Timeline.INDEFINITE);
        fruitTime.play();
    }
    
    public void initializeKeyBinds() {
        scene.setOnKeyPressed(e -> {
            switch(e.getCode()) {
                case A:
                case LEFT: player.startMove(Direction.LEFT); break;
                case D:
                case RIGHT: player.startMove(Direction.RIGHT); break;
                case ESCAPE:
                    scene.setRoot(pausePane);
                    pauseAll();
                    scene.setOnKeyPressed(eh -> {
                        switch(eh.getCode()) {
                            case ESCAPE:
                                scene.setRoot(gamePane);
                                playAll();
                                initializeKeyBinds();
                        }
                    });
            }
        });
        
        scene.setOnKeyReleased(e -> {
            switch(e.getCode()) {
                case A:
                case LEFT: player.stopMove(Direction.LEFT); break;
                case D:
                case RIGHT: player.stopMove(Direction.RIGHT); break;
            }
        });
    }
    
    public void addUnit(Unit unit) {
        gamePane.getChildren().add(unit);
        
        if (unit instanceof FruitUnit) {
            fruitList.add(unit);
        }
    }
    
    public void removeUnit(Unit unit) {
        gamePane.getChildren().remove(unit);
        
        if (unit instanceof FruitUnit) {
            fruitList.remove(unit);
        }
    }
    
    public void pauseAll() {
        mainTime.pause();
        fruitTime.pause();
    }
    
    public void playAll() {
        mainTime.play();
        fruitTime.play();
    }
    
    public void stopAll() {
        mainTime.stop();
        fruitTime.stop();
    }
    
    public Menu getMenu() {
        return menu;
    }
    
    public Scene getScene() {
        return scene;
    }
}
