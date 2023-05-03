package models;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("/fonts/Lato-Regular.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Lato-Bold.ttf"), 14);

        Parent root = FXMLLoader.load(getClass().getResource("/views/sampleView.fxml"));

        primaryStage.setTitle("USA Travel");
        primaryStage.setScene(new Scene(root, 830, 500));
        
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(830);

        
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
