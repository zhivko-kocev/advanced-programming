package mk.ukim.finki.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class StreamsTest {

    public static void main(String[] args) {

        String [] names = {"Zhivko", "Jovan", "Kostadin", "Cvetan", "Goran"};

        Integer su= Arrays.stream(names).mapToInt(String::length).reduce(0, Integer::sum);
        System.out.println(su);

        //map if i want to change values
        Integer [] lengths = Arrays.stream(names).map(String::length).toArray(Integer[]::new);
        System.out.println(Arrays.toString(lengths));

        //filter if I want to separate on condition
        String [] newNames=Arrays.stream(names).filter((name)->name.contains("o")).toArray(String[]::new);
        System.out.println(Arrays.toString(newNames));

        //sorted takes comparator or static method
        String [] sortedNames= Arrays.stream(names).sorted(Comparator.comparingInt(String::length).reversed()).toArray(String[]::new);
        System.out.println(Arrays.toString(sortedNames));

        Integer [] numbers={1,2,3,4,5,6,7,8,9,10};

        //use reduce if u want to take it to single value
        Integer sum= Arrays.stream(numbers).reduce(0, Integer::sum);
        System.out.println(sum);

        LinkedList<Integer> list=new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);

        //use foreach if u want all the elements to do an acion
        list.forEach(num-> System.out.print(num+" "));
        System.out.print("\n");

        //use collect to get new data type
        List<Integer> newList=list.stream().map(num-> num*num).collect(Collectors.toList());


        System.out.println(newList);


    }
}
