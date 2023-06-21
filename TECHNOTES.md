# Technical notes
## Configuration
### Used Techniques
- SpringBoot - context.properties
- Spring - context.annotation
- Spring - beans.factory.annotation
- Jakarta - annotation
- *.yaml and *.properties
### Internal
### External
External configuration makes it easier to configure the application and its behaviour on the flight, they doesn't require any deployment and are immediately effectuated.
Of course not all configuration are suited for this practice, under those:
- Default selected items from enumerators.
- Regular expressions used to validate data items.
- Date- and time formats.
- Logging- and messages texts.
- 
The downsides of external configuration are:
- Reading from external resources are expensive operations.
- Texts can use-up rather much of memory resources, especially when read all at once.

To reduces the impact of these downsides, the application uses the following techniques:
- Entity classes are extended with Entity Utility Classes.
- External configuration items are strict separated per entity.
## Structure
### Entity classes
Entity classes are abstract classes belonging to the persistence layer and subject of the (Hibernate) JPA-implementation.  
Being part of the persistence layer, no (business) functionality is allowed in these classes. According to the JPA-implementation, the class only describes the structure and the relation(s) of the entity, reflecting the database and other external data sources such as JAXB-implementations for XML-files.
Entity classes are placed in the root of the model package,
### Entity utility classes.
#### Instantiation of entities
To assign the default settings on instantiation of an entity class (without using field assignment), instantiation of an entity should be done by the method in the configuration class which is
attached tot the particular entity class.
### Payloads
Payload objects are meant to provide particular datastructures for requests and responcses. These objects are decoupled from the domain- and database objects and allows new datastructures. This offers a flexible data interchanges between the front- and backend.
### Enums
Due to there unique structure and purpose enumerators are placed under their own package.
## Repository
The application uses stored procedures alongside the Hibernate repository features. This enables controe over the database's performance (tuning query efficiency).
### In- and output mapping
When required, the in and output mapping is covered by the 'mapstruct-processor' library. This allows services (classes) to construct the
required structure before returning it to the client. It also enables mapping with dependency inhection.

## Logging

## Security
### Used technologies
- SpringBoot Security
- JWT authentication
- Angular 14
- RxJS 7
- Angular CLI 14
- Bootstrap 4
### End-points
### Controllers
The controllers handle the signup, signin and signout requests & authorized requests, based on the URI of the particular request.
- AuthController: @PostMapping(‘/signup’), @PostMapping(‘/signin’), @PostMapping(‘/signout’)
- TestController: @GetMapping(‘/api/test/all’), @GetMapping(‘/api/test/[role]’)
### JWT authentication
### Sign-in
