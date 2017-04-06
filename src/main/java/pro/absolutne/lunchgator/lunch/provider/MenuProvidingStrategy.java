package pro.absolutne.lunchgator.lunch.provider;

import pro.absolutne.lunchgator.data.entity.DailyMenu;
import pro.absolutne.lunchgator.data.entity.Restaurant;

public interface MenuProvidingStrategy {

    String getId();

    DailyMenu provide(Restaurant r);
}
