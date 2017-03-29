package pro.absolutne.lunchagator.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.absolutne.lunchagator.data.entity.DailyMenu;
import pro.absolutne.lunchagator.data.entity.Restaurant;
import pro.absolutne.lunchagator.importing.RestaurantImporter;
import pro.absolutne.lunchagator.integration.zomato.ZomatoService;
import pro.absolutne.lunchagator.lunch.DailyMenuService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@CrossOrigin
@RequestMapping("data")
@RestController
public class DataController {

    @Autowired
    private RestaurantImporter restaurantImporter;

    @PutMapping("import/restaurants")
    public Collection<Restaurant> imporAllRestaurants() throws IOException {
        Collection<Restaurant> all = new ArrayList<>();
        all.addAll(importZomatFromFile());
        all.addAll(importCustomStrategies());

        return all;
    }

    @PutMapping("import/zomato-restaurants")
    public Collection<Restaurant> importZomatFromFile() throws IOException {
        return restaurantImporter.importZomatFromFile();
    }

    @PutMapping("import/custom-strategy-restaurants")
    public Collection<Restaurant> importCustomStrategies() throws IOException {
        return restaurantImporter.importCustomStrategies();
    }

}
