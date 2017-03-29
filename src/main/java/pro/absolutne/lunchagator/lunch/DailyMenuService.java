package pro.absolutne.lunchagator.lunch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.absolutne.lunchagator.data.entity.*;
import pro.absolutne.lunchagator.data.repo.DailyMenuRepo;
import pro.absolutne.lunchagator.data.repo.RestaurantRepo;
import pro.absolutne.lunchagator.lunch.provider.ZomatoMenuProvider;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
public class DailyMenuService {

    @Autowired
    private RestaurantRepo restaurantRepo;

/*
    @Autowired
    private DailyMenuRepo menuRepo;

    @Autowired
    private ZomatoMenuProvider zomatoMenuProvider;*/

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


/*    public Collection<DailyMenu> getAll() {

        Collection<Restaurant> restaurants = restaurantRepo.findAll();

        Collection<DailyMenu> fromDb = menuRepo.findByDay(LocalDate.now());

        // Remove those which have menu
        Collection<Restaurant> restsWithDbMenu = fromDb.stream()
                .map(DailyMenu::getRestaurant)
                .collect(toList());

        restaurants.removeAll(restsWithDbMenu);

        if (restaurants.isEmpty())
            return fromDb; // Everything was in db already (or no restaurants)


        // Group by provider types
        Map<Class<? extends MenuSourceInfo>, List<Restaurant>> grouped =
                restaurants.stream()
                        .collect(groupingBy(r -> r.getMenuInfoSource().getClass()));

        Collection<Restaurant> fromZomato = grouped
                .getOrDefault(ZomatoMenuSourceInfo.class, Collections.emptyList());
        Collection<Restaurant> fromClass = grouped
                .getOrDefault(CustomMenuSourceInfo.class, Collections.emptyList());


        Collection<DailyMenu> result = new ArrayList<>();

        result.addAll(fromDb);
        // TODO don't do lazy call but do cron import at the beginning of the day
        result.addAll(getMenus(zomatoMenuProvider::findDailyMenus, fromZomato));
        result.addAll(getMenus(this::fromClass, fromClass));

        return result;
    }*/

/*    private Collection<DailyMenu> fromClass(Collection<Restaurant> restaurants) {
        return restaurants.stream()
                .map(this::getFromClassProvider)
                .collect(toList());
    }

    private DailyMenu getFromClassProvider(Restaurant r) {
        MenuProvider provider = menuProviders.stream()
                .filter(p -> {
                    CustomMenuSourceInfo source = (CustomMenuSourceInfo) r.getMenuInfoSource();
                    return p.getClass() == source.getProviderClass();
                })
                .findFirst().get();

        return provider.findDailyMenu(r);
    }


    private Collection<DailyMenu> getMenus(
            Function<Collection<Restaurant>, Collection<DailyMenu>> menusSource,
            Collection<Restaurant> restaurants) {

        if (restaurants.isEmpty())
            return Collections.emptyList();

        Collection<DailyMenu> menus = menusSource.apply(restaurants);
        return menuRepo.save(menus);
    }*/
}
