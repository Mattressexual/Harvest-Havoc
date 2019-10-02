package harvesthavoc;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Menu {
    
    // Values
    private double SCREEN_X = 600;
    private double SCREEN_Y = 500;
    private String MENU_PLAY_IMAGE = "play.png";
    private String MENU_HOWTO_IMAGE = "howto.png";
    private String MENU_QUIT_IMAGE = "quit.png";
    private String MENU_PLAY_H_IMAGE = "play_h.png";
    private String MENU_HOWTO_H_IMAGE = "howto_h.png";
    private String MENU_QUIT_H_IMAGE = "quit_h.png";
    private String MENU_BACKGROUND_IMAGE = "menu_background.png";
    private double MENU_PLAY_HEIGHT = 88;
    private double MENU_PLAY_WIDTH = 232;
    private double MENU_HOWTO_HEIGHT = 88;
    private double MENU_HOWTO_WIDTH = 488;
    private double MENU_QUIT_HEIGHT = 88;
    private double MENU_QUIT_WIDTH = 232;
    
    private Stage stage;
    private Scene scene;
    
    private Pane pane;
    private StackPane stackpane;
    private VBox vbButtons;
    
    private ImageView playButton;
    private ImageView howtoButton;
    private ImageView quitButton;
    private ImageView background;
    
    private Image playImage;
    private Image playHImage;
    private Image howtoImage;
    private Image howtoHImage;
    private Image quitImage;
    private Image quitHImage;
    
    private Image backgroundImage;
    
    private Timeline backgroundTime;
    
    private Engine engine;
    
    public Menu(Stage stage) {
        
        this.stage = stage;
        
        stackpane = new StackPane();
        pane = new Pane();
        
        playImage = new Image(MENU_PLAY_IMAGE, MENU_PLAY_WIDTH, MENU_PLAY_HEIGHT, false, false);
        playHImage = new Image(MENU_PLAY_H_IMAGE, MENU_PLAY_WIDTH, MENU_PLAY_HEIGHT, false, false);
        howtoImage = new Image(MENU_HOWTO_IMAGE, MENU_HOWTO_WIDTH, MENU_HOWTO_HEIGHT, false, false);
        howtoHImage = new Image(MENU_HOWTO_H_IMAGE, MENU_HOWTO_WIDTH, MENU_HOWTO_HEIGHT, false, false);
        quitImage = new Image(MENU_QUIT_IMAGE, MENU_QUIT_WIDTH, MENU_QUIT_HEIGHT, false, false);
        quitHImage = new Image(MENU_QUIT_H_IMAGE, MENU_QUIT_WIDTH, MENU_QUIT_HEIGHT, false, false);
        
        playButton = new ImageView(playImage);
        howtoButton = new ImageView(howtoImage);
        quitButton = new ImageView(quitImage);
        
        backgroundImage = new Image(MENU_BACKGROUND_IMAGE, SCREEN_X * 2, SCREEN_Y * 2, false, false); // BACKGROUND_IMAGE
        background = new ImageView(backgroundImage);
        
        vbButtons = new VBox(SCREEN_Y / 50);
        vbButtons.getChildren().addAll(playButton, howtoButton, quitButton);
        vbButtons.setAlignment(Pos.CENTER);
        
        pane.getChildren().add(background);
        
        stackpane.getChildren().addAll(pane, vbButtons);
        background.setX(0);
        background.setY(0);
        
        scene = new Scene(stackpane, SCREEN_X, SCREEN_Y);
        
        playButton.setOnMouseEntered(e -> {
            playButton.setImage(playHImage);
        });
        
        playButton.setOnMouseExited(e -> {
            playButton.setImage(playImage);
        });
        
        playButton.setOnMouseReleased(e -> {
            if (playButton.contains(e.getX(), e.getY())){
                backgroundTime.stop();
                this.engine = new Engine(stage);
                this.stage.setScene(engine.getScene());
            }
        });
        
        howtoButton.setOnMouseReleased(e -> {
            if (howtoButton.contains(e.getX(), e.getY())) {
                
            }
        });
        
        howtoButton.setOnMouseEntered(e -> {
            howtoButton.setImage(howtoHImage);
        });
        
        howtoButton.setOnMouseExited(e -> {
            howtoButton.setImage(howtoImage);
        });
        
        quitButton.setOnMouseReleased(e -> {
            if (quitButton.contains(e.getX(), e.getY())) {
                Platform.exit();
            }
        });
        
        quitButton.setOnMouseEntered(e -> {
            quitButton.setImage(quitHImage);
        });
        
        quitButton.setOnMouseExited(e -> {
            quitButton.setImage(quitImage);
        });
        
        startScrollBackground();
    }
    
    public void startScrollBackground() {
        backgroundTime = new Timeline(new KeyFrame(Duration.millis(1), e -> {
            if (background.getX() > -1 * SCREEN_X / 2)
                background.setX(background.getX() - 0.1);
            else
                background.setX(-100);
        }));
        
        backgroundTime.setCycleCount(Timeline.INDEFINITE);
        backgroundTime.play();
    }
    
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    
    public void setScene(Scene scene) {
        this.scene = scene;
    }
    
    public Engine getEngine() {
        return engine;
    }
    
    public Scene getScene() {
        return scene;
    }
}
