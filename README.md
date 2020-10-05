# Database System 

Homebrew SQL database system, built from the ground up. Works with the SQL [Syntax](#syntax) listed below in BNF form. This system uses no external libraries to set up the database or parse the SQL queries. 

## Usage

### Automatic Queries Showcase
    $ java DBServer
    $ java DBAutoClient 
    
DBServer starts up the database on the server, it can then be accessed by clients. Running DBAutoClient gives a client with pre-written sample queries than can be sent to the Server by just pressing Enter. 

### Normal Usage
    $ java DBServer
    $ java DBClient
    
Once the client is running, SQL queries can be entered on the command line. A full breakdown of the syntax is given below.

## Queries Examples

Basic data structures creation: 
    
     CREATE DATABASE coursework;
     Server response: OK
     
     USE coursework;
     Server response: OK
     
     CREATE TABLE marks (name, mark);
     Server response: OK
    
When data is added to a table other queries include: 
    
    SELECT * FROM marks;
    Server response:
    id  name   mark
    1   Steve  65
    2   Dave   55
    3   Bob    35
    4   Clive  20
    
    DELETE FROM marks WHERE name == 'Dave';
    Server response: OK

Also capable of handling JOIN queries and multiple WHERE conditions.

## Syntax 
    <sqlCompiler.sqlCommands>        ::=  <CommandType>;

    <CommandType>    ::=  <Use> | <Create> | <Drop> | <Alter> | <Insert> |
                      <Select> | <Update> | <Delete> | <Join>

    <Use>            ::=  USE <DatabaseName>

    <Create>         ::=  <CreateDatabase> | <CreateTable>

    <CreateDatabase> ::=  CREATE DATABASE <DatabaseName>

    <CreateTable>    ::=  CREATE TABLE <TableName> | CREATE TABLE <TableName> ( <AttributeList> )

    <Drop>           ::=  DROP <Structure> <StructureName>

    <Structure>      ::=  DATABASE | TABLE

    <Alter>          ::=  ALTER TABLE <TableName> <AlterationType> <AttributeName>

    <Insert>         ::=  INSERT INTO <TableName> VALUES ( <ValueList> )

    <Select>         ::=  SELECT <WildAttribList> FROM <TableName> |
                          SELECT <WildAttribList> FROM <TableName> WHERE <Condition> 

    <Update>         ::=  UPDATE <TableName> SET <NameValueList> WHERE <Condition> 

    <Delete>         ::=  DELETE FROM <TableName> WHERE <Condition>

    <Join>           ::=  JOIN <TableName> AND <TableName> ON <AttributeName> AND <AttributeName>

    <NameValueList>  ::=  <NameValuePair> | <NameValuePair> , <NameValueList>

    <NameValuePair>  ::=  <AttributeName> = <Value>

    <AlterationType> ::=  ADD | DROP

    <ValueList>      ::=  <Value>  |  <Value> , <ValueList>

    <Value>          ::=  '<StringLiteral>'  |  <BooleanLiteral>  |  <FloatLiteral>  |  <IntegerLiteral>

    <BooleanLiteral> ::=  true | false

    <WildAttribList> ::=  <AttributeList> | *

    <AttributeList>  ::=  <AttributeName> | <AttributeName> , <AttributeList>

    <Condition>      ::=  ( <Condition> ) AND ( <Condition> )  |
                          ( <Condition> ) OR ( <Condition> )   |
                          <AttributeName> <Operator> <Value>

    <Operator>       ::=   ==   |   >   |   <   |   >=   |   <=   |   !=   |   LIKE
    
## Disclaimer
This work was submitted as coursework for the COMSM0103 Object Oriented Programming with Java module at the University of Bristol. Please note that no student can use this work without my permission or attempt to pass this work off as their own.
