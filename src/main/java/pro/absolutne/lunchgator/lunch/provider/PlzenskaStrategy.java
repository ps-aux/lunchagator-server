package pro.absolutne.lunchgator.lunch.provider;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import pro.absolutne.lunchgator.data.entity.DailyMenu;
import pro.absolutne.lunchgator.data.entity.MenuItem;
import pro.absolutne.lunchgator.data.entity.Restaurant;
import pro.absolutne.lunchgator.scraping.ScrapUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class PlzenskaStrategy implements MenuProvidingStrategy {

    private static final String ID = "plzenska";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public DailyMenu provide(Restaurant r) {
        Document d = ScrapUtils.getDocument("http://www.plzenska.sk/prazdroj/menu.php");

        Element holder = d.select("#prazdroj-full-txt > div").get(1);

        List<MenuItem> items = holder.select("> div").stream()
                .map(PlzenskaStrategy::toMenuItem)
                .collect(toList());

        DailyMenu menu = new DailyMenu();
        menu.setDay(LocalDate.now());
        menu.setItems(items);
        menu.setRestaurant(r);

        return menu;
    }

    private static MenuItem toMenuItem(Element el) {
        Elements children = el.select("> div");

        String name = children.get(0).text();
        String priceText = children.get(2).text();

        double price = 0;

        Optional<Double> priceOpt = ScrapUtils.retrieveDecimal(priceText);
        if (priceOpt.isPresent()) {
            price = priceOpt.get();
        }

        MenuItem m = new MenuItem();
        m.setName(name);
        m.setPrice(price);
        return m;
    }

}
