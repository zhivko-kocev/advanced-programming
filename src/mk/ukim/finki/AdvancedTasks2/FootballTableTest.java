package mk.ukim.finki.AdvancedTasks2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Partial exam II 2016/2017
 */
class Team {
    private final String name;
    private int wins;
    private int loses;
    private int draws;
    private int goalsScored;
    private int goalsConceded;

    public Team(String name) {
        this.name = name;
        this.wins = 0;
        this.draws = 0;
        this.loses = 0;
        this.goalsConceded = 0;
        this.goalsScored = 0;
    }

    public void win(int goalsScored, int goalsConceded) {
        this.wins++;
        computeGoals(goalsScored, goalsConceded);
    }

    public void draw(int goalsScored, int goalsConceded) {
        this.draws++;
        computeGoals(goalsScored, goalsConceded);
    }

    public void lose(int goalsScored, int goalsConceded) {
        this.loses++;
        computeGoals(goalsScored, goalsConceded);
    }

    private void computeGoals(int goalsScored, int goalsConceded) {
        this.goalsScored += goalsScored;
        this.goalsConceded += goalsConceded;
    }

    public int total(){
        return wins * 3 + draws;
    }

    public int goalDiff(){
        return goalsScored - goalsConceded;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%-15s%5d%5d%5d%5d%5d",name,wins+draws+loses,wins,draws,loses,total());
    }
}

class FootballTable {
    private final Map<String, Team> teams;

    public FootballTable() {
        this.teams = new HashMap<>();
    }


    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        teams.putIfAbsent(homeTeam,new Team(homeTeam));
        teams.putIfAbsent(awayTeam,new Team(awayTeam));
        if (homeGoals==awayGoals){
            teams.get(homeTeam).draw(homeGoals,awayGoals);
            teams.get(awayTeam).draw(awayGoals,homeGoals);
        }else if(homeGoals>awayGoals){
            teams.get(homeTeam).win(homeGoals,awayGoals);
            teams.get(awayTeam).lose(awayGoals,homeGoals);
        }else{
            teams.get(homeTeam).lose(homeGoals,awayGoals);
            teams.get(awayTeam).win(awayGoals,homeGoals);
        }
    }

    public void printTable() {
        AtomicInteger i =new AtomicInteger(1);
        teams.values()
                .stream()
                .sorted(Comparator.comparing(Team::total)
                        .thenComparing(Team::goalDiff)
                        .reversed()
                        .thenComparing(Team::getName))
                .forEach(team -> System.out.printf("%2d. %s\n",i.getAndIncrement(),team));
    }
}

public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

// Your code here


