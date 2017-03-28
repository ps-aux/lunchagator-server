package pro.absolutne.lunchagator.lunch.provider;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import pro.absolutne.lunchagator.data.entity.DailyMenu;
import pro.absolutne.lunchagator.data.entity.MenuItem;
import pro.absolutne.lunchagator.data.entity.Restaurant;
import pro.absolutne.lunchagator.lunch.MenuProvider;
import pro.absolutne.lunchagator.scraping.ScrapUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
public class PlzenskaMenuProvider implements MenuProvider {

    @Override
    public DailyMenu findDailyMenu(Restaurant r) {

        Document d = ScrapUtils.getDocument("http://www.plzenska.sk/prazdroj/menu.php");

        Element holder = d.select("#prazdroj-full-txt > div").get(1);

        List<MenuItem> items = holder.select("> div").stream()
                .map(PlzenskaMenuProvider::toMenuItem)
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
