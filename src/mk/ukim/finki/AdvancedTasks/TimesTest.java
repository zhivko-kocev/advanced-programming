package mk.ukim.finki.AdvancedTasks;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}

class UnsupportedFormatException extends Exception {
    private final String format;

    public UnsupportedFormatException(String format) {
        this.format = format;
    }

    @Override
    public String getMessage() {
        return format;
    }
}

class InvalidTimeException extends Exception {
    private final String format;

    public InvalidTimeException(String format) {
        this.format = format;
    }

    @Override
    public String getMessage() {
        return format;
    }
}

class Time {
    private final int hour;
    private final int minute;

    public Time(String hour, String minute) throws InvalidTimeException {
        int newHour = Integer.parseInt(hour);
        int newMinute = Integer.parseInt(minute);
        if (newHour > 23 || newHour < 0) {
            throw new InvalidTimeException(hour);
        }
        if (newMinute > 59 || newMinute < 0) {
            throw new InvalidTimeException(minute);
        }
        this.hour = newHour;
        this.minute = newMinute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}

class TimeTable {
    private List<Time> times;

    public TimeTable() {
        this.times = new ArrayList<>();
    }

    void readTimes(InputStream inputStream) throws InvalidTimeException, UnsupportedFormatException {
        Scanner sc = new Scanner(inputStream);
        String line;
        while (sc.hasNext()) {

            line = sc.nextLine();
            String[] l = line.split("\\s+");
            for (String string : l) {

                String[] element = string.split("[.:]+");
                if (element.length < 2) {
                    throw new UnsupportedFormatException(element[0]);
                }
                try {
                    times.add(new Time(element[0], element[1]));
                } catch (InvalidTimeException e) {
                    throw e;
                }

            }


        }

    }

    void writeTimes(OutputStream outputStream, TimeFormat format){
        times=times.stream().sorted(Comparator.comparingInt(Time::getHour).thenComparing(Time::getMinute)).collect(Collectors.toList());
        if(format.equals(TimeFormat.FORMAT_24)){
           times.stream().forEach(t-> System.out.printf("%2d:%02d%n",t.getHour(),t.getMinute()));
        }else{
            for (Time time : times) {
                if(time.getHour()==0){
                    System.out.printf("%2d:%02d AM%n",time.getHour()+12,time.getMinute());
                }else if(time.getHour()>0 && time.getHour()<12){
                    System.out.printf("%2d:%02d AM%n",time.getHour(),time.getMinute());
                }else if(time.getHour()==12){
                    System.out.printf("%2d:%02d PM%n",time.getHour(),time.getMinute());
                }else{
                    System.out.printf("%2d:%02d PM%n",time.getHour()-12,time.getMinute());
                }
            }
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
