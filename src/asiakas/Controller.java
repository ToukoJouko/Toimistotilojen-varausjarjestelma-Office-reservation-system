package asiakas;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.net.URL;
import java.sql.*;
import javafx.scene.control.TextField;




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
    private TextField postitoimipaikka;


    @FXML
    void haeAsiakas(ActionEvent event) throws SQLException, Exception {
        String sql = "SELECT asiakas.Asiakasid, asiakas.Etunimi, asiakas.Sukunimi, asiakas.Email, asiakas.Katuosoite, asiakas.Postinro, asiakas.Toimipaikka"
                + " " + "FROM asiakas"
                + " " + "WHERE asiakas.AsiakasID = ?;";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;
        try {
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "Stpm2499");
            lause = mycon.prepareStatement(sql);
            lause.setInt(1, Integer.parseInt(asiakasid.getText())); //asettaa sql lauseen ? paikalle annetun laskuID:n
            tulosjoukko = lause.executeQuery();
            if (tulosjoukko == null) {
                throw new Exception("Asiakasta ei löydy");
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
                postitoimipaikka.setText(tulosjoukko.getString("Toimipaikka"));
            }
        } catch (SQLException se) {
            throw se;
        }
    }




    @FXML
    void initialize() {
        assert asiakasid != null : "fx:id=\"asiakasid\" was not injected: check your FXML file 'sample.fxml'.";
        assert etunimi != null : "fx:id=\"etunimi\" was not injected: check your FXML file 'sample.fxml'.";
        assert sukunimi != null : "fx:id=\"sukunimi\" was not injected: check your FXML file 'sample.fxml'.";
        assert sposti != null : "fx:id=\"sposti\" was not injected: check your FXML file 'sample.fxml'.";
        assert katu != null : "fx:id=\"katu\" was not injected: check your FXML file 'sample.fxml'.";
        assert postinro != null : "fx:id=\"postinro\" was not injected: check your FXML file 'sample.fxml'.";


    }


}