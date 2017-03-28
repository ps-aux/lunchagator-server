package pro.absolutne.lunchagator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * We need to specify Jsr310 converters explicitly
 * See https://github.com/spring-projects/spring-boot/issues/2721
 */
@EntityScan(
		basePackageClasses = { LunchagatorApplication.class, Jsr310JpaConverters.class }
)
@SpringBootApplication
public class LunchagatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LunchagatorApplication.class, args);
	}
}
