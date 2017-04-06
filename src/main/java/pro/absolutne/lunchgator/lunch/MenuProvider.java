package pro.absolutne.lunchgator.lunch;

import pro.absolutne.lunchgator.data.entity.DailyMenu;
import pro.absolutne.lunchgator.data.entity.MenuProviderInfo;
import pro.absolutne.lunchgator.data.entity.Restaurant;

public interface MenuProvider<T extends MenuProviderInfo> {

    DailyMenu findDailyMenu(Restaurant r, T infoSource);

}
