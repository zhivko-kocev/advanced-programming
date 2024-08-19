//package mk.ukim.finki.AdvancedTasks2;
//
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//class OperationNotAllowedException extends Exception{
//    public OperationNotAllowedException(String message) {
//        super(message);
//    }
//}
//
//class Semester{
//    private Map<String,Integer> courses;
//
//    public Semester() {
//        this.courses=new HashMap<>();
//    }
//
//    public void addGrade(String courseName,int grade) throws OperationNotAllowedException {
//        if(courses.size()==3){
//            throw new OperationNotAllowedException("");
//        }
//
//        courses.putIfAbsent(courseName,grade);
//    }
//
//    public Map<String, Integer> getCourses() {
//        return courses;
//    }
//    public boolean hasCourse(String name){
//        return courses.containsKey(name);
//    }
//    public int getGrade(String name){
//        return courses.get(name);
//    }
//}
//abstract class Student{
//    protected Map<Integer,Semester> semesters;
//    protected boolean graduated = false;
//
//    public Student() {
//        this.semesters=new HashMap<>();
//    }
//
//    public Map<Integer, Semester> getSemesters() {
//        return semesters;
//    }
//    public boolean hasCourse(String name){
//        return semesters.values().stream()
//                .anyMatch(semester -> semester.hasCourse(name));
//    }
//
//    public boolean isGraduated() {
//        return graduated;
//    }
//
//    public static Student createStudent(int years){
//        return (years==3)? new ThreeYearStudent() : new FourYearStudent();
//    }
//    public abstract void addGrade(int term, String courseGrade, int grade) throws OperationNotAllowedException;
//}
//
//class FourYearStudent extends Student{
//    @Override
//    public void addGrade(int term, String courseGrade, int grade) throws OperationNotAllowedException {
//        if(term<1 || term>8){
//            throw new OperationNotAllowedException("");
//        }
//        if(semesters.values().stream()
//                .map(Semester::getCourses)
//                .map(Map::size)
//                .count()==24){
//            this.graduated=true;
//        }
//        this.semesters.putIfAbsent(term,new Semester());
//        this.semesters.get(term).addGrade(courseGrade,grade);
//    }
//}
//
//class ThreeYearStudent extends Student{
//    @Override
//    public void addGrade(int term, String courseGrade, int grade) throws OperationNotAllowedException {
//        if(term<1 || term>6){
//            throw new OperationNotAllowedException("");
//        }
//        this.semesters.putIfAbsent(term,new Semester());
//        this.semesters.get(term).addGrade(courseGrade,grade);
//    }
//}
//class Faculty {
//    private Map<String,Student> students;
//    private Set<String> courses;
//    private List<String> logs;
//    public Faculty() {
//        this.students=new HashMap<>();
//        this.logs=new ArrayList<>();
//        this.courses=new HashSet<>();
//    }
//
//    void addStudent(String id, int yearsOfStudies) {
//        students.putIfAbsent(id,Student.createStudent(yearsOfStudies));
//    }
//
//    void addGradeToStudent(String studentId, int term, String courseName, int grade) throws OperationNotAllowedException {
//        students.get(studentId).addGrade(term,courseName,grade);
//        if(students.get(studentId).isGraduated()){
//            Student grad = students.remove(studentId);
//            logs.add(""+grad.)
//        }
//    }
//
//    String getFacultyLogs() {
//        return "";
//    }
//
//    String getDetailedReportForStudent(String id) {
//        return "";
//    }
//
//    void printFirstNStudents(int n) {
//
//    }
//
//    void printCourses() {
//
//    }
//}
//
//public class FacultyTest {
//
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int testCase = sc.nextInt();
//
//        if (testCase == 1) {
//            System.out.println("TESTING addStudent AND printFirstNStudents");
//            Faculty faculty = new Faculty();
//            for (int i = 0; i < 10; i++) {
//                faculty.addStudent("student" + i, (i % 2 == 0) ? 3 : 4);
//            }
//            faculty.printFirstNStudents(10);
//
//        } else if (testCase == 2) {
//            System.out.println("TESTING addGrade and exception");
//            Faculty faculty = new Faculty();
//            faculty.addStudent("123", 3);
//            faculty.addStudent("1234", 4);
//            try {
//                faculty.addGradeToStudent("123", 7, "NP", 10);
//            } catch (OperationNotAllowedException e) {
//                System.out.println(e.getMessage());
//            }
//            try {
//                faculty.addGradeToStudent("1234", 9, "NP", 8);
//            } catch (OperationNotAllowedException e) {
//                System.out.println(e.getMessage());
//            }
//        } else if (testCase == 3) {
//            System.out.println("TESTING addGrade and exception");
//            Faculty faculty = new Faculty();
//            faculty.addStudent("123", 3);
//            faculty.addStudent("1234", 4);
//            for (int i = 0; i < 4; i++) {
//                try {
//                    faculty.addGradeToStudent("123", 1, "course" + i, 10);
//                } catch (OperationNotAllowedException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//            for (int i = 0; i < 4; i++) {
//                try {
//                    faculty.addGradeToStudent("1234", 1, "course" + i, 10);
//                } catch (OperationNotAllowedException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//        } else if (testCase == 4) {
//            System.out.println("Testing addGrade for graduation");
//            Faculty faculty = new Faculty();
//            faculty.addStudent("123", 3);
//            faculty.addStudent("1234", 4);
//            int counter = 1;
//            for (int i = 1; i <= 6; i++) {
//                for (int j = 1; j <= 3; j++) {
//                    try {
//                        faculty.addGradeToStudent("123", i, "course" + counter, (i % 2 == 0) ? 7 : 8);
//                    } catch (OperationNotAllowedException e) {
//                        System.out.println(e.getMessage());
//                    }
//                    ++counter;
//                }
//            }
//            counter = 1;
//            for (int i = 1; i <= 8; i++) {
//                for (int j = 1; j <= 3; j++) {
//                    try {
//                        faculty.addGradeToStudent("1234", i, "course" + counter, (j % 2 == 0) ? 7 : 10);
//                    } catch (OperationNotAllowedException e) {
//                        System.out.println(e.getMessage());
//                    }
//                    ++counter;
//                }
//            }
//            System.out.println("LOGS");
//            System.out.println(faculty.getFacultyLogs());
//            System.out.println("PRINT STUDENTS (there shouldn't be anything after this line!");
//            faculty.printFirstNStudents(2);
//        } else if (testCase == 5 || testCase == 6 || testCase == 7) {
//            System.out.println("Testing addGrade and printFirstNStudents (not graduated student)");
//            Faculty faculty = new Faculty();
//            for (int i = 1; i <= 10; i++) {
//                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
//                int courseCounter = 1;
//                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
//                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
//                        try {
//                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), i % 5 + 6);
//                        } catch (OperationNotAllowedException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        ++courseCounter;
//                    }
//                }
//            }
//            if (testCase == 5)
//                faculty.printFirstNStudents(10);
//            else if (testCase == 6)
//                faculty.printFirstNStudents(3);
//            else
//                faculty.printFirstNStudents(20);
//        } else if (testCase == 8 || testCase == 9) {
//            System.out.println("TESTING DETAILED REPORT");
//            Faculty faculty = new Faculty();
//            faculty.addStudent("student1", ((testCase == 8) ? 3 : 4));
//            int grade = 6;
//            int counterCounter = 1;
//            for (int i = 1; i < ((testCase == 8) ? 6 : 8); i++) {
//                for (int j = 1; j < 3; j++) {
//                    try {
//                        faculty.addGradeToStudent("student1", i, "course" + counterCounter, grade);
//                    } catch (OperationNotAllowedException e) {
//                        e.printStackTrace();
//                    }
//                    grade++;
//                    if (grade == 10)
//                        grade = 5;
//                    ++counterCounter;
//                }
//            }
//            System.out.println(faculty.getDetailedReportForStudent("student1"));
//        } else if (testCase==10) {
//            System.out.println("TESTING PRINT COURSES");
//            Faculty faculty = new Faculty();
//            for (int i = 1; i <= 10; i++) {
//                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
//                int courseCounter = 1;
//                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
//                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
//                        int grade = sc.nextInt();
//                        try {
//                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
//                        } catch (OperationNotAllowedException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        ++courseCounter;
//                    }
//                }
//            }
//            faculty.printCourses();
//        } else if (testCase==11) {
//            System.out.println("INTEGRATION TEST");
//            Faculty faculty = new Faculty();
//            for (int i = 1; i <= 10; i++) {
//                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
//                int courseCounter = 1;
//                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
//                    for (int k = 1; k <= ((j % 2 == 1) ? 2 : 3); k++) {
//                        int grade = sc.nextInt();
//                        try {
//                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
//                        } catch (OperationNotAllowedException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        ++courseCounter;
//                    }
//                }
//
//            }
//
//            for (int i=11;i<15;i++) {
//                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
//                int courseCounter = 1;
//                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
//                    for (int k = 1; k <= 3; k++) {
//                        int grade = sc.nextInt();
//                        try {
//                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
//                        } catch (OperationNotAllowedException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        ++courseCounter;
//                    }
//                }
//            }
//            System.out.println("LOGS");
//            System.out.println(faculty.getFacultyLogs());
//            System.out.println("DETAILED REPORT FOR STUDENT");
//            System.out.println(faculty.getDetailedReportForStudent("student2"));
//            try {
//                System.out.println(faculty.getDetailedReportForStudent("student11"));
//                System.out.println("The graduated students should be deleted!!!");
//            } catch (NullPointerException e) {
//                System.out.println("The graduated students are really deleted");
//            }
//            System.out.println("FIRST N STUDENTS");
//            faculty.printFirstNStudents(10);
//            System.out.println("COURSES");
//            faculty.printCourses();
//        }
//    }
//}
//
