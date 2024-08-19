package mk.ukim.finki.AdvancedTasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Round{
    private List<Integer> attacker;
    private  List<Integer> defender;

    public Round(String line) {
        attacker=new ArrayList<>();
        defender=new ArrayList<>();
        String [] splited=line.split(";");
        String [] att=splited[0].split("\\s+");
        String [] def=splited[1].split("\\s+");
        for (int i = 0; i < 3; i++) {
            attacker.add(Integer.parseInt(att[i]));
            defender.add(Integer.parseInt(def[2-i]));
        }
        attacker=attacker.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        defender=defender.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());

    }

    public boolean hasWon(){
        return IntStream.range(0,3).allMatch(i->attacker.get(i)>defender.get(i));
    }
}

class Risk{

    public int processAttacksData(InputStream in) {
        BufferedReader br=new BufferedReader(new InputStreamReader(in));
        return (int) br.lines().map(Round::new).filter(Round::hasWon).count();
    }
}

public class RiskTester {
    public static void main(String[] args) {

        Risk risk = new Risk();

        System.out.println(risk.processAttacksData(System.in));

    }
}