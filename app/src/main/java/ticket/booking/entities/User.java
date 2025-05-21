package ticket.booking.entities;



import java.util.List;

public class User {
    private String name;  // Using Private because no one can access this

    private String password;

    private String hashedPassword;

    private List<Ticket> ticketsBooked;  // create a class for Ticket and it's a List

    private String userId;

    public User(String name, String password, String hashedPassword, List<Ticket> ticketsBooked , String userId){  //constructor
        this.name=name;
        this.password=password;
        this.hashedPassword=hashedPassword;
        this.ticketsBooked=ticketsBooked;
        this.userId=userId;
    }

    public User() {} // default constructor, if we didn't make any object then we didn't pass anything

    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }
    public String getHashedPassword(){
        return hashedPassword;
    }
    public List<Ticket> getTicketsBooked() {
        return ticketsBooked;
    }
    public void printTickets(){
        for (int i=0; i< ticketsBooked.size(); i++){
            System.out.println(ticketsBooked.get(i).getTicketInfo());
        }
    }
    public String getUserId(){
        return userId;
    }

    public void setName(String name){
        this.name= name;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setHashedPassword(String hashedPassword){
        this.hashedPassword=hashedPassword;
    }
    public void setTicketsBooked(List<Ticket> ticketsBooked){
        this.ticketsBooked=ticketsBooked;
    }
    public void setUserId(String userId){
        this.userId=userId;
    }

}
