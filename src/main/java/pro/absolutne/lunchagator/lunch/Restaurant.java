package pro.absolutne.lunchagator.lunch;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Restaurant {

    @Id
    private String id;

    private final String name;
    private final Location location;

    private MenuInfoSource menuInfoSource;

}
