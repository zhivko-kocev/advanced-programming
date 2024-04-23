package mk.ukim.finki.AdvancedTasks;

import java.util.*;
import java.util.stream.Collectors;

class InvalidPositionException extends Exception{
    private final int position;

    public InvalidPositionException(int position) {
        this.position = position;
    }

    @Override
    public String getMessage() {
        return String.format("Invalid position %d, alredy taken!",position);
    }
}
class Component{

    private String color;
    private final int weight;

    private List<Component> components;

    public Component(String color, int weight) {
        this.color = color;
        this.weight = weight;
        this.components=new ArrayList<>();
    }
    void addComponent(Component component){
        components.add(component);
        components=components.stream().sorted(Comparator.comparingInt(Component::getWeight).thenComparing(Component::getColor)).collect(Collectors.toList());
    }

    public String getColor() {
        return color;
    }

    public int getWeight() {
        return weight;
    }
    void changeColor(int weight, String color){
        if(this.weight<weight){
            this.color=color;
        }
        components.forEach(c->c.changeColor(weight,color));
    }

    public String format(String indent) {
        StringBuilder s = new StringBuilder(String.format("%s%d:%s\n", indent, weight, color));
        for (Component component : components) {
            s.append(component.format(indent + "---"));
        }
        return s.toString();
    }
    @Override
    public String toString() {
        return format("");
    }

}

class Window{

    private final String name;

    private final Map<Integer,Component> components;

    public Window(String name) {
        this.name = name;
        this.components=new TreeMap<>();
    }

    void addComponent(int position, Component component) throws InvalidPositionException {
        if(components.containsKey(position)){
            throw new InvalidPositionException(position);
        }
        components.put(position,component);
    }

    void changeColor(int weight, String color){
        components.values().forEach(c->c.changeColor(weight,color));
    }
    void swichComponents(int pos1, int pos2){
        Component tmp=components.get(pos1);
        components.replace(pos1,components.get(pos2));
        components.replace(pos2,tmp);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WINDOW ").append(name).append("\n");
        components.forEach((key, value) -> sb.append(key).append(":").append(value));
        return sb.toString();
    }

}
public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Window window = new Window(name);
        Component prev = null;
        while (true) {
            try {
                int what = scanner.nextInt();
                scanner.nextLine();
                if (what == 0) {
                    int position = scanner.nextInt();
                    window.addComponent(position, prev);
                } else if (what == 1) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev = component;
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                } else if(what == 4) {
                    break;
                }

            } catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }

        System.out.println("=== ORIGINAL WINDOW ===");
        System.out.println(window);
        int weight = scanner.nextInt();
        scanner.nextLine();
        String color = scanner.nextLine();
        window.changeColor(weight, color);
        System.out.printf("=== CHANGED COLOR (%d, %s) ===%n", weight, color);
        System.out.println(window);
        int pos1 = scanner.nextInt();
        int pos2 = scanner.nextInt();
        System.out.printf("=== SWITCHED COMPONENTS %d <-> %d ===%n", pos1, pos2);
        window.swichComponents(pos1, pos2);
        System.out.println(window);
    }
}

// вашиот код овде