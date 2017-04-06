package pro.absolutne.lunchgator.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.absolutne.lunchgator.data.entity.MenuProviderInfo;

public interface MenuProviderInfoRepo extends JpaRepository<MenuProviderInfo, Long> {

}
