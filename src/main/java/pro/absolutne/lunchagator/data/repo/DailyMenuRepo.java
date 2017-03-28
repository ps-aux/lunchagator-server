package pro.absolutne.lunchagator.data.repo;

import org.springframework.data.repository.CrudRepository;
import pro.absolutne.lunchagator.data.entity.DailyMenu;

public interface DailyMenuRepo extends CrudRepository<DailyMenu,Long>{
}
