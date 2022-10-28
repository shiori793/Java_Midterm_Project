package Flight_Reservation;
import java.util.*;

public class Customer {

    //Variable
    private String customerNo; //random 8 numbers
    private String lastName; //last name
    private String firstName; //first name

    //Constructor
    public Customer(String customerNo, String lastName, String firstName) {
        this.customerNo = customerNo;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Customer() {
    }

    //Getter and Setter
    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
  
    //Method

    //make random customer number and register to customer database
    //return customer number
    public Customer register(){
        do{
            Random rand = new Random();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 8; i++)
                sb.append(Integer.toString(rand.nextInt(10)));
            this.customerNo = sb.toString();
        }while(Database.customerDB.containsKey(this.customerNo));      
        Database.customerDB.put(this.customerNo, this);
        return this;
    }

    //check if customer number exists in database
    //return true or false
    public Customer login(){
        if(Database.customerDB.containsKey(this.customerNo)){
            return Database.customerDB.get(this.customerNo);
        }
        return new Customer();
    }

    //return empty string
    public Customer logout(){
        return new Customer();
    }    
}
