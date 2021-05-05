package src;

import java.sql.*;






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
    public static Lasku haeLasku(int laskuid) throws SQLException, Exception{
        String sql = "SELECT asiakas.asiakasid, asiakas.etunimi, asiakas.sukunimi, asiakas.email, asiakas.katuosoite, asiakas.postinro, asiakas.toimipaikka, lasku.summa, lasku.eräpäivä" 
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
        Lasku laskuolio = new Lasku();
        /*
        Hakee tarvittavat tiedot tietokannasta ja asettaa ne muuttujien arvoiksi 
        */
        try{
            if(tulosjoukko.next()==true){
                laskuolio.setAsiakasid(tulosjoukko.getInt("AsiakasID"));
                laskuolio.setEtunimi(tulosjoukko.getString("Etunimi"));
                laskuolio.setSukunimi(tulosjoukko.getString("Sukunimi"));
                laskuolio.setSposti(tulosjoukko.getString("Email"));
                laskuolio.setKatu(tulosjoukko.getString("Katuosoite"));
                laskuolio.setPostinro(tulosjoukko.getString("Postinro"));
                laskuolio.setToimipaikka(tulosjoukko.getString("Toimipaikka"));
                laskuolio.setSumma(tulosjoukko.getDouble("Summa"));
                laskuolio.setErapaiva(tulosjoukko.getString("Eräpäivä"));
            }
        }
        catch (SQLException se){
            throw se;
        }
        return laskuolio;
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
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/testi", "root", "Stpm2499");
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
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/testi","root","Stpm2499");
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
}