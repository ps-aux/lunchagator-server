package pro.absolutne.lunchagator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.absolutne.lunchagator.data.entity.*;
import pro.absolutne.lunchagator.data.repo.RestaurantRepository;
import pro.absolutne.lunchagator.lunch.provider.ZomatoMenusProvider;
import pro.absolutne.lunchagator.mvc.BadRequestException;
import pro.absolutne.lunchagator.service.ZomatoService;

import java.io.*;
import java.util.Collection;
import java.util.Optional;

@CrossOrigin
@RequestMapping("menu")
@RestController
public class MyController {

//    @Autowired
//    private List<MenuProvider> providers;

    @Autowired
    private RestaurantRepository repo;

    @Autowired
    private ZomatoService zomato;

    @Autowired
    private ZomatoMenusProvider zomatoMenusProvider;

    @PostMapping("go")
    public Restaurant bar() {

        Restaurant restaurant = new Restaurant(
                "Gatto Matto",
                new Location(
                        "Panská 17, 811 01 Bratislava - Staré Mesto",
                        48.142415,
                        17.107431));
        ZomatoMenuInfoSource is = new ZomatoMenuInfoSource();
        is.setRestaurantId(16507679);
        repo.save(restaurant);
        restaurant.setMenuInfoSource(is);
        return repo.save(restaurant);
    }

    @GetMapping("zo")
    public Collection<Restaurant> zo() {
        return repo.findZomatoRestaurants();
    }


    @GetMapping("dbg")
    public String foo() {
        Restaurant r = zomato.getRestaurant(16517005);

        return r.toString();
    }


    @PutMapping("zomato-import")
    public Restaurant importZomatoResurant(@RequestParam int id) {
        if (!zomato.hasDailyMenu(id))
            throw new BadRequestException("Restaurant " + id +
                    " does not have daily menu @ Zomato");

        // Prevent creating duplicates
        Optional<Restaurant> opt = repo.findByZomatoId(id);
        if (opt.isPresent())
            return opt.get();

        Restaurant r = zomato.getRestaurant(id);
        ZomatoMenuInfoSource is = new ZomatoMenuInfoSource();
        is.setRestaurantId(id);
        r.setMenuInfoSource(is);
        r = repo.save(r);

        return r;
    }

    @PutMapping("zomato-file-import")
    public String importZomatFromFile() throws IOException {
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

    @GetMapping("dbg2")
    public Iterable<Restaurant> foo2() {
        return repo.findZomatoRestaurants();
    }

    @GetMapping("today")
    public Collection<DailyMenu> todaysMenu() {
        return zomatoMenusProvider.findDailyMenus(repo.findZomatoRestaurants());
    }
}
