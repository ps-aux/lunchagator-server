package pro.absolutne.lunchagator.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pro.absolutne.lunchagator.data.entity.Restaurant;

import java.util.Collection;
import java.util.Optional;

public interface RestaurantRepo extends
        JpaRepository<Restaurant, String> {

    @Query("SELECt r from Restaurant r" +
            " JOIN r.menuInfoSource s where TYPE(s) = ZomatoMenuInfoSource")
    Collection<Restaurant> findZomatoRestaurants();

    @Query("SELECT r from Restaurant r where r.menuInfoSource.zomatoId = :id")
    Optional<Restaurant> findByZomatoId(@Param("id") int id);

}
