package mk.ukim.finki.AdvancedTasks;

import java.util.Scanner;

@SuppressWarnings("unchecked")
class Triple<E extends Number>{
    private E first,second,third;

    public Triple(E first, E second, E third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    double max(){
        double tmp=Math.max(first.doubleValue(), second.doubleValue());
        return Math.max(tmp,third.doubleValue());
    }
    double avarage(){
        return (first.doubleValue()+second.doubleValue()+third.doubleValue())/3;
    }

    void sort(){
        double max = Math.max(first.doubleValue(), Math.max(second.doubleValue(), third.doubleValue()));
        double min = Math.min(first.doubleValue(), Math.min(second.doubleValue(), third.doubleValue()));
        double mid = first.doubleValue() + second.doubleValue() + third.doubleValue() - max - min;
        Number f =  min;
        Number s = mid;
        Number t= max;
        first= (E) f;
        second=(E) s;
        third= (E) t;
     }

    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f",first.doubleValue(),second.doubleValue(),third.doubleValue());
    }
}

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}

