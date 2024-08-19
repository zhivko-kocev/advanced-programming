package mk.ukim.finki.AdvancedTasks2;

import java.time.Duration;
import java.util.*;
import java.util.stream.IntStream;


class Flight {
    private String from;
    private String to;
    private int time;
    private int duration;

    public Flight(String from, String to, int time, int duration) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.duration = duration;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

}

class Airport {
    private final String name;
    private final String country;
    private final String code;
    private final int passengers;
    private final List<Flight> flights;

    public Airport(String name, String country, String code, int passengers) {
        this.name = name;
        this.country = country;
        this.code = code;
        this.passengers = passengers;
        this.flights = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCode() {
        return code;
    }

    public int getPassengers() {
        return passengers;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" (").append(code).append(")\n");
        sb.append(country).append("\n");
        sb.append(passengers).append("\n");
        IntStream.range(0, flights.size()).forEach(i -> sb.append((i + 1)).append(".").append(" ").append(flights.get(i)).append("\n"));
        return sb.toString();
    }
}

class Airports {

    public void addFlights(String part, String part1, int i, int i1) {

    }

    public void addAirport(String part, String part1, String part2, int i) {

    }

    public void showFlightsFromAirport(String from) {

    }

    public void showDirectFlightsFromTo(String from, String to) {

    }

    public void showDirectFlightsTo(String to) {

    }
}

public class AirportsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Airports airports = new Airports();
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] codes = new String[n];
        for (int i = 0; i < n; ++i) {
            String al = scanner.nextLine();
            String[] parts = al.split(";");
            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            codes[i] = parts[2];
        }
        int nn = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nn; ++i) {
            String fl = scanner.nextLine();
            String[] parts = fl.split(";");
            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        int f = scanner.nextInt();
        int t = scanner.nextInt();
        String from = codes[f];
        String to = codes[t];
        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
        airports.showFlightsFromAirport(from);
        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
        airports.showDirectFlightsFromTo(from, to);
        t += 5;
        t = t % n;
        to = codes[t];
        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
        airports.showDirectFlightsTo(to);
    }
}



