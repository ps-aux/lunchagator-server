package pro.absolutne.lunchgator.lunch.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.absolutne.lunchgator.data.entity.DailyMenu;
import pro.absolutne.lunchgator.data.entity.Restaurant;
import pro.absolutne.lunchgator.data.entity.ZomatoMenuSourceInfo;
import pro.absolutne.lunchgator.integration.zomato.ZomatoService;
import pro.absolutne.lunchgator.lunch.MenuProvider;

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
