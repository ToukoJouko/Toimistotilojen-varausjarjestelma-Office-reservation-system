package lasku;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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

    @FXML
    private Text paperimsg;

    @FXML
    private Text spostimsg;

    @FXML
    private Text lisaamsg;

    

    @FXML
    void haeLasku(ActionEvent event) throws SQLException, Exception {
        String sql = "SELECT asiakas.Asiakasid, asiakas.Etunimi, asiakas.Sukunimi, asiakas.Email, asiakas.Katuosoite, asiakas.Postinro, asiakas.Toimipaikka, lasku.Summa, lasku.Eräpäivä, toimitilavaraukset.VarausID, lasku.LaskuID" 
        + " " + "FROM asiakas, lasku, toimitilavaraukset"
        + " " + "WHERE lasku.LaskuID = ? AND lasku.VarausID = toimitilavaraukset.VarausID AND asiakas.AsiakasID = toimitilavaraukset.AsiakasID;";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;
        try {
           Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "Stpm2499");
           lause = mycon.prepareStatement(sql);
           lause.setInt(1, Integer.parseInt(laskuid.getText())); //asettaa sql lauseen ? paikalle annetun laskuID:n
           tulosjoukko = lause.executeQuery();
           if (tulosjoukko == null){
               throw new Exception("Laskua ei löydy");
           }
        }
        catch(SQLException se){
            throw se;
            }catch (Exception e){
                throw e;
        }
         /*
        Hakee tarvittavat tiedot tietokannasta ja asettaa ne muuttujien arvoiksi 
        */
        try{
            if(tulosjoukko.next()==true){
                asiakasid.setText(String.valueOf(tulosjoukko.getInt("AsiakasID")));
                etunimi.setText(tulosjoukko.getString("Etunimi"));
                sukunimi.setText(tulosjoukko.getString("Sukunimi"));
                sposti.setText(tulosjoukko.getString("Email"));
                katu.setText(tulosjoukko.getString("Katuosoite"));
                postinro.setText(tulosjoukko.getString("Postinro"));
                toimipaikka.setText(tulosjoukko.getString("Toimipaikka"));
                summa.setText(String.valueOf(tulosjoukko.getDouble("Summa")));
                erapaiva.setText(tulosjoukko.getString("Eräpäivä"));
                varausid.setText(String.valueOf(tulosjoukko.getInt("VarausID")));
            }
        }
        catch (SQLException se){
            throw se;
        }
    }

    @FXML
    void kirjoitaLasku(ActionEvent event) throws IOException {
        try{
            File lasku = new File(etunimi.getText() + " " + sukunimi.getText()+ " " + "lasku");
            if(!lasku.exists()){
                lasku.createNewFile();
            }
            PrintWriter writer = new PrintWriter(lasku,"UTF-8");
            writer.println("LASKU");
            writer.println(etunimi.getText() + " " + sukunimi.getText());
            writer.println(katu.getText()+ " " +  postinro.getText()); 
            writer.println(toimipaikka.getText());
            writer.println(sposti.getText());
            writer.println("Summa: "+summa.getText()+"€");
            writer.println("Eräpäivä: "+erapaiva.getText());
            writer.close();
            paperimsg.setText("Lasku luotu");
        } catch (IOException e){
            throw e;
        }
    }

    @FXML
    void lahetaLasku(ActionEvent event) {
        String username = "mertakorpisanteri@gmail.com";
        String password = "Testi!-1234";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
        new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            };
        });
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sposti.getText()));
            message.setSubject("VuokraToimistot Oy lasku");
            message.setText("LASKU\n" 
            + etunimi.getText() + " " + sukunimi.getText() + "\n"
            + katu.getText() + " " + postinro.getText() + "\n"
            + toimipaikka.getText() + "\n"
            + sposti.getText() + "\n"
            + "Summa: "+summa.getText()+"€" + "\n"
            +"Eräpäivä: "+erapaiva.getText());
            Transport.send(message);
            spostimsg.setText("Lasku lähetetty");
        }catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void lisaaLasku(ActionEvent event) throws SQLException, Exception {
        //Tarkistetaan onko lasku jo olemassa
        String sql = "SELECT LaskuID"
        + " " +"FROM lasku"
        + " " + "WHERE LaskuID = ?";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;
        try{
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "Stpm2499");
            lause = mycon.prepareStatement(sql);
            lause.setInt(1, Integer.parseInt(laskuid.getText()));
            tulosjoukko = lause.executeQuery();
            if(tulosjoukko.next()== true){
                throw new Exception("Lasku on jo olemassa");
            }
        } catch (SQLException se){
                throw se;
                }catch (Exception e){
                    throw e;
            }
            sql = "INSERT INTO lasku"
            + " " + "(LaskuID, Summa, Eräpäivä, VarausID)"
            + " " + "VALUES (?,?,?,?)";
            lause = null;
            //asetetaan annetut tiedot muuttujien arvoiksi
            try{
                Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj","root","Stpm2499");
                lause = mycon.prepareStatement(sql);
                lause.setInt(1, Integer.parseInt(laskuid.getText()));
                lause.setDouble(2,Double.parseDouble(summa.getText()));
                lause.setString(3, erapaiva.getText());
                lause.setInt(4 , Integer.parseInt(varausid.getText()));
    
                int lkm = lause.executeUpdate();
    
                if (lkm == 0){
                    throw new Exception("Laskun lisäys ei onnistunut");
                }
                lisaamsg.setText("Lasku lisätty");
    
            } catch (SQLException se){
                throw se;
            } catch (Exception e){
                throw e;
            }
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


