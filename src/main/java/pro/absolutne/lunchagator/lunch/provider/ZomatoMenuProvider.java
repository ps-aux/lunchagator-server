package pro.absolutne.lunchagator.lunch.provider;

import pro.absolutne.lunchagator.lunch.DailyMenu;
import pro.absolutne.lunchagator.lunch.MenuProvider;
import pro.absolutne.lunchagator.lunch.Restaurant;

import java.time.LocalDate;

public abstract class ZomatoMenuProvider implements MenuProvider {

    private ZomatoService zomato;

    private final int zomatoId;
    private final Restaurant restaurant;

    public ZomatoMenuProvider(Restaurant restaurant,
                              int zomatoId,
                              ZomatoService zomato) {
        this.zomatoId = zomatoId;
        this.restaurant = restaurant;
        this.zomato = zomato;
    }

    @Override
    public DailyMenu findMenuFor() {
        return new DailyMenu(LocalDate.now(),
                restaurant,
                zomato.getDishes(zomatoId));
    }
}
