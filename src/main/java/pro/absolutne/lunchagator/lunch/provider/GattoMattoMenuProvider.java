package pro.absolutne.lunchagator.lunch.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.absolutne.lunchagator.lunch.Location;
import pro.absolutne.lunchagator.lunch.Restaurant;

@Component
public class GattoMattoMenuProvider extends ZomatoMenuProvider {

    private final static Restaurant restaurant = new Restaurant(
            "Gatto Matto",
            new Location(
                    "Panská 17, 811 01 Bratislava - Staré Mesto",
                    48.142415,
                    17.107431));

    private final static int zomatoId = 16507679;


    @Autowired
    public GattoMattoMenuProvider(ZomatoService zomatoService) {
        super(restaurant, zomatoId, zomatoService);
    }
}
