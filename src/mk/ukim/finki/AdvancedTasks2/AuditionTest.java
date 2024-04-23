package mk.ukim.finki.AdvancedTasks2;

import java.util.*;

class Participant {
    private final String code;
    private final String name;
    private final int age;

    public Participant(String code, String name, int age) {
        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + " " + name + " " + age;
    }
}
class City{
    private final List<Participant> participants;
    private final HashSet<String> codes;

    public City() {
        this.participants=new ArrayList<>();
        this.codes=new HashSet<>();
    }

    public void addParticipant(String code, String name, int age) {
        if(codes.add(code)){
            participants.add(new Participant(code,name,age));
        }
    }
    public List<Participant> getParticipants() {
        return participants;
    }
}
class Audition {

    HashMap<String,City> cities;

    public Audition() {
        this.cities=new HashMap<>();
    }

    public void addParticipant(String city, String code, String name, int age) {
        cities.putIfAbsent(city,new City());
        cities.get(city).addParticipant(code,name,age);
    }

    public void listByCity(String city) {
        cities.get(city).getParticipants().stream().sorted(Comparator.comparing(Participant::getName).thenComparing(Participant::getAge).thenComparing(Participant::getCode)).forEach(System.out::println);
    }
}

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticipant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}
