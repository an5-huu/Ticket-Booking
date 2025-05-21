package ticket.booking;

import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.services.UserBookingService;
import ticket.booking.util.UserServiceUtil;

import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) {
        System.out.println("Running Train Booking System");
        Scanner sc = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService;
        Train trainSelectedForBooking = null;
        boolean isLoggedIn = false;

        try {
            userBookingService = new UserBookingService();  // user just entered not login yet
        } catch (IOException ex) {
            System.out.println("Running train Booking System");
            return;
        }

        while (option != 7) {
            System.out.println("Choose option");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App");
            option = sc.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Enter the username to signup");
                    String nameToSignUp = sc.next();
                    System.out.println("Enter the password to signup");
                    String passwordToSignUp = sc.next();
                    User userToSignup = new User(nameToSignUp, passwordToSignUp, UserServiceUtil.hashPassword(passwordToSignUp), new ArrayList<>(), UUID.randomUUID().toString());
                    userBookingService.signUp(userToSignup);
                    break;

                case 2:
                    System.out.println("Enter the username to Login");
                    String nameToLogin = sc.next();
                    System.out.println("Enter the password to login");  // Fixed text
                    String passwordToLogin = sc.next();
                    User userToLogin = new User(nameToLogin, passwordToLogin, UserServiceUtil.hashPassword(passwordToLogin), new ArrayList<>(), UUID.randomUUID().toString());
                    try {
                        userBookingService = new UserBookingService(userToLogin);
                        isLoggedIn = true;
                        System.out.println("Login successful!");
                    } catch (IOException ex) {
                        System.out.println("Login failed: " + ex.getMessage());
                        return;
                    }
                    break;

                case 3:
                    if (!isLoggedIn) {
                        System.out.println("Please login first!");
                        break;
                    }
                    System.out.println("Fetching your bookings");
                    userBookingService.fetchBooking();
                    break;

                case 4:
                    if (!isLoggedIn) {
                        System.out.println("Please login first!");
                        break;
                    }
                    System.out.println("Type your source station");
                    String source = sc.next();
                    System.out.println("Type your destination station");
                    String dest = sc.next();
                    List<Train> trains = userBookingService.getTrains(source, dest);
                    if (trains.isEmpty()) {
                        System.out.println("There are no trains for this route");
                        break;
                    }
                    int index = 1;
                    for (Train t : trains) {
                        System.out.println(index + " Train id : " + t.getTrainId());
                        for (Map.Entry<String, String> entry : t.getStationTimes().entrySet()) {
                            System.out.println("station " + entry.getKey() + " time: " + entry.getValue());
                        }
                        index++;
                    }
                    System.out.println("Select a train by typing 1,2,3...");
                    int selectedTrain = sc.nextInt();
                    if (selectedTrain < 1 || selectedTrain > trains.size()) {
                        System.out.println("Invalid train selection!");
                        break;
                    }
                    trainSelectedForBooking = trains.get(selectedTrain - 1);
                    break;

                case 5:
                    if (!isLoggedIn) {
                        System.out.println("Please login first!");
                        break;
                    }
                    if (trainSelectedForBooking == null) {
                        System.out.println("Please select a train first!");
                        break;
                    }
                    System.out.println("Select a seat out of these seats");
                    List<List<Integer>> seats = userBookingService.fetchSeats(trainSelectedForBooking);
                    for (List<Integer> row : seats) {
                        for (Integer val : row) {
                            System.out.print(val + " ");
                        }
                        System.out.println();
                    }
                    System.out.println("Select the seat by typing the row and column");
                    System.out.println("Enter the row");
                    int row = sc.nextInt();
                    System.out.println("Enter the column");
                    int col = sc.nextInt();
                    System.out.println("Booking your seat....");
                    Boolean booked = userBookingService.bookSeat(trainSelectedForBooking, row, col);
                    if (booked) {
                        System.out.println("Booked! Enjoy your journey");
                    } else {
                        System.out.println("Can't book this seat");
                    }
                    break;

                case 6:
                    if (!isLoggedIn) {
                        System.out.println("Please login first!");
                        break;
                    }
                    System.out.println("Cancelling your current booking...");
                    userBookingService.cancelBooking();
                    break;

                case 7:
                    System.out.println("Exiting the app");
                    break;

                default:
                    System.out.println("Wrong choice. Please select between 1-7");
                    break;
            }
        }
        sc.close();
    }
}