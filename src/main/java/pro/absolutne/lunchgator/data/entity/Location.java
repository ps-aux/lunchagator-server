package pro.absolutne.lunchgator.data.entity;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Location {

    private String address;
    private double latitude;
    private double longitude;

    public Location(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location() {
    }
}
