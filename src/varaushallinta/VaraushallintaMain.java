package varaushallinta;

import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VaraushallintaMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url =  new File("src/varaushallinta/varaushallinta.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("VuokraToimistot OY/Laskujenhallinta");
        primaryStage.setScene(new Scene(root, 1000,600)); 
        primaryStage.show();
        
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
