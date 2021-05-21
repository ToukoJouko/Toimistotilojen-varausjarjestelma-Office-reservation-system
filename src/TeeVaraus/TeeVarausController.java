package TeeVaraus;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.io.IOException;
import java.net.URL;


public class TeeVarausController implements Initializable {

    @FXML
    private Button tee_varaus_painike;
   
    @FXML
    private ComboBox<String> toimipiste_id;

    @FXML
    private ComboBox<String> asiakas_id;

    @FXML
    private Label henkilökapasiteetti;

    @FXML
    private Label katuosoite;

    @FXML
    private Label postinro;

    @FXML
    private Label koko;

    @FXML
    private Label huoneiden_lkm;

    @FXML
    private Label vuokra;

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
    private DatePicker alku_pvm;

    @FXML
    private DatePicker loppu_pvm;

    @FXML
    private MenuButton palvelut;

    //sijaintien haku comboboxiin
    private List<String> haeToimipisteID() {

        List<String> vaihtoehdot = new ArrayList<String>();

        String url = "jdbc:mysql://localhost:3306/ohtu1_proj";
        String user = "root";
        String pwd = "Sqlonhienok13li!";
        String sql = "SELECT ToimipisteID, Toimipaikka FROM toimipiste;";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;


        try {
            Connection connection = DriverManager.getConnection(url, user, pwd);
            lause = connection.prepareStatement(sql);
            tulosjoukko = lause.executeQuery();

            while(tulosjoukko.next()) {
                vaihtoehdot.add(String.valueOf(tulosjoukko.getInt("ToimipisteID")) + ". " + tulosjoukko.getString("Toimipaikka"));
            }

            lause.close();
            tulosjoukko.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }
        //System.out.println("Success");
        return vaihtoehdot;
    }

    //haetaan AsiaksID:t comboboxiin
    private List<String> haeAsiakasID() {

        List<String> vaihtoehdot = new ArrayList<String>();

        String url = "jdbc:mysql://localhost:3306/ohtu1_proj";
        String user = "root";
        String pwd = "Sqlonhienok13li!";
        String sql = "SELECT AsiakasID FROM asiakas;";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;


        try {
            Connection connection = DriverManager.getConnection(url, user, pwd);
            lause = connection.prepareStatement(sql);
            tulosjoukko = lause.executeQuery();

            while(tulosjoukko.next()) {
                vaihtoehdot.add(String.valueOf(tulosjoukko.getInt("AsiakasID")));
            }

            lause.close();
            tulosjoukko.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return vaihtoehdot;
    }  

    //valitun asiakkaan tietojen haku
    @FXML 
    void haeAsiakasTiedot() throws IOException {
        String valittu_asiakas_id = asiakas_id.getValue();

        String url = "jdbc:mysql://localhost:3306/ohtu1_proj";
        String user = "root";
        String pwd = "Sqlonhienok13li!";
        String sql = "SELECT Etunimi, Sukunimi, Email, Puhelinnumero, Katuosoite, Postinro, Toimipaikka FROM asiakas WHERE AsiakasID = ?;";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;



        try {
            Connection connection = DriverManager.getConnection(url, user, pwd);
            lause = connection.prepareStatement(sql);
            lause.setInt(1, Integer.parseInt(valittu_asiakas_id));
            tulosjoukko = lause.executeQuery();

            if(tulosjoukko.next()) {
                asiakas_etunimi.setText(tulosjoukko.getString("Etunimi"));
                asiakas_sukunimi.setText(tulosjoukko.getString("Sukunimi"));
                asiakas_email.setText(tulosjoukko.getString("Email"));
                asiakas_puhelinnumero.setText(tulosjoukko.getString("Puhelinnumero"));
                asiakas_katuosoite.setText(tulosjoukko.getString("Katuosoite"));
                asiakas_postinro.setText(tulosjoukko.getString("Postinro"));
                asiakas_toimipaikka.setText(tulosjoukko.getString("Toimipaikka"));

            }

            lause.close();
            tulosjoukko.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }

    }

    //valitun sijainnin tietojen haku
    @FXML 
    void haeTiedot() throws IOException {
        //splitataan 'toimipiste_id, kaupunki' merkkijono, jotta voidaan hakea toimipaikan tietoja id:n perusteella
        String valittu_toimipiste = toimipiste_id.getValue();
        String[] toimipiste_id_ja_kaupunki = valittu_toimipiste.split(". ");
        String toimipisteID = toimipiste_id_ja_kaupunki[0];
        //String kaupunki = toimipiste_id_ja_kaupunki[1];

        String url = "jdbc:mysql://localhost:3306/ohtu1_proj";
        String user = "root";
        String pwd = "Sqlonhienok13li!";
        String sql = "SELECT Katuosoite, Postinro, Henkilomaara, Koko_m2, Huoneiden_lkm, Vuokra FROM toimipiste WHERE ToimipisteID = ?;";
        String sql_palvelut= "SELECT Nimi FROM palvelut WHERE ToimipisteiD = ?;";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;

        ResultSet palvelut_tulosjoukko = null;
        PreparedStatement palvelut_lause = null;

        palvelut.setText("");

        try {
            Connection connection = DriverManager.getConnection(url, user, pwd);
            lause = connection.prepareStatement(sql);
            lause.setInt(1, Integer.parseInt(toimipisteID));
            tulosjoukko = lause.executeQuery();

            if(tulosjoukko.next()) {
                katuosoite.setText(tulosjoukko.getString("Katuosoite"));
                henkilökapasiteetti.setText(String.valueOf(tulosjoukko.getInt("Henkilomaara")));
                postinro.setText(String.valueOf(tulosjoukko.getInt("Postinro")));
                koko.setText(String.valueOf(tulosjoukko.getInt("Koko_m2")));
                huoneiden_lkm.setText(String.valueOf(tulosjoukko.getInt("Huoneiden_lkm")));
                vuokra.setText(String.valueOf(tulosjoukko.getInt("vuokra")));
            }

            palvelut_lause = connection.prepareStatement(sql_palvelut);
            palvelut_lause.setInt(1, Integer.parseInt(toimipisteID));
            palvelut_tulosjoukko = palvelut_lause.executeQuery();

            //valitun sijainnin palvelut menubuttoniin
            palvelut.getItems().clear();    //menubuttonin tyhjennys
            while(palvelut_tulosjoukko.next()) {
                CheckMenuItem check_item = new CheckMenuItem(palvelut_tulosjoukko.getString("Nimi"));
                palvelut.getItems().add(check_item);

                //palvelu menubuttonin tekstin käsittely
                check_item.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        if (check_item.isSelected()) {
                            palvelut.setText(palvelut.getText() + check_item.getText() + ",");
                        }     
                        if (check_item.isSelected() == false) {
                            palvelut.setText(palvelut.getText().replace(check_item.getText() + ",", ""));
                        }
    
                    }
                });
            }

            lause.close();
            tulosjoukko.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }
        paivitaVaratutPaivat();


    }


    @FXML
    void paivitaVaratutPaivat() {

        String valittu_toimipiste = toimipiste_id.getValue();
        String[] toimipiste_id_ja_kaupunki = valittu_toimipiste.split(". ");
        String toimipisteID = toimipiste_id_ja_kaupunki[0];

        String url = "jdbc:mysql://localhost:3306/ohtu1_proj";
        String user = "root";
        String pwd = "Sqlonhienok13li!";
        String sql = "SELECT Alkupvm, Loppupvm FROM toimitilavaraukset WHERE ToimipisteID = ?;";

        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;

        try {
            Connection connection = DriverManager.getConnection(url, user, pwd);
            lause = connection.prepareStatement(sql);
            lause.setInt(1, Integer.parseInt(toimipisteID));
            tulosjoukko = lause.executeQuery();

            while(tulosjoukko.next()) {
                List<LocalDate> varatut = new ArrayList<LocalDate>();
                Date aloitus = tulosjoukko.getDate("Alkupvm");
                Date lopetus = tulosjoukko.getDate("Loppupvm");

                LocalDate aloitus_local = aloitus.toLocalDate();
                LocalDate lopetus_local = lopetus.toLocalDate();

                while(!aloitus_local.isAfter(lopetus_local)) {
                    varatut.add(aloitus_local);
                    aloitus_local = aloitus_local.plusDays(1);
                
                    alku_pvm.setDayCellFactory(picker -> new DateCell() {
                        public void updateItem(LocalDate date, boolean empty) {
                            super.updateItem(date, empty);

                            LocalDate tanaan = LocalDate.now();

                            if(varatut.contains(date) || date.isBefore(tanaan)) {
                                setDisable(true);
                            }

                        }
                    });

                    loppu_pvm.setDayCellFactory(picker -> new DateCell() {
                        public void updateItem(LocalDate date, boolean empty) {
                            super.updateItem(date, empty);
                            LocalDate tanaan = LocalDate.now();

                            if(varatut.contains(date) || date.isBefore(tanaan)){
                                setDisable(true);
                            }

                        }
                    });
                }

            }    
            }catch (Exception e) {
                e.printStackTrace();
            }
               
    }   
            
    //tämä ajetaan kun painetaan 'tee varaus' painiketta
    @FXML
    void TeeVaraus() throws SQLException, Exception {
        String valittu_asiakas_id = asiakas_id.getValue();
        
        String valittu_toimipiste = toimipiste_id.getValue();
        String[] toimipiste_id_ja_kaupunki = valittu_toimipiste.split(". ");
        String toimipisteID = toimipiste_id_ja_kaupunki[0];

        LocalDate alkupvm = alku_pvm.getValue();
        Date alku_sql_date = Date.valueOf(alkupvm);

        LocalDate loppupvm = loppu_pvm.getValue();
        Date loppu_sql_date = Date.valueOf(loppupvm);

        String url = "jdbc:mysql://localhost:3306/ohtu1_proj";
        String user = "root";
        String pwd = "Sqlonhienok13li!";
        String sql = "INSERT INTO toimitilavaraukset (Alkupvm, Loppupvm, AsiakasID, ToimipisteID)" + " " + "VALUES (?, ?, ?, ?);";
        PreparedStatement lause = null;

        try {
            Connection connection = DriverManager.getConnection(url, user, pwd);
            lause = connection.prepareStatement(sql);
            lause.setDate(1, alku_sql_date);
            lause.setDate(2, loppu_sql_date);
            lause.setInt(3, Integer.parseInt(valittu_asiakas_id));
            lause.setInt(4, Integer.parseInt(toimipisteID));
            lause.executeQuery();

            lause.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Varaus tehtiin onnistuneesti.", ButtonType.OK);
            alert.show();
  

        } catch(SQLException se) {
            throw se;
        } catch(Exception e) {
            throw e;
        }

        //lisaaPalveluVaraukseen();
    }
    /*
    void lisaaPalveluVaraukseen() throws SQLException {
        String valitut_palvelut = palvelut.getText();
        String[] valitut_palvelut_lista = valitut_palvelut.split(",");
        String vp1 = valitut_palvelut_lista[0];
        String vp2 = valitut_palvelut_lista[1];
        String vp3 = valitut_palvelut_lista[2];

        String url = "jdbc:mysql://localhost:3306/ohtu1_proj";
        String user = "root";
        String pwd = "Sqlonhienok13li!";
        String sql_hae_varausid = "SELECT VarausID FROM toimitilavaraukset" + " " + "ORDER BY VarausID DESC;";
        String sql_insert_varausid = "INSERT INTO varauksen_palvelut (VarausID)" + " " + "VALUES (?);";
        String sql_hae_palveluid = "SELECT PalveluID FROM palvelut WHERE Nimi = ?;";
        String sql_insert_palveluid = "INSERT INTO varauksen_palvelut (PalveluID)" + " " + "VALUES (?);";

        ResultSet tulosjoukko_hae_varausid = null;
        PreparedStatement lause_hae_varausid = null;

        PreparedStatement lause_insert_varausid = null;

        ResultSet tulosjoukko_hae_palveluid = null;
        PreparedStatement lause_hae_palveluid = null;

        PreparedStatement lause_insert_palveluid = null;

        
        try {
            Connection connection = DriverManager.getConnection(url, user, pwd);
            lause_hae_varausid = connection.prepareStatement(sql_hae_varausid);
            tulosjoukko_hae_varausid = lause_hae_varausid.executeQuery();

            if(tulosjoukko_hae_varausid.next()) {
                lause_insert_varausid = connection.prepareStatement(sql_insert_varausid);
                lause_insert_varausid.setInt(1, Integer.parseInt(String.valueOf(tulosjoukko_hae_varausid)));
                lause_insert_varausid.executeQuery();
            }

            lause_hae_palveluid = connection.prepareStatement(sql_hae_palveluid);
            lause_hae_palveluid.setString(1, String.valueOf(vp1));
            tulosjoukko_hae_palveluid = lause_hae_palveluid.executeQuery();

            if(tulosjoukko_hae_palveluid.next()) {
                lause_insert_palveluid = connection.prepareStatement(sql_insert_palveluid);
                lause_insert_palveluid.setInt(1, Integer.parseInt(String.valueOf(tulosjoukko_hae_palveluid)));
            }


        } catch (SQLException se) {
            throw se;
        }
            

        paivitaVaratutPaivat();

    }
*/   
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toimipiste_id.setItems(FXCollections.observableArrayList(haeToimipisteID()));
        asiakas_id.setItems(FXCollections.observableArrayList(haeAsiakasID()));
        //paivitaKalenterit();
        assert tee_varaus_painike != null : "fx:id=\"tee_varaus_painike\" was not injected: check your FXML file 'TeeVarausGUI.fxml'.";
    }


}    
