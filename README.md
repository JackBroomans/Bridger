# Case Bridger
## Entity classes
The entity classes are subjected to the (Hibernate) JPA-implementation and are kept as 'clean' as possible. This 
means that they are associated only to the (MySql) database tables and not to other components.
### External configuration classes.
When applicable (or required) a configuration class is associated to the entity class. The configuration class controls the external configuration. This practice allows fine-tuning per 
entity. 
### Instantiation of entities
To assign the default settings on instantiation of an entity class (without using field assignment), instantiation of an entity should be done by the method in the configuration class which is 
attached tot the particular entity class.

## Repository
The application uses stored procedures alongside the Hibernate repository features. This enables controe over the database's performance (tuning query efficiency).
### In- and output mapping
When required, the in and output mapping is covered by the 'mapstruct-processor' library. This allows services (classes) to construct the 
required structure before returning it to the client. It also enables mapping with dependency inhection.

## Logging
https://www.javaguides.net/2018/09/spring-boot-2-logging-slf4j-logback-and-log4j-example.html
#### Logback https://www.baeldung.com/logback
#### SLF4J https://www.baeldung.com/log4j2-appenders-layouts-filters



### De opdracht :page_facing_up:
Je levert een front-end op voor een deelnemer bij BeFrank. Hierop is de verwachte waarde van de pensioenpot op de geplande pensioendatum te zien.
De data voor dit front-end komt uit een java applicatie die hiervoor gegevens zal lezen uit een onderliggende database.
Daarnaast maakt de applicatie gebruik van een REST koppeling met een externe beleggingservice om de huidige waarde van de beleggingsrekening op te halen.
De deelnemer heeft de mogelijkheid om de verwachte pensioenleeftijd aan te passen en ziet dan het effect op de grootte van de pensioenpot op pensioendatum.
```mermaid
  graph LR;
      A[Frontend]-->|Aanpassen pensioenleeftijd|B[Web applicatie];
      B[Web applicatie]-->|Tonen effect op pensioenpot|A[Frontend];
      
      B[Web applicatie]-.->|Ophalen beleggingen|C[Externe beleggingsservice];
      C[Externe beleggingsservice]-.->B[Web applicatie];
      style A fill:#f9f,stroke:#333,stroke-width:4px
      style B fill:#f9f,stroke:#333,stroke-width:4px
      style C fill:#bbf,stroke:#f66,stroke-width:2px,color:#fff,stroke-dasharray: 5 5
```


#### Data model
Een deelnemer bij BeFrank is een (ex-)werknemer bij een werkgever die een pensioenregeling heeft bij BeFrank.
Voor deze deelnemer hebben we in ieder geval NAW gegevens nodig, een e-mailadres en zijn geboortedatum.
De deelnemer heeft een dienstverband met zijn werkgever, waarbij zijn salaris bepaalt hoeveel pensioenpremie
maandelijks op de pensioenrekening wordt gestort. De pensioenrekening zelf is een beleggingsrekening die door
een externe partij wordt beheerd en waarvan we alleen het rekeningnummer hoeven vast te leggen.

#### Berekeningen :computer:
##### Jaarlijkse premie
Indien de deelnemer nog in dienst is bij de werkgever, dan zal maandelijks een premie gestort worden op de beleggingsrekening die afhankelijk is van:

- Full-time salaris
- Parttime percentage
- Franchise in de regeling (voor nu: vastgesteld op 15.599, -)
- Beschikbare premie percentage (voor nu: vast percentage van 5%)

De jaarlijkse premiestorting kan je berekenen met de volgende formule:

```(Full-time salaris – Franchise) * Parttime percentage * Beschikbare premie percentage```

Indien de deelnemer uit dienst is, dan wordt er geen premie meer gestort.

##### Verwachte waarde :moneybag:
De (vereenvoudigde) berekening van de verwachte waarde van de pensioenrekening is afhankelijk van de
volgende gegevens:
- Huidige leeftijd van de deelnemer
- Gewenste pensioenleeftijd van de deelnemer
- Huidige waarde van de beleggingen
- Jaarlijkse premie storting
- Jaarlijks rendement op de beleggingen (voor eenvoud van deze opdracht: vast rendement van 3%)

Voor ieder jaar tot aan het pensioen is de berekening van de verwachte waarde van de pensioenrekening gelijk aan:

```Huidige waarde + Jaarlijkse premiestorting + (Huidige waarde + Jaarlijkse premiestorting/2) * rendement```


#### Externe beleggingsservice
De beleggingsservice biedt een REST interface, die voor een gegeven rekeningnummer retourneert in welke
fondsen momenteel belegd wordt en wat de waarde per fonds is. De totale waarde van de beleggingsrekening is
de som van de waarde in de fondsen. Deze service hoeft niet volledig geïmplementeerd te worden, maar kan
vervangen worden door een mock API.

### Voorbeelden
#### Voorbeeld 1
Gegeven:
- Een deelnemer van 60 jaar oud
- En een huidige waarde van 100.000, -
- En een full-time salaris van 60.000, -
- En een part-time percentage van 80%
- En een franchise van 15.599, -
- En een beschikbare premie percentage van 5%
- En een rendement van 3% per jaar

Wanneer een gewenste pensioenleeftijd van 61 jaar wordt ingevuld  
Dan levert dit een verwachte waarde op pensioendatum van 104.802,68 euro

#### Voorbeeld 2
Gegeven:
- Dezelfde deelnemer als hierboven

Wanneer een gewenste pensioenleeftijd van 65 jaar wordt ingevuld  
Dan levert dit een verwachte waarde op pensioendatum van 125.498,08 euro