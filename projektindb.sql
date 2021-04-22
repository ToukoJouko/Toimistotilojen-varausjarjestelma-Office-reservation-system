CREATE DATABASE OHTU1_Proj;

CREATE TABLE Toimipiste
(
  ToimipisteID INT NOT NULL,
  Katuosoite VARCHAR(50) NOT NULL,
  Postinro VARCHAR(25) NOT NULL,
  Toimipaikka VARCHAR(25) NOT NULL,
  Koko_m2 INT NOT NULL,
  Huoneiden_lkm INT NOT NULL,
  Henkilomaara INT NOT NULL,
  Vuokra INT NOT NULL,
  Saatavuus CHAR NOT NULL,
  PRIMARY KEY (ToimipisteID)
);

CREATE TABLE Asiakas
(
  Sukunimi VARCHAR(25) NOT NULL,
  Etunimi VARCHAR(25) NOT NULL,
  AsiakasID INT NOT NULL,
  Email VARCHAR(50),
  Puhelinnumero VARCHAR(25) NOT NULL,
  Katuosoite VARCHAR(50) NOT NULL,
  Postinro VARCHAR(25) NOT NULL,
  Toimipaikka VARCHAR(25) NOT NULL,
  PRIMARY KEY (AsiakasID)
);

CREATE TABLE Palvelut
(
  PalveluID INT NOT NULL,
  Tyyppi VARCHAR(25) NOT NULL,
  Nimi VARCHAR(25) NOT NULL,
  Hinta INT NOT NULL,
  PRIMARY KEY (PalveluID)
);

CREATE TABLE Toimipisteiden_palvelut
(
  PalveluID INT NOT NULL,
  ToimipisteID INT NOT NULL,
  PRIMARY KEY (PalveluID, ToimipisteID),
  FOREIGN KEY (PalveluID) REFERENCES Palvelut(PalveluID),
  FOREIGN KEY (ToimipisteID) REFERENCES Toimipiste(ToimipisteID)
);

CREATE TABLE Toimitilavaraukset
(
  Alkupvm DATE NOT NULL,
  Loppupvm DATE NOT NULL,
  VarausID INT NOT NULL,
  AsiakasID INT NOT NULL,
  ToimipisteID INT NOT NULL,
  PRIMARY KEY (VarausID),
  FOREIGN KEY (AsiakasID) REFERENCES Asiakas(AsiakasID),
  FOREIGN KEY (ToimipisteID) REFERENCES Toimipiste(ToimipisteID),
  UNIQUE (AsiakasID, ToimipisteID)
);

CREATE TABLE lasku
(
  Summa INT NOT NULL,
  Eräpäivä DATE NOT NULL,
  LaskuID INT NOT NULL,
  VarausID INT NOT NULL,
  PRIMARY KEY (LaskuID),
  FOREIGN KEY (VarausID) REFERENCES Toimitilavaraukset(VarausID)
);
