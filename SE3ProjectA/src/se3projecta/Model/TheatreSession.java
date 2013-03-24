package se3projecta.Model;

import se3projecta.Persistence.*;
import java.util.*;
import org.w3c.dom.*;

/**
 * Allows Booking of Seats for a particular SessionTime for a particular
 * Theatre.
 *
 * @author Timothy Moore <moor0330@flinders.edu.au>
 * @author Russell Peake <peak0042@flinders.edu.au>
 * @author Adam Rigg <rigg0035@flinders.edu.au>
 * @author Tobias Wooldridge <wool0114@flinders.edu.au>
 */
public class TheatreSession implements XmlSerializable, XmlUnserializable<Integer> {

    private Integer id;
    private int sessionTimeId;
    private SessionTime sessionTime;
    private int movieId;
    private Movie movie;
    private int theatreId;
    private Theatre theatre;
    private Seat[] seats;

    /**
     * Loads theatre session data from XML
     *
     * @param n an XML node which contains theatre session data
     */
    @Override
    public void load(Element n) {
        this.id = Integer.parseInt(n.getAttribute("id"));
        this.movieId = Integer.parseInt(n.getAttribute("movieId"));
        this.theatreId = Integer.parseInt(n.getAttribute("theatreId"));
        this.sessionTimeId = Integer.parseInt(n.getAttribute("sessionTimeId"));
        //load seats
        Element seatsElement = (Element) n.getElementsByTagName("Seats").item(0);
        NodeList seatNodes = n.getElementsByTagName("Seat");
        seats = new Seat[Integer.parseInt(seatsElement.getAttribute("count"))];
        for (int i = 0; i < seats.length; i++) {
            seats[i] = new Seat();
            seats[i].load((Element) seatNodes.item(i));
        }

    }

    public TheatreSession() {
    }

    /**
     * Creates a theatre session, which links a Theatre and a session time.
     *
     * @param t the theatre which is part of this theatre session
     * @param st the session time which is part of this theatre session
     * @param id id of this theatre session
     */
    public TheatreSession(Theatre t, SessionTime st, int id) {
        this.id = id;
        this.theatreId = t.getId();
        this.sessionTimeId = st.getId();
        this.movieId = t.getMovieId();
        seats = new Seat[t.getHeight() * t.getWidth()];
    }

    /**
     * Loads Theatre Session Relations - gets related objects objects (Theatre,
     * Movie, sessionTime) from their IDs. Also sets up seats details.
     *
     * @param theatres collection of theatres
     * @param movies collection of movies
     * @param sessionTimes collection of sessionTimes
     *
     * @throws InvalidRelationException Thrown when a reference does not exist
     */
    public void loadRelations(TreeMap<Integer, Theatre> theatres, TreeMap<Integer, Movie> movies, TreeMap<Integer, SessionTime> sessionTimes) throws InvalidRelationException {
        if (theatres.containsKey(theatreId)) {
            theatre = theatres.get(theatreId);
        } else {
            throw new InvalidRelationException("Theatre relation for movie '" + id + "' does not exist");
        }

        if (movies.containsKey(movieId)) {
            movie = movies.get(movieId);
        } else {
            throw new InvalidRelationException("Movie relation for TheatreSession '" + id + "' does not exist");
        }
        if (sessionTimes.containsKey(sessionTimeId)) {
            sessionTime = sessionTimes.get(sessionTimeId);
        } else {
            throw new InvalidRelationException("SessionTime relation for TheatreSession '" + id + "' does not exist");
        }

        //set up seat details
        if (theatre != null) {
            for (int i = 0; i < seats.length; i++) {
                if (seats[i] == null) {
                    seats[i] = new Seat();
                }
                seats[i].setSeatType(theatre.getSeatTypebyIndex(i));
            }
        }
    }

    /**
     * Gets theatre session ID
     *
     * @return theatre session ID
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Creates and returns a XML TheatreSession element, which stores theatre
     * session details, including seat state
     *
     * @param doc a XML document used to create the TheatreSession element
     * @return a XML TheatreSession element, which stores theatre session
     * details, including seat state
     */
    @Override
    public Element save(Document doc) {
        Element sessionElement = doc.createElement("TheatreSession");

        sessionElement.setAttribute("id", id.toString());
        sessionElement.setAttribute("movieId", Integer.toString(movieId));
        sessionElement.setAttribute("theatreId", Integer.toString(theatreId));
        sessionElement.setAttribute("sessionTimeId", Integer.toString(sessionTimeId));

        // staff elements
        Element seatsElement = doc.createElement("Seats");
        sessionElement.appendChild(seatsElement);
        seatsElement.setAttribute("count", Integer.toString(seats.length));

        for (int i = 0; i < seats.length; i++) {
            seatsElement.appendChild(seats[i].save(doc));
        }

        return sessionElement;
    }

    /**
     * Loads the Movie object from movieID
     *
     * @param movies collection of movies
     */
    public void loadMovie(TreeMap<Integer, Movie> movies) {
        if (movies.containsKey(movieId)) {
            this.movie = movies.get(movieId);
        } else {
            this.movie = null;
        }
    }

    /**
     * Gets all seats in the TheatreSession
     *
     * Clones the array to prevent movement of seats within array
     *
     * @return array of Seats
     */
    public Seat[] getSeats() {
        return seats.clone();
    }

    /**
     * Gets two-dimensional array of Seats
     *
     * @return Seat[][] two-dimensional array of seats
     */
    public Seat[][] getSeatRows() {
        Seat[][] rows = new Seat[theatre.getHeight()][theatre.getWidth()];

        int rowStart = 0;
        int rowSize = theatre.getWidth();
        for (int i = 0; i != theatre.getHeight(); i++) {
            rows[i] = Arrays.copyOfRange(seats, rowStart, rowStart + rowSize);
            rowStart += rowSize;
        }

        return rows;
    }

    /**
     * Sets all the state of all held seats in this TheatreSession to empty *
     */
    public void clearHeld() {
        for (Seat s : seats) {
            if (s.getState() == SeatState.Held) {
                s.setState(SeatState.Empty);
            }
        }
    }

    private Seat[] getContiguousAllocation(Seat[] row, SeatType type, int spanLength) {
        if (spanLength > row.length) {
            throw new IllegalArgumentException("Cannot allocate more seats than there are in the row!");
        }

        Seat[] allocation = null;

        // Make a shuffled list of indicies
        ArrayList<Integer> startIndicies = new ArrayList<Integer>(row.length - spanLength + 1);
        for (int spanStart = 0; spanStart <= row.length - spanLength; spanStart++) {
            startIndicies.add(spanStart);
        }
        Collections.shuffle(startIndicies);

        for (int spanStart : startIndicies) {
            boolean found = true;

            for (int spanOffset = 0; spanOffset < spanLength; spanOffset++) {
                int index = spanStart + spanOffset;
                if (!(row[index].getState() == SeatState.Empty && row[index].getType() == type)) {
                    found = false;
                    break;
                }
            }

            if (found == true) {
                allocation = Arrays.copyOfRange(row, spanStart, spanStart + spanLength);

                for (Seat seat : allocation) {
                    seat.setState(SeatState.Held);
                }

                break;
            }
        }

        return allocation;
    }

    private Seat[] getContiguousAllocation(Seat[][] rows, SeatType type, int spanLength) {
        if (rows == null || rows.length == 0) {
            throw new IllegalArgumentException("No rows of seats to allocate into");
        } else if (spanLength > rows[0].length) {
            throw new IllegalArgumentException("Spcified width greater than row");
        }

        Seat[] allocation = null;

        for (Seat[] row : rows) {
            allocation = getContiguousAllocation(row, type, spanLength);

            if (allocation != null) {
                break;
            }
        }

        return allocation;
    }

    /**
     * Attempts to find a random fit of a specified number of seats of a
     * specified type.
     *
     * If the specified number of seats is not available contiguous, break up
     * the allocation into smaller allocations
     *
     * @param type type of seats to fit
     * @param seats number of seats to fit
     * @return an array of Seat Objects representing best fit
     * @throws IllegalArgumentException
     */
    private Seat[] findRandomFit(SeatType type, int seats) throws IllegalArgumentException {
        if (!hasAvailable(type, seats)) {
            throw new IllegalArgumentException("Cannot allocate " + seats + " seats of type " + type.getName());
        }

        Seat[][] rows = getSeatRows();

        rows = Arrays.copyOf(rows, rows.length);
        Collections.shuffle(Arrays.asList(rows));

        int remainingSeats = seats;
        ArrayList<Seat> allocations = new ArrayList<Seat>();

        /* allocate seats using a random fit algorithm (choose a random place,
         see if allocation fits) */
        for (int spanLength = Math.min(theatre.getWidth(), remainingSeats); spanLength > 0; spanLength--) {
            while (remainingSeats >= spanLength) {
                Seat[] allocation = getContiguousAllocation(rows, type, spanLength);

                if (allocation == null) {
                    break;
                }

                allocations.addAll(Arrays.asList(allocation));
                remainingSeats = seats - allocations.size();
            }
        }

        Seat[] allocated = new Seat[allocations.size()];
        allocations.toArray(allocated);
        return allocated;
    }

    /**
     * Places seats seats of SeatType type pseudo-randomly
     *
     * @param type The type of seat to randomly place
     * @param seats The number of seats
     * @return
     */
    public Seat[] placeRandom(SeatType type, int seats) {
        return findRandomFit(type, seats);
    }

    /**
     * Return all seats in this TheatreSession of SeatType type and SeatState
     * state
     *
     * @param type The type of seat we're looking for
     * @param state The state of the seat we're looking for
     * @return
     */
    public Seat[] getSeats(SeatType type, SeatState state) {
        ArrayList<Seat> selected = new ArrayList<Seat>();

        for (Seat seat : seats) {
            if (seat.getType() == type && seat.getState() == state) {
                selected.add(seat);
            }
        }

        Seat[] selectedArray = new Seat[selected.size()];
        selected.toArray(selectedArray);

        return selectedArray;
    }

    /**
     * Counts how many seats in this TheatreSession of SeatType type are empty
     *
     * @param type the SeatType to count
     * @return The number of held seats of type SeatType that are empty
     */
    public int countEmpty(SeatType type) {
        return getSeats(type, SeatState.Empty).length;
    }

    /**
     * Counts how many seats in this TheatreSession of SeatType type are held
     *
     * @param type the SeatType to count
     * @return The number of held seats of type SeatType that are available
     */
    public int countHeld(SeatType type) {
        return getSeats(type, SeatState.Held).length;
    }

    /**
     * Checks whether or not this theatre session has a specified number of
     * available seats of the specified seat type
     *
     * @param type the seat type to check seat availability for
     * @param num number of seats to check availability for
     * @return whether or not this theatre session has a specified number of
     * available seats of the specified seat type
     */
    public boolean hasAvailable(SeatType type, int num) {
        return countEmpty(type) >= num;
    }

    /**
     * Checks whether the passed seat belongs to this TheatreSession.
     *
     * @param seat the seat to check whether it belongs to this TheatreSession
     * @return
     */
    public boolean ownsSeat(Seat seat) {
        //Could be optimized such that Seat holds a reference to TheatreSession 
        //and that reference is checked instead
        for (Seat s : seats) {
            if (s == seat) {
                return true;
            }
        }
        return false;
    }

    /**
     * Mark all held seats as occupied
     */
    public void commitSeats() {
        for (Seat seat : seats) {
            if (seat.getState() == SeatState.Held) {
                seat.setState(SeatState.Occupied);
            }
        }
    }

    /**
     * Gets session time for this theatre session
     *
     * @return session time for this theatre session
     */
    public SessionTime getSessionTime() {
        return sessionTime;
    }

    /**
     * Get movie showing in this theatre session
     *
     * @return movie showing in this theatre session
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * Gets theatre for this theatre session
     *
     * @return theatre for this theatre session
     */
    public Theatre getTheatre() {
        return theatre;
    }

    /*
     * The name for a TheatreSession is its theatre name.
     * 
     * This doesn't uniquely identify the TheatreSession
     */
    @Override
    public String toString() {
        return theatre.toString();
    }
}
