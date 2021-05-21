package TeeVaraus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class TeeVarausMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/TeeVaraus/TeeVarausGUI.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }
   
    public static void main(String[] args) {
        launch(args);
    }
}

