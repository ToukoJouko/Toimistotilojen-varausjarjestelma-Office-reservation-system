package varaushallinta;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.*;

public class varaushallintaController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField varausid;

    @FXML
    private TextField aloituspvm;

    @FXML
    private TextField lopetuspvm;

    @FXML
    private TextField asiakasid;

    @FXML
    private TextField toimipisteid;

    @FXML
    private Label henkilökapasiteetti;

    @FXML
    private Label postinro;

    @FXML
    private Label koko;

    @FXML
    private Label huoneiden_lkm;

    @FXML
    private Label vuokra;

    @FXML
    private Label katuosoite;

    @FXML
    private Label lbpalvelut;

    @FXML
    private Label asiakas_etunimi;

    @FXML
    private Label asiakas_sukunimi;

    @FXML
    private Label asiakas_email;

    @FXML
    private Label asiakas_puhelinnumero;

    @FXML
    private Label asiakas_katuosoite;

    @FXML
    private Label asiakas_postinro;

    @FXML
    private Label asiakas_toimipaikka;

    @FXML
    void haetiedot(ActionEvent event) throws SQLException {
        String sql = "SELECT tv.VarausID, tv.Alkupvm, tv.Loppupvm, tv.AsiakasID, tv.ToimipisteID, t.Katuosoite, t.Postinro, t.Toimipaikka, t.Koko_m2, t.Huoneiden_lkm,"
        + "t.Henkilomaara, t.Vuokra, a.Sukunimi, a.Etunimi, a.Email, a.Puhelinnumero, a.Katuosoite, a.Postinro, a.Toimipaikka, p.Nimi"
        +" " + "FROM toimitilavaraukset tv, toimipiste t, asiakas a, palvelut p, varauksen_palvelut vp"
        + " " + "WHERE tv.VarausID = ? AND tv.AsiakasID = a.AsiakasID AND tv.ToimipisteID = t.ToimipisteID AND vp.VarausID = tv.VarausID AND vp.PalveluID = p.PalveluID;";
        PreparedStatement lause = null;
        ResultSet tulosjoukko = null;

        try{
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "-");
            lause=mycon.prepareStatement(sql);
            lause.setInt(1, Integer.parseInt(varausid.getText()));
            tulosjoukko = lause.executeQuery();
            if(tulosjoukko == null){
                Alert alert = new Alert(Alert.AlertType.ERROR,"Varausta ei löydy.",ButtonType.OK);
                alert.show();
            }
        }
        catch(SQLException se){
            throw se;
        } catch(Exception e){
            throw e;
        }
        try{
            if (tulosjoukko.next()==true){
                varausid.setText(String.valueOf(tulosjoukko.getInt("VarausID")));
                aloituspvm.setText(tulosjoukko.getString("Alkupvm"));
                lopetuspvm.setText(tulosjoukko.getString("Loppupvm"));
                asiakasid.setText(String.valueOf(tulosjoukko.getInt("AsiakasID")));
                toimipisteid.setText(String.valueOf(tulosjoukko.getInt("ToimipisteID")));
                katuosoite.setText(tulosjoukko.getString("toimipiste.Katuosoite"));
                postinro.setText(tulosjoukko.getString("toimipiste.Postinro"));
                henkilökapasiteetti.setText(tulosjoukko.getString("Henkilomaara"));
                koko.setText(String.valueOf(tulosjoukko.getInt("Koko_m2")));
                huoneiden_lkm.setText(String.valueOf(tulosjoukko.getInt("Huoneiden_lkm")));
                henkilökapasiteetti.setText(String.valueOf(tulosjoukko.getInt("Henkilomaara")));
                vuokra.setText(String.valueOf(tulosjoukko.getDouble("Vuokra")));
                lbpalvelut.setText(tulosjoukko.getString("Nimi"));
                asiakas_etunimi.setText(tulosjoukko.getString("Etunimi"));
                asiakas_sukunimi.setText(tulosjoukko.getString("Sukunimi"));
                asiakas_email.setText(tulosjoukko.getString("Email"));
                asiakas_puhelinnumero.setText(tulosjoukko.getString("Puhelinnumero"));
                asiakas_katuosoite.setText(tulosjoukko.getString("asiakas.Katuosoite"));
                asiakas_postinro.setText(tulosjoukko.getString("asiakas.Postinro"));
                asiakas_toimipaikka.setText(tulosjoukko.getString("asiakas.Toimipaikka"));
            }
        }
        catch(SQLException se1){
            throw se1;
        }
    }

    @FXML
    void muutatiedot(ActionEvent event) throws SQLException {
        String sql = "UPDATE toimitilavaraukset" 
        +" " +"SET Alkupvm = ?, Loppupvm = ?, AsiakasID = ?, ToimipisteID = ? WHERE VarausID = ?;";
        PreparedStatement lause = null;

        try{
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "-");
            lause = mycon.prepareStatement(sql);
            lause.setDate(1, Date.valueOf(aloituspvm.getText()));
            lause.setDate(2, Date.valueOf(lopetuspvm.getText()));
            lause.setInt(3, Integer.parseInt(asiakasid.getText()));
            lause.setInt(4, Integer.parseInt(toimipisteid.getText()));
            lause.setInt(5, Integer.parseInt(varausid.getText()));
            lause.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Varauksen tiedot muutettu.",ButtonType.OK);
            alert.show();
        }
        catch(SQLException se){
            throw se;
        } catch(Exception e){
            throw e;
        }

    }

    @FXML
    void poistavaraus(ActionEvent event) throws SQLException {
        String sql_vp = "DELETE FROM varauksen_palvelut WHERE VarausID = ?;";
        String sql_tv = "DELETE FROM toimitilavaraukset WHERE VarausID = ?;";
        PreparedStatement vp_lause = null;
        PreparedStatement tv_lause = null;

        try{
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "-");
            vp_lause = mycon.prepareStatement(sql_vp);
            tv_lause = mycon.prepareStatement(sql_tv);
            vp_lause.setInt(1, Integer.parseInt(varausid.getText()));
            tv_lause.setInt(1, Integer.parseInt(varausid.getText()));
            vp_lause.executeUpdate();
            tv_lause.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Varaus poistettu.",ButtonType.OK);
            alert.show();
        }
        catch(SQLException se){
            throw se;
        } catch(Exception e){
            throw e;
        }

    }

    @FXML
    void tyhjennaTiedot(ActionEvent event) {
        varausid.setText("");
        aloituspvm.setText("");
        lopetuspvm.setText("");
        asiakasid.setText("");
        toimipisteid.setText("");
        katuosoite.setText("");
        postinro.setText("");
        henkilökapasiteetti.setText("");
        koko.setText("");
        huoneiden_lkm.setText("");
        vuokra.setText("");
        lbpalvelut.setText("");
        asiakas_etunimi.setText("");
        asiakas_sukunimi.setText("");
        asiakas_email.setText("");
        asiakas_puhelinnumero.setText("");
        asiakas_katuosoite.setText("");
        asiakas_postinro.setText("");
        asiakas_toimipaikka.setText("");
    }

    @FXML
    void initialize() {
        assert varausid != null : "fx:id=\"varausid\" was not injected: check your FXML file 'varaushallinta.fxml'.";
        assert aloituspvm != null : "fx:id=\"aloituspvm\" was not injected: check your FXML file 'varaushallinta.fxml'.";
        assert lopetuspvm != null : "fx:id=\"lopetuspvm\" was not injected: check your FXML file 'varaushallinta.fxml'.";
        assert asiakasid != null : "fx:id=\"asiakasid\" was not injected: check your FXML file 'varaushallinta.fxml'.";
        assert toimipisteid != null : "fx:id=\"toimipisteid\" was not injected: check your FXML file 'varaushallinta.fxml'.";
        assert henkilökapasiteetti != null : "fx:id=\"henkilökapasiteetti\" was not injected: check your FXML file 'varaushallinta.fxml'.";
        assert postinro != null : "fx:id=\"postinro\" was not injected: check your FXML file 'varaushallinta.fxml'.";
        assert koko != null : "fx:id=\"koko\" was not injected: check your FXML file 'varaushallinta.fxml'.";
        assert huoneiden_lkm != null : "fx:id=\"huoneiden_lkm\" was not injected: check your FXML file 'varaushallinta.fxml'.";
        assert vuokra != null : "fx:id=\"vuokra\" was not injected: check your FXML file 'varaushallinta.fxml'.";
        assert katuosoite != null : "fx:id=\"katuosoite\" was not injected: check your FXML file 'varaushallinta.fxml'.";
        assert lbpalvelut != null : "fx:id=\"lbpalvelut\" was not injected: check your FXML file 'varaushallinta.fxml'.";
        assert asiakas_etunimi != null : "fx:id=\"asiakas_etunimi\" was not injected: check your FXML file 'varaushallinta.fxml'.";
        assert asiakas_sukunimi != null : "fx:id=\"asiakas_sukunimi\" was not injected: check your FXML file 'varaushallinta.fxml'.";
        assert asiakas_email != null : "fx:id=\"asiakas_email\" was not injected: check your FXML file 'varaushallinta.fxml'.";
        assert asiakas_puhelinnumero != null : "fx:id=\"asiakas_puhelinnumero\" was not injected: check your FXML file 'varaushallinta.fxml'.";
        assert asiakas_katuosoite != null : "fx:id=\"asiakas_katuosoite\" was not injected: check your FXML file 'varaushallinta.fxml'.";
        assert asiakas_postinro != null : "fx:id=\"asiakas_postinro\" was not injected: check your FXML file 'varaushallinta.fxml'.";
        assert asiakas_toimipaikka != null : "fx:id=\"asiakas_toimipaikka\" was not injected: check your FXML file 'varaushallinta.fxml'.";

    }
}

