

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;







public class Lasku {
    public String etunimi;
    public String sukunimi;
    public String katu;
    public String postinro;
    public String toimipaikka;
    public double summa;
    public String erapaiva;
    public String sposti;
    public int laskuid;
    public int asiakasid;
    public int varausid;


    //set ja get metodit
    public int getVarausid() {
        return this.varausid;
    }

    public void setVarausid(int varausid) {
        this.varausid = varausid;
    }
    
    public String getEtunimi() {
        return this.etunimi;
    }

    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    public String getSukunimi() {
        return this.sukunimi;
    }

    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }

    public String getKatu() {
        return this.katu;
    }

    public void setKatu(String katu) {
        this.katu = katu;
    }

    public String getPostinro() {
        return this.postinro;
    }

    public void setPostinro(String postinro) {
        this.postinro = postinro;
    }

    public String getToimipaikka() {
        return this.toimipaikka;
    }

    public void setToimipaikka(String toimipaikka) {
        this.toimipaikka = toimipaikka;
    }

    public double getSumma() {
        return this.summa;
    }

    public void setSumma(double summa) {
        this.summa = summa;
    }

    public String getErapaiva() {
        return this.erapaiva;
    }

    public void setErapaiva(String erapaiva) {
        this.erapaiva = erapaiva;
    }

    public String getSposti() {
        return this.sposti;
    }

    public void setSposti(String sposti) {
        this.sposti = sposti;
    }
    public int getLaskuid(){
        return this.laskuid;
    }
    public void setLaskuid(int id){
        this.laskuid = id;
    }
    public int getAsiakasid(){
        return this.asiakasid;
    }
    public void setAsiakasid(int asiakasid){
        this.asiakasid = asiakasid;
    }
    
    
    /* 
    haeLasku-metodi. Hakee olemassa olevien laskujen tiedot
    */
    public void haeLasku() throws SQLException, Exception{
        String sql = "SELECT asiakas.asiakasid, asiakas.etunimi, asiakas.sukunimi, asiakas.email, asiakas.katuosoite, asiakas.postinro, asiakas.toimipaikka, lasku.summa, lasku.eräpäivä, toimitilavaraukset.VarausID" 
        + "FROM asiakas, lasku, toimitilavaraukset"
        + "WHERE lasku.laskuid = ? and lasku.VarausID = toimitilavaraukset.VarausID and asiakas.AsiakasID = toimitilavaraukset.AsiakasID;";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;
        try {
           Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj", "root", "Stpm2499");
           lause = mycon.prepareStatement(sql);
           lause.setInt(1, laskuid); //asettaa sql lauseen ? paikalle annetun laskuID:n
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
                setAsiakasid(tulosjoukko.getInt("AsiakasID"));
                setEtunimi(tulosjoukko.getString("Etunimi"));
                setSukunimi(tulosjoukko.getString("Sukunimi"));
                setSposti(tulosjoukko.getString("Email"));
                setKatu(tulosjoukko.getString("Katuosoite"));
                setPostinro(tulosjoukko.getString("Postinro"));
                setToimipaikka(tulosjoukko.getString("Toimipaikka"));
                setSumma(tulosjoukko.getDouble("Summa"));
                setErapaiva(tulosjoukko.getString("Eräpäivä"));
                setVarausid(tulosjoukko.getInt("VarausID"));
            }
        }
        catch (SQLException se){
            throw se;
        }
        

        
    }
    
    /*
    Luodaan uusi lasku ja lisätään sen tiedot tietokantaan.
     */
    public int lisaaLasku() throws SQLException, Exception{
        //Tarkistetaan onko lasku jo olemassa
        String sql = "SELECT LaskuID"
        +"FROM lasku"
        + "WHERE LaskuID = ?";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;
        try{
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohti1_proj", "root", "Stpm2499");
            lause = mycon.prepareStatement(sql);
            lause.setInt(1, getLaskuid());
            tulosjoukko = lause.executeQuery();
            if(tulosjoukko.next()== true){
                throw new Exception("Lasku on jo olemassa");
            }

        }catch (SQLException se){
            throw se;
            }catch (Exception e){
                throw e;
        }
        sql = "INSERT INTO lasku"
        + "(LaskuID, Summa, Eräpäivä, VarausID)"
        + "VALUES (?,?,?,?)";
        lause = null;
        //asetetaan annetut tiedot muuttujien arvoiksi
        try{
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ohtu1_proj","root","Stpm2499");
            lause = mycon.prepareStatement(sql);
            lause.setInt(1, getLaskuid());
            lause.setDouble(2, getSumma());
            lause.setString(3, getErapaiva());
            lause.setInt(4 , getVarausid());

            int lkm = lause.executeUpdate();

            if (lkm == 0){
                throw new Exception("Laskun lisäys ei onnistunut");
            }

        } catch (SQLException se){
            throw se;
        } catch (Exception e){
            throw e;
        }
        
        return 0;
    }
    
    //kirjoittaa laskun tekstitiedostoon
    public void kirjoitaLasku()throws IOException{
        try{
            File lasku = new File(getEtunimi() + " " + getSukunimi()+ " " + "lasku");
            if(!lasku.exists()){
                lasku.createNewFile();
            }

            PrintWriter writer = new PrintWriter(lasku,"UTF-8");
            writer.println("LASKU");
            writer.println(getEtunimi() + getSukunimi());
            writer.println(getKatu()+ getPostinro());
            writer.println(getToimipaikka());
            writer.println(getSposti());
            writer.println(getSumma());
            writer.println(getErapaiva());
            writer.close();
        } catch (IOException e){
            throw e;
        }        
    }
    
    public void lahetaLasku(){
        String username = "mertakorpisanteri@gmail.com";
        String password = "StpM2499";

        Properties props = new Properties();
        props.put("mail.stmp.starttls.enable", "true");
        props.put("mail.smpt.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
        new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(username, password);
            }

        });

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mertakorpisanteri@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(getSposti()));
            message.setSubject("VuokraToimistot Oy lasku");
            message.setText("LASKU\n" 
            + getEtunimi() + " " + getSukunimi() + "\n"
            + getKatu() + " " + getPostinro() + "\n"
            + getToimipaikka() + "\n"
            + getSposti() + "\n"
            + getSumma() + "\n"
            + getErapaiva());
            Transport.send(message);
        }catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }
    
   
    
}