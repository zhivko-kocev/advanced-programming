package mk.ukim.finki.AdvancedTasks;

import java.util.Scanner;

class InvalidEvaluation extends Exception {
    public InvalidEvaluation() {
    }
}

class EvaluatorBuilder {
    public static Evaluator build(Evaluator.TYPE type) throws InvalidEvaluation {

        if (type.equals(Evaluator.TYPE.NO_CRIMINAL_RECORD)) {
            return Evaluator.noCriminalRecord;
        } else if (type.equals(Evaluator.TYPE.MORE_EXPERIENCE)) {
            return Evaluator.moreExperience;
        } else if (type.equals(Evaluator.TYPE.MORE_CREDIT_SCORE)) {
            return Evaluator.moreCreditScore;
        } else if (type.equals(Evaluator.TYPE.NO_CRIMINAL_RECORD_AND_MORE_EXPERIENCE)) {
            return Evaluator.noCriminalRecord.and(Evaluator.moreExperience);
        } else if (type.equals(Evaluator.TYPE.MORE_EXPERIENCE_AND_MORE_CREDIT_SCORE)) {
            return Evaluator.moreExperience.and(Evaluator.moreCreditScore);
        } else if (type.equals(Evaluator.TYPE.NO_CRIMINAL_RECORD_AND_MORE_CREDIT_SCORE)) {
            return Evaluator.noCriminalRecord.and(Evaluator.moreCreditScore);
        } else {
            throw new InvalidEvaluation();
        }

    }
}

public class ApplicantEvaluationTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        int creditScore = scanner.nextInt();
        int employmentYears = scanner.nextInt();
        boolean hasCriminalRecord = scanner.nextBoolean();
        int choice = scanner.nextInt();
        Applicant applicant = new Applicant(name, creditScore, employmentYears, hasCriminalRecord);
        Evaluator.TYPE type = Evaluator.TYPE.values()[choice];
        Evaluator evaluator = null;
        try {
            evaluator = EvaluatorBuilder.build(type);
            System.out.println("Applicant");
            System.out.println(applicant);
            System.out.println("Evaluation type: " + type.name());
            if (evaluator.evaluate(applicant)) {
                System.out.println("Applicant is ACCEPTED");
            } else {
                System.out.println("Applicant is REJECTED");
            }
        } catch (InvalidEvaluation invalidEvaluation) {
            System.out.println("Invalid evaluation");
        }
    }
}

class Applicant {
    private final String name;

    private final int creditScore;
    private final int employmentYears;
    private final boolean hasCriminalRecord;

    public Applicant(String name, int creditScore, int employmentYears, boolean hasCriminalRecord) {
        this.name = name;
        this.creditScore = creditScore;
        this.employmentYears = employmentYears;
        this.hasCriminalRecord = hasCriminalRecord;
    }

    public String getName() {
        return name;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public int getEmploymentYears() {
        return employmentYears;
    }

    public boolean hasCriminalRecord() {
        return hasCriminalRecord;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nCredit score: %d\nExperience: %d\nCriminal record: %s\n",
                name, creditScore, employmentYears, hasCriminalRecord ? "Yes" : "No");
    }
}

interface Evaluator {
    enum TYPE {
        NO_CRIMINAL_RECORD,
        MORE_EXPERIENCE,
        MORE_CREDIT_SCORE,
        NO_CRIMINAL_RECORD_AND_MORE_EXPERIENCE,
        MORE_EXPERIENCE_AND_MORE_CREDIT_SCORE,
        NO_CRIMINAL_RECORD_AND_MORE_CREDIT_SCORE,
        INVALID // should throw exception
    }

    Evaluator noCriminalRecord = a -> !a.hasCriminalRecord();
    Evaluator moreExperience = a -> a.getEmploymentYears() >= 10;
    Evaluator moreCreditScore = a -> a.getCreditScore() >= 500;

    default Evaluator and(Evaluator other) {
        return a -> this.evaluate(a) && other.evaluate(a);
    }

    boolean evaluate(Applicant applicant);
}



