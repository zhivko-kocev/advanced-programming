package mk.ukim.finki.AdvancedTasks;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

class NonExistingItemException extends Exception{
        private final int id;

    public NonExistingItemException(int id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("Item with id %d doesn't exist",id);
    }
}

abstract class Archive{
    protected int id;

    public Archive(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract String open(LocalDate date);
}

class LockedArchive extends Archive{

    private final LocalDate dateToOpen;

    public LockedArchive(int id, LocalDate dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }

    @Override
    public String open(LocalDate date) {
        if(date.isAfter(dateToOpen)){
            return String.format("Item %d opened at %s\n",getId(),date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }else{
            return String.format("Item %d cannot be opened before %s\n",getId(),dateToOpen.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
    }
}

class SpecialArchive extends Archive{

    private final int maxOpen;

    private int openedTimes;


    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
        this.openedTimes=0;
    }

    public int getMaxOpen() {
        return maxOpen;
    }

    @Override
    public String open(LocalDate date) {
        if(openedTimes==maxOpen){
            return String.format("Item %d cannot be opened more than %d times\n",getId(),getMaxOpen());
        }else{
            openedTimes++;
            return String.format("Item %d opened at %s\n",getId(),date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        }
    }
}
class ArchiveStore{

    private final List<Archive> archives;

    private final StringBuilder history;

    public ArchiveStore() {
        this.archives=new ArrayList<>();
        this.history=new StringBuilder();
    }

    void archiveItem(Archive item, LocalDate date){
        archives.add(item);
        history.append(String.format("Item %d archived at %s\n",item.getId(),date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
    }
    void openItem(int id, LocalDate date)throws NonExistingItemException{
        Optional<Archive> item=archives.stream().filter(a->a.getId()==id).findAny();
        if(item.isPresent()){
            history.append(item.get().open(date));
        }else{
            throw new NonExistingItemException(id);
        }
    }
    String getLog(){
        return history.toString();
    }
}

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        LocalDate date = LocalDate.of(2013, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();

            LocalDate dateToOpen = date.atStartOfDay().plusSeconds(days * 24 * 60 * 60).toLocalDate();
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while(scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch(NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}