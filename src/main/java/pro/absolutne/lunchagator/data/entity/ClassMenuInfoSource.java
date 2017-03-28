package pro.absolutne.lunchagator.data.entity;

import lombok.Data;
import pro.absolutne.lunchagator.lunch.MenuProvider;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("class")
@Data
public class ClassMenuInfoSource extends MenuInfoSource {

    private Class<? extends MenuProvider> providerClass;
}
