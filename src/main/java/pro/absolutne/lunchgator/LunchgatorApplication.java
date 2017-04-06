package pro.absolutne.lunchgator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * We need to specify Jsr310 converters explicitly
 * See https://github.com/spring-projects/spring-boot/issues/2721
 */
@EntityScan(
		basePackageClasses = { LunchgatorApplication.class, Jsr310JpaConverters.class }
)
@SpringBootApplication
public class LunchgatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LunchgatorApplication.class, args);
	}
}
