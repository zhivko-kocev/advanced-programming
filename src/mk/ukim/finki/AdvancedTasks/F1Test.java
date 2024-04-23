package mk.ukim.finki.AdvancedTasks;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class DriverTime{
    LocalTime lt;
    String t;

    public DriverTime(LocalTime lt, String t) {
        this.lt = lt;
        this.t = t;
    }

    public LocalTime getLt() {
        return lt;
    }

    public String getT() {
        return t;
    }
}
class Driver{
    private final String name;
    private final List<DriverTime> times;

    public Driver(String driver) {
        String[] times=driver.split("\\s+");
        this.name = times[0];
        this.times=new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            String [] values=times[i].split(":");
            int minutes=Integer.parseInt(values[0]);
            int seconds=Integer.parseInt(values[1]);
            int millis=Integer.parseInt(values[2]);
            this.times.add(new DriverTime(LocalTime.of(0,minutes,seconds,millis),times[i]));
        }
    }

    public String getName() {
        return name;
    }

    public String getBestTime(){

       return times.stream().min(Comparator.comparing(DriverTime::getLt)).get().getT();
    }

    @Override
    public String toString() {
        return String.format("%-10s  %10s",name,getBestTime());
    }
}
class F1Race {

    private final List<Driver> drivers;

    public F1Race() {
        this.drivers=new ArrayList<>();
    }
    void readResults(InputStream inputStream){
        Scanner sc=new Scanner(inputStream);
        while(sc.hasNext()){
            String line=sc.nextLine();
            drivers.add(new Driver(line));
        }
    }
    void printSorted(OutputStream outputStream){
        PrintWriter pw=new PrintWriter(outputStream);
        AtomicInteger pos= new AtomicInteger(0);
        drivers.stream().sorted(Comparator.comparing(Driver::getBestTime)).forEach(d-> System.out.println(pos.incrementAndGet() +". "+d));
    }
}

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

