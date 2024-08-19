package mk.ukim.finki.AdvancedTasks2;

import java.util.*;
import java.util.stream.Collectors;

class Names {
    private final Map<String, Integer> names;
    private final Set<String> unique;

    public Names() {
        this.names=new TreeMap<>();
        this.unique=new TreeSet<>();
    }

    public static int uniqueChars(String name) {
        return (int) Arrays.stream(name.split(""))
                .map(String::toLowerCase)
                .distinct()
                .count();
    }

    public void addName(String name) {
        if(!names.containsKey(name)){
            names.put(name,1);
        }else{
            names.computeIfPresent(name,(key,value)->value+1);
        }

        if(name.length() == uniqueChars(name)){
            unique.add(name);
        }
    }

    public void printN(int n) {
        names.entrySet().stream()
                .filter(entry -> entry.getValue()>=n)
                .forEach(entry ->
                        System.out.printf("%s (%d) %d%n",
                                entry.getKey(),entry.getValue(),uniqueChars(entry.getKey())));
    }

    public String findName(int len, int index) {
        int n = unique.stream()
                .filter(name -> name.length()<len)
                .collect(Collectors.toList())
                .size();
        return unique.stream()
                .filter(name->name.length()<len)
                .collect(Collectors.toList()).get(index % n);
    }
}

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}