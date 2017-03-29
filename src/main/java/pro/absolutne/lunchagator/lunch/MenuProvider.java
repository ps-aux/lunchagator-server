package pro.absolutne.lunchagator.lunch;

import pro.absolutne.lunchagator.data.entity.DailyMenu;
import pro.absolutne.lunchagator.data.entity.MenuProviderInfo;
import pro.absolutne.lunchagator.data.entity.Restaurant;

public interface MenuProvider<T extends MenuProviderInfo> {

    DailyMenu findDailyMenu(Restaurant r, T infoSource);

}
