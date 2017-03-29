package pro.absolutne.lunchagator.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;

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
