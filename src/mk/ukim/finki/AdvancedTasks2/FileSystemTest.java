package mk.ukim.finki.AdvancedTasks2;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class File {
    private final String name;
    private final int size;
    private final LocalDateTime createdAt;

    public static Comparator<File> cmp = Comparator.comparing(File::getCreatedAt).thenComparing(File::getName).thenComparing(File::getSize);

    public File(String name, int size, LocalDateTime createdAt) {
        this.name = name;
        this.size = size;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return String.format("%-10s %5dB %s", name, size, createdAt.toString());
    }
}

class FileSystem {

    private final Map<Character, Set<File>> folders;

    public FileSystem() {
        this.folders = new HashMap<>();
    }

    public void addFile(char folder, String name, int size, LocalDateTime createdAt) {
        folders.computeIfAbsent(folder, s -> new TreeSet<>(File.cmp));
        folders.get(folder).add(new File(name, size, createdAt));
    }

    public List<File> findAllHiddenFilesWithSizeLessThen(int size) {
        return folders.values().stream().flatMap(Collection::stream).filter(f -> f.getSize() < size && f.getName().startsWith(".")).collect(Collectors.toList());
    }

    public int totalSizeOfFilesFromFolders(List<Character> folders) {
        return folders.stream().mapToInt(f -> this.folders.get(f).stream().mapToInt(File::getSize).sum()).sum();
    }

    public Map<Integer, Set<File>> byYear() {
        return folders.values().stream().flatMap(Collection::stream).collect(Collectors.groupingBy(f -> f.getCreatedAt().getYear(), Collectors.toSet()));
    }

    public Map<String, Long> sizeByMonthAndDay() {
        return folders.values().stream().flatMap(Collection::stream).collect(Collectors.groupingBy(f -> f.getCreatedAt().getMonth().name() + "-" + f.getCreatedAt().getDayOfMonth(), Collectors.toList())).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, l -> (long) l.getValue().stream().mapToInt(File::getSize).sum()));
    }
}


public class FileSystemTest {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            fileSystem.addFile(parts[0].charAt(0), parts[1],
                    Integer.parseInt(parts[2]),
                    LocalDateTime.of(2016, 12, 29, 0, 0, 0).minusDays(Integer.parseInt(parts[3]))
            );
        }
        int action = scanner.nextInt();
        if (action == 0) {
            scanner.nextLine();
            int size = scanner.nextInt();
            System.out.println("== Find all hidden files with size less then " + size);
            List<File> files = fileSystem.findAllHiddenFilesWithSizeLessThen(size);
            files.forEach(System.out::println);
        } else if (action == 1) {
            scanner.nextLine();
            String[] parts = scanner.nextLine().split(":");
            System.out.println("== Total size of files from folders: " + Arrays.toString(parts));
            int totalSize = fileSystem.totalSizeOfFilesFromFolders(Arrays.stream(parts)
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toList()));
            System.out.println(totalSize);
        } else if (action == 2) {
            System.out.println("== Files by year");
            Map<Integer, Set<File>> byYear = fileSystem.byYear();
            byYear.keySet().stream().sorted()
                    .forEach(key -> {
                        System.out.printf("Year: %d\n", key);
                        Set<File> files = byYear.get(key);
                        files.stream()
                                .sorted(File.cmp)
                                .forEach(System.out::println);
                    });
        } else if (action == 3) {
            System.out.println("== Size by month and day");
            Map<String, Long> byMonthAndDay = fileSystem.sizeByMonthAndDay();
            byMonthAndDay.keySet().stream().sorted()
                    .forEach(key -> System.out.printf("%s -> %d\n", key, byMonthAndDay.get(key)));
        }
        scanner.close();
    }
}

// Your code here

