package pro.absolutne.lunchgator.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.absolutne.lunchgator.data.entity.DailyMenu;
import pro.absolutne.lunchgator.data.entity.Restaurant;
import pro.absolutne.lunchgator.data.repo.DailyMenuRepo;
import pro.absolutne.lunchgator.importing.RestaurantImporter;

import java.time.LocalDate;
import java.util.Collection;

@Slf4j
@CrossOrigin
@RequestMapping("menu")
@RestController
public class MenuController {

    @Autowired
    private DailyMenuRepo menuRepo;

    @Autowired
    private RestaurantImporter restaurantImporter;


    @PutMapping("zomato-import")
    public Restaurant importZomatoResurant(@RequestParam("id") int id) {
        return restaurantImporter.importZomatoResurant(id);
    }

    @GetMapping("today")
    public Collection<DailyMenu> todaysMenu() {
        return menuRepo.findByDay(LocalDate.now());
    }



}
