package pro.absolutne.lunchagator.data.repo;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import pro.absolutne.lunchagator.lunch.Restaurant;
import pro.absolutne.lunchagator.lunch.ZomatoMenuInfoSource;

import java.util.Collection;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Slf4j
public class RestaurantRepositoryImpl implements RestaurantRepositoryCustom {

    @Autowired
    private MongoTemplate mongo;

    private final Query withZomatoInfoSource = query(where("menuInfoSource._class")
            .is(ZomatoMenuInfoSource.class.getName()));

    @Override
    public Collection<Restaurant> findZomatoRestaurants() {
        log.trace("Finding restaurants with Zomato info source");
        Collection<Restaurant> res = mongo.find(withZomatoInfoSource, Restaurant.class);
        log.trace("Found {}", res);

        return res;
    }

    @Override
    public Optional<Restaurant> findByZomatoId(int id) {
        log.trace("Finding restaurant with Zomato id {}", id);
        Criteria zomatoIdCriteria = where("menuInfoSource.restaurantId");
        Collection<Restaurant> res = mongo.find(query(zomatoIdCriteria.is(id)), Restaurant.class);

        if (res.size() > 1)
            throw new IllegalStateException("Found more than one result");

        Optional<Restaurant> r = res.isEmpty() ?
                Optional.empty() :
                Optional.of(res.iterator().next());

        log.trace("Found {}", r);

        return r;
    }
}
