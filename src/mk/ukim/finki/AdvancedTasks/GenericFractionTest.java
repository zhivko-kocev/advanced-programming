package mk.ukim.finki.AdvancedTasks;

import java.util.Scanner;

@SuppressWarnings("unchecked")
class ZeroDenominatorException extends Exception{
    public ZeroDenominatorException() {
    }

    @Override
    public String getMessage() {
        return "Denominator cannot be zero";
    }
}
class GenericFraction<T extends Number,U extends Number>{
    private final T numerator;
    private final U denominator;

    public GenericFraction(T numerator, U denominator) throws ZeroDenominatorException {
        if(denominator.doubleValue()==0){
            throw new ZeroDenominatorException();
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }
    GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf) throws ZeroDenominatorException {
        Double newNumerator=(numerator.doubleValue()*gf.denominator.doubleValue())+(denominator.doubleValue()*gf.numerator.doubleValue());
        Double newDenominator=denominator.doubleValue()*gf.denominator.doubleValue();
        return new GenericFraction<>(newNumerator,newDenominator);
    }
    double toDouble(){
        return numerator.doubleValue()/denominator.doubleValue();
    }

    private int gcd(){
        int s= Math.max(numerator.intValue(), denominator.intValue());
        for (int i = s; i >0 ; i--) {
            if(numerator.intValue()%i==0 && denominator.intValue()%i==0){
                return i;
            }
        }
        return 1;
    }

    @Override
    public String toString() {
        int maxD=gcd();
        return String.format("%.2f / %.2f",numerator.doubleValue()/maxD,denominator.doubleValue()/maxD);
    }
}

public class GenericFractionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double n1 = scanner.nextDouble();
        double d1 = scanner.nextDouble();
        float n2 = scanner.nextFloat();
        float d2 = scanner.nextFloat();
        int n3 = scanner.nextInt();
        int d3 = scanner.nextInt();
        try {
            GenericFraction<Double, Double> gfDouble = new GenericFraction<Double, Double>(n1, d1);
            GenericFraction<Float, Float> gfFloat = new GenericFraction<Float, Float>(n2, d2);
            GenericFraction<Integer, Integer> gfInt = new GenericFraction<Integer, Integer>(n3, d3);
            System.out.printf("%.2f\n", gfDouble.toDouble());
            System.out.println(gfDouble.add(gfFloat));
            System.out.println(gfInt.add(gfFloat));
            System.out.println(gfDouble.add(gfInt));
            gfInt = new GenericFraction<Integer, Integer>(n3, 0);
        } catch(ZeroDenominatorException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }

}

