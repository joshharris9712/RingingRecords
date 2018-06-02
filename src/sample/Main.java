package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Scraper s = new Scraper("https://bb.ringingworld.co.uk/search.php?ringer=Josh%2AHarris&length=quarter", "C:/Users/Josh/Documents/Records");
        //s.scrape();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Ringing Records");
        primaryStage.setScene(new Scene(root, 600, 200));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
