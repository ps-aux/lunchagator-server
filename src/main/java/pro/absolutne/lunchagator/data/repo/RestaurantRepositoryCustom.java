package pro.absolutne.lunchagator.data.repo;

import pro.absolutne.lunchagator.lunch.Restaurant;

import java.util.Collection;
import java.util.Optional;

public interface RestaurantRepositoryCustom {

    Collection<Restaurant> findZomatoRestaurants();

    Optional<Restaurant> findByZomatoId(int id);
}
