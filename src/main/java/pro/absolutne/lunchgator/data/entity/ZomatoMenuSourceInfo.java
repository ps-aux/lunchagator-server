package pro.absolutne.lunchgator.data.entity;

import lombok.Data;
import pro.absolutne.lunchgator.lunch.MenuProvider;
import pro.absolutne.lunchgator.lunch.provider.ZomatoMenuProvider;

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
