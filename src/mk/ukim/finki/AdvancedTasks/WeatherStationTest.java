package mk.ukim.finki.AdvancedTasks;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

class Measurement {

    private final double temperature;
    private final double wind;
    private final double humidity;
    private final double visibility;
    private final Date date;

    public Measurement(double temperature, double wind, double humidity, double visibility, Date date) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.visibility = visibility;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public double getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s", temperature, wind, humidity, visibility, date.toString().replace("UTC", "GMT"));
    }
}

class WeatherStation {

    private List<Measurement> measurements;
    private final int xDays;

    public WeatherStation(int xDays) {
        this.xDays = xDays;
        this.measurements = new ArrayList<>();
    }

    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date) {
        if (measurements.stream().anyMatch(m -> Math.abs(((m.getDate().getTime() / 1000) - (date.getTime() / 1000))) < 150)) {
            return;
        }
        measurements.add(new Measurement(temperature, wind, humidity, visibility, date));
        measurements = measurements.stream().filter(m -> Math.abs(m.getDate().getTime() / 1000 - date.getTime() / 1000) < (long) (xDays * 24 * 60 * 60)).collect(Collectors.toList());
    }

    public int total() {
        return measurements.size();
    }

    public void status(Date from, Date to) {
        List<Measurement> tmp = measurements.stream().sorted(Comparator.comparing(Measurement::getDate)).filter(m -> m.getDate().compareTo(from) >= 0 && m.getDate().compareTo(to) <= 0).collect(Collectors.toList());
        if (!tmp.isEmpty()) {
            tmp.forEach(System.out::println);
            System.out.printf(
                    "Average temperature: %.2f%n",
                    tmp.stream()
                            .mapToDouble(Measurement::getTemperature)
                            .average()
                            .getAsDouble()
            );
        } else {
            throw new RuntimeException();
        }
    }
}

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}
