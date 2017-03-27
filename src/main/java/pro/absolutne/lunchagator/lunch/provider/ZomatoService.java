package pro.absolutne.lunchagator.lunch.provider;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
public class ZomatoService {

    private static final String URL = "https://developers.zomato.com/api/v2.1/dailymenu?res_id=16507679";
    private final OkHttpClient client = new OkHttpClient();

    @Autowired
    private ObjectMapper mapper;


    public void get() {
        String PATH = "$.daily_menus[0].daily_menu.dishes[*].dish";

        Request r = new Request.Builder()
                .url(URL)
                .header("user_key", "26f285d8d3210d236d113e223850a017")
                .build();



        try {
            String s = client.newCall(r).execute().body().string();
            System.out.println(s);
            DocumentContext jsonContext = JsonPath.parse(s);
            List<String> d = jsonContext.read(PATH);
//            Map<String, Object> m = mapper.readValue(s.getBytes(), new TypeReference<Map<String, Object>>() {
//            });
            System.out.println(d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
