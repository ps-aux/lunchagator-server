package pro.absolutne.lunchagator.lunch.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.absolutne.lunchagator.data.entity.DailyMenu;
import pro.absolutne.lunchagator.data.entity.Restaurant;
import pro.absolutne.lunchagator.data.entity.ZomatoMenuInfoSource;
import pro.absolutne.lunchagator.service.ZomatoService;

import java.time.LocalDate;
import java.util.Collection;

import static java.util.stream.Collectors.toList;

@Component
public class ZomatoMenusProvider {

    private final ZomatoService zomato;

    @Autowired
    public ZomatoMenusProvider(ZomatoService zomato) {
        this.zomato = zomato;
    }


    public Collection<DailyMenu> findDailyMenus(Collection<Restaurant> rest) {
        return rest.stream()
                .map(this::getDailyMenu)
                .collect(toList());
    }

    private DailyMenu getDailyMenu(Restaurant r) {
        if (!(r.getMenuInfoSource() instanceof ZomatoMenuInfoSource))
            throw new IllegalArgumentException("Restaurant does not have Zomato info source");

        ZomatoMenuInfoSource info = (ZomatoMenuInfoSource) r.getMenuInfoSource();
        return new DailyMenu(LocalDate.now(),
                r, zomato.getDishes(info.getRestaurantId()));

    }
}
