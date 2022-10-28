package Flight_Reservation;
import java.text.SimpleDateFormat;
import java.util.*;

public class Flight {
    
    //Variable
    private String flightNo; //Capital alphabet(2)(D,A) + number(3)
    private Date departureDate; //date to depart
    private Date arrivalDate; //date to arrive
    private String origin; //where to leave
    private String destination; //where to arrive
    private HashMap<String, Integer> capacity = new HashMap<>(); //capacity of each seat type

    //Constructor
    public Flight(String flightNo, Date departureDate, Date arrivalDate, String origin, String destination,
            HashMap<String, Integer> capacity) {
        this.flightNo = flightNo;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.origin = origin;
        this.destination = destination;
        this.capacity = capacity;
    }

    public Flight() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        Date date = calendar.getTime();
        this.departureDate = date;
        this.arrivalDate = date;
    }

    //Getter and Setter
    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public HashMap<String, Integer> getCapacity() {
        return capacity;
    }

    public void setCapacity(HashMap<String, Integer> capacity) {
        this.capacity = capacity;
    }

    //Method
    
    //check parameters, check capacity, update the flight database
    public static boolean reserveFlight(String flightNo, String seat, int people){
        if(Database.flightDB.containsKey(flightNo) && Database.seatType.contains(seat) && (people > 0)){
            int capacity = Database.flightDB.get(flightNo).capacity.get(seat);
            if(capacity >= people){
                Flight flight = Database.flightDB.get(flightNo);
                flight.capacity.put(seat, capacity - people);
                Database.flightDB.put(flightNo, flight);
                return true;
            }else{
                System.out.println("The flight doesn't have enough capacity for you.");
                System.out.println("Please reserve another flight.");
            }
        }else{
            Menu.showInvalidErrorMessage();
        }
        return false;
    }

    //check parameters, update the flight database
    public static boolean cancellFlight(String flightNo, String seat, int people){
        if(Database.flightDB.containsKey(flightNo) && Database.seatType.contains(seat) && (people > 0)){
            int capacity = Database.flightDB.get(flightNo).capacity.get(seat);
            Flight flight = Database.flightDB.get(flightNo);
            flight.capacity.put(seat, capacity + people);
            Database.flightDB.put(flightNo, flight);
            return true;
        }else{
            Menu.showInvalidErrorMessage();
        }
        return false;
    }

    //check parameters, check capacity, update the flight database
    public static boolean changeFlight(String beforeFlightNo, String beforeSeat, int beforePeople, String afterFlightNo, String afterSeat, int afterPeople){
        if(beforeFlightNo.equals(afterFlightNo) && beforeSeat.equals(afterSeat)){
            if(Database.flightDB.containsKey(afterFlightNo) && Database.seatType.contains(beforeSeat) && (beforePeople > 0) && Database.seatType.contains(afterSeat) && (afterPeople > 0)){
                    int capacity = Database.flightDB.get(afterFlightNo).capacity.get(afterSeat);
                    int gap = afterPeople - beforePeople;
                    if((gap > 0) && (gap <= capacity)){
                        return reserveFlight(afterFlightNo, afterSeat, gap);
                    }else if(gap < 0){
                        return cancellFlight(afterFlightNo, afterSeat, -gap);
                    }else{
                        System.out.println("The flight doesn't have enough capacity for you.");
                        System.out.println("Please reserve another flight.");
                    }
                    return false;
            }else{
                Menu.showInvalidErrorMessage();
            }
            return false;
        }else{
            return reserveFlight(afterFlightNo, afterSeat, afterPeople) && cancellFlight(beforeFlightNo, beforeSeat, beforePeople);
        }    
    }

    //search the database and return the list
    public LinkedList<Flight> searchFlight(LinkedList<Flight> list, int parameter){
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        Date date = calendar.getTime();

        if(parameter == 1){
            list.addAll(Database.flightDB.values());
            list.removeIf(flight -> flight.departureDate.before(date));
        }
        else if(parameter == 2){
            list.removeIf(flight -> !this.origin.equals(flight.origin));
        }
        else if(parameter == 3){
            list.removeIf(flight -> !this.destination.equals(flight.destination));
        }
        else if(parameter == 4){
            list.removeIf(flight -> !sdf.format(this.departureDate.getTime()).equals(sdf.format(flight.departureDate.getTime())));
        }
        else if(parameter == 5){
            list.removeIf(flight -> !sdf.format(this.arrivalDate.getTime()).equals(sdf.format(flight.arrivalDate.getTime())));
        }
        
        return list;
    }

    //show detail of flight
    public void showFlights(){
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy/MM/dd HH:mm z");
        Menu.divider2();
        System.out.println("Flight number: " + this.getFlightNo());
        System.out.println("Origin => Destination: " + this.getOrigin() + " => " + this.getDestination());
        System.out.println("Departuredate: " + sdf.format(this.getDepartureDate()));
        System.out.println("Arrivaldate: " + sdf.format(this.getArrivalDate()));
        for(String seat: Database.seatType){
            System.out.println("Seattype [capacity]: " + seat + " [" + this.getCapacity().get(seat) + "]");
        }
        Menu.divider2();
    }
}