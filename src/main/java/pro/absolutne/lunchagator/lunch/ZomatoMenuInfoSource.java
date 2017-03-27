package pro.absolutne.lunchagator.lunch;

import lombok.Data;

@Data
public class ZomatoMenuInfoSource implements MenuInfoSource {

    private final int restaurantId;

}
