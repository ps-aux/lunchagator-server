package pro.absolutne.lunchagator.lunch.provider;


import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.stereotype.Service;
import pro.absolutne.lunchagator.lunch.MenuItem;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;


@Service
public class ZomatoService {

    private static final String URL = "https://developers.zomato.com/api/v2.1/dailymenu?" +
            "res_id={restaurant-id}";
    private final OkHttpClient client = new OkHttpClient();

    private static final Pattern DECIMAL_NUM = Pattern.compile("\\d+,?\\d*");
    private static final String DISHES_PATH = "$.daily_menus[0].daily_menu.dishes[*].dish";

    private static final String ROZHRANOVANY_KLIC = "26f285d8d3210d236d113e223850a017";

    public Collection<MenuItem> getDishes(int restaurantId) {

        String url = URL.replace("{restaurant-id}",
                Integer.toString(restaurantId));

        Request r = new Request.Builder()
                .url(url)
                .header("user_key", ROZHRANOVANY_KLIC)
                .build();
        try {
            String s = client.newCall(r).execute().body().string();
            DocumentContext jsonContext = JsonPath.parse(s);
            List<Map<String, String>> d = jsonContext.read(DISHES_PATH);

            return d.stream()
                    .map(ZomatoService::toMenuItem)
                    .collect(toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static MenuItem toMenuItem(Map<String, String> dish) {
        String name = dish.get("name");
        String price = dish.get("price");

        double priceVal = 0;

        if (!price.isEmpty()) {
            Matcher m = DECIMAL_NUM.matcher(price);
            boolean found = m.find();
            assert found;
            price = m.group().replace(",", ".");
            priceVal = Double.parseDouble(price);
        }


        return new MenuItem(name, priceVal);
    }
}
