package mk.ukim.finki.AdvancedTasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

enum Color {
    RED, GREEN, BLUE
}
interface Scalable{
    void scale(float scaleFactor);
}
interface Stackable{
    float weight();
}
abstract class Form implements Scalable,Stackable{

    protected String id;
    protected Color color;

    public Form(String id, Color color) {
        this.id = id;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }
}

class Circle extends Form{

    private float radius;

    public Circle(String id, Color color, float radius) {
        super(id, color);
        this.radius = radius;
    }

    @Override
    public void scale(float scaleFactor) {
        radius*=scaleFactor;
    }

    @Override
    public float weight() {
        return (float) (radius*radius*Math.PI);
    }
}
class Rectangle extends Form{

    private float width;
    private float height;

    public Rectangle(String id, Color color, float width, float height) {
        super(id, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void scale(float scaleFactor) {
        width*=scaleFactor;
        height*=scaleFactor;
    }

    @Override
    public float weight() {
        return width*height;
    }
}
class Canvas {

    private final List<Form> forms;

    public Canvas() {
        this.forms=new ArrayList<>();
    }

    void add(Form f){

        if(forms.isEmpty()){
            forms.add(f);
        }else{
            int index=0;
            for (int i = 0; i <forms.size(); i++) {
                if(Float.compare(f.weight(),forms.get(i).weight())<0){
                   index=i+1;
                }
            }
            forms.add(index,f);

        }
    }

    void add(String id, Color color, float radius){
        add(new Circle(id,color,radius));
    }

    void add(String id, Color color, float width, float height){
       add(new Rectangle(id,color,width,height));
    }

    void scale(String id, float scaleFactor){
        Form f=forms.stream().filter(e->e.getId().equals(id)).findAny().orElse(new Circle("0",Color.RED,1));
        f.scale(scaleFactor);
        int indexToAdd = forms.size()-1, indexOfForm = 0;
        for (int i = forms.size()-1; i >= 0; i--) {
            if(f.weight()>forms.get(i).weight()){
                indexToAdd=i;
            }
            if(f.getId().equals(forms.get(i).getId())){
                indexOfForm=i;
            }
        }
        forms.add(indexToAdd, forms.remove(indexOfForm));
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        for (Form form : forms) {
            if(form.getClass().equals(Circle.class)){
                sb.append(String.format("C: %-4s %-10s %9.2f\n",form.getId(),form.getColor(),form.weight()));
            }else{
                sb.append(String.format("R: %-4s %-10s %9.2f\n",form.getId(),form.getColor(),form.weight()));
            }
        }
        return sb.toString();
    }
}
public class ShapesTest {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}


