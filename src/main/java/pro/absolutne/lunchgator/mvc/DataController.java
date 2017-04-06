package pro.absolutne.lunchgator.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.absolutne.lunchgator.data.entity.Restaurant;
import pro.absolutne.lunchgator.importing.DailyMenuImporter;
import pro.absolutne.lunchgator.importing.RestaurantImporter;

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

    @Autowired
    private DailyMenuImporter menuImporter;

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

    @PutMapping("import/run-menus-import")
    public void importDailyMenus() throws IOException {
        menuImporter.importDailyMenus();
    }

    @PutMapping("import/custom-strategy-restaurants")
    public Collection<Restaurant> importCustomStrategies() throws IOException {
        return restaurantImporter.importCustomStrategies();
    }

}
