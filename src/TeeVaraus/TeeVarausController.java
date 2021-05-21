package TeeVaraus;

import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.io.File;
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
    private Label lbpalvelut;

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
        String pwd = "Stpm2499";
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
        String pwd = "Stpm2499";
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
        String pwd = "Stpm2499";
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
        String pwd = "Stpm2499";
        String sql = "SELECT Katuosoite, Postinro, Henkilomaara, Koko_m2, Huoneiden_lkm, Vuokra FROM toimipiste WHERE ToimipisteID = ?;";
        String sql_palvelut= "SELECT PalveluID, Nimi FROM palvelut;";
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
            //palvelut_lause.setInt(1, Integer.parseInt(toimipisteID));
            palvelut_tulosjoukko = palvelut_lause.executeQuery();
            for (int i = 0; i<10; i++){


            }

            //valitun sijainnin palvelut menubuttoniin
            palvelut.getItems().clear();    //menubuttonin tyhjennys
            while(palvelut_tulosjoukko.next()) {
                CheckMenuItem check_item = new CheckMenuItem(palvelut_tulosjoukko.getString("PalveluID"));
                palvelut.getItems().add(check_item);
                String palvelu_nimi = palvelut_tulosjoukko.getString("Nimi");

                //palvelu menubuttonin tekstin käsittely
                check_item.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        if (check_item.isSelected()) {
                            palvelut.setText(palvelut.getText() + check_item.getText() + ",");
                            lbpalvelut.setText(lbpalvelut.getText()+palvelu_nimi + ",");
                        }     
                        if (check_item.isSelected() == false) {
                            palvelut.setText(palvelut.getText().replace(check_item.getText() + ",", ""));
                            lbpalvelut.setText(lbpalvelut.getText().replace(palvelu_nimi + ",", ""));
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
        String pwd = "Stpm2499";
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
        String pwd = "Stpm2499";
        String sql = "INSERT INTO toimitilavaraukset (Alkupvm, Loppupvm, AsiakasID, ToimipisteID)" + " " + "VALUES (?, ?, ?, ?);";
        //String palvelu_sql = "INSERT INTO varauksen_palvelu (VarausID, PalveluID) VALUES(?,?);";
        PreparedStatement lause = null;
        //PreparedStatement palvelu_lause = null;

        try {
            Connection varaus_connection = DriverManager.getConnection(url, user, pwd);
            lause = varaus_connection.prepareStatement(sql);
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

        lisaaPalveluVaraukseen();
    }
    
    void lisaaPalveluVaraukseen() throws SQLException {
       

        String url = "jdbc:mysql://localhost:3306/ohtu1_proj";
        String user = "root";
        String pwd = "Stpm2499";

        String[] toimipiste_id_ja_kaupunki = toimipiste_id.getValue().split(". ");
        String toimipisteID = toimipiste_id_ja_kaupunki[0];

        String palvelu_sql = "INSERT INTO varauksen_palvelut (VarausID, PalveluID) VALUES(?,?);";
        PreparedStatement palvelu_lause = null;

        String valitut_palvelut = palvelut.getText();
        String[] valitut_palvelut_lista = valitut_palvelut.split(",");
        String varausid_sql = "SELECT VarausID FROM toimitilavaraukset WHERE Alkupvm = ? AND Loppupvm = ? AND AsiakasID = ? AND ToimipisteID = ?;";
        PreparedStatement varausid_lause = null;
        ResultSet varausid_tulos = null;
        try{
            Connection varausid_connection = DriverManager.getConnection(url, user, pwd);
            varausid_lause = varausid_connection.prepareStatement(varausid_sql);
            varausid_lause.setDate(1, Date.valueOf(alku_pvm.getValue()));
            varausid_lause.setDate(2, Date.valueOf(loppu_pvm.getValue()));
            varausid_lause.setInt(3, Integer.parseInt(asiakas_id.getValue()));
            varausid_lause.setInt(4, Integer.parseInt(toimipisteID));
            varausid_tulos = varausid_lause.executeQuery();
        }
        catch(SQLException se1){
            throw se1;
        } catch(Exception e1){
            throw e1;
        }
        
        try{
            Connection palvelu_connection = DriverManager.getConnection(url, user, pwd);
            palvelu_lause=palvelu_connection.prepareStatement(palvelu_sql);
            for (int i=0; i< valitut_palvelut_lista.length;i++){
                varausid_tulos.first();
                palvelu_lause.setInt(1, Integer.parseInt(varausid_tulos.getString("VarausID")));
                palvelu_lause.setInt(2, Integer.parseInt(valitut_palvelut_lista[i]));
                palvelu_lause.executeUpdate();
            }
        }
        catch(SQLException se1){
            throw se1;
        } catch(Exception e1){
            throw e1;
        }
            

        paivitaVaratutPaivat();

    }
    @FXML
    void siirryHallintaan(ActionEvent event) throws IOException {
        URL url =  new File("src/varaushallinta/varaushallinta.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("VoukraToimistot OY/Varausten hallinta");
        stage.setMaximized(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();

    } 
  
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toimipiste_id.setItems(FXCollections.observableArrayList(haeToimipisteID()));
        asiakas_id.setItems(FXCollections.observableArrayList(haeAsiakasID()));
        //paivitaKalenterit();
        assert tee_varaus_painike != null : "fx:id=\"tee_varaus_painike\" was not injected: check your FXML file 'TeeVarausGUI.fxml'.";
    }


}    
