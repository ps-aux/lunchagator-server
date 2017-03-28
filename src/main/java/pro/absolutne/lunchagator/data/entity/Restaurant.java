package pro.absolutne.lunchagator.data.entity;

import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;

@Data
@Entity
public class Restaurant {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String url;

    @Embedded
    private Location location;

    @Cascade(CascadeType.PERSIST)
    @OneToOne
    private MenuInfoSource menuInfoSource;


    public Restaurant() {

    }
}
