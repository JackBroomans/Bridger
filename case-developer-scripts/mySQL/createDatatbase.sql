CREATE DATABASE IF NOT EXISTS casebefrank COLLATE latin1_general_cs;

USE casebefrank;

CREATE TABLE IF NOT EXISTS deelnemer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    deelnemersnummer VARCHAR(15) NOT NULL,
    familieNaam VARCHAR(131) NOT NULL,
    voorvoegsels VARCHAR(31),
    voornamen VARCHAR(255) NOT NULL,
    initialen VARCHAR(31),
    prefixTitels VARCHAR(63),
    suffixTitels VARCHAR(63),
    codeGeslacht CHAR(1) NOT NULL,
    geboortedatum DATE NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefoonVast VARCHAR(15),
    telefoonMobiel VARCHAR(15) NOT NULL
) ENGINE=INNODB;

CREATE UNIQUE INDEX iDeelnemersnummer ON deelnemer (deelnemersnummer);
CREATE INDEX iFamilienaam ON deelnemer (deelnemersnummer, voornamen);
CREATE INDEX iEmail ON deelnemer (email);

CREATE TABLE IF NOT EXISTS adres (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    volgnummer SMALLINT NOT NULL DEFAULT 0,
    deelnemer BIGINT NOT NULL,
    straatnaam VARCHAR(127) NOT NULL,
    huisnummer VARCHAR(15),
    postcode VARCHAR(15),
    plaatsnaam VARCHAR(127) NOT NULL,
    land VARCHAR(127) DEFAULT 'Nederland',
    isActief BOOLEAN NOT NULL DEFAULT false,
    FOREIGN KEY fDeelnemerAdres(deelnemer)
        REFERENCES deelnemer (id)
        ON UPDATE RESTRICT
        ON DELETE CASCADE
) ENGINE=INNODB;

CREATE INDEX iDeelnemerAdres ON adres(deelnemer);
CREATE INDEX iPostcode ON adres(postcode);
