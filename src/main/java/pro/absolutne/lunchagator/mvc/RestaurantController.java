package pro.absolutne.lunchagator.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.absolutne.lunchagator.data.entity.DailyMenu;
import pro.absolutne.lunchagator.data.entity.Restaurant;
import pro.absolutne.lunchagator.data.repo.DailyMenuRepo;
import pro.absolutne.lunchagator.data.repo.RestaurantRepo;
import pro.absolutne.lunchagator.importing.RestaurantImporter;

import java.time.LocalDate;
import java.util.Collection;

@Slf4j
@CrossOrigin
@RequestMapping("jada")
@RestController
public class RestaurantController {

    @Autowired
    private RestaurantRepo repo;

    @Autowired
    private RestaurantImporter restaurantImporter;


    @PutMapping("import-zomato")
    public Restaurant importZomatoResurant(@RequestParam("id") int id) {
        return restaurantImporter.importZomatoResurant(id);
    }

}
