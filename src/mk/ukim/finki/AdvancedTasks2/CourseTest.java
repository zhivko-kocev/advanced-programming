//package mk.ukim.finki.AdvancedTasks2;
//
////package mk.ukim.finki.midterm;
//
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//class Student{
//    private final String index;
//    private final String name;
//    private int first_midterm;
//    private int second_midterm;
//    private int labs;
//
//    public Student(String index, String name) {
//        this.index = index;
//        this.name = name;
//        this.first_midterm=0;
//        this.second_midterm=0;
//        this.labs=0;
//    }
//
//    public void update(String activity,int points){
//        if(activity.equals("midterm1")){
//            this.first_midterm=points;
//        }else if(activity.equals("midterm2")){
//            this.second_midterm=points;
//        }else if(activity.equals("labs")){
//            this.labs=points;
//        }
//    }
//    public double getSummary(){
//        return first_midterm * 0.45 + second_midterm * 0.45 + labs;
//    }
//    public int getGrade(){
//        return (getSummary()<50)? 5 : (int) ((getSummary() >= 100) ? 10 : (getSummary() / 10) + 1);
//    }
//    @Override
//    public String toString() {
//        return String.format("ID: %s Name: %s First midterm: %d Second midterm %d Labs: %d Summary points: %.2f Grade: %d",
//                index,name,first_midterm,second_midterm,labs,getSummary(),getGrade());
//    }
//
//    public String getIndex() {
//        return index;
//    }
//}
//class AdvancedProgrammingCourse{
//    private final Map<String,Student> students;
//
//    public AdvancedProgrammingCourse() {
//        this.students=new HashMap<>();
//    }
//
//
//    public void addStudent(Student student) {
//        students.putIfAbsent(student.getIndex(),student);
//    }
//
//
//    public void updateStudent(String idNumber, String activity, int points) {
//        students.get(idNumber).update(activity,points);
//    }
//
//    public List<Student> getFirstNStudents(int n) {
//        return students.values()
//                .stream()
//                .sorted(Comparator.comparing(Student::getSummary).reversed())
//                .limit(n)
//                .collect(Collectors.toList());
//    }
//
//    public Map<Integer, Integer> getGradeDistribution() {
//         return IntStream.rangeClosed(5, 10)
//                .boxed()
//                .collect(Collectors.toMap(Function.identity(), grade -> (int) students.values()
//                        .stream()
//                        .map(Student::getGrade)
//                        .filter(studentGrade -> studentGrade.equals(grade))
//                        .count()));
//    }
//
//    public void printStatistics() {
//        DoubleSummaryStatistics ds =
//                students.values()
//                        .stream()
//                        .filter(student -> student.getGrade()>5)
//                        .mapToDouble(Student::getSummary)
//                        .summaryStatistics();
//
//        System.out.printf("Count: %d Min: %.2f Average: %.2f Max: %.2f%n",
//                ds.getCount(),ds.getMin(),ds.getAverage(),ds.getMax());
//    }
//}
//
//public class CourseTest {
//
//    public static void printStudents(List<Student> students) {
//        students.forEach(System.out::println);
//    }
//
//    public static void printMap(Map<Integer, Integer> map) {
//        map.forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
//    }
//
//    public static void main(String[] args) {
//        AdvancedProgrammingCourse advancedProgrammingCourse = new AdvancedProgrammingCourse();
//
//        Scanner sc = new Scanner(System.in);
//
//        while (sc.hasNextLine()) {
//            String line = sc.nextLine();
//            String[] parts = line.split("\\s+");
//
//            String command = parts[0];
//
//            if (command.equals("addStudent")) {
//                String id = parts[1];
//                String name = parts[2];
//                advancedProgrammingCourse.addStudent(new Student(id, name));
//            } else if (command.equals("updateStudent")) {
//                String idNumber = parts[1];
//                String activity = parts[2];
//                int points = Integer.parseInt(parts[3]);
//                advancedProgrammingCourse.updateStudent(idNumber, activity, points);
//            } else if (command.equals("getFirstNStudents")) {
//                int n = Integer.parseInt(parts[1]);
//                printStudents(advancedProgrammingCourse.getFirstNStudents(n));
//            } else if (command.equals("getGradeDistribution")) {
//                printMap(advancedProgrammingCourse.getGradeDistribution());
//            } else {
//                advancedProgrammingCourse.printStatistics();
//            }
//        }
//    }
//}
//
