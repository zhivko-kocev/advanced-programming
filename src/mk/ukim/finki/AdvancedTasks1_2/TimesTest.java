package mk.ukim.finki.AdvancedTasks1_2;


import java.io.InputStream;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class UnsupportedFormatException extends Exception {
    public UnsupportedFormatException(String message) {
        super(message);
    }
}

class InvalidTimeException extends Exception {
    public InvalidTimeException(String message) {
        super(message);
    }
}

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}

class Time {
    private final LocalTime time;

    private Time(LocalTime time) {

        this.time = time;
    }

    public static Time createTime(String toPrint) throws UnsupportedFormatException, InvalidTimeException {
        String[] parts = toPrint.split("[.:]+");
        if (parts.length != 2) {
            throw new UnsupportedFormatException(toPrint);
        }
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        if ((hours < 0 || hours > 23) || (minutes < 0 || minutes > 59)) {
            throw new InvalidTimeException(toPrint);
        }
        return new Time(LocalTime.of(hours, minutes));
    }

    public String getToPrint() {
        return String.format("%2d:%02d", time.getHour(), time.getMinute());
    }

    public LocalTime getTime() {
        return time;
    }

    public String toAMPM() {
        if ((time.getHour() > 0 && time.getHour() < 12) || time.getHour() == 0) {
            return (time.getHour() != 0) ? String.format("%2d:%02d AM", time.getHour(), time.getMinute()) : String.format("%2d:%02d AM", 12, time.getMinute());
        } else {
            return (time.getHour() != 12) ? String.format("%2d:%02d PM", Math.abs(time.getHour() - 12), time.getMinute()) : String.format("%2d:%02d PM", Math.abs(time.getHour()), time.getMinute());
        }
    }
}

class TimeTable {

    private final List<Time> times;

    public TimeTable() {
        this.times = new ArrayList<>();
    }

    void readTimes(InputStream inputStream) throws InvalidTimeException, UnsupportedFormatException {
        Scanner sc = new Scanner(inputStream);
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");
            for (String part : parts) {
                times.add(Time.createTime(part));
            }
        }
        sc.close();
    }

    void writeTimes(OutputStream outputStream, TimeFormat format) {
//        PrintWriter pw=new PrintWriter(outputStream);
        if (format.equals(TimeFormat.FORMAT_24)) {
            times.stream().sorted(Comparator.comparing(Time::getTime)).forEach(t -> System.out.println(t.getToPrint()));
        } else {
            times.stream().sorted(Comparator.comparing(Time::getTime)).forEach(t -> System.out.println(t.toAMPM()));
        }
    }
}

public class TimesTest {

    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}

