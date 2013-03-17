/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se3projecta.Model;

import java.util.ArrayList;
import se3projecta.Persistance.XmlSerializable;
import se3projecta.Persistance.XmlUnserializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
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
     * loads theatre session data from XML
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
     * creates a theatre session, which links a Theatre and a session time
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
     */
    public void loadRelations(TreeMap<Integer, Theatre> theatres, TreeMap<Integer, Movie> movies, TreeMap<Integer, SessionTime> sessionTimes) {
        if (theatres.containsKey(theatreId)) {
            theatre = theatres.get(theatreId);
        }
        if (movies.containsKey(movieId)) {
            movie = movies.get(movieId);
        }
        if (sessionTimes.containsKey(sessionTimeId)) {
            sessionTime = sessionTimes.get(sessionTimeId);
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
     * get theatre session ID
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
     * load the Movie object from movieID
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
     * get array of Seats
     *
     * @return array of Seats
     */
    public Seat[] getSeats() {
        return seats.clone();
    }

    /**
     * Get 2-dimensional array of Seats. Useful for random seat allocation.
     *
     * @return 2-dimensional of Seats
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
                if (!(row[index].available() && row[index].getType() == type)) {
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
     * attempts to find a random fit of a specified number of seats of a specified
     * type.
     * 
     * If the specified number of seats is not available contiguous, break up the
     * allocation into smaller allocations
     *
     * @param type type of seats to fit
     * @param seats number of seats to fit
     * @return an array of Seat Objects representing best fit
     * @throws IllegalArgumentException
     */
    public Seat[] findRandomFit(SeatType type, int seats) throws IllegalArgumentException {
        if (!hasAvailable(type, seats)) {
            throw new IllegalArgumentException("Cannot allocate " + seats + " seats of type " + type.getName());
        }

        Seat[][] rows = getSeatRows();
        
        rows = Arrays.copyOf(rows, rows.length);
        Collections.shuffle(Arrays.asList(rows));

        int remainingSeats = seats;
        ArrayList<Seat> allocations = new ArrayList<Seat>();

        // allocate seats using a best-fit algorithm
        // Try to allocate as big a span as we can each time
        // Start with the biggest span 
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

    //TODO: write JavaDoc for this - same as findRandomFit
    public Seat[] placeRandom(SeatType type, int seats) {
        return findRandomFit(type, seats);
    }

    /**
     * gets the number of available seats for a specified seat type for this
     * theatre session
     *
     * @param type the seat type to check seat availability for
     * @return the number of available seats for a specified seat type for this
     * theatre session
     */
    public int numAvailable(SeatType type) {
        // This could be optimized quite significantly by updating a counter
        // stored in TheatreSession when the available flag on each seat is
        // changed.
        int count = 0;

        for (Seat seat : seats) {
            if (seat.getType() == type && seat.available()) {
                count++;
            }
        }

        return count;
    }

    /**
     * checks whether or not this theatre session has a specified number of
     * available seats of the specified seat type
     *
     * @param type the seat type to check seat availability for
     * @param seats the number of seats to check seat availability for
     * @return whether or not this theatre session has a specified number of
     * available seats of the specified seat type
     */
    public boolean hasAvailable(SeatType type, int seats) {
        return numAvailable(type) > seats;
    }

    /**
     * get session time for this theatre session
     *
     * @return session time for this theatre session
     */
    public SessionTime getSessionTime() {
        return sessionTime;
    }

    /**
     * get movie showing in this theatre session
     *
     * @return movie showing in this theatre session
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * get theatre for this theatre session
     *
     * @return theatre for this theatre session
     */
    public Theatre getTheatre() {
        return theatre;
    }

    @Override
    public String toString() {
        return theatre.toString();
    }
}
