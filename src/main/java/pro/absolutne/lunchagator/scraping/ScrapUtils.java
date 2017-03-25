package pro.absolutne.lunchagator.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public final class ScrapUtils {

    private ScrapUtils() {
    }

    public static Document getDocument(String url) {
        try {
            return Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
