package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PalvelutMain{
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Palvelut.fxml"));
        primaryStage.setTitle("Vuokratoimistot OY");
        primaryStage.setScene(new Scene(root, 1200, 700));
        primaryStage.show();
    }
}
