package mk.ukim.finki.AdvancedTasks;

import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
class Subtitle{

    private final int id;
    private LocalTime from;
    private LocalTime to;
    private final String text;

    public Subtitle(int id,String time, String text) {
        DateTimeFormatter df=DateTimeFormatter.ofPattern("HH:mm:ss,SSS", Locale.getDefault());
        String [] times=time.split("-->");

        this.id = id;
        this.from = LocalTime.from(df.parse(times[0].trim()));
        this.to = LocalTime.from(df.parse(times[1].trim()));
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format("%d%n%s --> %s%n%s",id,from.format(DateTimeFormatter.ofPattern("HH:mm:ss,SSS")),to.format(DateTimeFormatter.ofPattern("HH:mm:ss,SSS")),text);
    }

    public void shiftTime(int shift) {
        if(shift<0){
            from=from.minus(Math.abs(shift),ChronoUnit.MILLIS);
            to=to.minus(Math.abs(shift),ChronoUnit.MILLIS);
        }else{
            from=from.plus(Math.abs(shift),ChronoUnit.MILLIS);
            to=to.plus(Math.abs(shift),ChronoUnit.MILLIS);
        }
    }
}
class Subtitles{

    private final List<Subtitle> subtitles;

    public Subtitles() {
        this.subtitles=new ArrayList<>();
    }


    public int loadSubtitles(InputStream in) {
        Scanner sc=new Scanner(in);
        while (sc.hasNext()){
            int id=sc.nextInt();
            sc.nextLine();
            String time=sc.nextLine();
            StringBuilder sb=new StringBuilder();
            while (sc.hasNext()) {
                String line =sc.nextLine();
                if(line.isEmpty()){
                    break;
                }
                sb.append(line).append("\n");
            }
            subtitles.add(new Subtitle(id,time,sb.toString()));
        }
        sc.close();
        return subtitles.size();
    }

    public void print() {

        subtitles.forEach(System.out::println);
    }

    public void shift(int shift) {
        subtitles.forEach(s->s.shiftTime(shift));
    }
}
public class SubtitlesTest {
    public static void main(String[] args) {
        Locale.setDefault(Locale.CANADA);
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.printf("SHIFT FOR %d ms%n", shift);
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}

