CREATE DATABASE IF NOT EXISTS casebefrank COLLATE latin1_general_cs;

USE casebefrank;

DROP PROCEDURE IF EXISTS sp_SumInvestmentsParticipant;
DROP PROCEDURE IF EXISTS sp_GetLastProvidedParticipantNumber;

DROP TABLE IF EXISTS belegging;
DROP TABLE IF EXISTS participantpremium;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS participant;
DROP TABLE IF EXISTS useraccount;

CREATE TABLE useraccount
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(15) NOT NULL,
    password VARCHAR(255) NOT NULL,
    dateregistered DATE NOT NULL,
    datelastpasswordreset DATE NOT NULL,
    datalastused DATE,
    loginattempts INT NOT NULL DEFAULT 0,
    active TINYINT DEFAULT 1,
    locked TINYINT DEFAULT 0
) ENGINE = INNODB;
CREATE UNIQUE INDEX iUserName ON useraccount (username);

CREATE TABLE  participant
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    useraccount_id BIGINT NOT NULL,
    participantnumber VARCHAR(16)  NOT NULL,
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

ALTER TABLE participant
    ADD CONSTRAINT participant_useraccount_id_fk
    FOREIGN KEY (useraccount_id) REFERENCES useraccount (id) ON DELETE CASCADE;

CREATE TABLE  address
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sequence SMALLINT NOT NULL DEFAULT 0,
    participant_id BIGINT,
    street VARCHAR(127) NOT NULL,
    housenumber VARCHAR(15),
    postalcode VARCHAR(15),
    city VARCHAR(127) NOT NULL,
    country VARCHAR(127) DEFAULT 'Nederland',
    current BOOLEAN  NOT NULL DEFAULT false
) ENGINE = INNODB;

CREATE INDEX iParticipantAddress ON address (participant_id);
CREATE INDEX iPostalCode ON address (postalcode);

ALTER TABLE address
    ADD CONSTRAINT address_participant_id_fk
    FOREIGN KEY (participant_id) REFERENCES participant (id) ON DELETE CASCADE;

CREATE TABLE participantpremium
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    participantid BIGINT,
    fulltime_salaris  DOUBLE NOT NULL DEFAULT 0,
    parttime_percentage FLOAT NOT NULL DEFAULT 0,
    francise_actueel DOUBLE NOT NULL DEFAULT 0,
    percentage_beschikbare_premie FLOAT NOT NULL DEFAULT 0
) ENGINE = INNODB;

CREATE INDEX iParticipantPremium ON participantpremium (participantid);

ALTER TABLE participantpremium
    ADD CONSTRAINT participantpremium_participant_id_fk
    FOREIGN KEY (participantid) REFERENCES participant (id) ON DELETE CASCADE;

CREATE TABLE belegging
(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  participant_id BIGINT NOT NULL,
  instituut VARCHAR(63)  NOT NULL,
  fonds VARCHAR(255) NOT NULL,
  huidige_waarde DOUBLE NOT NULL DEFAULT 0
) ENGINE = INNODB;

ALTER TABLE belegging
    ADD CONSTRAINT belegging_participant_id_fk
    FOREIGN KEY (participant_id) REFERENCES participant (id) ON DELETE CASCADE;

CREATE INDEX iParticipantInvestment ON belegging (participant_id);


# User-accounts
INSERT INTO useraccount (username, password, dateregistered, datelastpasswordreset, loginattempts)
VALUES ('Botje', '', '2023-06-01', '2023-06-01', 0);
INSERT INTO useraccount (username, password, dateregistered, datelastpasswordreset, loginattempts)
VALUES ('PukP33', '', '2023-06-01', '2023-06-01', 0);
INSERT INTO useraccount (username, password, dateregistered, datelastpasswordreset, loginattempts)
VALUES ('Giraffe', '', '2023-06-01', '2023-06-01', 0);

# Participants
INSERT INTO participant (useraccount_id, participantnumber, familyname, surnames, initials, prefixtitles, gendercode,
                         birthdate, email, cellphone)
VALUES (1, '220416-00732-834', 'Botje', 'Berend', 'B.', 'dhr.', 'M', '1978-04-16', 'b.botje@zeilboot.nl', '06-11223344');

INSERT INTO participant(useraccount_id, participantnumber, familyname, prefixes, surnames, initials, prefixtitles, gendercode,
                        birthdate, email, cellphone)
VALUES (2, '220416-17902-055', 'Petteflet', 'van de', 'Puk', 'P.', 'dhr.', 'M', '1933-11-23', 'petteflet@flatgebouw.nl',
        '06-55667788');

INSERT INTO participant(useraccount_id, participantnumber, familyname, surnames, initials, gendercode, birthdate, email,
                        hometelephone, cellphone)
VALUES (3, '220416-21495-701', 'Dap', 'Dikkertje', 'D.', 'M', '2001-05-03', 'dikkertje@dierentuin.nl', '020-12345678',
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
INSERT INTO participantpremium(participantid, fulltime_salaris, parttime_percentage, francise_actueel, percentage_beschikbare_premie)
VALUES (1, 49000, 100, 12600, 3);

INSERT INTO participantpremium(participantid, fulltime_salaris, parttime_percentage, francise_actueel, percentage_beschikbare_premie)
VALUES (2, 63500, 80, 16000, 3);

INSERT INTO participantpremium(participantid, fulltime_salaris, parttime_percentage, francise_actueel, percentage_beschikbare_premie)
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

# Get the sum of de current value of an individual participant, based on the indentifier of that participant.
CREATE PROCEDURE sp_SumInvestmentsParticipant(IN id BIGINT, OUT currentValue NUMERIC)
    BEGIN
        SELECT SUM(huidige_waarde) INTO currentValue
        FROM belegging
        WHERE participant_id = id;
    END;

# Get the last provided participant number
CREATE PROCEDURE sp_GetLastProvidedParticipantNumber(OUT lastNumber TEXT)
        BEGIN
            SELECT participantnumber INTO lastNumber
            FROM participant
            ORDER BY participantnumber DESC
            LIMIT 1;
        END;

