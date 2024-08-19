package mk.ukim.finki.AdvancedTasks2;


import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class Payment {
    private final String name;
    private final Integer price;

    public Payment(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " " + price;
    }
}

class OnlinePayments {

    private final Map<String, Set<Payment>> payments;

    public OnlinePayments() {
        this.payments = new HashMap<>();
    }

    public void readItems(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        br.lines()
                .map(line -> line.split(";"))
                .forEach(arr -> {
                    payments.putIfAbsent(arr[0], new TreeSet<>(Comparator.comparing(Payment::getPrice).thenComparing(Payment::getName).reversed()));
                    payments.get(arr[0]).add(new Payment(arr[1], Integer.parseInt(arr[2])));
                });
    }

    public void printStudentReport(String index, OutputStream os) {

        if (!payments.containsKey(index)) {
            System.out.println("Student " + index + " not found!");
            return;
        }

        int sum =
                payments.get(index).stream()
                        .mapToInt(Payment::getPrice)
                        .sum();
        long fee = (Math.round(sum * 0.0114)<3)? 3
                : (Math.round(sum * 0.0114)>300)? 300
                : Math.round(sum * 0.0114);
        System.out.printf("Student: %s Net: %d Fee: %d Total: %d\nItems:\n"
                , index, sum, fee, sum +fee);
        AtomicInteger i = new AtomicInteger(1);
        payments.get(index)
                .forEach(payment -> System.out.println(i.getAndIncrement() + ". "+payment));
    }
}

public class OnlinePaymentsTest {
    public static void main(String[] args) {
        OnlinePayments onlinePayments = new OnlinePayments();

        onlinePayments.readItems(System.in);

        IntStream.range(151020, 151025).mapToObj(String::valueOf).forEach(id -> onlinePayments.printStudentReport(id, System.out));
    }
}