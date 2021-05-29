package asiakas;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.sql.*;
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
    private TextField puhelinnumero;

    @FXML
    private Text lisaamsg;

    @FXML
    private Text poistamsg;

    @FXML
    private Text errormsg;

    @FXML
    private Text muutamsg;



    @FXML
    void haeAsiakas(ActionEvent event) throws SQLException, Exception {
        String sql = "SELECT asiakas.Asiakasid, asiakas.Etunimi, asiakas.Sukunimi, asiakas.Email, asiakas.Katuosoite, asiakas.Postinro, asiakas.toimipaikka, asiakas.Puhelinnumero"
                + " " + "FROM asiakas"
                + " " + "WHERE asiakas.AsiakasID = ?;";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;
        try {
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "-");
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
                puhelinnumero.setText(tulosjoukko.getString("Puhelinnumero"));
                errormsg.setText("");
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
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "-");
            lause = mycon.prepareStatement(sql);
            lause.setInt(1, Integer.parseInt(asiakasid.getText()));
            tulosjoukko = lause.executeQuery();
            if (tulosjoukko.next() == true) {
                errormsg.setText("Asiakas on jo olemassa");
            }// else {
                errormsg.setText("");//
            //}
        } catch (SQLException se) {
            throw se;
        } catch (Exception e) {
            throw e;
        }
        sql = "INSERT INTO asiakas"
                + " " + "(AsiakasID, Sukunimi, Etunimi, Email,Puhelinnumero, Katuosoite, Postinro, Toimipaikka)"
                + " " + "VALUES (?,?,?,?,?,?,?,?)";
        lause = null;
        //asetetaan annetut tiedot muuttujien arvoiksi
        try {
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "-");
            lause = mycon.prepareStatement(sql);
            lause.setInt(1, Integer.parseInt(asiakasid.getText()));
            lause.setString(2, sukunimi.getText());
            lause.setString(3, etunimi.getText());
            lause.setString(4, sposti.getText());
            lause.setString(5, puhelinnumero.getText());
            lause.setString(6, katu.getText());
            lause.setString(7, postinro.getText());
            lause.setString(8, toimipaikka.getText());
            


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
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "-");
            lause = mycon.prepareStatement(sql);
            lause.setInt(1, Integer.parseInt(asiakasid.getText()));
            lause.executeUpdate();
            poistamsg.setText("Asiakas poistettu");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void tyhjennaTiedot(ActionEvent event) {
        asiakasid.setText("");
        etunimi.setText("");
        sukunimi.setText("");
        sposti.setText("");
        katu.setText("");
        postinro.setText("");
        toimipaikka.setText("");
        puhelinnumero.setText("");
        lisaamsg.setText("");
        poistamsg.setText("");
        errormsg.setText("");
        muutamsg.setText("");

    }

    @FXML
    void muutaTiedot(ActionEvent event) {
        String sql = "UPDATE asiakas"
        + " " + "SET Sukunimi = ?, Etunimi = ?, Email = ?, Puhelinnumero = ?, Katuosoite = ?, Postinro = ?, Toimipaikka = ?"
        + " " + "WHERE AsiakasID = ?";
        PreparedStatement lause = null;
        try{
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "-");
            lause = mycon.prepareStatement(sql);
            lause.setString(1, sukunimi.getText());
            lause.setString(2, etunimi.getText());
            lause.setString(3, sposti.getText());
            lause.setString(4, puhelinnumero.getText());
            lause.setString(5, katu.getText());
            lause.setString(6, postinro.getText());
            lause.setString(7, toimipaikka.getText());
            lause.setString(8, asiakasid.getText());
            lause.executeUpdate();
            muutamsg.setText("Tiedot muutettu");
        }catch (Exception e){
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
        assert puhelinnumero != null : "fx:id=\"puhelinnumero\" was not injected: check your FXML file 'AsiakasGUI.fxml'.";

    }

}

