package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UserBookingService {

    private User user;  // any function can use this global user

    private List<User> userList;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String USERS_PATH = "app/src/main/java/ticket/booking/localDb/user.json";  // static means no one can change the path and final means no initialization will be done after that

    public UserBookingService(User user1) throws IOException // constructor
    {
        this.user = user1;
        File users = new File(USERS_PATH);
     userList=objectMapper.readValue(users, new TypeReference<List<User>>() {});  // readValue means to reads data from JSOn and converts it into a java object and TypeReference it tells the objectMapper that what kind of complex generic object want to create from JSON file

    }

    public Boolean loginUser(){
        
    }


}