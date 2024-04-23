package mk.ukim.finki.AdvancedTasks;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Student {
    String id;
    List<Integer> grades;

    public Student(String id, List<Integer> grades) {
        this.id = id;
        this.grades = grades;
    }

    public static Student create(String line) {
        String[] parts = line.split("\\s+");
        String id = parts[0];
        List<Integer> grades = Arrays.stream(parts).skip(1).map(Integer::parseInt).collect(Collectors.toList());
        return new Student(id, grades);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", grades=" + grades +
                '}';
    }
}
class Rule<T,U>{
    private final Function<T,U> function;
    private final Predicate<T> predicate;

    public Rule(Function<T, U> function, Predicate<T> predicate) {
        this.function = function;
        this.predicate = predicate;
    }

    public Optional<U> apply(T input){
        Optional<U> res;
        if(predicate.test(input)){
           res = Optional.of(function.apply(input));
        }else {
            res=Optional.empty();
        }
        return res;
    }
}
class RuleProcessor{
    public static <T,U> void process(List<T> list ,List<Rule<T,U>> rules){
        list.forEach(i->{
            System.out.println("Input: "+i);
           rules.forEach(r-> {
               if(r.apply(i).isPresent()){
                   System.out.println("Result: "+r.apply(i).get());
               }else {
                   System.out.println("Condition not met");
               }
           });
        });
    }
}
public class RuleTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase == 1) { //Test for String,Integer
            List<Rule<String, Integer>> rules = new ArrayList<>();

            rules.add(new Rule<>(s->s.indexOf("NP"),s->s.contains("NP")));
            /*
            TODO: Add a rule where if the string contains the string "NP", the result would be index of the first occurrence of the string "NP"
            * */


            rules.add(new Rule<>(String::length, s->s.startsWith("NP")));
            /*
            TODO: Add a rule where if the string starts with the string "NP", the result would be length of the string
            * */


            List<String> inputs = new ArrayList<>();
            while (sc.hasNext()) {
                inputs.add(sc.nextLine());
            }

            RuleProcessor.process(inputs, rules);


        } else { //Test for Student, Double
            List<Rule<Student, Double>> rules = new ArrayList<>();

            rules.add(new Rule<>(s->s.grades.stream().mapToDouble(Integer::doubleValue).max().getAsDouble(),s->s.grades.size()>=3));
            //TODO Add a rule where if the student has at least 3 grades, the result would be the max grade of the student

            rules.add(new Rule<>(s->s.grades.stream().mapToDouble(Integer::doubleValue).average().orElse(5.0),s->s.id.startsWith("20")));
            //TODO Add a rule where if the student has an ID that starts with 20, the result would be the average grade of the student
            //If the student doesn't have any grades, the average is 5.0


            List<Student> students = new ArrayList<>();
            while (sc.hasNext()){
                students.add(Student.create(sc.nextLine()));
            }

            RuleProcessor.process(students, rules);
        }
    }
}
