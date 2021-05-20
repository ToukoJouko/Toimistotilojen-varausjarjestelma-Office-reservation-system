package sample;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.awt.*;

public class Controller implements Initializable {
    protected Button naytaPalvelut;

public Controller(){
}
    @FXML
    public void naytaPalvelut(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(this.getClass().getResource("Palvelut.fxml"));
    Stage stage = new Stage();
    stage.setTitle("Palveluiden hallinta");
    stage.setMaximized(false);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(new Scene(root));
    stage.showAndWait();
}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}