package Flight_Reservation;
import java.util.*;

public class Reservation {

    //Variable
    private String reserveNo; // customerNo + 4 serial numbers
    private String customerNo; //random 8 numbers
    private String flightNo; //Date + 4 serial numbers
    private String seat; //Economy, Business, First
    private int people; //1 ~ numbers of people to reserve
    private String status; //Reserved or Cancelled

    //Constructor
    public Reservation(String reserveNo, String customerNo, String flightNo, String seat, int people, String status) {
        this.reserveNo = reserveNo;
        this.customerNo = customerNo;
        this.flightNo = flightNo;
        this.seat = seat;
        this.people = people;
        this.status = status;
    }

    public Reservation(String customerNo, String flightNo, String seat, int people) {
        this.customerNo = customerNo;
        this.flightNo = flightNo;
        this.seat = seat;
        this.people = people;
    }
  
    public Reservation() {
    }

    //Getter and Setter
    public String getReserveNo() {
        return reserveNo;
    }

    public void setReserveNo(String reserveNo) {
        this.reserveNo = reserveNo;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    //Method

    //make the reservation
    //reserve the flight and update flight database,
    //make the reservation number based on the customer number,
    //update the resevation Database
    //return reservation instance, if fail return empty instance
    public boolean reserve(){
        if(Flight.reserveFlight(this.flightNo, this.seat, this.people)){
            Set<String> keySet = new HashSet<>();
            for(String set: Database.reservationDB.keySet()){
                keySet.add(String.valueOf(set));
            };
            keySet.removeIf(key -> !key.regionMatches(false, 0, this.customerNo, 0, 7));
            this.reserveNo = this.customerNo + String.format("%02d", keySet.size()+1);
            this.setStatus("Reserved");
            Database.reservationDB.put(this.reserveNo, this);
            return true;
        }     
        return false; 
    }

    //change the reservation
    //check if the reserve number exists, get the reserve information from database,
    //check customer number and status,
    //change the flight and update flight database,
    //update reservation database and return the reservation instance
    //if fail return empty instance
    public boolean change(){
        if(Database.reservationDB.containsKey(this.reserveNo)){
            Reservation oldReservation = new Reservation().getReservation(this.reserveNo);
            if(oldReservation.customerNo.equals(this.customerNo) && oldReservation.status.equals("Reserved")){
                if(Flight.changeFlight(oldReservation.flightNo, oldReservation.seat, oldReservation.people, this.flightNo, this.seat, this.people)){
                    Database.reservationDB.put(this.reserveNo, this);
                    return true;
                }
            }else{
                System.out.println("You cannot change this reservation.");
                System.out.println("Please check your account or reservation status.");
            }
        }else{
            System.out.println("The reservation you chose doesn't exist.");
            System.out.println("Please check your reservation.");
        }
        return false;        
    }

    //cancell the reservation
    //check if the reserve number exists, get the reserve information from database,
    //check customer number and status,
    //cancell the flight and update flight database,
    //update reservation database and return the reservation instance
    //if fail return empty instance
    public boolean cancell(){
        if(Database.reservationDB.containsKey(this.reserveNo)){
            Reservation cancellReservation = new Reservation().getReservation(this.reserveNo);
            if(cancellReservation.customerNo.equals(this.customerNo) && cancellReservation.status.equals("Reserved")){
                if(Flight.cancellFlight(cancellReservation.flightNo, cancellReservation.seat, cancellReservation.people)){
                        this.setStatus("Cancelled");
                        Database.reservationDB.put(this.reserveNo, this);
                        return true;
                }
            }else{
                System.out.println("You cannot cancell this reservation.");
                System.out.println("Please check your account or reservation status.");
            }
        }else{
            System.out.println("The reservation you chose doesn't exist.");
            System.out.println("Please check your reservation.");
        }
        return false;
    }

    //show list for the customer
    //return the list or all reservation for specific customer
    public static boolean showListForCustomer(String customerNo){
        Menu.divider();
        LinkedList<Reservation> list = new LinkedList<>();
        Set<String> keySet = new HashSet<>();
        for(String set: Database.reservationDB.keySet()){
            keySet.add(String.valueOf(set));
        };
        keySet.removeIf(key -> !key.regionMatches(false, 0, customerNo, 0, 8));
        if(keySet.size() > 0){
            for(String key: keySet){
                list.add(Database.reservationDB.get(key));
            }
            list.sort((a, b) -> Integer.parseInt(a.reserveNo.substring(8)) - Integer.parseInt(b.reserveNo.substring(8)));
            for(Reservation reservation: list){
                System.out.println("Reservation: " + (list.indexOf(reservation) + 1));
                reservation.showReservation();
            }
            Menu.divider();
            return true;
        }else{
            System.out.println("You don't have any reservation.");
        }
        return false;        
    }
    
    //show each Reservation
    public void showReservation(){
        Menu.divider2();
        System.out.println("Reservation number: " + this.getReserveNo());
        System.out.println("Flight number: " + this.getFlightNo());
        System.out.println("Seat type: " + this.getSeat());
        System.out.println("People: " + this.getPeople());
        System.out.println("Status: " + this.getStatus());
        Database.flightDB.get(this.getFlightNo()).showFlights();
        Menu.divider2();
    }

    public Reservation getReservation(String reserveNo){
        if(Database.reservationDB.containsKey(reserveNo)){
            Reservation oldReservation = Database.reservationDB.get(reserveNo);
            Reservation reservation = new Reservation(oldReservation.getReserveNo(), oldReservation.getCustomerNo(), oldReservation.getFlightNo(), oldReservation.getSeat(), oldReservation.getPeople(), oldReservation.getStatus());
            return reservation;
        }else{
            Reservation reservation = new Reservation("0", "0", "0", "0", 0, "0");
            return reservation;
        }
    }
}
    