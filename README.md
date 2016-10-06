# _Checkers_

### _Epicodus: Java Week 5, Advanced Java Topics: Group Project_

#### By _[**Sandro Alvarez**](https://github.com/SandroMateo), [**Satchel Grant**](https://github.com/grantsrb), [**Chance Neff**](https://github.com/crneff84), [**Caleb Stevenson**](https://github.com/CGrahamS)_

## Description

This webpage allows users to play a game of checkers. If the user creates an account they may also save games to play later.

## Specs

| BEHAVIOR                                              | INPUT                                          | OUTPUT                                           |
|-------------------------------------------------------|------------------------------------------------|--------------------------------------------------|
| Program creates a game                                | Select: 2 Player                               | Display: checkers board                          |
| Program moves checkers                                | Select: checker, select: space                 | Update: checker row and column                   |
| Program limits moves to diagonal movements            | Select: checker, select: space                 |                                                  |
| Program captures checkers                             | Select: checker, select: space                 | Delete: captured checker                         |
| Program determines when double captures are available | Select: checker, select: space                 | Delete: captured checkerDelete: captured checker |
| Program kings checkers:                               | Select: checker, select: space                 | Update: checker type                             |
| Program switches player turn                          | Player 1 turn - select: checker, select: space | Player 2 turn                                    |
| Program determines when a player wins                 | Player 1 turn - select: checker, select: space | Player 1 Wins!                                   |

## Setup/Installation Requirements

* In your first terminal window:
  * Start postgres: `$ postgres`
* In your second terminal window:
  * Start psql: `$ psql`
  * Create database: `# CREATE DATABASE checkers;`
* In your third terminal window:
  * Clone this repository to your desktop: `$ cd Desktop; git clone https://github.com/grantsrb/checkers-project`
  * Navigate to the hair-salon directory: `$ cd checkers-project`
  * Create database schema from wildlife_tracker.sql: `$ psql checkers < checkers.sql`
* Back in your second terminal window:
  * Confirm the database has been restored correctly:
    * Connect to wildlife_tracker database: `# \c checkers;`
    * Print out database tables: `# \dt;`
    <br>
    <!-- TODO add all table names -->
    NOTE: You should see `animals` and `sightings` tables in the `wildlife_tracker` database,
* Back in your third terminal window:
  * Run the app: `$ gradle run`
* In the browser of your choosing, navigate to "localhost:4567" (tested in Chrome).

## Known Bugs

Navigating to the previous webpage via the back button visually reverts the checkers the most recent prior position.

## Support and contact details

Sandro Alvarez: _sandromateo22@gmail.com_
<br>
Satchel Grant: _grantsrb@gmail.com_
<br>
Chance Neff: _crneff84@gmail.com_
<br>
Caleb Stevenson: _cgrahamstevenson@gmail.com_

## Technologies Used

_Java,
Spark,
Velocity,
SQL_

### License

This webpage is licensed under the GPL license.

Copyright &copy; 2016 **_Sandro Alvarez, Satchel Grant, Chance Neff &amp; Caleb Stevenson_**
