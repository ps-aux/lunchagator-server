package pro.absolutne.lunchagator.data.entity;

import javax.persistence.*;

@Entity
@Inheritance
@DiscriminatorColumn(name = "type")
public abstract class MenuInfoSource {

    @Id
    @GeneratedValue
    private long id;

}
