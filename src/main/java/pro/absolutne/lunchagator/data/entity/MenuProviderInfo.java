package pro.absolutne.lunchagator.data.entity;

import pro.absolutne.lunchagator.lunch.MenuProvider;

import javax.persistence.*;

@Entity
@Inheritance
@DiscriminatorColumn(name = "type")
public abstract class MenuProviderInfo {

    @Id
    @GeneratedValue
    private long id;

    public abstract Class<? extends MenuProvider> getMenuProviderImpl();

}
