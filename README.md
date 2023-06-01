# Case Bridger
Bridger is an application for bridge players, and offer the following features:
- It can be used as a simple score calculator, including assingned IMP's.
- It can register a number of played  games in a single bridge event with two couples.
- It can organize and register a tournament among a number of couples on different times and locations.
- It can detect other participants in application in a certain geographical range, and invite them for a bridge meeting.
## The bridge calculator
The bridge calculator is a web based services which calculates the score based on the required data of a game:
- The dealer: North, East, South, West
- The player: North, East, South, West
- The vulnerability: None, North/South, East/west, ALL
- The contract:
  - Number of bidden tracks
  - Color of the contract: Clubs, Diamonds, Hearts, Spades, No Trumps
  - The eventual punishment bid: Regular, Doubled, Redoubled
- The result: The number of tricks gained by the player's couple.
The service returns the result in points assigned to the player's couple. Off course this result can be negative in case the contract went down.
### The calculation of the IMP's
Additional to the score calculation the calculator determines the awarded IMP's based on the score. The method to determine these IMP's is yhe one which is descripted by the NBB. (Dutch Brigde Association)


# Bridger Backend
## Model
### Entity classes
Entity classes are abstract classes belonging to the persistence layer and subject of the (Hibernate) JPA-implementation.  
Being part of the persistence layer, no (business) functionality is allowed in these classes. According to the JPA-implementation, the class only describes the structure and the relation(s) of the entity, reflecting the database and other external data sources such as JAXB-implementations for XML-files.
Entity classes are placed in the root of the model package,
### Entity utility classes.
Entity utility classes are extensions of specific (abstract) entity class. They are:
* Adding functionality to the entity class which doesn't belong to the persistence layer of the application, avoiding transient functional fields or methods.
* Enabling external configuration of the particular entity.
* Containing the (external stored) messages applicable in the entity class.
#### Instantiation of entities
To assign the default settings on instantiation of an entity class (without using field assignment), instantiation of an entity should be done by the method in the configuration class which is 
attached tot the particular entity class.
### Destination entities
Destination entities are meant to service the request data structures of the frontend. It's not not required to introduce a destination claas for each time, because the reasonable change exist that returning standard entities is sufficient and ths effective.
### Enums
Due to there unique structure and purpose enumerators are placed under there own package.
## Repository
The application uses stored procedures alongside the Hibernate repository features. This enables controe over the database's performance (tuning query efficiency).
### In- and output mapping
When required, the in and output mapping is covered by the 'mapstruct-processor' library. This allows services (classes) to construct the 
required structure before returning it to the client. It also enables mapping with dependency inhection.

## Logging

# Business rules
## User-account
### Username
A valid username must apply the following rules:
- Contains at least 5 characters an at most 15.
- Can contain lowercase characters, uppercase characters, digits, hyphens and underscores.
- Must start with a lower- or uppercase character.

### Password
#### Format
A valid password must apply the following rules:
- Contains at least 8 and at most 15 characters.
- Can contain:
- - Lowercase characters, uppercase charaters and digits 
- - The following characters: ! @ # $ % & * _ -
- - Whitespaces
- Can't start neither end with a whitespace.
#### Policy
Regarding the password the following policy is applicable:
- Is valid for a (external) adjustable number of days.
- When redefining, it can't be equal to the former three.