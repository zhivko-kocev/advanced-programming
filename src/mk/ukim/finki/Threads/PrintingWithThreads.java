package mk.ukim.finki.Threads;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PrintingWithThreads {
    public static void main(String[] args) {
        List<Thread> threadList=new ArrayList<>();
        IntStream.range(0,100).forEach(i->threadList.add(new Thread(()-> System.out.println(i))));
        threadList.forEach(Thread::start);
        threadList.forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
