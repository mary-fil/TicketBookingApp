# Ticket booking app

The project handles a seat reservation system for a multiplex.

## Description

Project was written in JVM language: Java and provides APIs for managing showings, seats, and reservations.
The system allows users to view available showings, select seats, and make reservations for movies.
The project includes features such as seat reservation, ticket pricing based on ticket type, and validation of seat availability and reservation constraints.
It also supports the creation and management of rooms with configurable number of rows and columns.
The project aims to provide a user-friendly and efficient platform for booking movie tickets at the multiplex theater.

API documentation was generated using Springdoc-openapi library and can be accesses via:
<http://localhost:8080/swagger-ui/index.html>

## Business scenario (use case)

1. The user selects the day and the time when he/she would like to see the movie.
2. The system lists movies available in the given time interval - title and screening times.
3. The user chooses a particular screening.
4. The system gives information regarding screening room and available seats.
5. The user chooses seats, and gives the name of the person doing the reservation (name and surname).
6. The system gives back the total amount to pay and reservation expiration time.

## Assumptions

1. The system covers a single cinema with multiple rooms (multiplex).
2. Seats can be booked at latest 15 minutes before the screening begins.
3. Screenings given in point 2. of the scenario should be sorted by title and screening time.
4. There are three ticket types: adult (25 PLN), student (18 PLN), child (12.50 PLN).

## Business requirements

1. The data in the system should be valid, in particular:
Name and surname should each be at least three characters long, starting
with a capital letter. The surname could consist of two parts separated with a
single dash, in this case the second part should also start with a capital letter.
Reservation applies to at least one seat.
2. There cannot be a single place left over in a row between two already reserved places.
3. The system should properly handle Polish characters.

## Additional information and assumptions

* Project was created using Spring Initializer.
* Project uses H2 database and Spring Data JPA.
* The user specifies the time using ISO 8601 format ex. "2023-07-24T20:00:00".
* The system lists movies available in the +/- two hour time interval. This value can be changed in the code.
* User chooses a particular screening by its ID.
* User chooses seats by ID, user can choose more than one seat in the request.
* System returns total amount to pay and expiration time in a json format. Total amount is calculated as a total of this particular transaction.
* Rooms are assumed to be rectangular.
* More information about the classes is provided in the code in the format javadoc.

## How to build and run the project

## Ideas to implement in the future

* Create class user to store information about user's reservations. For example if user creates a reservation more than one time,
total amount to pay would be stored in database and would be increased every time user creates new reservation.
