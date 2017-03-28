package pro.absolutne.lunchagator.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ScrapUtils {

    private static final Pattern DECIMAL_NUM = Pattern.compile("\\d+,?\\d*");

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

    public static Optional<Double> retrieveDecimal(String str) {
        Matcher m = DECIMAL_NUM.matcher(str);
        if (!m.find())
            return Optional.empty();

        String price = m.group().replace(",", ".");
        return Optional.of(Double.parseDouble(price));
    }

}
