import java.util.ArrayList;

public class MakeNewTable {
    //string between date and temperature
    private final String spaceBetween = String.format("%-12s | %-12s | %-12s | %-12s | %-12s",
            "-------------", "------------", "------------", "------------", "------------");
    //at the top of the table
    private final String spaceUp = String.format("%-12s   %-12s   %-12s   %-12s   %-12s",
            "_____________", "____________", "____________", "____________", "____________");
    //pattern for editing a string
    private final String patternForString = "%-12s  | %-12s | %-12s | %-12s | %-12s";

    //print info about weather
    public void printPageOfWeather(String[] dates, ArrayList<String> degrees, ArrayList<String> tempSecond,
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
