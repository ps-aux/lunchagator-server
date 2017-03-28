package pro.absolutne.lunchagator.lunch;

import pro.absolutne.lunchagator.data.entity.DailyMenu;
import pro.absolutne.lunchagator.data.entity.Restaurant;

public interface MenuProvider {

    DailyMenu findDailyMenu(Restaurant r);

}
