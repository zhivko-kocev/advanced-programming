package mk.ukim.finki.AdvancedTasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Round1 {
    private List<Integer> attacker;
    private List<Integer> defender;

    public Round1(String line) {
        attacker = new ArrayList<>();
        defender = new ArrayList<>();
        String[] splited = line.split(";");
        String[] att = splited[0].split("\\s+");
        String[] def = splited[1].split("\\s+");
        for (int i = 0; i < 3; i++) {
            attacker.add(Integer.parseInt(att[i]));
            defender.add(Integer.parseInt(def[2 - i]));
        }
        attacker = attacker.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        defender = defender.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());

    }
    public void soldiersLeft(){
        AtomicInteger attSoldiers= new AtomicInteger(3);
        AtomicInteger defSoldiers= new AtomicInteger(3);
        IntStream.range(0,3).forEach(i->{
            if(attacker.get(i)>defender.get(i)){
                defSoldiers.getAndDecrement();
            }else {
                attSoldiers.getAndDecrement();
            }
        });
        System.out.println(attSoldiers+" "+defSoldiers);
    }
}
class Risk1{
    public void processAttacksData(InputStream in) {
        BufferedReader br=new BufferedReader(new InputStreamReader(in));
        br.lines().map(Round1::new).forEach(Round1::soldiersLeft);

    }
}
public class RiskTester1 {
    public static void main(String[] args) {
        Risk1 risk = new Risk1();
        risk.processAttacksData(System.in);
    }
}
