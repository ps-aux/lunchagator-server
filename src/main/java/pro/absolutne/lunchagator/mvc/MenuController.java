package pro.absolutne.lunchagator.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.absolutne.lunchagator.data.entity.*;
import pro.absolutne.lunchagator.data.repo.MenuInfoSourceRepo;
import pro.absolutne.lunchagator.data.repo.RestaurantRepo;
import pro.absolutne.lunchagator.importing.RestaurantImporter;
import pro.absolutne.lunchagator.integration.zomato.ZomatoService;
import pro.absolutne.lunchagator.lunch.DailyMenuService;
import pro.absolutne.lunchagator.lunch.provider.PlzenskaMenuProvider;

import java.io.IOException;
import java.util.Collection;

@Slf4j
@CrossOrigin
@RequestMapping("menu")
@RestController
public class MenuController {

    @Autowired
    private ZomatoService zomato;

    @Autowired
    private DailyMenuService menuService;

    @Autowired
    private RestaurantImporter restaurantImporter;

    @PutMapping("zomato-import")
    public Restaurant importZomatoResurant(@RequestParam int id) {
        return restaurantImporter.importZomatoResurant(id);
    }

    @PutMapping("zomato-file-import")
    public Collection<Restaurant> importZomatFromFile() throws IOException {
        return restaurantImporter.importZomatFromFile();
    }


    @PutMapping("class-file-import")
    public Collection<Restaurant> importClassProviderRestaurantsFromFile() throws IOException {
        return restaurantImporter.importClassProviderRestaurantsFromFile();
    }

    @GetMapping("has-menu")
    public boolean hasZomatoMenu(@RequestParam int id) {
        return zomato.hasDailyMenu(id);
    }

    @GetMapping("today")
    public Collection<DailyMenu> todaysMenu() {
        return menuService.getAll();
    }

}
