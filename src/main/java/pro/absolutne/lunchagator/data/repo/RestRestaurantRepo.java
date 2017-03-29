package pro.absolutne.lunchagator.data.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import pro.absolutne.lunchagator.data.entity.Restaurant;

@CrossOrigin
@RepositoryRestResource
public interface RestRestaurantRepo extends CrudRepository<Restaurant, Long> {

}
