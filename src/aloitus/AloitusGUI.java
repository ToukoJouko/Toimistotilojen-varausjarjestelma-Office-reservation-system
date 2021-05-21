package aloitus;

import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AloitusGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url =  new File("src/aloitus/Aloitus.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Vuokratoimistot OY");
        primaryStage.setScene(new Scene(root, 1200, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
