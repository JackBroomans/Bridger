# Use this file to populate the tables in the H2 database, remove create table statement and replace by your own script

CREATE DATABASE IF NOT EXISTS casebefrank COLLATE latin1_general_cs;

USE casebefrank;

DROP PROCEDURE IF EXISTS PSomBeleggingenDeelnemer;

DROP TABLE IF EXISTS belegging;
DROP TABLE IF EXISTS premie;
DROP TABLE IF EXISTS adres;
DROP TABLE IF EXISTS deelnemer;

CREATE TABLE  deelnemer
(
    id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    deelnemersnummer VARCHAR(15)  NOT NULL,
    familieNaam VARCHAR(131) NOT NULL,
    voorvoegsels VARCHAR(31),
    voornamen VARCHAR(255) NOT NULL,
    initialen VARCHAR(31),
    titelsprefix VARCHAR(63),
    titelssuffix VARCHAR(63),
    geslachtscode VARCHAR(1) NOT NULL,
    geboortedatum DATE NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefoon_vast VARCHAR(15),
    telefoon_mobiel VARCHAR(15) NOT NULL
) ENGINE = INNODB;

CREATE UNIQUE INDEX iDeelnemersnummer ON deelnemer (deelnemersnummer);
CREATE INDEX iFamilienaam ON deelnemer (deelnemersnummer, voornamen);
CREATE INDEX iEmail ON deelnemer (email);

CREATE TABLE  adres
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    volgnummer SMALLINT NOT NULL DEFAULT 0,
    participant_id BIGINT,
    straatnaam VARCHAR(127) NOT NULL,
    huisnummer VARCHAR(15),
    postcode VARCHAR(15),
    plaatsnaam VARCHAR(127) NOT NULL,
    land VARCHAR(127) DEFAULT 'Nederland',
    actief BOOLEAN  NOT NULL DEFAULT false,
    FOREIGN KEY fDeelnemerAdres (participant_id)
        REFERENCES deelnemer (id)
        ON UPDATE RESTRICT
        ON DELETE CASCADE
) ENGINE = INNODB;

CREATE INDEX iDeelnemerAdres ON adres (participant_id);
CREATE INDEX iPostcode ON adres (postcode);

CREATE TABLE  premie
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    participant_id BIGINT,
    fulltime_salaris  DOUBLE NOT NULL DEFAULT 0,
    parttime_percentage FLOAT NOT NULL DEFAULT 0,
    francise_actueel DOUBLE NOT NULL DEFAULT 0,
    percentage_beschikbare_premie FLOAT NOT NULL DEFAULT 0,
    FOREIGN KEY fDeelnemerPremie (participant_id)
        REFERENCES deelnemer (id)
        ON UPDATE RESTRICT
        ON DELETE CASCADE
) ENGINE = INNODB;

CREATE INDEX iDeelnemerPremie ON premie (participant_id);

CREATE TABLE belegging
(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  participant_id BIGINT NOT NULL,
  instituut VARCHAR(63)  NOT NULL,
  fonds VARCHAR(255) NOT NULL,
  huidige_waarde DOUBLE NOT NULL DEFAULT 0,
  FOREIGN KEY fDeelnemerPremie (participant_id)
      REFERENCES deelnemer (id)
      ON UPDATE RESTRICT
      ON DELETE CASCADE
) ENGINE = INNODB;

CREATE INDEX iDeelnemerBelegging ON belegging (participant_id);

# Deelnemers
INSERT INTO deelnemer (deelnemersnummer, familienaam, voornamen, initialen, titelsprefix, geslachtscode,
                       geboortedatum, email, telefoon_mobiel)
VALUES ('20220416-00001', 'Botje', 'Berend', 'B.', 'dhr.', 'M', '1978-04-16', 'b.botje@zeilboot.nl', '06-11223344');

INSERT INTO deelnemer(deelnemersnummer, familienaam, voorvoegsels, voornamen, initialen, titelsprefix, geslachtscode,
                      geboortedatum, email, telefoon_mobiel)
VALUES ('20220416-00002', 'Petteflet', 'van de', 'Puk', 'P.', 'dhr.', 'M', '1933-11-23', 'petteflet@flatgebouw.nl',
        '06-55667788');

INSERT INTO deelnemer(deelnemersnummer, familienaam, voornamen, initialen, geslachtscode, geboortedatum, email,
                      telefoon_vast, telefoon_mobiel)
VALUES ('20220416-00003', 'Dap', 'Dikkertje', 'D.', 'M', '2001-05-03', 'dikkertje@dierentuin.nl', '020-12345678',
        '06-9911223344');

# Adressen (Gekoppeld aan deelnemers)
INSERT INTO adres (volgnummer, participant_id, straatnaam, huisnummer, postcode, plaatsnaam, land, actief)
VALUES (1, 1, 'Dorpstraat', '2a', '1234 AB', 'Zuidlaren', 'Nederland', 1);

INSERT INTO adres(volgnummer, participant_id, straatnaam, huisnummer, postcode, plaatsnaam, land, actief)
VALUES (1, 2, 'Brink', '23', '2345 CD', 'Ons Dorp', 'Nederland', 1);

INSERT INTO adres(volgnummer, participant_id, straatnaam, huisnummer, postcode, plaatsnaam, land, actief)
VALUES (1, 3, 'Girafverblijf', '1', '3456 EF', 'Artis', 'Nederland', 1);

INSERT INTO adres (volgnummer, participant_id, straatnaam, huisnummer, postcode, plaatsnaam, land, actief)
VALUES (2, 1, 'Thuisstraat', '91-I', '4567 GH', 'Ergens', 'Nederland', 0);

# Actuele premies van deelnemers
INSERT INTO premie(participant_id, fulltime_salaris, parttime_percentage, francise_actueel, percentage_beschikbare_premie)
VALUES (1, 49000, 100, 12600, 3);

INSERT INTO premie(participant_id, fulltime_salaris, parttime_percentage, francise_actueel, percentage_beschikbare_premie)
VALUES (2, 63500, 80, 16000, 3);

INSERT INTO premie(participant_id, fulltime_salaris, parttime_percentage, francise_actueel, percentage_beschikbare_premie)
VALUES (3, 11000, 5, 2000, 3);

# Beleggingen per deelnener
INSERT INTO belegging(participant_id, instituut, fonds, huidige_waarde)
VALUES (1, 'Nationale Nederlanden', 'BlackRock Sustainable Energy', 1723);
INSERT INTO belegging(participant_id, instituut, fonds, huidige_waarde)
VALUES (1, 'ASN', 'Groenprojectenfonds', 831);
INSERT INTO belegging(participant_id, instituut, fonds, huidige_waarde)
VALUES (1, 'Triodos', 'Impact Mixed Fund - Defensive', 37887);
INSERT INTO belegging(participant_id, instituut, fonds, huidige_waarde)
VALUES (2, 'BNP Parisbas', 'KBC-Life S Dynamic Responsible Investing Comfort', 0);
INSERT INTO belegging(participant_id, instituut, fonds, huidige_waarde)
VALUES (2, 'Nationale Nederlanden', 'Aanvullende PensioenOpbouw', 11299);
INSERT INTO belegging(participant_id, instituut, fonds, huidige_waarde)
VALUES (2, 'Nationale Nederlanden', 'Beheerd Beleggen ', 32664);
INSERT INTO belegging(participant_id, instituut, fonds, huidige_waarde)
VALUES (2, 'ABN-Amro', 'Begeleid Beleggen Matig Defensief', 24301);
INSERT INTO belegging(participant_id, instituut, fonds, huidige_waarde)
VALUES (3, 'KBC', 'Life S Defensive Balanced Responsible Investing Comfort', 10688);
INSERT INTO belegging(participant_id, instituut, fonds, huidige_waarde)
VALUES (3, 'Triodos', 'Triodos Global Equities', 466);

# Bepaal de totale huidige waarde van alle, onder een bepaalde deelnemer geregistreerde, beleggingen.
CREATE PROCEDURE PSomBeleggingenDeelnemer(IN id BIGINT, OUT huidigeWaarde NUMERIC)
    BEGIN
        SELECT SUM(huidige_waarde) INTO huidigeWaarde
        FROM belegging
        WHERE participant_id = id;
    END

