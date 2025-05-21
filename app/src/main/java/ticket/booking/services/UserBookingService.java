package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserBookingService {

    private User user;  // any function can use this global user

    private List<User> userList;

    private String ticketId;

    private ObjectMapper objectMapper = new ObjectMapper();

    private final String USER_PATH = "src/main/java/ticket/booking/localDb/user.json";  // static means no one can change the path and final means no initialization will be done after that

    public UserBookingService(User user1) throws IOException { // constructor
        this.user = user1;
        loadusers();
    }

    public UserBookingService() throws IOException {
        loadusers();
    }

    public List<User> loadusers() throws IOException {
        File users = new File(USER_PATH);
        if (!users.exists()) {
            users.getParentFile().mkdir();
            objectMapper.writeValue(users, new ArrayList<User>());
        }
        return objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }


    public Boolean loginUser() {
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1) {
        try {
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        } catch (IOException ex) {
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        File userFile = new File(USER_PATH);
        objectMapper.writeValue(userFile, userList);
    }

    public void fetchBooking() {
        user.printTickets();
    }

    public Boolean cancelBooking() {
        try {
            // Find the user in the userList to ensure we're modifying the correct data
            Optional<User> currentUser = userList.stream()
                    .filter(u -> u.getName().equals(user.getName()))
                    .findFirst();

            if (currentUser.isPresent()) {
                // Get the tickets list from the user
                List<Ticket> tickets = currentUser.get().getTicketsBooked();

                // Find and remove the ticket with matching ID
                boolean removed = tickets.removeIf(ticket -> ticket.getTicketId().equals(ticketId));

                if (removed) {
                    // Update the tickets in user object
                    user.setTicketsBooked(tickets);

                    // Save the updated user list to file
                    saveUserListToFile();
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        } catch (IOException ex) {
            return Boolean.FALSE;
        }
    }

    public List<Train> getTrains(String source, String destination) {
        try {
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination);
        }
        catch (IOException ex){
            return new ArrayList<>();
        }
    }

    public List<List<Integer>> fetchSeats(Train trainSelectedForBooking) {
        try {
            List<List<Integer>> seats = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                List<Integer> row = new ArrayList<>();
                for (int j = 0; j < 6; j++) {
                    row.add(0);  // 0 represents empty seat
                }
                seats.add(row);
            }
            return seats;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public Boolean bookSeat(Train trainSelectedForBooking, int row, int col) {
        try {
            // Create a new ticket
            Ticket ticket = new Ticket(
                    UUID.randomUUID().toString(),
                    user.getUserId(),
                    trainSelectedForBooking.getStationTimes().keySet().stream().findFirst().get(),
                    trainSelectedForBooking.getStationTimes().keySet().stream().reduce((first, second) -> second).get(),
                    new String().toString(),
                    trainSelectedForBooking
            );

            // Find the current user in the list
            Optional<User> currentUser = userList.stream()
                    .filter(u -> u.getName().equals(user.getName()))
                    .findFirst();

            if (currentUser.isPresent()) {
                // Get current tickets and add new ticket
                List<Ticket> tickets = currentUser.get().getTicketsBooked();
                tickets.add(ticket);

                // Update the tickets in the user object
                user.setTicketsBooked(tickets);

                // Save to file
                saveUserListToFile();
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (IOException ex) {
            return Boolean.FALSE;
        }
    }
}