# Domain model
# Security (Sign-up, sign-in and sign-out)
## User-account

![UserAccountClassDiagram](.\Images\UserAccountClassDiagram.png)
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
  - Lowercase characters, uppercase charaters and digits
  - The following characters: ! @ # $ % & * _ -
  - Whitespaces
- Can't start with a whitespace.
#### Policy
Regarding the password the following policy is applicable:
- Is valid for a (external) adjustable number of days.
- When redefining, it can't be equal to the former three.
## Participant
### Participant number
To each participant a mandatory unique participant number is assigned by the system. A participant number is constructed from three components:
- A date part from the actual date in the format 'yyMMdd'
- A time part from the actual time in the format 'mmSSS'
- A random generate number of three digits
The components are separated by a hyphen.
# Database model

