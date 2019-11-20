#Parking Lot Design Doc

This is the solution to problem statement given by Gojek to create an automatic ticketing system.

##Problem Statement:


- A parking lot can hold up to 'n' cars at any given point in time
- Each slot is given a number starting at 1 increasing with increasing distance from the entry point in steps of one
- Create an automated ticketing system that allows the use of parking lot
- When a car enters the parking lot, a ticket issued is issued to the driver
- The ticket issuing process includes documenting the registration number and the color of the car and allocating an available parking slot to the car before actually handing over a ticket to the driver
- The customer should be allocated a parking slot which is nearest to the entry
- At the exit the customer returns the ticket which then marks the slot they were using as being available
- At any time, the  ticketing system should be able to provide
	- Registration numbers of all cars of a particular colour
	- Slot number in which a car with a given registration number is parked
	- Slot numbers of all slots where a car of a particular colour is parked
- This project supports 2 modes of input
	- An interactive command prompt based shell where commands can be typed in
	- It accepts a filename as a parameter at the command prompt and reads the commands from that file
- exit command will exit the interactive command prompt
- The interactive command prompt will also exit if any incorrect command is provided
- Sample Test Cases & outputs are provied at the end of this file.

##Getting Started with the Setup

- Instructions to setup the project in local machine and run the test file

###System Requirements:
- Java 1.8
- Apache Maven 
- Git (For Version Control)

###Running the Test Cases:
- Run the command bin/setup to install the build the project (Java, Maven assumed to be pre-installed on the system)
   ````
   ./bin/setup
   ````
###Running with Bash
````
./bin/setup java -jar ../target/gojek_parking_lot-1.0-SNAPSHOT
````
### Versioning
   
 The folder also contains a .git file. Please check the version history using "git log" & "git diff" commands

   
### Built With

- [Maven](https://maven.apache.org/) - Build/Dependency Management


###Sample Test Cases & Outputs for Reference
- Sample Case is incorporated into /functional_spec/fixtures/file_input.txt 
- To give custom input edit the main file configuration => edit configuration => (remove) the project arguments .

			$ create_parking_lot 6
			Created a parking lot with 6 slots

			$ park KA-01-HH-1234 White
			Allocated slot number: 1

			$ park KA-01-HH-9999 White
			Allocated slot number: 2

			$ park KA-01-BB-0001 Black
			Allocated slot number: 3

			$ park KA-01-HH-7777 Red
			Allocated slot number: 4

			$ park KA-01-HH-2701 Blue
			Allocated slot number: 5

			$ park KA-01-HH-3141 Black
			Allocated slot number: 6

			$ leave 4
			Slot number 4 is free

			$ status
			Slot No.    Registration No    Colour
			1           KA-01-HH-1234      White
			2           KA-01-HH-9999      White
			3           KA-01-BB-0001      Black
			5           KA-01-HH-2701      Blue
			6           KA-01-HH-3141      Black

			$ park KA-01-P-333 White
			Allocated slot number: 4

			$ park DL-12-AA-9999 White
			Sorry, parking lot is full

			$ registration_numbers_for_cars_with_colour White
			KA-01-HH-1234, KA-01-HH-9999, KA-01-P-333

			$ slot_numbers_for_cars_with_colour White
			1, 2, 4

			$ slot_number_for_registration_number KA-01-HH-3141
			6

			$ slot_number_for_registration_number MH-04-AY-1111
			Not found

			$ exit
			
###Test Cases Usage:
 The Exceptions are handled keeping actual real life scenario in mind:
 
 - Parking Lot Once Created Cannot be override.
 - Empty Slot Cannot be Emptied again.
 - Car with same Registration number is not allowed.
 - Car number not available in any slot then should throw error.
 - Slot will not be given if registration number or colour of the vehicle is not available.
 - Same slot is not provided for two different cars.
 - For Integration testing commands are checked for any invalid command or argument.
 

