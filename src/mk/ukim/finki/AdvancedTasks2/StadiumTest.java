package mk.ukim.finki.AdvancedTasks2;

import java.util.*;
import java.util.stream.IntStream;

class SeatTakenException extends Exception{
    public SeatTakenException() {
        super();
    }
}

class SeatNotAllowedException extends Exception{
    public SeatNotAllowedException() {
        super();
    }
}

class Sector{
    private int TYPE;
    private final String id;
    private final int seats;
    private final Set<Integer> takenSeats;

    public Sector(String id, int seats) {
        this.id = id;
        this.seats = seats;
        this.takenSeats = new HashSet<>();
    }

    public void addSeat(int seat , int type) throws SeatNotAllowedException, SeatTakenException {
        if(takenSeats.isEmpty() || TYPE==0){
            TYPE=type;
            takenSeats.add(seat);
        }else{
            if(takenSeats.contains(seat)){
                throw new SeatTakenException();
            }
            if(type!=TYPE && type!=0){
                throw new SeatNotAllowedException();
            }
            takenSeats.add(seat);

        }
    }

    public String getId() {
        return id;
    }
    public double percentage(){
        return ((takenSeats.size()/(double)seats)*100);
    }


    @Override
    public String toString() {
        return String.format("%s\t%d/%d\t%.1f%%",id,seats-takenSeats.size(),seats,percentage());
    }
}
class Stadium{
    private final Map<String,Sector> sectorsMap;

    public Stadium(String name) {
        this.sectorsMap=new HashMap<>();
    }

    public void createSectors(String[] sectorNames, int[] sectorSizes) {
        IntStream.range(0,sectorSizes.length).forEach(i->sectorsMap.put(sectorNames[i],new Sector(sectorNames[i],sectorSizes[i])));
    }

    public void buyTicket(String sectorName, int seat, int type) throws SeatNotAllowedException, SeatTakenException {
        sectorsMap.get(sectorName).addSeat(seat,type);
    }

    public void showSectors() {
       sectorsMap.values().stream().sorted(Comparator.comparing(Sector::percentage).thenComparing(Sector::getId)).forEach(System.out::println);
    }
}
public class StadiumTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}

