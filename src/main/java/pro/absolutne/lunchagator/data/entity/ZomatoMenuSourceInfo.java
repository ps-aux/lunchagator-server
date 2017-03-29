package pro.absolutne.lunchagator.data.entity;

import lombok.Data;
import pro.absolutne.lunchagator.lunch.MenuProvider;
import pro.absolutne.lunchagator.lunch.provider.ZomatoMenuProvider;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("zomato")
@Data
public class ZomatoMenuSourceInfo extends MenuProviderInfo {

    private int zomatoId;

    @Override
    public Class<? extends MenuProvider> getMenuProviderImpl() {
        return ZomatoMenuProvider.class;
    }
}
