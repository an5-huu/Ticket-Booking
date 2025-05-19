package ticket.booking.entities;

import java.util.List;

public class User {
    private String name;  // Using Private because no one can access this

    private String password;

    private String hashedPassword;

    private List<Ticket> ticketsBooked;  // create a class for Ticket and it's a List

    private String userId;

}
