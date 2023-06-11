import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CreatePageOfWeather {

    private String[] dates = new String[3];
    private ArrayList<String> degrees = new ArrayList<>();
    private ArrayList<String> tempFelt = new ArrayList<>();
    private ArrayList<String> windDirection = new ArrayList<>();

    public void printWeather() throws IOException {
        MakeNewTable newTable = new MakeNewTable();
        writerNewPage();
        newTable.printPageOfWeather(dates, degrees, tempFelt, windDirection);
    }

    //select the relevant data on the page
    private void writerNewPage() throws IOException {
        Document document = ParseThePage.getPage();
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
}
