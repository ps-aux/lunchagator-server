package pro.absolutne.lunchgator.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.absolutne.lunchgator.data.entity.Location;
import pro.absolutne.lunchgator.data.repo.MenuProviderInfoRepo;
import pro.absolutne.lunchgator.data.repo.RestaurantRepo;
import pro.absolutne.lunchgator.importing.RestaurantImporter;
import pro.absolutne.lunchgator.integration.zomato.ZomatoService;
import pro.absolutne.lunchgator.lunch.DailyMenuService;

@CrossOrigin
@RestController
public class DebugController {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private MenuProviderInfoRepo menuProviderInfoRepo;

    @Autowired
    private ZomatoService zomato;

    @Autowired
    private DailyMenuService menuService;

    @Autowired
    private RestaurantImporter restaurantImporter;

    @GetMapping("go")
    public void bar() {
        System.out.println("some debug");
//        return restaurantRepo.findWithoutTodaysMenu();
    }

    @GetMapping("foo")
    public Object foo() {
        Location l = new Location();
        l.setLatitude(48.141525);
        l.setLongitude(17.110226);
        l.setAddress("Garwan office");
        return zomato.getRestaurantsByLocation(l, 1000);
    }

}
