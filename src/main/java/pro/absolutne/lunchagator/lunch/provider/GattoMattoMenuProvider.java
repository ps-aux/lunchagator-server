package pro.absolutne.lunchagator.lunch.provider;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pro.absolutne.lunchagator.lunch.*;
import pro.absolutne.lunchagator.scraping.ScrapUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;

import static java.util.stream.Collectors.toList;

public class GattoMattoMenuProvider implements MenuProvider {

    private static final String URL =
            "https://www.zomato.com/bratislava/gatto-matto-ristorante-star%C3%A9-mesto-bratislava-i";

    private final Restaurant restaurant = new Restaurant(
            "Gatto Matto",
            new Location(
                    "Panská 17, 811 01 Bratislava - Staré Mesto",
                    48.142415,
                    17.107431));

    @Override
    public DailyMenu findMenuFor() {
        try {
            return doFindMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private DailyMenu doFindMenu() throws IOException {
        // TODO it much slow in comparison to browser - investigate if
        // sending proper headers won't make it load faster
        Document d = ScrapUtils.getDocument(URL);

        Elements e = d.select(".menu-preview  .tmi-group .tmi-daily");

        Collection<MenuItem> items = e.stream()
                .map(GattoMattoMenuProvider::toItem)
                .collect(toList());

        return new DailyMenu(LocalDate.now(), items, restaurant);
    }

    private static MenuItem toItem(Element el) {
        String name = el.select(".tmi-name").text();
        String desc = el.select(".tmi-desc").text();
        String price = el.select(".tmi-price").text();

        price = price
                .replaceAll("€", "")
                .replaceAll(",", ".");


        // Cut out last character (it is garbage)
        if (!price.isEmpty())
            price = price.substring(0, price.length() - 1);

        double priceVal = 0;
        if (!price.isEmpty())
            priceVal = Double.parseDouble(price);

        return new MenuItem(name + ": " + desc, priceVal);

    }


}
