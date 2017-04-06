package pro.absolutne.lunchgator.data.entity;

import pro.absolutne.lunchgator.lunch.MenuProvider;

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
