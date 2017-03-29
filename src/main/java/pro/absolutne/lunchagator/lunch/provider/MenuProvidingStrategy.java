package pro.absolutne.lunchagator.lunch.provider;

import pro.absolutne.lunchagator.data.entity.DailyMenu;
import pro.absolutne.lunchagator.data.entity.Restaurant;

public interface MenuProvidingStrategy {

    String getId();

    DailyMenu provide(Restaurant r);
}
