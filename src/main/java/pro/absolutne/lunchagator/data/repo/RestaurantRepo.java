package pro.absolutne.lunchagator.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.absolutne.lunchagator.data.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepo extends JpaRepository<Restaurant, String> {

    @Query("SELECT r from Restaurant r where r.menuProviderInfo.zomatoId = :id")
    Optional<Restaurant> findByZomatoId(@Param("id") int id);
}
