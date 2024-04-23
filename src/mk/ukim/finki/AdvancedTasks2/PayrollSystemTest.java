//package mk.ukim.finki.AdvancedTasks2;
//
//import java.io.*;
//import java.util.*;
//import java.util.stream.Collectors;
//
//
//class BonusNotAllowedException extends Exception{
//    public BonusNotAllowedException(String message) {
//        super(message);
//    }
//}
//
//abstract class Employee {
//    protected String id;
//    protected String level;
//    protected List<Double> work;
//    protected int bonus;
//    protected boolean isPercentage;
//
//    protected Employee(String id, String level, List<Double> work) {
//        this.id = id;
//        this.level = level;
//        this.work = work;
//    }
//
//    protected Employee(String id, String level, List<Double> work, int bonus, boolean isPercentage) {
//        this.id = id;
//        this.level = level;
//        this.work = work;
//        this.bonus = bonus;
//        this.isPercentage = isPercentage;
//    }
//
//    public static Employee createWithoutBonus(String type,String id,String level,List<String> points){
//        List<Double> dPoints=points.stream().map(Double::parseDouble).collect(Collectors.toList());
//        if(type.equals("H")){
//            return new HourlyEmployee(id,level,dPoints);
//        }else{
//            return new FreelanceEmployee(id,level,dPoints);
//        }
//    }
//
//    public static Employee createWithBonus(String type,String id,String level,List<String> points,String bonus) throws BonusNotAllowedException {
//        List<Double> dPoints = points.stream().map(Double::parseDouble).collect(Collectors.toList());
//        int b=Integer.parseInt(bonus.replace("%",""));
//        if(bonus.contains("%")){
//            if(b>20){
//                throw new BonusNotAllowedException("Exception");
//            }
//            if(type.equals("H")){
//                return new HourlyEmployee(id,level,dPoints,b,true);
//            }else{
//                return new FreelanceEmployee(id,level,dPoints,b,true);
//            }
//        }else {
//            if(b>1000){
//                throw new BonusNotAllowedException("Exception");
//            }
//            if(type.equals("H")){
//                return new HourlyEmployee(id,level,dPoints,b,false);
//            }else{
//                return new FreelanceEmployee(id,level,dPoints,b,false);
//            }
//        }
//
//    }
//
//    public String getLevel() {
//        return level;
//    }
//
//    public abstract double salary();
//
//    @Override
//    public abstract String toString();
//}
//
//class HourlyEmployee extends Employee {
//
//    public HourlyEmployee(String id, String level, List<Double> work) {
//        super(id, level, work);
//    }
//
//    public HourlyEmployee(String id, String level, List<Double> work, int bonus, boolean isPercentage) {
//        super(id, level, work, bonus, isPercentage);
//    }
//
//    @Override
//    public double salary() {
//        double rate = PayrollSystem.hourlyRateByLevel.get(level);
//        double hours = work.get(0);
//        return (hours <= 40) ? hours * rate :
//                (40 * rate) + (hours % 40 * (rate * 1.5));
//    }
//
//    @Override
//    public String toString() {
//        double regular = (work.get(0) < 40) ? work.get(0) : 40;
//        double overtime = (regular < 40) ? 0 : work.get(0) % 40;
//        return String.format("Employee ID: %s Level: %s Salary: %.2f Regular hours: %.2f Overtime hours: %.2f", id, level, salary(), regular, overtime);
//    }
//}
//
//class FreelanceEmployee extends Employee {
//
//    public FreelanceEmployee(String id, String level, List<Double> work) {
//        super(id, level, work);
//    }
//
//    public FreelanceEmployee(String id, String level, List<Double> work, int bonus, boolean isPercentage) {
//        super(id, level, work, bonus, isPercentage);
//    }
//
//    @Override
//    public double salary() {
//        return work.stream().mapToDouble(d -> d).sum() * PayrollSystem.ticketRateByLevel.get(level);
//    }
//
//    @Override
//    public String toString() {
//        int points = work.stream().mapToInt(Double::intValue).sum();
//        int count = work.size();
//        return String.format("Employee ID: %s Level: %s Salary: %.2f Tickets count: %d Tickets points: %d", id, level, salary(), count, points);
//    }
//}
//
//class PayrollSystem {
//
//    private List<Employee> employees;
//    public static Map<String,Double> hourlyRateByLevel;
//    public static Map<String,Double> ticketRateByLevel;
//
//    public PayrollSystem(Map<String,Double> hourlyRateByLevel, Map<String,Double> ticketRateByLevel){
//        PayrollSystem.hourlyRateByLevel=hourlyRateByLevel;
//        PayrollSystem.ticketRateByLevel=ticketRateByLevel;
//        this.employees=new ArrayList<>();
//    }
//    Employee createEmployee (String line) throws BonusNotAllowedException {
//        String [] parts=line.split(";+");
//        String type = parts[0];
//        String id= parts[1];
//        String level= parts[2];
//        List<String> points= Arrays.stream(parts).skip(3).collect(Collectors.toList());
//        String [] bonus = points.get(points.size()-1).split("\\s+");
//        if(bonus.length!=2){
//            Employee tmp=Employee.createWithoutBonus(type,id,level,points);
//            return tmp;
//        }else{
//            points.remove(points.size()-1);
//            points.add(bonus[0]);
//            Employee tmp=Employee.createWithBonus(type,id,level,points,bonus[1]);
//            employees.add(tmp);
//            return tmp;
//        }
//    }
//}
//
//public class PayrollSystemTest {
//
//    public static void main(String[] args) {
//
//        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
//        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();
//        for (int i = 1; i <= 10; i++) {
//            hourlyRateByLevel.put("level" + i, 11 + i * 2.2);
//            ticketRateByLevel.put("level" + i, 5.5 + i * 2.5);
//        }
//
//        Scanner sc = new Scanner(System.in);
//
//        int employeesCount = Integer.parseInt(sc.nextLine());
//
//        PayrollSystem ps = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);
//        Employee emp = null;
//        for (int i = 0; i < employeesCount; i++) {
//            try {
//                emp = ps.createEmployee(sc.nextLine());
//            } catch (BonusNotAllowedException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        int testCase = Integer.parseInt(sc.nextLine());
//
//        switch (testCase) {
//            case 1: //Testing createEmployee
//                if (emp != null)
//                    System.out.println(emp);
//                break;
//            case 2: //Testing getOvertimeSalaryForLevels()
//                ps.getOvertimeSalaryForLevels().forEach((level, overtimeSalary) -> {
//                    System.out.printf("Level: %s Overtime salary: %.2f\n", level, overtimeSalary);
//                });
//                break;
//            case 3: //Testing printStatisticsForOvertimeSalary()
//                ps.printStatisticsForOvertimeSalary();
//                break;
//            case 4: //Testing ticketsDoneByLevel
//                ps.ticketsDoneByLevel().forEach((level, overtimeSalary) -> {
//                    System.out.printf("Level: %s Tickets by level: %d\n", level, overtimeSalary);
//                });
//                break;
//            case 5: //Testing getFirstNEmployeesByBonus (int n)
//                ps.getFirstNEmployeesByBonus(Integer.parseInt(sc.nextLine())).forEach(System.out::println);
//                break;
//        }
//
//    }
//}