package pro.absolutne.lunchagator.data.entity;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("zomato")
@Data
public class ZomatoMenuInfoSource extends MenuInfoSource {

    private int restaurantId;

}
