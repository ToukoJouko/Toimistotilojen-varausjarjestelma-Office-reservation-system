package toimipiste;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class ToimipisteMain extends Application {
    public void start(Stage primaryStage) throws Exception{
        URL url =  new File("src/toimipiste/ToimipisteGUI.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Vuokratoimistot OY / Toimipisteet");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}