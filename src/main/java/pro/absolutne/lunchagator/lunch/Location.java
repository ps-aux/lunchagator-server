package pro.absolutne.lunchagator.lunch;

import lombok.Data;

@Data
public class Location {

    private final String address;
    private final double latitude;
    private final double longitude;
}
