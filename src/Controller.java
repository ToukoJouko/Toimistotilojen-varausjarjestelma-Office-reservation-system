

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField laskuid;

    @FXML
    private TextField summa;

    @FXML
    private TextField erapaiva;

    @FXML
    private TextField varausid;

    @FXML
    private TextField asiakasid;

    @FXML
    private TextField etunimi;

    @FXML
    private TextField sukunimi;

    @FXML
    private TextField sposti;

    @FXML
    private TextField katu;

    @FXML
    private TextField postinro;

    @FXML
    private TextField toimipaikka;

    Lasku uusilasku = new Lasku();

    

    @FXML
    void haeLasku(ActionEvent event) throws SQLException, Exception {
        uusilasku.haeLasku();
    }

    @FXML
    void kirjoitaLasku(ActionEvent event) throws IOException {
        uusilasku.kirjoitaLasku();

    }

    @FXML
    void lahetaLasku(ActionEvent event) {
        uusilasku.lahetaLasku();

    }

    @FXML
    void lisaaLasku(ActionEvent event) throws SQLException, Exception {
        uusilasku.lisaaLasku();

    }
    

    @FXML
    void initialize() {
        assert laskuid != null : "fx:id=\"laskuid\" was not injected: check your FXML file 'LaskuGUI.fxml'.";
        assert summa != null : "fx:id=\"summa\" was not injected: check your FXML file 'LaskuGUI.fxml'.";
        assert erapaiva != null : "fx:id=\"erapaiva\" was not injected: check your FXML file 'LaskuGUI.fxml'.";
        assert varausid != null : "fx:id=\"varausid\" was not injected: check your FXML file 'LaskuGUI.fxml'.";
        assert asiakasid != null : "fx:id=\"asiakasid\" was not injected: check your FXML file 'LaskuGUI.fxml'.";
        assert etunimi != null : "fx:id=\"etunimi\" was not injected: check your FXML file 'LaskuGUI.fxml'.";
        assert sukunimi != null : "fx:id=\"sukunimi\" was not injected: check your FXML file 'LaskuGUI.fxml'.";
        assert sposti != null : "fx:id=\"sposti\" was not injected: check your FXML file 'LaskuGUI.fxml'.";
        assert katu != null : "fx:id=\"katu\" was not injected: check your FXML file 'LaskuGUI.fxml'.";
        assert postinro != null : "fx:id=\"postinro\" was not injected: check your FXML file 'LaskuGUI.fxml'.";
        assert toimipaikka != null : "fx:id=\"toimipaikka\" was not injected: check your FXML file 'LaskuGUI.fxml'.";

    }
}

