package mk.ukim.finki.AdvancedTasks;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

abstract class Question {

    private final String name;
    private final int points;

    public Question(String name, String points) {
        this.name = name;
        this.points = Integer.parseInt(points);
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public static Question createQuestion(String type, String name, String points, String answer) throws InvalidOperationException {
        if (type.equals("TF")) {
            return new TFQuestion(name, points, answer);
        } else {
            if (answer.charAt(0) != 'A' && answer.charAt(0) != 'B' && answer.charAt(0) != 'C' && answer.charAt(0) != 'D'&&answer.charAt(0)!='E'){
                throw new InvalidOperationException(answer+" is not allowed option for this question");
            }
                return new MCQuestion(name, points, answer);
        }
    }

    abstract double getRealPoints(String answer);

}

class TFQuestion extends Question {

    private final boolean answer;

    public TFQuestion(String name, String points, String answer) {
        super(name, points);
        this.answer = Boolean.parseBoolean(answer);
    }

    @Override
    double getRealPoints(String answer) {
        return (Boolean.parseBoolean(answer)==this.answer)? getPoints(): 0;
    }

    @Override
    public String toString() {
        return String.format("True/False Question: %s Points: %d Answer: %b",getName(),getPoints(),answer);
    }
}

class MCQuestion extends Question {

    private final char answer;

    public MCQuestion(String name, String points, String answer) {
        super(name, points);
        this.answer = answer.charAt(0);
    }

    @Override
    double getRealPoints(String answer) {
        return (answer.charAt(0)==this.answer)? getPoints(): (getPoints()*0.2)*-1;
    }
    @Override
    public String toString() {
        return String.format("Multiple Choice Question: %s Points %d Answer: %c",getName(),getPoints(),answer);
    }
}
class Quiz{
    private final List<Question> questions;

    public Quiz() {
        this.questions=new ArrayList<>();
    }

    void addQuestion(String questionData){
        String [] parts=questionData.split(";");
        try {
            questions.add(Question.createQuestion(parts[0],parts[1],parts[2],parts[3]));
        } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
        }
    }

    void printQuiz(OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        questions.stream().sorted(Comparator.comparingInt(Question::getPoints).reversed()).forEach(pw::println);
        pw.close();
    }
    void answerQuiz (List<String> answers, OutputStream os) throws InvalidOperationException {
        PrintWriter pw = new PrintWriter(os);
        if(answers.size()!=questions.size()){
            throw new InvalidOperationException("Answers and questions must be of same length!");
        }
        double total=0;
        for (int i = 0; i < questions.size(); i++) {
            pw.printf("%d. %.2f%n",i+1,questions.get(i).getRealPoints(answers.get(i)));
            total+=questions.get(i).getRealPoints(answers.get(i));
        }
        pw.println(String.format("Total points: %.2f",total));
        pw.close();
    }
}
public class QuizTest {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Quiz quiz = new Quiz();

        int questions = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < questions; i++) {
            quiz.addQuestion(sc.nextLine());
        }

        List<String> answers = new ArrayList<>();

        int answersCount = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < answersCount; i++) {
            answers.add(sc.nextLine());
        }

        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase == 1) {
            quiz.printQuiz(System.out);
        } else if (testCase == 2) {
            try {
                quiz.answerQuiz(answers, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}

