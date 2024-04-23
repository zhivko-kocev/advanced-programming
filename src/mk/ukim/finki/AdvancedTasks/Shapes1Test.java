//package mk.ukim.finki.AdvancedTasks;
//
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintStream;
//import java.util.Comparator;
//import java.util.LinkedList;
//import java.util.Scanner;
//
//class Canvas2{
//    private final String id;
//    private final LinkedList<Integer> sizes;
//
//    public Canvas2(String id, LinkedList<Integer> sizes) {
//        this.id = id;
//        this.sizes = sizes;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public LinkedList<Integer> getSizes() {
//        return sizes;
//    }
//
//    public int sumSquares(){
//        return sizes.stream().mapToInt(e->e).sum()*4;
//    }
//
//    @Override
//    public String toString() {
//        return id+" "+sizes.size()+" "+sumSquares();
//    }
//}
//class ShapesApplication2 {
//
//    LinkedList<Canvas2> canvases;
//    public ShapesApplication2() {
//        this.canvases=new LinkedList<>();
//    }
//
//    int readCanvasess (InputStream inputStream){
//        Scanner s=new Scanner(inputStream);
//        int sum=0;
//        while (s.hasNext()){
//            String [] line=s.nextLine().split(" ");
//            String id=line[0];
//            LinkedList<Integer> sizes=new LinkedList<>();
//            for (int i = 1; i < line.length ; i++) {
//                sizes.add(Integer.parseInt(line[i]));
//            }
//            canvases.add(new Canvas2(id,sizes));
//            sum+=line.length-1;
//        }
//        s.close();
//        return sum;
//    }
//
//    void printLargestCanvasTo (OutputStream outputStream){
//        PrintStream output=new PrintStream(outputStream);
//        Canvas2 max=canvases.stream().max(Comparator.comparing(Canvas2::sumSquares)).get();
//        output.println(max);
//        output.close();
//
//    }
//}
//
//public class Shapes1Test {
//
//    public static void main(String[] args) {
//        ShapesApplication2 shapesApplication = new ShapesApplication2();
//
//        System.out.println("===READING SQUARES FROM INPUT STREAM===");
//        System.out.println(shapesApplication.readCanvasess(System.in));
//        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
//        shapesApplication.printLargestCanvasTo(System.out);
//
//    }
//}