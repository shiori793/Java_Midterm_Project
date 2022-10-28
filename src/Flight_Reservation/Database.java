package Flight_Reservation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Database {
    public static HashMap<String, Customer> customerDB = new HashMap<>();
    public static HashMap<String, Flight> flightDB = new HashMap<>();
    public static HashMap<String, Reservation> reservationDB = new HashMap<>();

    public static LinkedList<String> seatType = new LinkedList<>();

    private static Date addTime(Date date, String string){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String addHour = string.substring(0, 2);
        String addMin = string.substring(2);
        calendar.add(Calendar.HOUR, Integer.parseInt(addHour));
        calendar.add(Calendar.MINUTE, Integer.parseInt(addMin));
        return calendar.getTime();
    }

    //set up Database when application starts
    public static void setupDB(){
        
        seatType.add("Economy");
        seatType.add("Business");
        seatType.add("First");

        Customer cst1 = new Customer("11111111", "AAA", "AAAA");
        Customer cst2 = new Customer("22222222", "BBB", "BBBB");
        Customer cst3 = new Customer("33333333", "CCC", "CCCC");

        customerDB.put(cst1.getCustomerNo(), cst1);
        customerDB.put(cst2.getCustomerNo(), cst2);
        customerDB.put(cst3.getCustomerNo(), cst3);

        String[] city = {"Vancouver", "Toronto", "Montreal", "Tokyo", "Newyork", "London", "Rome", "Paris"};

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        String num;
        Date ddate = new Date();
        Date adate = new Date();

        try{
        //Vancouver -> Toronto
        num = city[0].substring(0,1) + city[1].substring(0, 1) + "001";
        ddate = sdf.parse("2022/10/27 13:20");
        adate = Database.addTime(ddate, "0423");
        HashMap<String, Integer> capacity1 = new HashMap<>();
        capacity1.put("Economy", 100);
        capacity1.put("Business", 20);
        capacity1.put("First", 10);
        Flight flt1 = new Flight(num, ddate, adate, city[0], city[1], capacity1);
        flightDB.put(num, flt1);
        
        //Vancouver -> Montreal
        num = city[0].substring(0,1) + city[2].substring(0, 1) + "002";
        ddate = sdf.parse("2022/10/28 11:25");
        adate = Database.addTime(ddate, "0450");
        HashMap<String, Integer> capacity2 = new HashMap<>(capacity1);
        Flight flt2 = new Flight(num, ddate, adate, city[0], city[2], capacity2);
        flightDB.put(num, flt2);

        //Vancouver -> Tokyo
        num = city[0].substring(0,1) + city[3].substring(0, 1) + "003";
        ddate = sdf.parse("2022/10/29 13:45");
        adate = Database.addTime(ddate, "1045");
        HashMap<String, Integer> capacity3 = new HashMap<>(capacity1);
        Flight flt3 = new Flight(num, ddate, adate, city[0], city[3], capacity3);
        flightDB.put(num, flt3);

        //Vancouver -> NewYork
        num = city[0].substring(0,1) + city[4].substring(0, 1) + "004";
        ddate = sdf.parse("2022/10/30 23:55");
        adate = Database.addTime(ddate, "0515");
        HashMap<String, Integer> capacity4 = new HashMap<>(capacity1);
        Flight flt4 = new Flight(num, ddate, adate, city[0], city[4], capacity4);
        flightDB.put(num, flt4);

        //Vancouver -> London
        num = city[0].substring(0,1) + city[5].substring(0, 1) + "005";
        ddate = sdf.parse("2022/11/02 18:00");
        adate = Database.addTime(ddate, "1115");
        HashMap<String, Integer> capacity5 = new HashMap<>(capacity1);
        Flight flt5 = new Flight(num, ddate, adate, city[0], city[5], capacity5);
        flightDB.put(num, flt5);

        //Vancouver -> Rome
        num = city[0].substring(0,1) + city[6].substring(0, 1) + "006";
        ddate = sdf.parse("2022/11/17 17:45");
        adate = Database.addTime(ddate, "1735");
        HashMap<String, Integer> capacity6 = new HashMap<>(capacity1);
        Flight flt6 = new Flight(num, ddate, adate, city[0], city[6], capacity6);
        flightDB.put(num, flt6);
        
        //Vancouver -> Paris
        num = city[0].substring(0,1) + city[7].substring(0, 1) + "007";
        ddate = sdf.parse("2022/11/07 17:40");
        adate = Database.addTime(ddate, "1235");
        HashMap<String, Integer> capacity7 = new HashMap<>(capacity1);
        Flight flt7 = new Flight(num, ddate, adate, city[0], city[7], capacity7);
        flightDB.put(num, flt7);
        
        //Toronto -> Vancouver
        num = city[1].substring(0,1) + city[0].substring(0, 1) + "008";
        ddate = sdf.parse("2022/11/04 19:00");
        adate = Database.addTime(ddate, "0500");
        HashMap<String, Integer> capacity8 = new HashMap<>(capacity1);
        Flight flt8 = new Flight(num, ddate, adate, city[1], city[0], capacity8);
        flightDB.put(num, flt8);
        
        //Montreal -> Vancouver
        num = city[2].substring(0,1) + city[0].substring(0, 1) + "009";
        ddate = sdf.parse("2022/11/13 07:00");
        adate = Database.addTime(ddate, "0823");
        HashMap<String, Integer> capacity9 = new HashMap<>(capacity1);
        Flight flt9 = new Flight(num, ddate, adate, city[2], city[0], capacity9);
        flightDB.put(num, flt9);
        
        //Tokyo -> Vancouver
        num = city[3].substring(0,1) + city[0].substring(0, 1) + "010";
        ddate = sdf.parse("2022/10/31 14:45");
        adate = Database.addTime(ddate, "1500");
        HashMap<String, Integer> capacity10 = new HashMap<>(capacity1);
        Flight flt10 = new Flight(num, ddate, adate, city[3], city[0], capacity10);
        flightDB.put(num, flt10);
        
        //NewYork -> Vancouver
        num = city[4].substring(0,1) + city[0].substring(0, 1) + "011";
        ddate = sdf.parse("2022/11/03 19:29");
        adate = Database.addTime(ddate, "0626");
        HashMap<String, Integer> capacity11 = new HashMap<>(capacity1);
        Flight flt11 = new Flight(num, ddate, adate, city[4], city[0], capacity11);
        flightDB.put(num, flt11);
        
        //London -> Vancouver
        num = city[5].substring(0,1) + city[0].substring(0, 1) + "012";
        ddate = sdf.parse("2022/11/08 14:50");
        adate = Database.addTime(ddate, "1217");
        HashMap<String, Integer> capacity12 = new HashMap<>(capacity1);
        Flight flt12 = new Flight(num, ddate, adate, city[5], city[0], capacity12);
        flightDB.put(num, flt12);
        
        //Rome -> Vancouver
        num = city[6].substring(0,1) + city[0].substring(0, 1) + "013";
        ddate = sdf.parse("2022/11/18 06:55");
        adate = Database.addTime(ddate, "2420");
        HashMap<String, Integer> capacity13 = new HashMap<>(capacity1);
        Flight flt13 = new Flight(num, ddate, adate, city[6], city[0], capacity13);
        flightDB.put(num, flt13);
        
        //Paris -> Vancouver
        num = city[7].substring(0,1) + city[0].substring(0, 1) + "014";
        ddate = sdf.parse("2022/11/14 11:20");
        adate = Database.addTime(ddate, "1955");
        HashMap<String, Integer> capacity14 = new HashMap<>(capacity1);
        Flight flt14 = new Flight(num, ddate, adate, city[7], city[0], capacity14);
        flightDB.put(num, flt14);
        
        }catch(ParseException e){

        }

    }

}
