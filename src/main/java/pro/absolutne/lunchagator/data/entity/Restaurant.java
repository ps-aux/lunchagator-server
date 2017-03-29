package pro.absolutne.lunchagator.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import pro.absolutne.lunchagator.lunch.MenuProvider;

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

    @JsonIgnore
    @Cascade(CascadeType.PERSIST)
    @OneToOne
    private MenuProviderInfo menuProviderInfo;

}
