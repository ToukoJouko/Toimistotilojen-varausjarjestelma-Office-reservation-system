package aloitus;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AloitusController  {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void naytaAsiakas(ActionEvent event) throws IOException {
        URL url =  new File("src/asiakas/AsiakasGUI.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("VuokraToimistot OY/Asiakashallinta");
        stage.setMaximized(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }

    @FXML
    void naytaLasku(ActionEvent event) throws IOException {
        URL url =  new File("src/lasku/LaskuGUI.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("VuokraToimistot OY/Laskujen hallinta");
        stage.setMaximized(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }

    @FXML
    void naytaToimipiste(ActionEvent event) throws IOException {
        URL url =  new File("src/toimipiste/ToimipisteGUI.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("VuokraToimistot OY/Toimipisteiden hallinta");
        stage.setMaximized(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }

    @FXML
    void naytaVaraus(ActionEvent event) throws IOException {
        URL url =  new File("src/TeeVaraus/TeeVarausGUI.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("VuokraToimistot OY/Palveluiden hallinta");
        stage.setMaximized(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }



    @FXML
    public void naytaPalvelut(ActionEvent event) throws IOException {
        URL url =  new File("src/Palvelu/Palvelut.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("VuokraToimistot OY/Palveluiden hallinta");
        stage.setMaximized(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
}
@FXML
void initialize() {

}
}