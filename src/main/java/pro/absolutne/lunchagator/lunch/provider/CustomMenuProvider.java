package pro.absolutne.lunchagator.lunch.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pro.absolutne.lunchagator.data.entity.CustomProviderInfo;
import pro.absolutne.lunchagator.data.entity.DailyMenu;
import pro.absolutne.lunchagator.data.entity.Restaurant;
import pro.absolutne.lunchagator.lunch.MenuProvider;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Slf4j
@Component
public class CustomMenuProvider implements MenuProvider<CustomProviderInfo> {

    private final Collection<MenuProvidingStrategy> strategies;

    public CustomMenuProvider(List<MenuProvidingStrategy> strategies) {
        checkForDuplicateStrategies(strategies);
        this.strategies = strategies;
    }

    private void checkForDuplicateStrategies(List<MenuProvidingStrategy> strategies) {
        Map<String, Long> grouped = strategies.stream()
                .collect(
                        groupingBy(
                                MenuProvidingStrategy::getId,
                                counting()));

        Collection<String> duplicates = grouped.entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(toList());

        if (!duplicates.isEmpty())
            throw new IllegalStateException("Detected id duplicated in "
                    + MenuProvidingStrategy.class.getSimpleName() + ": " + duplicates);
    }

    @Override
    public DailyMenu findDailyMenu(Restaurant r, CustomProviderInfo infoSource) {
        String strategyId = infoSource.getStrategyId();
        log.debug("Finding daily menu for {} using '{}' strategy", r, strategyId);

        MenuProvidingStrategy strategy = strategies.stream()
                .filter(s -> s.getId().equals(strategyId))
                .findFirst().get();

        return strategy.provide(r);
    }
}
