package pro.absolutne.lunchgator.importing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import pro.absolutne.lunchgator.data.entity.CustomProviderInfo;
import pro.absolutne.lunchgator.data.entity.Restaurant;
import pro.absolutne.lunchgator.data.entity.ZomatoMenuSourceInfo;
import pro.absolutne.lunchgator.data.repo.RestaurantRepo;
import pro.absolutne.lunchgator.integration.zomato.ZomatoService;
import pro.absolutne.lunchgator.lunch.provider.MenuProvidingStrategy;
import pro.absolutne.lunchgator.mvc.BadRequestException;

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
    private List<MenuProvidingStrategy> strategies;

    private final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    public Collection<Restaurant> importZomatFromFile() throws IOException {
        log.info("Importing Zomato restaurants from file");
        InputStream is = this.getClass()
                .getResourceAsStream("/zomato-restaurants.txt");

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

    public Collection<Restaurant> importCustomStrategies() throws IOException {
        log.info("Importing custom providing strategies from file");

        InputStream is = this.getClass()
                .getResourceAsStream("/custom-strategies.yaml");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = yamlMapper.readValue(is, List.class);

        List<Restaurant> restaurants = list.stream().map(m -> {
            Restaurant r = yamlMapper.convertValue(m.get("restaurant"), Restaurant.class);

            String strategyId = (String) m.get("strategy-id");

            // Just Check if strategy exists (if not we call get() on empty optional)
            MenuProvidingStrategy provider = strategies.stream()
                    .filter(s -> s.getId().equals(strategyId))
                    .findFirst().get();

            CustomProviderInfo source = new CustomProviderInfo();
            source.setStrategyId(strategyId);

            r.setMenuProviderInfo(source);

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
        ZomatoMenuSourceInfo is = new ZomatoMenuSourceInfo();
        is.setZomatoId(id);

        r.setMenuProviderInfo(is);
        r = restaurantRepo.save(r);

        return r;
    }
}
