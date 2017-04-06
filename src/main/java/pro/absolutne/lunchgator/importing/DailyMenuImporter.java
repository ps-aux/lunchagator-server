package pro.absolutne.lunchgator.importing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.absolutne.lunchgator.data.entity.DailyMenu;
import pro.absolutne.lunchgator.data.entity.Restaurant;
import pro.absolutne.lunchgator.data.repo.DailyMenuRepo;
import pro.absolutne.lunchgator.data.repo.RestaurantRepo;
import pro.absolutne.lunchgator.lunch.DailyMenuService;

import java.time.LocalDate;
import java.util.Collection;

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

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void doScheduledImport() {
        log.debug("Running scheduled import job");
        if (isImportNeeded())
            importDailyMenus();
        else
            log.debug("Import not needed");
    }

    public void importDailyMenus() {
        log.debug("Importing daily menus");

        Collection<Restaurant> rests = getRestaurantsWithouDailyMenu();

        // Exclude those which already have menus
        log.debug("Daily menus of {} restaurants will be imported", rests.size());

        rests.forEach(this::importDailyMenu);
    }

    private boolean isImportNeeded() {
        return !getRestaurantsWithouDailyMenu().isEmpty();
    }

    private Collection<Restaurant> getRestaurantsWithouDailyMenu() {
        Collection<Restaurant> restaurants = restaurantRepo.findAll();

        Collection<Restaurant> withMenu = menuRepo.findByDay(LocalDate.now()).stream()
                .map(DailyMenu::getRestaurant)
                .collect(toList());

        restaurants.removeAll(withMenu);

        return restaurants;
    }

    @Async
    private void importDailyMenu(Restaurant r) {
        // Forgive exceptions - next scheduled import job will do it
        try {
            log.debug("Importing daily menu for {} ", r);
            DailyMenu m = menuService.getMenu(r);
            log.debug("Menu {} retrieved successfully. Saving.", m);
            menuRepo.save(m);
        } catch (Exception e) {
            log.error("Importing of daily menu for {} failed", r, e);
        }
    }
}
