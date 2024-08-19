package mk.ukim.finki.AdvancedTasks1_2;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

class InvalidCanvasException extends Exception {
    public InvalidCanvasException(String message) {
        super(message);
    }
}

abstract class Shape {
    protected int side;
    protected String type;

    public Shape(int side, String type) {
        this.side = side;
        this.type = type;
    }

    public abstract double area();
}

class Square extends Shape {

    public Square(int side, String type) {
        super(side, type);
    }

    @Override
    public double area() {
        return side * side;
    }
}

class Circle extends Shape {
    public Circle(int side, String type) {
        super(side, type);
    }

    @Override
    public double area() {
        return Math.PI * (side * side);
    }
}

class Canvas {

    private String id;
    private List<Shape> shapes;

    private Canvas(String id, List<Shape> shapes) {
        this.id = id;
        this.shapes = shapes;
    }

    public static Canvas createCanvas(String id, List<Shape> shapes, double maxArea) throws InvalidCanvasException {
        if (shapes.stream().anyMatch(s -> s.area() > maxArea)) {
            throw new InvalidCanvasException(String.format("Canvas %s has a shape with area larger than %.2f", id, maxArea));
        }

        return new Canvas(id, shapes);
    }

    public double getSum() {
        return shapes.stream().mapToDouble(Shape::area).sum();
    }

    @Override
    public String toString() {
        DoubleSummaryStatistics ds = shapes.stream().mapToDouble(Shape::area).summaryStatistics();
        int cCount = (int) shapes.stream().filter(s -> s.getClass().equals(Circle.class)).count();
        int sCount = (int) shapes.stream().filter(s -> s.getClass().equals(Square.class)).count();

        return String.format("%s %d %d %d %.2f %.2f %.2f", id, shapes.size(), cCount, sCount, ds.getMin(), ds.getMax(), ds.getAverage());
    }
}

class ShapesApplication {

    private double maxArea;
    private List<Canvas> canvases;

    public ShapesApplication(double maxArea) {
        this.canvases = new ArrayList<>();
        this.maxArea = maxArea;
    }

    void readCanvases(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");
            String id = parts[0];
            List<Shape> shapes = new ArrayList<>();
            for (int i = 1; i < parts.length - 1; i += 2) {
                if (parts[i].equals("C")) {
                    shapes.add(new Circle(Integer.parseInt(parts[i + 1]), parts[i]));
                } else {
                    shapes.add(new Square(Integer.parseInt(parts[i + 1]), parts[i]));
                }
            }
            try {
               canvases.add(Canvas.createCanvas(id, shapes, maxArea));
            } catch (InvalidCanvasException e) {
                System.out.println(e.getMessage());
            }
        }
        sc.close();
    }

    void printCanvases(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        canvases.stream().sorted(Comparator.comparing(Canvas::getSum).reversed()).forEach(pw::println);
        pw.close();
    }
}

public class Shapes2Test {

    public static void main(String[] args) {

        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);


    }
}
