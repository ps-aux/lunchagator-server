package pro.absolutne.lunchagator.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class MenuItem {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private double price;

}
