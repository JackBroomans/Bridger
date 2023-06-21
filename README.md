# Bridger
## Other documentation
Detail information is provided in teh following files:
- [TECHNOTES.md](TECHNOTES.md)
- [MODEL.md](MODEL.md)
## Introduction
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

# Processes
## Security
### Sign-on
### Sign-in (single)
The (single) sign-in process is a sequence of the following sub-processes:
1. The user provides username and password from the login-page in a Request to the webservice.
2. The webservice filters these requests before they hit any landing-page, extract the username and password from the request and creates a 'token' from them. Then this 'token' is validated on the requirements. (format) When the token is not validated an HTTP:401 error response is returned.
3. The 'token' is used to authenticate the user against the credentials in the database. When not authenticated, the request will be responded with a HTTP:403 error.
4. When authorized the user is redirected tp the appropriate landing-page, if not a HTTP:403 error is returned.

![Single Sign-in Flowchart](.\Images\Single Sign-in Flowchart.png)

### Sign-out


## Appriciations
