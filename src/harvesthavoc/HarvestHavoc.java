package harvesthavoc;

import javafx.application.Application;
import javafx.stage.Stage;

public class HarvestHavoc extends Application {
    
    @Override
    public void start(Stage stage) {
        Engine engine = new Engine(stage);
        
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    
}
