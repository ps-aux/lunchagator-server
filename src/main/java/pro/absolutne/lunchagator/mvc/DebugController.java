package pro.absolutne.lunchagator.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.absolutne.lunchagator.data.entity.*;
import pro.absolutne.lunchagator.data.repo.MenuProviderInfoRepo;
import pro.absolutne.lunchagator.data.repo.RestaurantRepo;
import pro.absolutne.lunchagator.importing.RestaurantImporter;
import pro.absolutne.lunchagator.integration.zomato.ZomatoService;
import pro.absolutne.lunchagator.lunch.DailyMenuService;

import static java.util.stream.Collectors.toList;

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

    @PostMapping("go")
    public Restaurant bar() {
        throw new UnsupportedOperationException();
    }

    @GetMapping("foo")
    public Object foo() {
        Location l = new Location();
        l.setLatitude(48.141525);
        l.setLongitude(17.110226);
        l.setAddress("Garwan office");
        return zomato.getRestaurantsByLocation(l, 1000).stream()
                .filter(zomato::hasDailyMenu)
                .collect(toList());
    }

}
