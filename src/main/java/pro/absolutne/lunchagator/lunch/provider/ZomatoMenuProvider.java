package pro.absolutne.lunchagator.lunch.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.absolutne.lunchagator.data.entity.DailyMenu;
import pro.absolutne.lunchagator.data.entity.Restaurant;
import pro.absolutne.lunchagator.data.entity.ZomatoMenuSourceInfo;
import pro.absolutne.lunchagator.integration.zomato.ZomatoService;
import pro.absolutne.lunchagator.lunch.MenuProvider;

import java.time.LocalDate;

@Component
public class ZomatoMenuProvider implements MenuProvider<ZomatoMenuSourceInfo> {

    @Autowired
    private ZomatoService zomato;

    public DailyMenu findDailyMenu(Restaurant r, ZomatoMenuSourceInfo infoSource) {
        DailyMenu m = new DailyMenu();
        m.setDay(LocalDate.now());
        m.setItems(zomato.getDishes(infoSource.getZomatoId()));
        m.setRestaurant(r);

        return m;
    }
}
