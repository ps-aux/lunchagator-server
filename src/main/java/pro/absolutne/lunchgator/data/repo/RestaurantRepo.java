package pro.absolutne.lunchgator.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import pro.absolutne.lunchgator.data.entity.Restaurant;

import java.util.Optional;


@CrossOrigin
@RepositoryRestResource
public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r from Restaurant r where r.menuProviderInfo.zomatoId = :id")
    Optional<Restaurant> findByZomatoId(@Param("id") int id);
}
