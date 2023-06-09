import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CreatePageOfWeather {
    //string between date and temperature
    private final String spaceBetween = String.format("%-12s | %-12s | %-12s | %-12s | %-12s",
            "-------------", "------------", "------------", "------------", "------------");
    //at the top of the table
    private final String spaceUp = String.format("%-12s   %-12s   %-12s   %-12s   %-12s",
            "_____________", "____________", "____________", "____________", "____________");
    //pattern for editing a string
    private final String patternForString = "%-12s  | %-12s | %-12s | %-12s | %-12s";

    private String[] dates = new String[3];
    private ArrayList<String> degrees = new ArrayList<>();
    private ArrayList<String> wind = new ArrayList<>(){{
        add("-");
        add("-");
    }};

    public void printWeather() throws IOException {
        writerNewPage();
        printPageOfWeather(dates, degrees, wind);
    }

    //parsing a website page
    private org.jsoup.nodes.Document getPage() throws IOException {
        String url = "https://www.gismeteo.ru/weather-moscow-4368/3-days/";
        try {
            return Jsoup.parse(new URL(url), 3000);
        } catch (Exception e) {
            System.out.println("The site doesn't respond");
        }
        return null;
    }

    //select the relevant data on the page
    private void writerNewPage() throws IOException {
        Document document = getPage();
        Element table = document.select("div[class=widget-body widget-columns-40]").first();
        assert table != null;
        int index = 1;
        //date addition
        for (int i = 0; i < 3; i++) {
            dates[i] = table.select("a[class=item item-day-" + (i + 3) + " link date-left]").first().text();
        }
        //temperature addition
        while (index < 13) {
            degrees.add(table.select("span[class=unit unit_temperature_c]").get(index).text());
            index++;
        }
        //wind addition
        index = 40;
        while (index < 50) {
            wind.add(table.select("span[class=wind-unit unit unit_wind_m_s]").get(index).text());
            index++;
        }
    }

    //print info about weather
    private void printPageOfWeather(String[] dates, ArrayList<String> degrees, ArrayList<String> wind) {
        int index = 0;
        for (String date : dates) {
            String title = String.format(patternForString,
                    date, "Night", "Morning", "Day", "Evening");
            String temperatureInfo = String.format(patternForString,
                    "Temperature", degrees.get(index), degrees.get(index+1), degrees.get(index+2), degrees.get(index+3));
            String windInfo = String.format(patternForString,
                    "Wind", wind.get(index), wind.get(index+1), wind.get(index+2), wind.get(index+3));
            System.out.println();
            System.out.println(spaceUp);
            System.out.println(title);
            System.out.println(spaceBetween);
            System.out.println(temperatureInfo);
            System.out.println(windInfo);
            index += 4;
        }
    }

}
