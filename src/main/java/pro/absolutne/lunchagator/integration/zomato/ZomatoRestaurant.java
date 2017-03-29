package pro.absolutne.lunchagator.integration.zomato;

import lombok.Data;
import pro.absolutne.lunchagator.data.entity.Restaurant;

@Data
public class ZomatoRestaurant extends Restaurant {

    private long zomatoId;
}
