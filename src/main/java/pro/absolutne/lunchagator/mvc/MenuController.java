package pro.absolutne.lunchagator.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.absolutne.lunchagator.data.entity.*;
import pro.absolutne.lunchagator.data.repo.DailyMenuRepo;
import pro.absolutne.lunchagator.data.repo.MenuInfoSourceRepo;
import pro.absolutne.lunchagator.data.repo.RestaurantRepository;
import pro.absolutne.lunchagator.lunch.provider.PlzenskaMenuProvider;
import pro.absolutne.lunchagator.lunch.provider.ZomatoMenusProvider;
import pro.absolutne.lunchagator.integration.zomato.ZomatoService;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Slf4j
@CrossOrigin
@RequestMapping("menu")
@RestController
public class MenuController {

//    @Autowired
//    private List<MenuProvider> providers;

    @Autowired
    private RestaurantRepository restaurantRepo;

    @Autowired
    private DailyMenuRepo menuRepo;

    @Autowired
    private MenuInfoSourceRepo menuInfoSourceRepo;

    @Autowired
    private ZomatoService zomato;

    @Autowired
    private ZomatoMenusProvider zomatoMenusProvider;

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


    @PutMapping("zomato-import")
    public Restaurant importZomatoResurant(@RequestParam int id) {
        // Prevent creating duplicates
        Optional<Restaurant> opt = restaurantRepo.findByZomatoId(id);
        if (opt.isPresent())
            return opt.get();

        log.debug("Importing Zomato restaurant {}", id);
        if (!zomato.hasDailyMenu(id))
            throw new BadRequestException("Restaurant " + id +
                    " does not have daily menu @ Zomato");


        Restaurant r = zomato.getRestaurant(id);
        ZomatoMenuInfoSource is = new ZomatoMenuInfoSource();
        is.setZomatoId(id);
        r.setMenuInfoSource(is);
        r = restaurantRepo.save(r);

        return r;
    }

    @PutMapping("zomato-file-import")
    public String importZomatFromFile() throws IOException {
        log.info("Importing Zomato restaurants from file");
        InputStream is = this.getClass()
                .getResourceAsStream("/import-from-zomato.txt");

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        while (true) {
            String line = br.readLine();
            if (line == null)
                break;
            if (line.startsWith("#") || line.trim().isEmpty())
                continue;
            int id = Integer.parseInt(line.trim());
            importZomatoResurant(id);
        }

        return "Done";
    }

    @GetMapping("has-menu")
    public boolean hasZomatoMenu(@RequestParam int id) {
        return zomato.hasDailyMenu(id);
    }

    @GetMapping("today")
    public Collection<DailyMenu> todaysMenu() {
        Collection<Restaurant> rests = restaurantRepo.findZomatoRestaurants();

        return doGetMenus(rests);
    }

    private Collection<DailyMenu> doGetMenus(Collection<Restaurant> restaurants) {
        if (restaurants.isEmpty())
            return Collections.emptyList();

        // Find in DB first
        Collection<DailyMenu> dbMenus = menuRepo.findByDayAndRestaurants(
                LocalDate.now(),
                restaurants);


        // Remove those which have menu
        Collection<Restaurant> haveMenu = dbMenus.stream()
                .map(DailyMenu::getRestaurant)
                .collect(toList());

        restaurants.removeAll(haveMenu);

        if (restaurants.isEmpty())
            return dbMenus;


        // Pull the missing dbMenus from Zomato
        Collection<DailyMenu> newMenus =
                zomatoMenusProvider.findDailyMenus(restaurants);

        menuRepo.save(newMenus);

        Collection<DailyMenu> res = new ArrayList<>();
        res.addAll(newMenus);
        res.addAll(dbMenus);

        return res;
    }
}
