package pro.absolutne.lunchagator.importing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.absolutne.lunchagator.data.entity.DailyMenu;
import pro.absolutne.lunchagator.data.entity.Restaurant;
import pro.absolutne.lunchagator.data.repo.DailyMenuRepo;
import pro.absolutne.lunchagator.data.repo.RestaurantRepo;
import pro.absolutne.lunchagator.lunch.DailyMenuService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class DailyMenuImporter {

    @Autowired
    private DailyMenuService menuService;

    @Autowired
    private DailyMenuRepo menuRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    public void importDailyMenus() {
        // Exclude those which already have menus
        Collection<Restaurant> restaurants = restaurantRepo.findAll();

        Collection<Restaurant> withMenu = menuRepo.findByDay(LocalDate.now()).stream()
                .map(DailyMenu::getRestaurant)
                .collect(toList());

        long allCount = restaurants.size();
        restaurants.removeAll(withMenu);
        log.debug("Out of {} restaurant {} does not have daily menu imported",
                allCount, restaurants.size());

        restaurants.forEach(this::importDailyMenu);
    }

    private void importDailyMenu(Restaurant r) {
        log.debug("Importing daily menu for {} ", r);
        DailyMenu m = menuService.getMenu(r);
        log.debug("Menu retrieved successfully. Saving.", m);
        menuRepo.save(m);
    }
}
