package Palvelu;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;


public class PalvelutController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label tfPalvelu;

    @FXML
    private Label tfTyyppi;

    @FXML
    private Label tfNimi;

    @FXML
    private Label tfHinta;

    @FXML
    private TextField palveluID;

    @FXML
    private TextField tyyppi;

    @FXML
    private TextField nimi;

    @FXML
    private TextField hinta;

    @FXML
    private TableView<?> tvPalvelu;

    @FXML
    private Button tyhjenna;

    @FXML
    private TableColumn<?, ?> colPalvelu;

    @FXML
    private TableColumn<?, ?> colLaite;

    @FXML
    private TableColumn<?, ?> colSijainti;

    @FXML
    private TableColumn<?, ?> colHinta;

    @FXML
    private Button btnSijoita;

    @FXML
    private Button btnPaivita;

    @FXML
    private Button btnPoista;

    @FXML
    void tyhjennaTiedot(ActionEvent event) {
        palveluID.setText("");
        tyyppi.setText("");
        nimi.setText("");
        hinta.setText("");

    }

    @FXML
    void lisaaPalvelu(ActionEvent event) throws SQLException, Exception {
        String sql = "SELECT PalveluID FROM palvelut WHERE PalveluID = ?";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;
        try {
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "-");
            lause = mycon.prepareStatement(sql);
            lause.setInt(1, Integer.parseInt(palveluID.getText()));
            tulosjoukko = lause.executeQuery();
            if (tulosjoukko.next() == true) {
                throw new Exception("Palvelu on jo olemassa");
        }
        } catch (SQLException var8) {
            throw var8;
        } catch (Exception var9) {
            throw var9;
        }
        sql = "INSERT INTO palvelut(PalveluID, Tyyppi, Nimi, Hinta) VALUES(?,?,?,?);";
        lause = null;
        try {
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "-");
            lause = mycon.prepareStatement(sql);
            lause.setInt(1,Integer.parseInt(palveluID.getText()));
            lause.setString(2,tyyppi.getText());
            lause.setString(3,nimi.getText());
            lause.setDouble(4,Double.parseDouble(hinta.getText()));
            int lkm = lause.executeUpdate();

            if (lkm == 0){
                throw new Exception("Palvelun lisäys ei onnistunut");

            }
        }
        catch (SQLException var8) {
            throw var8;
        } catch (Exception var9) {
            throw var9;
        }
    }

    @FXML
    void muutaPalvelu(ActionEvent event) throws SQLException, Exception {
        String sql = "UPDATE palvelut SET Tyyppi = ?, Nimi = ?, Hinta = ? WHERE PalveluID = ?";
        PreparedStatement paivitys = null;
        try {
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "-");
            paivitys = mycon.prepareStatement(sql);
            paivitys.setString(1,tyyppi.getText());
            paivitys.setString(2,nimi.getText());
            paivitys.setString(3,hinta.getText());
            paivitys.setString(4,palveluID.getText());
            paivitys.executeUpdate();
        }
        catch (SQLException var8) {
            throw var8;
        } catch (Exception var9) {
            throw var9;
        }
    }

    @FXML
    void poistaPalvelu(ActionEvent event) throws SQLException, Exception {
        String sql = "DELETE FROM palvelut WHERE PalveluID = ?";
        PreparedStatement poisto = null;
        try {
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "-");
            poisto = mycon.prepareStatement(sql);
            poisto.setInt(1,Integer.parseInt(palveluID.getText()));
            poisto.executeUpdate();
        }
        catch (SQLException var8) {
            throw var8;
        } catch (Exception var9) {
            throw var9;
        }

    }

    @FXML
    void haePalvelu(ActionEvent event) throws SQLException, Exception {

        String sql = "SELECT palveluID, Tyyppi, Nimi, Hinta FROM palvelut WHERE palveluID = ?";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;

        try {
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "-");
            lause = mycon.prepareStatement(sql);
            lause.setInt(1, Integer.parseInt(palveluID.getText())); //asettaa sql lauseen ? paikalle annetun palveluID:n
            tulosjoukko = lause.executeQuery();
            if (tulosjoukko == null) {
                throw new Exception("Palvelua ei löydy");
            }
        } catch (SQLException var8) {
            throw var8;
        } catch (Exception var9) {
            throw var9;
        }
        try {
            if (tulosjoukko.next()) {
                palveluID.setText(String.valueOf(tulosjoukko.getInt("PalveluID")));
                tyyppi.setText(tulosjoukko.getString("Tyyppi"));
                nimi.setText(tulosjoukko.getString("Nimi"));
                hinta.setText(String.valueOf(tulosjoukko.getDouble("Hinta")));
            }
        } catch (SQLException var7) {
            throw var7;
        }
    }
    @FXML
    void initialize() {
        assert tfPalvelu != null : "fx:id=\"tfPalvelu\" was not injected: check your FXML file 'Palvelut.fxml'.";
        assert tfTyyppi != null : "fx:id=\"tfTyyppi\" was not injected: check your FXML file 'Palvelut.fxml'.";
        assert tfNimi != null : "fx:id=\"tfNimi\" was not injected: check your FXML file 'Palvelut.fxml'.";
        assert tfHinta != null : "fx:id=\"tfHinta\" was not injected: check your FXML file 'Palvelut.fxml'.";
        assert palveluID != null : "fx:id=\"palveluID\" was not injected: check your FXML file 'Palvelut.fxml'.";
        assert tyyppi != null : "fx:id=\"tyyppi\" was not injected: check your FXML file 'Palvelut.fxml'.";
        assert nimi != null : "fx:id=\"nimi\" was not injected: check your FXML file 'Palvelut.fxml'.";
        assert hinta != null : "fx:id=\"hinta\" was not injected: check your FXML file 'Palvelut.fxml'.";
        assert tyhjenna != null : "fx:id=\"tyhjenna\" was not injected: check your FXML file 'Palvelut.fxml'.";
        assert btnSijoita != null : "fx:id=\"btnSijoita\" was not injected: check your FXML file 'Palvelut.fxml'.";
        assert btnPoista != null : "fx:id=\"btnPoista\" was not injected: check your FXML file 'Palvelut.fxml'.";
        assert btnPaivita != null : "fx:id=\"btnPaivita\" was not injected: check your FXML file 'Palvelut.fxml'.";

    }
}