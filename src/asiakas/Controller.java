package asiakas;

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
import java.io.File;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;




public class Controller {


    public Controller() {
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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

    @FXML
    private Text errormsg;

    @FXML
    private Text lisaamsg;

    @FXML
    private Text poistamsg;


    @FXML
    void haeAsiakas(ActionEvent event) throws SQLException, Exception {
        String sql = "SELECT asiakas.Asiakasid, asiakas.Etunimi, asiakas.Sukunimi, asiakas.Email, asiakas.Katuosoite, asiakas.Postinro, asiakas.toimipaikka"
                + " " + "FROM asiakas, lasku, toimitilavaraukset"
                + " " + "WHERE lasku.LaskuID = ? AND lasku.VarausID = toimitilavaraukset.VarausID AND asiakas.AsiakasID = toimitilavaraukset.AsiakasID;";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;
        try {
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "Stpm2499");
            lause = mycon.prepareStatement(sql);
            lause.setInt(1, Integer.parseInt(asiakasid.getText())); //asettaa sql lauseen ? paikalle annetun laskuID:n
            tulosjoukko = lause.executeQuery();
            if (tulosjoukko == null) {
                errormsg.setText("Asiakasta ei löydy");
            }
        } catch (SQLException se) {
            throw se;
        } catch (Exception e) {
            throw e;
        }
         /*
        Hakee tarvittavat tiedot tietokannasta ja asettaa ne muuttujien arvoiksi
        */
        try {
            if (tulosjoukko.next() == true) {
                asiakasid.setText(String.valueOf(tulosjoukko.getInt("AsiakasID")));
                etunimi.setText(tulosjoukko.getString("Etunimi"));
                sukunimi.setText(tulosjoukko.getString("Sukunimi"));
                sposti.setText(tulosjoukko.getString("Email"));
                katu.setText(tulosjoukko.getString("Katuosoite"));
                postinro.setText(tulosjoukko.getString("Postinro"));
                toimipaikka.setText(tulosjoukko.getString("Toimipaikka"));
            }
        } catch (SQLException se) {
            throw se;
        }
    }

    @FXML
    void lisaaAsiakas(ActionEvent event) throws SQLException, Exception {
        //Tarkistetaan onko asiakas jo olemassa
        String sql = "SELECT AsiakasID"
                + " " + "FROM asiakas"
                + " " + "WHERE AsiakasID = ?";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;
        try {
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "Stpm2499");
            lause = mycon.prepareStatement(sql);
            lause.setInt(1, Integer.parseInt(asiakasid.getText()));
            tulosjoukko = lause.executeQuery();
            if (tulosjoukko.next() == true) {
                errormsg.setText("Asiakas on jo olemassa");
            } else {
                errormsg.setText("");
            }
        } catch (SQLException se) {
            throw se;
        } catch (Exception e) {
            throw e;
        }
        sql = "INSERT INTO asiakas"
                + " " + "(AsiakasID, Sukunimi, Etunimi, Email, Katuosoite, Postinro, Toimipaikka)"
                + " " + "VALUES (?,?,?,?,?,?,?)";
        lause = null;
        //asetetaan annetut tiedot muuttujien arvoiksi
        try {
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "Stpm2499");
            lause = mycon.prepareStatement(sql);
            lause.setInt(1, Integer.parseInt(asiakasid.getText()));
            lause.setInt(2, Integer.parseInt(postinro.getText()));
            lause.setString(3, etunimi.getText());
            lause.setString(4, sukunimi.getText());
            lause.setString(5, sposti.getText());
            lause.setString(6, katu.getText());
            lause.setString(7, toimipaikka.getText());


            int lkm = lause.executeUpdate();

            if (lkm == 0) {
                errormsg.setText("Asiakkaan lisäys ei onnistunut");
            }
            lisaamsg.setText("Asiakas lisätty");
            errormsg.setText("");

        } catch (SQLException se) {
            throw se;
        } catch (Exception e) {
            throw e;
        }
    }

    @FXML
    void poistaAsiakas(ActionEvent event) {
        String sql = "DELETE"
                + " " + "FROM asiakas"
                + " " + "WHERE AsiakasID = ?";
        PreparedStatement lause = null;
        try {
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "Stpm2499");
            lause = mycon.prepareStatement(sql);
            lause.setInt(1, Integer.parseInt(asiakasid.getText()));
            lause.executeQuery();
            poistamsg.setText("Asiakas poistettu");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        assert asiakasid != null : "fx:id=\"asiakasid\" was not injected: check your FXML file 'AsiakasGUI.fxml'.";
        assert etunimi != null : "fx:id=\"etunimi\" was not injected: check your FXML file 'AsiakasGUI.fxml'.";
        assert sukunimi != null : "fx:id=\"sukunimi\" was not injected: check your FXML file 'AsiakasGUI.fxml'.";
        assert sposti != null : "fx:id=\"sposti\" was not injected: check your FXML file 'AsiakasGUI.fxml'.";
        assert katu != null : "fx:id=\"katu\" was not injected: check your FXML file 'AsiakasGUI.fxml'.";
        assert postinro != null : "fx:id=\"postinro\" was not injected: check your FXML file 'AsiakasGUI.fxml'.";
        assert toimipaikka != null : "fx:id=\"toimipaikka\" was not injected: check your FXML file 'AsiakasGUI.fxml'.";

    }

}

