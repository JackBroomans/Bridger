# Use this file to populate the tables in the H2 database, remove create table statement and replace by your own script

CREATE DATABASE IF NOT EXISTS casebefrank COLLATE latin1_general_cs;

USE casebefrank;

DROP TABLE IF EXISTS adres;
DROP TABLE IF EXISTS deelnemer;

CREATE TABLE  deelnemer
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    deelnemersnummer VARCHAR(15)  NOT NULL,
    familieNaam      VARCHAR(131) NOT NULL,
    voorvoegsels     VARCHAR(31),
    voornamen        VARCHAR(255) NOT NULL,
    initialen        VARCHAR(31),
    prefixTitels     VARCHAR(63),
    suffixTitels     VARCHAR(63),
    codeGeslacht     CHAR(1)      NOT NULL,
    geboortedatum    DATE         NOT NULL,
    email            VARCHAR(255) NOT NULL,
    telefoonVast     VARCHAR(15),
    telefoonMobiel   VARCHAR(15)  NOT NULL
) ENGINE = INNODB;

CREATE UNIQUE INDEX iDeelnemersnummer ON deelnemer (deelnemersnummer);
CREATE INDEX iFamilienaam ON deelnemer (deelnemersnummer, voornamen);
CREATE INDEX iEmail ON deelnemer (email);

CREATE TABLE  adres
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    volgnummer SMALLINT     NOT NULL DEFAULT 0,
    deelnemer  BIGINT       NOT NULL,
    straatnaam VARCHAR(127) NOT NULL,
    huisnummer VARCHAR(15),
    postcode   VARCHAR(15),
    plaatsnaam VARCHAR(127) NOT NULL,
    land       VARCHAR(127)          DEFAULT 'Nederland',
    isActief   BOOLEAN      NOT NULL DEFAULT false,
    FOREIGN KEY fDeelnemerAdres (deelnemer)
        REFERENCES deelnemer (id)
        ON UPDATE RESTRICT
        ON DELETE CASCADE
) ENGINE = INNODB;

CREATE INDEX iDeelnemerAdres ON adres (deelnemer);
CREATE INDEX iPostcode ON adres (postcode);

# Deelnemers
INSERT INTO deelnemer (deelnemersnummer, familienaam, voornamen, initialen, prefixTitels, codeGeslacht,
                       geboortedatum, email, telefoonMobiel)
VALUES ('20220416-00001', 'Botje', 'Berend', 'B.', 'dhr.', 'M', '1978-04-16', 'b.botje@zeilboot.nl', '06-11223344');

INSERT INTO deelnemer(deelnemersnummer, familienaam, voorvoegsels, voornamen, initialen, prefixTitels, codeGeslacht,
                      geboortedatum, email, telefoonMobiel)
VALUES ('20220416-00002', 'Petteflet', 'van de', 'Puk', 'P.', 'dhr.', 'M', '1933-11-23', 'petteflet@flatgebouw.nl',
        '06-55667788');

INSERT INTO deelnemer(deelnemersnummer, familienaam, voornamen, initialen, codeGeslacht, geboortedatum, email,
                      telefoonVast, telefoonMobiel)
VALUES ('20220416-00003', 'Dap', 'Dikkertje', 'D.', 'M', '2001-05-03', 'dikkertje@dierentuin.nl', '020-12345678',
        '06-9911223344');

# Adressen (Gekoppeld aan deelnemers)
INSERT INTO adres (volgnummer, deelnemer, straatnaam, huisnummer, postcode, plaatsnaam, land, isActief)
VALUES (1, 1, 'Dorpstraat', '2a', '1234 AB', 'Zuidlaren', 'Nederland', 1);

INSERT INTO adres(volgnummer, deelnemer, straatnaam, huisnummer, postcode, plaatsnaam, land, isActief)
VALUES (1, 2, 'Brink', '23', '2345 CD', 'Ons Dorp', 'Nederland', 1);

INSERT INTO adres(volgnummer, deelnemer, straatnaam, huisnummer, postcode, plaatsnaam, land, isActief)
VALUES (1, 3, 'Girafverblijf', '1', '3456 EF', 'Artis', 'Nederland', 1);

INSERT INTO adres (volgnummer, deelnemer, straatnaam, huisnummer, postcode, plaatsnaam, land, isActief)
VALUES (2, 1, 'Thuisstraat', '91-I', '4567 GH', 'Ergens', 'Nederland', 0);


