package ticket.booking.entities;

import java.sql.Time;
import java.util.List;
import java.util.Map;

public class Train {

    private String trainId;

    private String trainNo;

    private List <List<Integer>> seats;  // 2D Matrix

    private Map<String, Time> stationTimes;  // deserialize this stationTimes with station_times that is in train.json

    private List<String> stations; // stoppage in b/w a -> b
}
