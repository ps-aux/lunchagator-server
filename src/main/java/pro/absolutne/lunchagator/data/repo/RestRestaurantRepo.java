package pro.absolutne.lunchagator.data.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pro.absolutne.lunchagator.data.entity.Restaurant;

@RepositoryRestResource
public interface RestRestaurantRepo extends CrudRepository<Restaurant, Long> {

}
