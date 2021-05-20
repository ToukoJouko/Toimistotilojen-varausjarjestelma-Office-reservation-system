package toimipiste;

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

public class ToimipisteController {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TextField ToimipisteID;

        @FXML
        private TextField Katuosoite;

        @FXML
        private TextField Postinro;

        @FXML
        private TextField Toimipaikka;

        @FXML
        private TextField Koko_m2;

        @FXML
        private TextField Huoneiden_lkm;

        @FXML
        private TextField Henkilomaara;

        @FXML
        private TextField Vuokra;

        @FXML
        private TextField Saatavuus;

        @FXML
        private Text errormsg;

        @FXML
        private Text lisaamsg;

        @FXML
        private Text poistamsg;

        @FXML
        private Text muutamsg;


        @FXML
        void haeToimipiste(ActionEvent event) throws SQLException, Exception {
            String sql = "SELECT toimipiste.ToimipisteID, toimipiste.Katuosoite, toimipiste.Postinro, toimipiste.Toimipaikka, toimipiste.Koko_m2, toimipiste.Huoneiden_lkm, toimipiste.Henkilomaara, toimipiste.Vuokra, toimipiste.Saatavuus"
                    + " " + "FROM toimipiste"
                    + " " + "WHERE toimipiste.ToimipisteID = ?";
            ResultSet tulosjoukko = null;
            PreparedStatement lause = null;
            try {
                Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "Stpm2499");
                lause = mycon.prepareStatement(sql);
                lause.setInt(1, Integer.parseInt(ToimipisteID.getText())); //asettaa sql lauseen ? paikalle annetun ToimipisteID:n
                tulosjoukko = lause.executeQuery();
                if (tulosjoukko == null) {
                    errormsg.setText("Toimipistettä ei löydy");
                }//else{
                    //errormsg.setText("");
                //}
            }
            catch (SQLException se){
                throw se;
            }catch (Exception e){
                throw e;
            }
         /*
        Hakee tarvittavat tiedot tietokannasta ja asettaa ne muuttujien arvoiksi
        */
            try {
                if (tulosjoukko.next() == true) {
                    ToimipisteID.setText(String.valueOf(tulosjoukko.getInt("ToimipisteID")));
                    Katuosoite.setText(tulosjoukko.getString("Katuosoite"));
                    Postinro.setText(tulosjoukko.getString("Postinro"));
                    Toimipaikka.setText(tulosjoukko.getString("Toimipaikka"));
                    Koko_m2.setText(tulosjoukko.getString("Koko_m2"));
                    Huoneiden_lkm.setText(tulosjoukko.getString("Huoneiden_lkm"));
                    Henkilomaara.setText(tulosjoukko.getString("Henkilomaara"));
                    Vuokra.setText(tulosjoukko.getString("Vuokra"));
                    Saatavuus.setText(tulosjoukko.getString("Saatavuus"));
                }
            }
            catch (SQLException se){
                throw se;
            }
        }

    @FXML
    void lisaaToimipiste(ActionEvent event) throws SQLException, Exception {
        //Tarkistetaan onko toimipiste jo olemassa
        String sql = "SELECT ToimipisteID"
                + " " +"FROM toimipiste"
                + " " + "WHERE ToimipisteID = ?";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;
        try{
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "Stpm2499");
            lause = mycon.prepareStatement(sql);
            lause.setInt(1, Integer.parseInt(ToimipisteID.getText()));
            tulosjoukko = lause.executeQuery();
            if(tulosjoukko.next()== true){
                errormsg.setText("Toimipiste on jo olemassa");
            }//else{
                errormsg.setText("");
            //}
        } catch (SQLException se){
            throw se;
        }catch (Exception e){
                throw e;
        }
        sql = "INSERT INTO toimipiste"
                + " " + "(ToimipisteID, Katuosoite, Postinro, Toimipaikka, Koko_m2, Huoneiden_lkm, Henkilomaara, Vuokra, Saatavuus)"
                + " " + "VALUES (?,?,?,?,?,?,?,?,?)";
        lause = null;
        //asetetaan annetut tiedot muuttujien arvoiksi
        try{
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj","root","Stpm2499");
            lause = mycon.prepareStatement(sql);
            lause.setInt(1, Integer.parseInt(ToimipisteID.getText()));
            lause.setString(2, Katuosoite.getText());
            lause.setString(3, Postinro.getText());
            lause.setString(4, Toimipaikka.getText());
            lause.setInt(5, Integer.parseInt(Koko_m2.getText()));
            lause.setInt(6, Integer.parseInt(Huoneiden_lkm.getText()));
            lause.setInt(7, Integer.parseInt(Henkilomaara.getText()));
            lause.setDouble(8, Double.parseDouble(Vuokra.getText()));
            lause.setInt(9, Integer.parseInt(Saatavuus.getText()));

            int lkm = lause.executeUpdate();

            if (lkm == 0){
                errormsg.setText("Toimipisteen lisäys ei onnistunut");
            }
            lisaamsg.setText("Toimipiste lisätty");
            errormsg.setText("");

        } catch (SQLException se){
            throw se;
        } catch (Exception e){
            throw e;
        }
    }

    @FXML
    void poistaToimipiste(ActionEvent event) {
        String sql = "DELETE"
                + " " + "FROM toimipiste"
                +" "+"WHERE ToimipisteID = ?";
        PreparedStatement lause = null;
        try{
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "Stpm2499");
            lause = mycon.prepareStatement(sql);
            lause.setInt(1, Integer.parseInt(ToimipisteID.getText()));
            lause.executeQuery();
            poistamsg.setText("Toimipiste poistettu");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    void muutaTiedot(ActionEvent event) {
        String sql = "UPDATE toimipiste"
                + " " + "SET Katuosoite = ?, Postinro = ?, Toimipaikka = ?, Koko_m2 = ?, Huoneiden_lkm = ?, Henkilomaara = ?, Vuokra = ?, Saatavuus = ?"
                + " " + "WHERE ToimipisteID = ?";
        PreparedStatement lause = null;
        try{
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "Stpm2499");
            lause = mycon.prepareStatement(sql);
            lause.setString(1, Katuosoite.getText());
            lause.setString(2, Postinro.getText());
            lause.setString(3, Toimipaikka.getText());
            lause.setString(4, Koko_m2.getText());
            lause.setString(5, Huoneiden_lkm.getText());
            lause.setString(6, Henkilomaara.getText());
            lause.setString(7, Vuokra.getText());
            lause.setString(8, Saatavuus.getText());
            lause.setString(9, ToimipisteID.getText());
            lause.executeUpdate();
            muutamsg.setText("Tiedot muutettu");
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @FXML
    void tyhjennaTiedot(ActionEvent event) {
        ToimipisteID.setText("");
        Katuosoite.setText("");
        Postinro.setText("");
        Toimipaikka.setText("");
        Koko_m2.setText("");
        Huoneiden_lkm.setText("");
        Henkilomaara.setText("");
        Vuokra.setText("");
        Saatavuus.setText("");
        lisaamsg.setText("");
        poistamsg.setText("");
        errormsg.setText("");
        muutamsg.setText("");

    }





    @FXML
        void initialize() {
            assert ToimipisteID != null : "fx:id=\"ToimipisteID\" was not injected: check your FXML file 'ToimipisteGUI.fxml'.";
            assert Katuosoite != null : "fx:id=\"Katuosoite\" was not injected: check your FXML file 'ToimipisteGUI.fxml'.";
            assert Postinro != null : "fx:id=\"Postinro\" was not injected: check your FXML file 'ToimipisteGUI.fxml'.";
            assert Toimipaikka != null : "fx:id=\"Toimipaikka\" was not injected: check your FXML file 'ToimipisteGUI.fxml'.";
            assert Koko_m2 != null : "fx:id=\"Koko_m2\" was not injected: check your FXML file 'ToimipisteGUI.fxml'.";
            assert Huoneiden_lkm != null : "fx:id=\"Huoneiden_lkm\" was not injected: check your FXML file 'ToimipisteGUI.fxml'.";
            assert Henkilomaara != null : "fx:id=\"Henkilomaara\" was not injected: check your FXML file 'ToimipisteGUI.fxml'.";
            assert Vuokra != null : "fx:id=\"Vuokra\" was not injected: check your FXML file 'ToimipisteGUI.fxml'.";
            assert Saatavuus != null : "fx:id=\"Saatavuus\" was not injected: check your FXML file 'ToimipisteGUI.fxml'.";


        }



    }
