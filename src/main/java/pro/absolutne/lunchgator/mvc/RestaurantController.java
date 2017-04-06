package pro.absolutne.lunchgator.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.absolutne.lunchgator.data.entity.Restaurant;
import pro.absolutne.lunchgator.data.repo.RestaurantRepo;
import pro.absolutne.lunchgator.importing.RestaurantImporter;

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
