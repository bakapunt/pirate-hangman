# pirate-hangman
A pirate themed hangman game created in java for the CS12420 Software Development assignment.

## The assignment
- This assignment involved programming both a text-based and a graphics-based version of the same hangman game.
- The graphics-based version had to be pirate themed.
- The words to be guessed had to be pirate themed and read from the file `piratewords.txt`.
- Users should be able to play either the text version or the graphics version.

## Building and running
This is an Eclipse project, but can also be built and run from the terminal with these commands:

### Compiling the game
`javac -d bin -cp src src/uk/ac/aber/dcs/piratehangman/launcher/*.java src/uk/ac/aber/dcs/piratehangman/model/*.java src/uk/ac/aber/dcs/piratehangman/text/*.java src/uk/ac/aber/dcs/piratehangman/gui/*.java`

### Running the game
`java -cp bin uk.ac.aber.dcs.piratehangman.launcher.PirateApp`

### Compiling jUnit tests
`javac -d bin -cp src:lib/junit-4.12.jar src/uk/ac/aber/dcs/piratehangman/tests/*.java`

### Running jUnit tests
`java -cp bin:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore uk.ac.aber.dcs.piratehangman.tests.GameModelTest uk.ac.aber.dcs.piratehangman.tests.PhraseListTest`