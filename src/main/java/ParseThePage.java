import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;

public class ParseThePage extends CreatePageOfWeather {
    //parsing a website page
    public static org.jsoup.nodes.Document getPage() throws IOException {
        String url = "https://www.gismeteo.ru/weather-moscow-4368/3-days/";
        try {
            return Jsoup.parse(new URL(url), 3000);
        } catch (Exception e) {
            System.out.println("The site doesn't respond");
        }
        return null;
    }
}
