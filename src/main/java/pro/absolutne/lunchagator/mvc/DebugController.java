package pro.absolutne.lunchagator.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.absolutne.lunchagator.data.entity.*;
import pro.absolutne.lunchagator.data.repo.MenuInfoSourceRepo;
import pro.absolutne.lunchagator.data.repo.RestaurantRepo;
import pro.absolutne.lunchagator.importing.RestaurantImporter;
import pro.absolutne.lunchagator.integration.zomato.ZomatoService;
import pro.absolutne.lunchagator.lunch.DailyMenuService;
import pro.absolutne.lunchagator.lunch.provider.PlzenskaMenuProvider;

@CrossOrigin
@RestController
public class DebugController {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private MenuInfoSourceRepo menuInfoSourceRepo;

    @Autowired
    private ZomatoService zomato;

    @Autowired
    private DailyMenuService menuService;

    @Autowired
    private RestaurantImporter restaurantImporter;

    @PostMapping("go")
    public Restaurant bar() {

        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test rest");
        restaurant.setLocation(
                new Location(
                        "Panská 17, 811 01 Bratislava - Staré Mesto",
                        48.142415,
                        17.107431));

        ZomatoMenuInfoSource is = new ZomatoMenuInfoSource();
        is.setZomatoId(16507679);
        restaurantRepo.save(restaurant);
        restaurant.setMenuInfoSource(is);
        return restaurantRepo.save(restaurant);
    }


    @GetMapping("dbg")
    public DailyMenu foo(PlzenskaMenuProvider provider) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Plzenska");
        restaurant.setLocation(
                new Location(
                        "Panská 17, 811 01 Bratislava - Staré Mesto",
                        48.142415,
                        17.107431));

        return provider.findDailyMenu(restaurant);
    }

    @GetMapping("dbg2")
    public ClassMenuInfoSource foo2(PlzenskaMenuProvider provider) {
        ClassMenuInfoSource s = new ClassMenuInfoSource();

        s.setProviderClass(PlzenskaMenuProvider.class);

        return menuInfoSourceRepo.save(s);
    }


}
