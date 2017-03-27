package pro.absolutne.lunchagator.service;


import com.jayway.jsonpath.JsonPath;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.absolutne.lunchagator.data.entity.Location;
import pro.absolutne.lunchagator.data.entity.MenuItem;
import pro.absolutne.lunchagator.data.entity.Restaurant;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;


@Service
public class ZomatoService {

    private static final Logger logger = LoggerFactory.getLogger(ZomatoService.class);

    private static final String API_ROOT = "https://developers.zomato.com/api/v2.1";

    private static final String RESTAURANT_PARAM = "{restaurant-id}";

    private static final String DAILY_MENU_URL = API_ROOT
            + "/dailymenu?res_id=" + RESTAURANT_PARAM;

    private static final String RESTAURANT_URL = API_ROOT +
            "/restaurant?res_id=" + RESTAURANT_PARAM;

    private final OkHttpClient client = new OkHttpClient();

    private static final Pattern DECIMAL_NUM = Pattern.compile("\\d+,?\\d*");
    private static final String DISHES_PATH = "$.daily_menus[0].daily_menu.dishes[*].dish";

    private static final String ROZHRANOVANY_KLIC = "26f285d8d3210d236d113e223850a017";

    public Collection<MenuItem> getDishes(int restaurantId) {
        Request r = buildRequest(setRestaurantId(DAILY_MENU_URL, restaurantId));
        List<Map<String, String>> d = JsonPath.parse(makeRequest(r)).read(DISHES_PATH);

        return d.stream()
                .map(ZomatoService::toMenuItem)
                .collect(toList());
    }

    public boolean hasDailyMenu(int restaurantId) {
        try {
            getDishes(restaurantId);
        } catch (Non200Exception e) {
            if (e.getMsg().toLowerCase()
                    .contains("No Daily Menu".toLowerCase()))
                return false;
            else
                throw e;
        }
        return true;
    }

    public Restaurant getRestaurant(int id) {
        Request r = buildRequest(setRestaurantId(RESTAURANT_URL, id));
        Map<String, Object> res = JsonPath.parse(makeRequest(r)).read("$");

        Location location = getLocation(res);
        String address = (String) res.get("name");

        return new Restaurant(address, location);
    }

    private Location getLocation(Map<String, Object> res) {
        Map<String, String> locationRes = (Map<String, String>) res.get("location");
        String addresss = locationRes.get("address");
        double lat = Double.parseDouble(locationRes.get("latitude"));
        double longi = Double.parseDouble(locationRes.get("longitude"));

        return new Location(addresss, lat, longi);
    }


    private String makeRequest(Request req) {
        try {
            logger.trace("Making request {}", req);
            Response res = client.newCall(req).execute();
            logger.trace("Response is {}", res);
            if (res.code() != 200) {
                String msg = null;
                try {
                    msg = JsonPath.parse(res.body().string())
                            .read("message");
                } catch (Exception e) {
                    logger.warn("Failed to retrieve 'message' from non 200 response");
                }
                throw new Non200Exception(res.code(), msg);
            }

            return res.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String setRestaurantId(String url, int id) {
        return url.replace(RESTAURANT_PARAM, Integer.toString(id));
    }

    private static Request buildRequest(String url) {
        return new Request.Builder()
                .header("user_key", ROZHRANOVANY_KLIC)
                .url(url)
                .build();
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
