package pro.absolutne.lunchagator.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import pro.absolutne.lunchagator.data.entity.MenuProviderInfo;

public interface MenuProviderInfoRepo extends JpaRepository<MenuProviderInfo, Long> {

}
