package mk.ukim.finki.Labs5;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

@SuppressWarnings("unchecked")
class ResizableArray<T> {
    private T[] elements;
    private int size;

    public ResizableArray() {
        this.elements = (T[]) new Object[0];
        this.size = 0;
    }

    private void resize() {
        elements = Arrays.copyOf(elements, elements.length + 1);
    }

    public void addElement(T element) {
        if (size == elements.length) {
            resize();
        }
        elements[size++] = element;
    }
    private int find(T element){
        for (int i = 0; i < size; i++) {
            if(elements[i].equals(element)){
                return i;
            }
        }
        return -1;
    }
    private void remove(T element) {
        int newElement=find(element);
        elements[newElement]=elements[size-1];
        elements=Arrays.copyOf(elements,size-1);
        size--;
    }

    public boolean contains(T element){
        return Arrays.stream(elements).anyMatch(e->e.equals(element));
    }
    public boolean removeElement(T element) {
        if (contains(element)) {
            remove(element);
            return true;
        } else {
            return false;
        }
    }

    public Object [] toArray(){
        return Arrays.stream(elements).toArray();
    }

    public boolean isEmpty(){
        return size == 0;
    }
    public int count(){
        return size;
    }

    public T elementAt(int index){
        if(index>=0 && index<size){
            return elements[index];
        }
        throw new ArrayIndexOutOfBoundsException();
    }
    public T[] getElements() {
        return elements;
    }

    public int getSize() {
        return size;
    }

    public static <T> void copyAll(ResizableArray<? super T> dest, ResizableArray<? extends T> src){
        if(src==dest){
            T [] tmp=Arrays.copyOf(src.elements,src.size);
            for (int i = 0; i < tmp.length; i++) {
                dest.addElement(tmp[i]);
            }
            return;
        }
        for (int i = 0; i < src.size; i++) {
            dest.addElement(src.elementAt(i));
        }
    }
}

class IntegerArray extends ResizableArray<Integer>{

    public double sum(){
        return Arrays.stream(toArray()).mapToDouble(e->(Integer)e).sum();
    }
    public double mean(){
        return sum()/getSize();
    }

    public int countNonZero(){
        return (int) Arrays.stream(toArray()).mapToInt(e->(Integer)e).filter(e->e!=0).count();
    }

    public IntegerArray distinct() {

        IntegerArray temp = new IntegerArray();
        for (int i = 0; i < count(); i++) {
            if (!temp.contains(elementAt(i)))
                temp.addElement(elementAt(i));
        }

        return temp;

    }

    public IntegerArray increment(int offset) {
        IntegerArray temp = new IntegerArray();

        for (int i = 0; i < count(); i++) {
            temp.addElement(elementAt(i) + offset);
        }

        return temp;
    }

}

public class ResizableArrayTest {
    public static void main(String[] args) {

        Scanner jin = new Scanner(System.in);
        int test = jin.nextInt();
        if (test == 0) { //test ResizableArray on ints
            ResizableArray<Integer> a = new ResizableArray<Integer>();
            System.out.println(a.count());
            int first = jin.nextInt();
            a.addElement(first);
            System.out.println(a.count());
            int last = first;
            while (jin.hasNextInt()) {
                last = jin.nextInt();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
        }
        if (test == 1) { //test ResizableArray on strings
            ResizableArray<String> a = new ResizableArray<String>();
            System.out.println(a.count());
            String first = jin.next();
            a.addElement(first);
            System.out.println(a.count());
            String last = first;
            for (int i = 0; i < 4; ++i) {
                last = jin.next();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
            ResizableArray<String> b = new ResizableArray<String>();
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));

            System.out.println(a.removeElement(first));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
        }
        if (test == 2) { //test IntegerArray
            IntegerArray a = new IntegerArray();
            System.out.println(a.isEmpty());
            while (jin.hasNextInt()) {
                a.addElement(jin.nextInt());
            }
            jin.next();
            System.out.println(a.sum());
            System.out.println(a.mean());
            System.out.println(a.countNonZero());
            System.out.println(a.count());
            IntegerArray b = a.distinct();
            System.out.println(b.sum());
            IntegerArray c = a.increment(5);
            System.out.println(c.sum());
            if (a.sum() > 100)
                ResizableArray.copyAll(a, a);
            else
                ResizableArray.copyAll(a, b);
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.contains(jin.nextInt()));
            System.out.println(a.contains(jin.nextInt()));
        }
        if (test == 3) { //test insanely large arrays
            LinkedList<ResizableArray<Integer>> resizable_arrays = new LinkedList<ResizableArray<Integer>>();
            for (int w = 0; w < 500; ++w) {
                ResizableArray<Integer> a = new ResizableArray<Integer>();
                int k = 2000;
                int t = 1000;
                for (int i = 0; i < k; ++i) {
                    a.addElement(i);
                }

                a.removeElement(0);
                for (int i = 0; i < t; ++i) {
                    a.removeElement(k - i - 1);
                }
                resizable_arrays.add(a);
            }
            System.out.println("You implementation finished in less then 3 seconds, well done!");
        }
    }
}
