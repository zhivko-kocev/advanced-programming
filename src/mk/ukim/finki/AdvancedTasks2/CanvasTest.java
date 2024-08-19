package mk.ukim.finki.AdvancedTasks2;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class InvalidIDException extends Exception {
    public InvalidIDException(String message) {
        super(message);
    }
}

class InvalidDimensionException extends Exception {
    public InvalidDimensionException(String message) {
        super(message);
    }
}

abstract class Shape {
    protected String userID;
    protected Double[] sides;

    protected Shape(String userID, Double... sides) {
        this.userID = userID;
        this.sides = sides;
    }

    public static Shape createShape(String type, String userID, String... sides) throws InvalidIDException {
        Double[] tmp = Arrays.stream(sides).map(Double::parseDouble).toArray(Double[]::new);
        if (userID.chars().allMatch(Character::isLetterOrDigit) && userID.length() == 6) {
            if (type.equals("1")) {
                return new Circle(userID, tmp);
            } else if (type.equals("2")) {
                return new Square(userID, tmp);
            } else {
                return new Rectangle(userID, tmp);
            }
        } else {
            throw new InvalidIDException("ID "+userID+" is not valid");
        }

    }


    public String getUserID() {
        return userID;
    }

    abstract double area();

    abstract double perimeter();

    abstract void scale(double number);

    abstract String getName();

    @Override
    public String toString() {
        if (sides.length > 1) {
            return String.format("%s: -> Sides: %.2f, %.2f Area: %.2f Perimeter: %.2f", getName(), sides[0], sides[1], area(), perimeter());
        } else if (getName().equals("Circle")) {
            return String.format("%s -> Radius: %.2f Area: %.2f Perimeter: %.2f", getName(), sides[0], area(), perimeter());
        } else {
            return String.format("%s: -> Side: %.2f Area: %.2f Perimeter: %.2f", getName(), sides[0], area(), perimeter());

        }
    }
}

class Circle extends Shape {

    protected Circle(String userID, Double... sides) {
        super(userID, sides);
    }

    @Override
    double area() {
        return (sides[0] * sides[0]) * Math.PI;
    }

    @Override
    double perimeter() {
        return 2 * sides[0] * Math.PI;
    }

    @Override
    void scale(double number) {
        sides[0] *= number;
    }

    @Override
    String getName() {
        return "Circle";
    }
}

class Square extends Shape {

    public Square(String userID, Double... sides) {
        super(userID, sides);
    }

    @Override
    double area() {
        return sides[0] * sides[0];
    }

    @Override
    double perimeter() {
        return sides[0] * 4;
    }

    @Override
    void scale(double number) {
        sides[0] *= number;
    }

    @Override
    String getName() {
        return "Square";
    }
}

class Rectangle extends Shape {

    public Rectangle(String userID, Double... sides) {
        super(userID, sides);
    }

    @Override
    double area() {
        return sides[0] * sides[1];
    }

    @Override
    double perimeter() {
        return (sides[0] * 2) + (sides[1] * 2);
    }

    @Override
    void scale(double number) {
        sides[0] *= number;
        sides[1] *= number;
    }

    @Override
    String getName() {
        return "Rectangle";
    }
}

class Canvas {

    private final Set<Shape> shapes;
    private Map<String, Set<Shape>> users;

    public Canvas() {
        this.shapes = new TreeSet<>(Comparator.comparing(Shape::area));
        this.users=new HashMap<>();
    }

    public void readShapes(InputStream in) throws InvalidDimensionException {
        Scanner sc = new Scanner(in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");
            if (Arrays.stream(parts).skip(2).anyMatch(p -> Double.parseDouble(p) == 0)) {
                users = shapes.stream().collect(Collectors.groupingBy(
                        Shape::getUserID,
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Shape::perimeter)))

                ));
                throw new InvalidDimensionException("Dimension 0 is not allowed!");
            }
            try {
                shapes.add(Shape.createShape(parts[0], parts[1], Arrays.stream(parts).skip(2).toArray(String[]::new)));
            } catch (InvalidIDException e) {
                System.out.println(e.getMessage());
            }
        }
        users = shapes.stream().collect(Collectors.groupingBy(
                Shape::getUserID,
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Shape::perimeter)))

        ));
    }

    public void printAllShapes(PrintStream out) {
        shapes.forEach(System.out::println);
    }

    public void scaleShapes(String userID, double number) {
        shapes.stream().filter(s -> s.getUserID().equals(userID)).forEach(s -> s.scale(number));
    }

    public void printByUserId(PrintStream out) {
        Comparator<Map.Entry<String,Set<Shape>>> countComparator=Comparator.comparing(entry->users.get(entry.getKey()).size());
        Comparator<Map.Entry<String,Set<Shape>>> sumComparator=Comparator.comparing(entry->users.get(entry.getKey()).stream().mapToDouble(Shape::area).sum());

        users.entrySet().stream().sorted(countComparator.thenComparing(sumComparator.reversed()).reversed()).forEach(entry->{
            System.out.println("Shapes of user: "+entry.getKey());
            entry.getValue().forEach(System.out::println);
        });
    }


    public void statistics(PrintStream out) {
        DoubleSummaryStatistics ds = shapes.stream().mapToDouble(Shape::area).summaryStatistics();
        System.out.printf("count: %d\nsum: %.2f\nmin: %.2f\naverage: %.2f\nmax: %.2f\n", ds.getCount(), ds.getSum(), ds.getMin(), ds.getAverage(), ds.getMax());
    }
}

public class CanvasTest {

    public static void main(String[] args) {
        Canvas canvas = new Canvas();

        System.out.println("READ SHAPES AND EXCEPTIONS TESTING");
        try {
            canvas.readShapes(System.in);
        } catch (InvalidDimensionException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("BEFORE SCALING");
        canvas.printAllShapes(System.out);
        canvas.scaleShapes("123456", 1.5);
        System.out.println("AFTER SCALING");
        canvas.printAllShapes(System.out);

        System.out.println("PRINT BY USER ID TESTING");
        canvas.printByUserId(System.out);

        System.out.println("PRINT STATISTICS");
        canvas.statistics(System.out);
    }
}