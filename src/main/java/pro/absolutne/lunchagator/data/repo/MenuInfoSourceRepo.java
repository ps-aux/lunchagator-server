package pro.absolutne.lunchagator.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.absolutne.lunchagator.data.entity.MenuInfoSource;

public interface MenuInfoSourceRepo extends JpaRepository<MenuInfoSource, Long> {

}
