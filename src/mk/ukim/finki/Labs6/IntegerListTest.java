package mk.ukim.finki.Labs6;


import java.util.*;
import java.util.stream.Collectors;

class IntegerList {

    private List<Integer> list;

    public IntegerList() {
        this.list = new LinkedList<>();
    }

    public IntegerList(Integer... numbers) {
        this.list = new LinkedList<>();
        list.addAll(Arrays.asList(numbers));
    }

    public IntegerList(int[] arr) {
        this.list = new LinkedList<>();
        for (int element : arr) {
            list.add(element);
        }
    }

    public void add(int element, int index) {
        if (index > list.size()) {
            for (int i = list.size(); i <= index; i++) {
                if (i == index) {
                    list.add(index, element);
                    return;
                }
                list.add(i, 0);
            }
        }
        list.add(index, element);
    }

    public int remove(int index) {
        return list.remove(index);
    }

    public void set(int element, int index) {
        list.set(index, element);
    }

    public int get(int index) {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

    public int count(int element) {
        return (int) list.stream().filter(e -> e.equals(element)).count();
    }

    public void removeDuplicates() {
        Collections.reverse(list);
        list = list.stream().distinct().collect(Collectors.toList());
        Collections.reverse(list);
    }

    public int sumFirst(int k) {
        return list.stream().mapToInt(Integer::intValue).limit(k).sum();
    }

    public int sumLast(int k) {
        Collections.reverse(list);
        int s = list.stream().mapToInt(Integer::intValue).limit(k).sum();
        Collections.reverse(list);
        return s;
    }

    private int shift(String dir,int index , int k){
        for (int i = 0; i < k; i++) {

            if(dir.equals("left")){
                if(index==0){
                    index=size()-1;
                    i++;
                    continue;
                }
                index--;
            }else{

                if(index==size()-1){
                    index=0;
                    i++;
                    continue;
                }
                index++;

            }

        }
        return index;
    }
    public void shiftRight(int index, int k) {
        Integer element= list.remove(index);

        list.add(shift("right",index,k),element);
    }

    public void shiftLeft(int index, int k) {
        Integer element= list.remove(index);
        list.add(shift("left",index,k),element);

    }

    public IntegerList addValue(int value) {
        return new IntegerList(list.stream().mapToInt(n -> n + value).toArray());
    }

    public List<Integer> getList() {
        return list;
    }
}

public class IntegerListTest {
    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test standard methods
            int subtest = jin.nextInt();
            if (subtest == 0) {
                IntegerList list = new IntegerList();
                while (true) {
                    int num = jin.nextInt();
                    if (num == 0) {
                        list.add(jin.nextInt(), jin.nextInt());
                    }
                    if (num == 1) {
                        list.remove(jin.nextInt());
                    }
                    if (num == 2) {
                        print(list);
                    }
                    if (num == 3) {
                        break;
                    }
                }
            }
            if (subtest == 1) {
                int n = jin.nextInt();
                Integer[] a = new Integer[n];
                for (int i = 0; i < n; ++i) {
                    a[i] = jin.nextInt();
                }
                IntegerList list = new IntegerList(a);
                print(list);
            }
        }
        if (k == 1) { //test count,remove duplicates, addValue
            int n = jin.nextInt();
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; ++i) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while (true) {
                int num = jin.nextInt();
                if (num == 0) { //count
                    System.out.println(list.count(jin.nextInt()));
                }
                if (num == 1) {
                    list.removeDuplicates();
                }
                if (num == 2) {
                    print(list.addValue(jin.nextInt()));
                }
                if (num == 3) {
                    list.add(jin.nextInt(), jin.nextInt());
                }
                if (num == 4) {
                    print(list);
                }
                if (num == 5) {
                    break;
                }
            }
        }
        if (k == 2) { //test shiftRight, shiftLeft, sumFirst , sumLast
            int n = jin.nextInt();
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; ++i) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while (true) {
                int num = jin.nextInt();
                if (num == 0) { //count
                    list.shiftLeft(jin.nextInt(), jin.nextInt());
                }
                if (num == 1) {
                    list.shiftRight(jin.nextInt(), jin.nextInt());
                }
                if (num == 2) {
                    System.out.println(list.sumFirst(jin.nextInt()));
                }
                if (num == 3) {
                    System.out.println(list.sumLast(jin.nextInt()));
                }
                if (num == 4) {
                    print(list);
                }
                if (num == 5) {
                    break;
                }
            }
        }
    }

    public static void print(IntegerList il) {
        if (il.size() == 0) System.out.print("EMPTY");
        for (int i = 0; i < il.size(); ++i) {
            if (i > 0) System.out.print(" ");
            System.out.print(il.get(i));
        }
        System.out.println();
    }

}
