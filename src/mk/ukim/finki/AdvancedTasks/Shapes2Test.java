//package mk.ukim.finki.AdvancedTasks;
//
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintStream;
//import java.util.Comparator;
//import java.util.LinkedList;
//import java.util.Scanner;
//class InvalidCanvasException extends Exception{
//    private final String id;
//    private final double maxArea;
//
//    public InvalidCanvasException(String id, double maxArea) {
//        this.id = id;
//        this.maxArea = maxArea;
//    }
//    public void message(){
//        System.out.printf("Canvas %s has a shape with area larger than %.2f%n",id,maxArea);
//    }
//}
//abstract class Form1{
//
//    protected double size;
//
//    public Form1( double size) {
//        this.size = size;
//    }
//    abstract double area();
//
//}
//class Circle1 extends Form1{
//    public Circle1( double size) {
//        super(size);
//    }
//
//    @Override
//    public double area() {
//        return size*size*Math.PI;
//    }
//}
//class Square extends Form1{
//    public Square( double size) {
//        super(size);
//    }
//
//    @Override
//    public double area() {
//        return size*size;
//    }
//}
//class Canvas1{
//    private final String id;
//    private final LinkedList<Form1> forms;
//
//    public Canvas1(String id) {
//        this.id = id;
//        this.forms=new LinkedList<>();
//    }
//    public void addForm(Form1 f,double maxArea) throws InvalidCanvasException {
//        if(f.area()>maxArea){
//            throw new InvalidCanvasException(id,maxArea);
//        }else {
//            forms.add(f);
//        }
//    }
//
//    public int totalCircles(){
//        return (int) forms.stream().filter(f->f.getClass().equals(Circle.class)).count();
//    }
//    public int totalSquares(){
//        return forms.size()-totalCircles();
//    }
//    public double minArea(){
//        return forms.stream().mapToDouble(Form1::area).min().getAsDouble();
//    }
//    public double maxArea(){
//        return forms.stream().mapToDouble(Form1::area).max().getAsDouble();
//    }
//    public double sumForms(){
//        return forms.stream().mapToDouble(Form1::area).sum();
//    }
//    public double averageArea(){
//        return sumForms()/forms.size();
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s %d %d %d %.2f %.2f %.2f",id,forms.size(),totalCircles(),totalSquares(),minArea(),maxArea(),averageArea());
//    }
//}
//class ShapesApplication{
//     private final double maxArea;
//     LinkedList<Canvas1> canvases;
//
//    public ShapesApplication(double maxArea) {
//        this.canvases=new LinkedList<>();
//        this.maxArea=maxArea;
//    }
//
//    public void readCanvases(InputStream in) {
//        Scanner s=new Scanner(in);
//        while (s.hasNext()){
//            String [] line=s.nextLine().split(" ");
//            Canvas1 c=new Canvas1(line[0]);
//            boolean isThrown = false;
//            for (int i = 1; i < line.length; i+=2) {
//                if(line[i].equals("C")){
//                    try {
//                        c.addForm(new Circle1(Double.parseDouble(line[i + 1])), maxArea);
//                    }catch(InvalidCanvasException e){
//                        e.message();
//                        isThrown=true;
//                    }
//                }else {
//                    try{
//                        c.addForm(new Square(Double.parseDouble(line[i + 1])), maxArea);
//                    }catch (InvalidCanvasException e){
//                        e.message();
//                        isThrown=true;
//                    }
//                }
//            }
//            if(!isThrown){
//                canvases.add(c);
//            }
//        }
//        s.close();
//
//    }
//
//    public void printCanvases(OutputStream out) {
//        PrintStream output=new PrintStream(out);
//        canvases.stream().sorted(Comparator.comparing(Canvas1::sumForms).reversed()).forEach(output::println);
//        output.close();
//    }
//}
//public class Shapes2Test {
//
//    public static void main(String[] args) {
//
//        ShapesApplication shapesApplication = new ShapesApplication(10000);
//
//        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
//        shapesApplication.readCanvases(System.in);
//
//        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
//        shapesApplication.printCanvases(System.out);
//
//
//    }
//}