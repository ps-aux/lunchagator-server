package pro.absolutne.lunchagator.integration.zomato;


import com.jayway.jsonpath.JsonPath;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.absolutne.lunchagator.data.entity.Location;
import pro.absolutne.lunchagator.data.entity.MenuItem;
import pro.absolutne.lunchagator.data.entity.Restaurant;
import pro.absolutne.lunchagator.scraping.ScrapUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;


@SuppressWarnings("OptionalGetWithoutIsPresent")
@Service
public class ZomatoService {

    private static final Logger logger = LoggerFactory.getLogger(ZomatoService.class);

    private static final String API_ROOT = "https://developers.zomato.com/api/v2.1";

    private static final HttpUrl urlRoot = HttpUrl.parse(API_ROOT);

    private static final String ROZHRANOVANY_KLIC = "26f285d8d3210d236d113e223850a017";

    private final OkHttpClient client = new OkHttpClient();

    private HttpUrl.Builder buildUrl(String path) {
        return urlRoot.newBuilder()
                .addPathSegment(path);
    }

    public List<MenuItem> getDishes(long restaurantId) {
        HttpUrl url = addRestaurantId(buildUrl("dailymenu"), restaurantId)
                .build();

        List<Map<String, String>> d = JsonPath.parse(doRequest(url))
                .read("$.daily_menus[0].daily_menu.dishes[*].dish");

        return d.stream()
                .map(ZomatoService::toMenuItem)
                .collect(toList());
    }

    public boolean hasDailyMenu(ZomatoRestaurant r) {
        return  hasDailyMenu(r.getZomatoId());
    }

    public boolean hasDailyMenu(long restaurantId) {
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

    public Restaurant getRestaurant(long id) {
        HttpUrl url = addRestaurantId(buildUrl("restaurant"), id)
                .build();

        Map<String, Object> res = JsonPath.parse(doRequest(url))
                .read("$");

        return parseRestaurant(res);
    }

    private static ZomatoRestaurant parseRestaurant(Map<String, Object> data) {

        String name = (String) data.get("name");
        String resUrl = (String) data.get("url");
        String id = (String) data.get("id");

        ZomatoRestaurant rest = new ZomatoRestaurant();
        rest.setLocation(parseLocation(data));
        rest.setName(name);
        rest.setUrl(resUrl.split("\\?")[0]); // Remove query string (has just referrals)
        rest.setZomatoId(Long.parseLong(id));

        return rest;

    }

    public Collection<ZomatoRestaurant> getRestaurantsByLocation(Location location, int radius) {
        logger.debug("Retrieving restaurants {} m around {} ", radius, location);
        HttpUrl url = buildUrl("search")
                .addQueryParameter("lat", location.getLatitude() + "")
                .addQueryParameter("long", location.getLongitude() + "")
                .addQueryParameter("radius", radius + "").build();

        List<Map<String, Object>> res = JsonPath.parse(doRequest(url))
                .read("$.restaurants[*].restaurant");

        return res.stream()
                .map(ZomatoService::parseRestaurant)
                .collect(toList());
    }

    private HttpUrl.Builder setOffset(HttpUrl.Builder builder, int offset) {
        return builder.setQueryParameter("start", offset + "");
    }

    private static Location parseLocation(Map<String, Object> res) {
        Map<String, String> locationRes = (Map<String, String>) res.get("location");
        String addresss = locationRes.get("address");
        double lat = Double.parseDouble(locationRes.get("latitude"));
        double longi = Double.parseDouble(locationRes.get("longitude"));

        return new Location(addresss, lat, longi);
    }


    private String doRequest(HttpUrl url) {
        Request req = buildRequest(url);
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

    private static HttpUrl.Builder addRestaurantId(HttpUrl.Builder urlBuilder, long id) {
        return urlBuilder.addQueryParameter("res_id", Long.toString(id));
    }

    private static Request buildRequest(HttpUrl url) {
        return new Request.Builder()
                .header("user_key", ROZHRANOVANY_KLIC)
                .url(url)
                .build();
    }

    private static MenuItem toMenuItem(Map<String, String> dish) {
        String name = dish.get("name");
        String price = dish.get("price");

        double priceVal = 0;

        if (!price.isEmpty())
            priceVal = ScrapUtils.retrieveDecimal(price).get();

        MenuItem item = new MenuItem();
        item.setName(name);
        item.setPrice(priceVal);

        return item;
    }
}
