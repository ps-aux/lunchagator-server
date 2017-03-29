package pro.absolutne.lunchagator.lunch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.absolutne.lunchagator.data.entity.DailyMenu;
import pro.absolutne.lunchagator.data.entity.Restaurant;
import pro.absolutne.lunchagator.data.repo.RestaurantRepo;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class DailyMenuService {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private List<MenuProvider> menuProviders;

    public Collection<DailyMenu> getAll() {
        return restaurantRepo.findAll()
                .stream()
                .map(this::getMenu)
                .collect(toList());
    }

    public DailyMenu getMenu(Restaurant r) {
        MenuProvider provider = menuProviders.stream()
                .filter(p ->
                        p.getClass()
                                .equals(
                                        r.getMenuProviderInfo().getMenuProviderImpl()))
                .findFirst().get();

        //noinspection unchecked
        return provider.findDailyMenu(r, r.getMenuProviderInfo());
    }

}
