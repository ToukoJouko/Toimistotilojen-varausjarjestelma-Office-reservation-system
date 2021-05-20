CREATE DATABASE OHTU1_Proj;
DROP TABLE if EXISTS asiakas, lasku, palvelut, toimipiste,toimipisteiden_palvelut, toimitilavaraukset;
CREATE TABLE Toimipiste
(
  ToimipisteID INT NOT NULL AUTO_INCREMENT,
  Katuosoite VARCHAR(50) NOT NULL,
  Postinro VARCHAR(25) NOT NULL,
  Toimipaikka VARCHAR(25) NOT NULL,
  Koko_m2 INT NOT NULL,
  Huoneiden_lkm INT NOT NULL,
  Henkilomaara INT NOT NULL,
  Vuokra DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (ToimipisteID)
);

CREATE TABLE Asiakas
(
  AsiakasID INT NOT NULL AUTO_INCREMENT,
  Sukunimi VARCHAR(25) NOT NULL,
  Etunimi VARCHAR(25) NOT NULL,
  Email VARCHAR(50),
  Puhelinnumero VARCHAR(25) NOT NULL,
  Katuosoite VARCHAR(50) NOT NULL,
  Postinro VARCHAR(25) NOT NULL,
  Toimipaikka VARCHAR(25) NOT NULL,
  PRIMARY KEY (AsiakasID)
);

CREATE TABLE Palvelut
(
  PalveluID INT NOT NULL AUTO_INCREMENT,
  Tyyppi VARCHAR(25) NOT NULL,
  Nimi VARCHAR(25) NOT NULL,
  Hinta DECIMAL(10,2) NOT NULL,
  ToimipisteiD INT NOT NULL,
  PRIMARY KEY (PalveluID),
  FOREIGN KEY (ToimipisteID) REFERENCES toimipiste(ToimipisteID)
);

CREATE TABLE Toimitilavaraukset
(
  VarausID INT NOT NULL AUTO_INCREMENT,
  Alkupvm DATE NOT NULL,
  Loppupvm DATE NOT NULL,
  AsiakasID INT NOT NULL,
  ToimipisteID INT NOT NULL,
  PRIMARY KEY (VarausID),
  FOREIGN KEY (AsiakasID) REFERENCES Asiakas(AsiakasID),
  FOREIGN KEY (ToimipisteID) REFERENCES Toimipiste(ToimipisteID),
  UNIQUE (AsiakasID, ToimipisteID)
);

CREATE TABLE Varauksen_palvelut
( 
  VarausID INT NOT NULL,
  PalveluID INT NOT NULL,
  lkm INT NOT NULL,
  PRIMARY KEY (PalveluID, VarausID),
  FOREIGN KEY (PalveluID) REFERENCES Palvelut(PalveluID),
  FOREIGN KEY (VarausID) REFERENCES Toimitilavaraukset(VarausID)
);

CREATE TABLE lasku
(
  LaskuID INT NOT NULL AUTO_INCREMENT,
  Summa DECIMAL(10,2) NOT NULL,
  Eräpäivä DATE NOT NULL,
  VarausID INT NOT NULL,
  PRIMARY KEY (LaskuID),
  FOREIGN KEY (VarausID) REFERENCES Toimitilavaraukset(VarausID)
);
