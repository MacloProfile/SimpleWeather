import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
    private ArrayList<String> tempFelt = new ArrayList<>();
    private ArrayList<String> windDirection = new ArrayList<>();

    public void printWeather() throws IOException {
        writerNewPage();
        printPageOfWeather(dates, degrees, tempFelt, windDirection);
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
        //date addition
        Elements links = table.getElementsByTag("a");
        for (int i = 0; i < 3; i++) {
            dates[i] = links.get(i).text();
        }
        //temperature addition and temperature feels like
        int index = 1;
        while (index < 13) {
            degrees.add(table.select("span[class=unit unit_temperature_c]").get(index).text());
            tempFelt.add(table.select("span[class=unit unit_temperature_c]").get(index+41).text());
            index++;
        }
        //wind direction addition
        Elements wind = table.getElementsByClass("direction");
        index = 0;
        while (index < 12) {
            windDirection.add(wind.get(index).text());
            index++;
        }

    }

    //print info about weather
    private void printPageOfWeather(String[] dates, ArrayList<String> degrees, ArrayList<String> tempSecond,
                                    ArrayList<String> wind) {
        int index = 0;
        for (String date : dates) {
            String title = String.format(patternForString,
                    date, "Night", "Morning", "Day", "Evening");
            String temperatureInfo = String.format(patternForString,
                    "Temperature", degrees.get(index), degrees.get(index+1), degrees.get(index+2), degrees.get(index+3));
            String tempSeconds = String.format(patternForString,
                    "Feels like", tempSecond.get(index), tempSecond.get(index+1), tempSecond.get(index+2), tempSecond.get(index+3));
            String windDirection = String.format(patternForString,
                    "wind Direct.", wind.get(index), wind.get(index+1), wind.get(index+2), wind.get(index+3));

            System.out.println();
            System.out.println(spaceUp);
            System.out.println(title);
            System.out.println(spaceBetween);
            System.out.println(temperatureInfo);
            System.out.println(tempSeconds);
            System.out.println(windDirection);
            index += 4;

        }
    }

}
