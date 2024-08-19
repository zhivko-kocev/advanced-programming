package mk.ukim.finki.AdvancedTasks1_2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

class ShapeFirst {

    private final int side;

    public ShapeFirst(int side) {
        this.side = side;
    }

    public int perimeter() {
        return 4 * side;
    }
}

class CanvasFirst {

    private String id;
    private List<ShapeFirst> ShapeFirsts;

    public CanvasFirst(String id, List<ShapeFirst> ShapeFirsts) {
        this.ShapeFirsts = ShapeFirsts;
        this.id = id;
    }

    public int getNumShapeFirsts() {
        return ShapeFirsts.size();
    }

    public int getSize() {
        return ShapeFirsts.stream().mapToInt(ShapeFirst::perimeter).sum();
    }

    @Override
    public String toString() {
        return String.format("%s %d %d", id, getNumShapeFirsts(), getSize());
    }
}

class ShapeFirstsApplication {
    private List<CanvasFirst> CanvasFirstes;

    public ShapeFirstsApplication() {
        this.CanvasFirstes = new ArrayList<>();
    }

    int readCanvasFirstes(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String id = line.split("\\s+")[0];
            List<ShapeFirst> ShapeFirsts = Arrays.stream(line.split("\\s+")).skip(1).map(s -> new ShapeFirst(Integer.parseInt(s))).collect(Collectors.toList());
            CanvasFirstes.add(new CanvasFirst(id, ShapeFirsts));

        }
        return CanvasFirstes.stream().mapToInt(CanvasFirst::getNumShapeFirsts).sum();
    }

    void printLargestCanvasFirstTo(OutputStream outputStream) {
        System.out.println(CanvasFirstes.stream().max(Comparator.comparing(CanvasFirst::getSize)).get());
    }
}

public class Shapes1Test {

    public static void main(String[] args) {
        ShapeFirstsApplication ShapeFirstsApplication = new ShapeFirstsApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(ShapeFirstsApplication.readCanvasFirstes(System.in));
        System.out.println("===PRINTING LARGEST CanvasFirst TO OUTPUT STREAM===");
        ShapeFirstsApplication.printLargestCanvasFirstTo(System.out);

    }
}