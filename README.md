SE3ProjectA
===========
## Running
To build the project, open the project in netbeans and press the "Clean and Build Project"
button

To run the project, having opened the project in netbeans, press the "Run Project" button.

## Usage

This system allows users to book seats in a movie cinema for a movie and time.

### Choosing a movie session

Upon opening the program, you will be presented with a dialog showing which movies and times
you can book seats for.

When you select one of these movies or times, the seating plan for that movie will be displayed
on the left side of the window.

You will be able to see how many available seats there are here.

### Selecting number of tickets

Once you have chosen your movie, click the "Select Tickets" button, which will take you to another
screen showing which types of tickets you can book. For example, you might want to choose tickets
in "Gold" seats for children.

You may choose eats from multiple sections and for multiple people; e.g. by pressing the "+" button,
another row will appear for you to make further choices within.

Once you chosen your seating arrangements, click the "Forward" button. If you have chosen more
seats of a certain type than there are available (e.g. chosen 20 gold seats when there are only
10 empty gold seats in the theatre), you will be asked to change your chosen number of seats.

### Placing Seats

The next page allows you to choose where your seats are. One way to do this is to click the seats
on the left of the window. A person will appear in them, indiciating that you have chosen them.

The counters on the right will reflect how many more seats you have to place.

There is also a button shown on the right, labelled "Randomly Allocate Remaining Seats". Pressing
this button will randomly choose a location for every unplaced seat. The program tries to keep your
seats together, however, they will be broken up if they're of different types or do not fit together

Once all seats have been placed, you may click the "Book Seats" button to finalize your booking.

## Assumptions

1. It has been assumed that each theatre only shows one movie 
2. There is only one day of bookings considered. Dated theatre sessions and movies do not exist.
