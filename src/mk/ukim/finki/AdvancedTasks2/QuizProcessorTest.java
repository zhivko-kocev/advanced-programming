//package mk.ukim.finki.AdvancedTasks2;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//class InvalidQuizException extends Exception {
//    public InvalidQuizException(String message) {
//        super(message);
//    }
//}
//
//class Student {
//    private final String id;
//    private final String[] answers;
//    private final String[] guesses;
//
//    public Student(String id, String[] answers, String[] guesses) throws InvalidQuizException {
//        if (answers.length != guesses.length) {
//            throw new InvalidQuizException("A quiz must have same number of correct and selected answers");
//        }
//        this.id = id;
//        this.answers = answers;
//        this.guesses = guesses;
//    }
//
//    public double points() {
//        AtomicInteger numGuessed = new AtomicInteger();
//        AtomicInteger numMissed = new AtomicInteger();
//        IntStream.range(0, answers.length).forEach(i -> {
//            if (answers[i].equals(guesses[i])) {
//                numGuessed.getAndIncrement();
//            } else {
//                numMissed.getAndIncrement();
//            }
//        });
//        return (numGuessed.get() - (numMissed.get() * 0.25));
//    }
//
//    public String getId() {
//        return id;
//    }
//}
//
//class QuizProcessor {
//    public static Map<String, Double> processAnswers(InputStream is) {
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        return br.lines().map(l -> {
//            try {
//                return new Student(l.split(";")[0], l.split(";")[1].split(","), l.split(";")[2].split(","));
//            } catch (InvalidQuizException e) {
//                System.out.println(e.getMessage());
//                return null;
//            }
//        }).filter(Objects::nonNull).collect(Collectors.toMap(Student::getId, Student::points, (s1, s2) -> s1, TreeMap::new));
//    }
//}
//
//public class QuizProcessorTest {
//    public static void main(String[] args) {
//        QuizProcessor.processAnswers(System.in).forEach((k, v) -> System.out.printf("%s -> %.2f%n", k, v));
//    }
//}
//
//
