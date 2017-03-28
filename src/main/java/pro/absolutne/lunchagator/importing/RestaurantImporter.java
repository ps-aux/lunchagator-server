package pro.absolutne.lunchagator.importing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import pro.absolutne.lunchagator.data.entity.ClassMenuInfoSource;
import pro.absolutne.lunchagator.data.entity.Restaurant;
import pro.absolutne.lunchagator.data.entity.ZomatoMenuInfoSource;
import pro.absolutne.lunchagator.data.repo.RestaurantRepo;
import pro.absolutne.lunchagator.integration.zomato.ZomatoService;
import pro.absolutne.lunchagator.lunch.MenuProvider;
import pro.absolutne.lunchagator.mvc.BadRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class RestaurantImporter {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private ZomatoService zomato;

    @Autowired
    private List<MenuProvider> providers;

    private final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    public Collection<Restaurant> importZomatFromFile() throws IOException {
        log.info("Importing Zomato restaurants from file");
        InputStream is = this.getClass()
                .getResourceAsStream("/import-from-zomato.txt");

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        Collection<Restaurant> result = new ArrayList<>();

        while (true) {
            String line = br.readLine();
            if (line == null)
                break;
            if (line.startsWith("#") || line.trim().isEmpty())
                continue;
            int id = Integer.parseInt(line.trim());
            result.add(importZomatoResurant(id));
        }

        return result;
    }

    public Collection<Restaurant> importClassProviderRestaurantsFromFile() throws IOException {
        log.info("Importing class provider restaurants from file");

        InputStream is = this.getClass()
                .getResourceAsStream("/import-from-class-providers.yaml");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = yamlMapper.readValue(is, List.class);

        List<Restaurant> restaurants = list.stream().map(m -> {
            Restaurant r = yamlMapper.convertValue(m.get("restaurant"), Restaurant.class);

            String sourceClass = (String) m.get("menu-source");
            MenuProvider provider = providers.stream()
                    .filter(p -> p.getClass().getName().equals(sourceClass))
                    .findFirst().get();

            ClassMenuInfoSource source = new ClassMenuInfoSource();
            source.setProviderClass(provider.getClass());

            r.setMenuInfoSource(source);


            return r;

        }).collect(toList());


        return restaurantRepo.save(restaurants);
    }

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
}
