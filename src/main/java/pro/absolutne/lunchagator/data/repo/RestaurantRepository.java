package pro.absolutne.lunchagator.data.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import pro.absolutne.lunchagator.lunch.Restaurant;

public interface RestaurantRepository extends
        MongoRepository<Restaurant, String>,
        RestaurantRepositoryCustom {

}
