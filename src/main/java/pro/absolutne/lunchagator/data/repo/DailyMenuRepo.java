package pro.absolutne.lunchagator.data.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pro.absolutne.lunchagator.data.entity.DailyMenu;
import pro.absolutne.lunchagator.data.entity.Restaurant;

import java.time.LocalDate;
import java.util.Collection;

public interface DailyMenuRepo extends CrudRepository<DailyMenu, Long> {


    @Query("SELECT m from DailyMenu m where m.restaurant in (:restaurants) and m.day = :day")
    Collection<DailyMenu> findByDayAndRestaurants(@Param("day") LocalDate day,
                                                  @Param("restaurants") Collection<Restaurant> restaurants);
}
