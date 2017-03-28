package pro.absolutne.lunchagator.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class MenuItem {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private double price;

    @JsonIgnore
    @ManyToOne
    private DailyMenu dailyMenu;

}
