CREATE DATABASE IF NOT EXISTS casebefrank COLLATE latin1_general_cs;

USE casebefrank;

DROP PROCEDURE IF EXISTS sp_SumInvestmentsParticipant;

DROP TABLE IF EXISTS belegging;
DROP TABLE IF EXISTS premie;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS participant;

CREATE TABLE  participant
(
    id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    participantnumber VARCHAR(15)  NOT NULL,
    familyname VARCHAR(131) NOT NULL,
    prefixes VARCHAR(31),
    surnames VARCHAR(255) NOT NULL,
    initials VARCHAR(31),
    prefixtitles VARCHAR(63),
    suffixtitles VARCHAR(63),
    gendercode VARCHAR(1) NOT NULL,
    birthdate DATE NOT NULL,
    email VARCHAR(255) NOT NULL,
    hometelephone VARCHAR(15),
    cellphone VARCHAR(15) NOT NULL
) ENGINE = INNODB;

CREATE UNIQUE INDEX iParticipantNumber ON participant (participantnumber);
CREATE INDEX iFamilyName ON participant (familyname);
CREATE INDEX iEmail ON participant (email);

CREATE TABLE  address
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sequence SMALLINT NOT NULL DEFAULT 0,
    participant_id BIGINT,
    street VARCHAR(127) NOT NULL,
    housenumber VARCHAR(15),
    postalcode VARCHAR(15),
    city VARCHAR(127) NOT NULL,
    country VARCHAR(127) DEFAULT 'Nedercountry',
    current BOOLEAN  NOT NULL DEFAULT false,
    FOREIGN KEY fParticipantAddress (participant_id)
        REFERENCES participant (id)
        ON UPDATE RESTRICT
        ON DELETE CASCADE
) ENGINE = INNODB;

CREATE INDEX iParticipantAddress ON address (participant_id);
CREATE INDEX iPostalCode ON address (postalcode);

CREATE TABLE  premie
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    participant_id BIGINT,
    fulltime_salaris  DOUBLE NOT NULL DEFAULT 0,
    parttime_percentage FLOAT NOT NULL DEFAULT 0,
    francise_actueel DOUBLE NOT NULL DEFAULT 0,
    percentage_beschikbare_premie FLOAT NOT NULL DEFAULT 0,
    FOREIGN KEY fkParticipantPremium (participant_id)
        REFERENCES participant (id)
        ON UPDATE RESTRICT
        ON DELETE CASCADE
) ENGINE = INNODB;

CREATE INDEX iParticipantPremium ON premie (participant_id);

CREATE TABLE belegging
(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  participant_id BIGINT NOT NULL,
  instituut VARCHAR(63)  NOT NULL,
  fonds VARCHAR(255) NOT NULL,
  huidige_waarde DOUBLE NOT NULL DEFAULT 0,
  FOREIGN KEY fkParticipantPremium (participant_id)
      REFERENCES participant (id)
      ON UPDATE RESTRICT
      ON DELETE CASCADE
) ENGINE = INNODB;

CREATE INDEX iParticipantInvestment ON belegging (participant_id);

# Participants
INSERT INTO participant (participantnumber, familyname, surnames, initials, prefixtitles, gendercode,
                         birthdate, email, cellphone)
VALUES ('20220416-00001', 'Botje', 'Berend', 'B.', 'dhr.', 'M', '1978-04-16', 'b.botje@zeilboot.nl', '06-11223344');

INSERT INTO participant(participantnumber, familyname, prefixes, surnames, initials, prefixtitles, gendercode,
                        birthdate, email, cellphone)
VALUES ('20220416-00002', 'Petteflet', 'van de', 'Puk', 'P.', 'dhr.', 'M', '1933-11-23', 'petteflet@flatgebouw.nl',
        '06-55667788');

INSERT INTO participant(participantnumber, familyname, surnames, initials, gendercode, birthdate, email,
                        hometelephone, cellphone)
VALUES ('20220416-00003', 'Dap', 'Dikkertje', 'D.', 'M', '2001-05-03', 'dikkertje@dierentuin.nl', '020-12345678',
        '06-9911223344');

# Addresses (associated with participants)
INSERT INTO address (sequence, participant_id, street, housenumber, postalcode, city, country, current)
VALUES (1, 1, 'Dorpstraat', '2a', '1234 AB', 'Zuidlaren', 'Nedercountry', 1);

INSERT INTO address(sequence, participant_id, street, housenumber, postalcode, city, country, current)
VALUES (1, 2, 'Brink', '23', '2345 CD', 'Ons Dorp', 'Nedercountry', 1);

INSERT INTO address(sequence, participant_id, street, housenumber, postalcode, city, country, current)
VALUES (1, 3, 'Girafverblijf', '1', '3456 EF', 'Artis', 'Nedercountry', 1);

INSERT INTO address (sequence, participant_id, street, housenumber, postalcode, city, country, current)
VALUES (2, 1, 'Thuisstraat', '91-I', '4567 GH', 'Ergens', 'Nedercountry', 0);

# Participant's actual premiums
INSERT INTO premie(participant_id, fulltime_salaris, parttime_percentage, francise_actueel, percentage_beschikbare_premie)
VALUES (1, 49000, 100, 12600, 3);

INSERT INTO premie(participant_id, fulltime_salaris, parttime_percentage, francise_actueel, percentage_beschikbare_premie)
VALUES (2, 63500, 80, 16000, 3);

INSERT INTO premie(participant_id, fulltime_salaris, parttime_percentage, francise_actueel, percentage_beschikbare_premie)
VALUES (3, 11000, 5, 2000, 3);

# Investments of individual participants
INSERT INTO belegging(participant_id, instituut, fonds, huidige_waarde)
VALUES (1, 'Nationale Nedercountryen', 'BlackRock Sustainable Energy', 1723);
INSERT INTO belegging(participant_id, instituut, fonds, huidige_waarde)
VALUES (1, 'ASN', 'Groenprojectenfonds', 831);
INSERT INTO belegging(participant_id, instituut, fonds, huidige_waarde)
VALUES (1, 'Triodos', 'Impact Mixed Fund - Defensive', 37887);
INSERT INTO belegging(participant_id, instituut, fonds, huidige_waarde)
VALUES (2, 'BNP Parisbas', 'KBC-Life S Dynamic Responsible Investing Comfort', 0);
INSERT INTO belegging(participant_id, instituut, fonds, huidige_waarde)
VALUES (2, 'Nationale Nedercountryen', 'Aanvullende PensioenOpbouw', 11299);
INSERT INTO belegging(participant_id, instituut, fonds, huidige_waarde)
VALUES (2, 'Nationale Nedercountryen', 'Beheerd Beleggen ', 32664);
INSERT INTO belegging(participant_id, instituut, fonds, huidige_waarde)
VALUES (2, 'ABN-Amro', 'Begeleid Beleggen Matig Defensief', 24301);
INSERT INTO belegging(participant_id, instituut, fonds, huidige_waarde)
VALUES (3, 'KBC', 'Life S Defensive Balanced Responsible Investing Comfort', 10688);
INSERT INTO belegging(participant_id, instituut, fonds, huidige_waarde)
VALUES (3, 'Triodos', 'Triodos Global Equities', 466);

# Fetch the sum of de current value of an individual participant, based on the indentifier of that participant.
CREATE PROCEDURE sp_SumInvestmentsParticipant(IN id BIGINT, OUT huidigeWaarde NUMERIC)
    BEGIN
        SELECT SUM(huidige_waarde) INTO huidigeWaarde
        FROM belegging
        WHERE participant_id = id;
    END

