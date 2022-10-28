package Flight_Reservation;

public class Menu {

    public static void showHomeMenu(){
        divider();
        System.out.println("Welcome to the flight reservation system!");
        divider();
        System.out.println("1. Sign up");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Please enter the option: ");
    }

    public static void showMenu(Customer customer){
        divider();
        System.out.println("Your name: " + customer.getFirstName() + " " + customer.getLastName());
        System.out.println("Your customer number: " + customer.getCustomerNo());
        divider();
        System.out.println("1. Search");
        System.out.println("2. Reserve");
        System.out.println("3. Change");
        System.out.println("4. Cancell");
        System.out.println("5. List");
        System.out.println("6. Logout");
        System.out.print("Please enter the option: ");
    }

    public static void showThankMessage(){
        divider();
        System.out.println("Thank you for using our flight reservation system!");
        divider();
    }

    public static void divider(){
        System.out.println("\n====================\n");
    }

    public static void divider2(){
        System.out.println("\n--------------------\n");
    }

    public static void showInvalidErrorMessage(){
        System.out.println();
        System.out.println("Invalid error!");
        System.out.println("Please try again.");
    }
    
}
