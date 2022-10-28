import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;

import Flight_Reservation.*;

public class App {
    public static void main(String[] args) throws Exception {

        //set up Databse
        Database.setupDB();

        //show home menu (1.Sign up, 2.log in, 3.exit)
        char option1 = '\0';
        char option2 = '\0';
        Customer customer = new Customer();
        Scanner scanner = new Scanner(System.in);

        do{
            option2 = '\0';
            Menu.showHomeMenu();
            try{
                option1 = scanner.next().charAt(0);
                switch(option1){
                    //Sign up (register information and get the cusomer number then go to the individual page)
                    case '1':
                        Menu.divider();
                        System.out.print("What is your first name?: ");
                        customer.setFirstName(scanner.next());
                        System.out.println();
                        System.out.print("What is your last name?: ");
                        customer.setLastName(scanner.next());                        
                        customer = customer.register();
                        break;

                    //Log in (login and if its true go to the individual page if its false show the message to register)
                    case '2':
                        System.out.print("What is your customer number?: ");
                        customer.setCustomerNo(scanner.next());
                        customer = customer.login();
                        if(customer.getCustomerNo() == null){
                            System.out.println();
                            System.out.println("Your customer number doesn't exist.");
                            System.out.println("Please try again.");
                            option2 = '6';
                        }
                        break;

                    //exit
                    case '3':
                        Menu.showThankMessage();
                        scanner.close();
                        break;
                    
                    //default
                    case default:
                        Menu.showInvalidErrorMessage();
                        option2 = '6';
                        break;
                }
                //Menu (1.search, 2.reserve, 3.change, 4.cancell, 5.show reservation list, 6.exit)
                while(option1 != '3' && option2 != '6'){
                    Menu.showMenu(customer);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                    try{
                        option2 = scanner.next().charAt(0);
                        switch(option2){
                            //1.search (type the date and orgin and destination get the list of flights)
                            case '1':
                                Menu.divider();
                                Flight flight = new Flight();
                                LinkedList<Flight> list = flight.searchFlight(new LinkedList<>(), 1);

                                if(list.size() > 0){
                                    System.out.println("Please choose the origin\nPlease type 0 if you want to search all flights: ");
                                    TreeSet<String> originSet = new TreeSet<>();
                                    for(Flight a: list){
                                        originSet.add(a.getOrigin());
                                    }
                                    originSet.forEach(elem -> System.out.println(">" + elem));
                                    String origin = scanner.next();
                                    System.out.println();

                                    if(!origin.equals("0")){
                                        if(origin.length()>1)
                                            origin = origin.substring(0, 1).toUpperCase() + origin.substring(1).toLowerCase();
                                        flight.setOrigin(origin);
                                        list = flight.searchFlight(list, 2);
                                    }         
                                }                       
                                
                                if(list.size() > 0){
                                    System.out.println("Please choose the destination\nPlease type 0 if you want to search all flights:: ");
                                    TreeSet<String> destinationSet = new TreeSet<>();
                                    for(Flight b: list){
                                        destinationSet.add(b.getDestination());
                                    }
                                    destinationSet.forEach(elem -> System.out.println(">" + elem));
                                    String destination = scanner.next();
                                    System.out.println();

                                    if(!destination.equals("0")){
                                        if(destination.length()>1)
                                            destination = destination.substring(0, 1).toUpperCase() + destination.substring(1).toLowerCase();
                                        flight.setDestination(destination);
                                        list = flight.searchFlight(list, 3);
                                    }
                                }

                                if(list.size() > 0){
                                    System.out.print("Please enter the date you want to depart\nPlease type 0 if you want to search all flights:(ex. yyyyMMdd): ");
                                    String departureDate = scanner.next();
                                    System.out.println();
                                    
                                    if(!departureDate.equals("0")){
                                        if(departureDate.matches("[0-9]{8}")){                                        
                                            Date dDate = sdf.parse(departureDate);
                                            flight.setDepartureDate(dDate);
                                            list = flight.searchFlight(list, 4);
                                        }else{
                                            System.out.println("Invalid date. Please try again.");
                                            list.clear();
                                        }
                                    }
                                }
                                
                                if(list.size() > 0){
                                    System.out.print("Please enter the date you want to arrive\nPlease type 0 if you want to search all flights:(ex. yyyyMMdd): ");
                                    String arraivalDate = scanner.next();
                                    System.out.println();

                                    if(!arraivalDate.equals("0")){
                                        if(arraivalDate.matches("[0-9]{8}")){                                        
                                            Date aDate = sdf.parse(arraivalDate);
                                            flight.setArrivalDate(aDate);
                                            list = flight.searchFlight(list, 5);
                                        }else{
                                            System.out.println("Invalid date. Please try again.");
                                            list.clear();
                                        }
                                    }
                                }

                                Menu.divider();

                                System.out.println("Search Result: ");
                                if(list.size() > 0){
                                    list.forEach(elem -> elem.showFlights());
                                }else{
                                    System.out.println("Sorry. There are no results.");
                                }

                                break;

                            //2.reserve 
                            //(type the flight number, seattype, people and reserve)
                            case '2':
                                Menu.divider();
                                System.out.print("Please type the flight number you want to reserve: ");
                                String flightNo = scanner.next();
                                System.out.println();
                                if((flightNo.length()==5) && flightNo.matches("[A-Z]{2}[0-9]{3}")){
                                    System.out.println("Please type the seat type you want to reserve: ");
                                    for(String i: Database.seatType){
                                        System.out.println(">" + i);
                                    }
                                    String seat = scanner.next();
                                    if(seat.length()>1)
                                        seat = seat.substring(0, 1).toUpperCase() + seat.substring(1).toLowerCase();
                                    System.out.println();
                                    
                                    System.out.print("Please type the number of people you want to reserve: ");
                                    int people = scanner.nextInt();
                                    Menu.divider();

                                    Reservation reservation = new Reservation(customer.getCustomerNo(), flightNo, seat, people);
                                    if(reservation.reserve()){
                                        System.out.println("The reservation succeded.");
                                        reservation.showReservation();
                                    }
                                }else{
                                    System.out.println("The flight number is invalid.");
                                    System.out.println("Please try again.");
                                }            
                                
                                break;

                            //3.change
                            //(show reservation list and type the flight number to be changed,
                            //then type the flight number, seattype, people)
                            case '3':
                                Menu.divider();
                                if(Reservation.showListForCustomer(customer.getCustomerNo())){
                                    System.out.print("Please type a reservation number that you want to change: ");
                                    String changeNo = scanner.next();
                                    Reservation changeReservation = new Reservation().getReservation(changeNo);
                                    if(changeReservation.getReserveNo().equals(changeNo)){
                                        if(changeReservation.getCustomerNo().equals(customer.getCustomerNo())){

                                            System.out.print("Please type the flight number you want to reserve: ");
                                            String changeFlightNo = scanner.next();                                    
                                            System.out.println();
                                            if((changeFlightNo.length()==5) && changeFlightNo.matches("[A-Z]{2}[0-9]{3}")){

                                                System.out.println("Please type the seat type you want to reserve: ");
                                                for(String j: Database.seatType){
                                                    System.out.println(">" + j);
                                                }
                                                String changeSeat = scanner.next();
                                                if(changeSeat.length()>1)
                                                    changeSeat = changeSeat.substring(0, 1).toUpperCase() + changeSeat.substring(1).toLowerCase();
                                                System.out.println();

                                                System.out.print("Please type the number of people you want to reserve: ");
                                                int changePeople = scanner.nextInt();
                                                Menu.divider();

                                                changeReservation.setFlightNo(changeFlightNo);
                                                changeReservation.setSeat(changeSeat);
                                                changeReservation.setPeople(changePeople);

                                                if(changeReservation.change()){
                                                    System.out.println("The reservation was changed successfully.");
                                                    changeReservation.showReservation();
                                                }
                                            }else{
                                                System.out.println("The flight number is invalid.");
                                                System.out.println("Please try again.");
                                            }
                                        }else{
                                            System.out.println("You cannot change this reservation.");
                                            System.out.println("Please check your account.");
                                        }
                                    }else{
                                        System.out.println("You cannot change this reservation.");
                                        System.out.println("Please check reservation number.");
                                    }
                                }
                                break;

                            //4.cancell
                            //(show reservation list and type the flight number to be cancelled)
                            case '4':
                                Menu.divider();
                                if(Reservation.showListForCustomer(customer.getCustomerNo())){
                                    System.out.print("Please type a reservation number that you want to cancell: ");
                                    String cancellNo = scanner.next();
                                    Reservation cancellReservation = new Reservation().getReservation(cancellNo);
                                    if(cancellReservation.getReserveNo().equals(cancellNo)){
                                        if(cancellReservation.getCustomerNo().equals(customer.getCustomerNo())){
                                            if(cancellReservation.cancell()){
                                                System.out.println("The reservation was cancelled successfully.");
                                                cancellReservation.showReservation();
                                            }
                                        }else{
                                            System.out.println("You cannot cancell this reservation.");
                                            System.out.println("Please check your account.");
                                        }
                                    }else{
                                        System.out.println("You cannot cancell this reservation.");
                                        System.out.println("Please check reservation number.");
                                    }
                                }
                                break;

                            //5.show reservation list
                            //(show reservation list)
                            case '5':
                                Menu.divider();
                                System.out.println("Your reservation list: ");
                                Reservation.showListForCustomer(customer.getCustomerNo());
                                break;


                            //6.logout  
                            case '6':
                                Menu.showThankMessage();
                                customer = customer.logout();
                                break;
                            
                            case default:
                                Menu.showInvalidErrorMessage();
                                break;
                        }

                    }catch(InputMismatchException exception){
                        scanner.nextLine();
                        Menu.showInvalidErrorMessage();
                    }catch(DateTimeParseException exception){
                        System.out.println("Invalid date. Please try again.");
                    }catch(Exception exception){
                        System.out.println("System error. Please try again.");
                    }
                }

            }catch(InputMismatchException exception){
                scanner.nextLine();
                Menu.showInvalidErrorMessage();
            }catch(DateTimeParseException exception){
                System.out.println("Invalid date. Please try again.");
            }catch(Exception exception){
                System.out.println("System error. Please try again.");
            }
        } while(option1 != '3');        
    }
}
