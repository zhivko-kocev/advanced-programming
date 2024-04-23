package mk.ukim.finki.AdvancedTasks;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class FileNameExistsException extends Exception {
    private final String nameFile;
    private final String nameFolder;

    public FileNameExistsException(String nameFile, String nameFolder) {
        this.nameFile = nameFile;
        this.nameFolder = nameFolder;
    }

    @Override
    public String getMessage() {
        return String.format("There is already a file named %s in the folder %s", nameFile, nameFolder);
    }
}

interface IFile {
    String getFileName();

    long getFileSize();

    String getFileInfo(String indent);

    void sortBySize();

    long findLargestFile();

}

class File implements IFile {

    protected final String name;
    protected long size;

    public File(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
        return size;
    }

    @Override
    public String getFileInfo(String indent) {
        return String.format("File name:%11s File size:%11d\n", name, size);
    }

    @Override
    public void sortBySize() {

    }

    @Override
    public long findLargestFile() {
        return size;
    }
}

class Folder extends File implements IFile {

    private List<IFile> files;

    public Folder(String name) {
        super(name, 0);
        this.files = new ArrayList<>();
    }

    void addFile(IFile file) throws FileNameExistsException {
        if (files.stream().anyMatch(f -> f.getFileName().equals(file.getFileName()))) {
            throw new FileNameExistsException(file.getFileName(), name);
        }
        files.add(file);
        size += file.getFileSize();
    }

    @Override
    public String getFileInfo(String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Folder name:%11s Folder size:%11d\n", name, size));
        files.forEach(f -> sb.append(indent + "    ").append(f.getFileInfo(indent + "    ")));

        return sb.toString();
    }

    @Override
    public void sortBySize() {
        files.stream().forEach(IFile::sortBySize);
        files = files.stream().sorted(Comparator.comparingLong(IFile::getFileSize)).collect(Collectors.toList());
    }

    @Override
    public long findLargestFile() {
        return files.stream().mapToLong(IFile::findLargestFile).max().orElse(0);
    }

    public List<IFile> getFiles() {
        return files;
    }
}

class FileSystem {

    private final Folder root;

    public FileSystem() {
        this.root = new Folder("root");
    }

    void addFile(IFile file) throws FileNameExistsException {

        root.addFile(file);
    }

    long findLargestFile() {
        return root.findLargestFile();
    }

    void sortBySize() {
        root.sortBySize();
    }

    @Override
    public String toString() {
        return root.getFileInfo("");
    }
}

public class FileSystemTest {
    public static Folder readFolder(Scanner sc) {

        Folder folder = new Folder(sc.nextLine());
        int totalFiles = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < totalFiles; i++) {
            String line = sc.nextLine();

            if (line.startsWith("0")) {
                String fileInfo = sc.nextLine();
                String[] parts = fileInfo.split("\\s+");
                try {
                    folder.addFile(new File(parts[0], Long.parseLong(parts[1])));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try {
                    folder.addFile(readFolder(sc));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return folder;
    }

    public static void main(String[] args) {

        //file reading from input

        Scanner sc = new Scanner(System.in);

        System.out.println("===READING FILES FROM INPUT===");
        FileSystem fileSystem = new FileSystem();
        try {
            fileSystem.addFile(readFolder(sc));
        } catch (FileNameExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("===PRINTING FILE SYSTEM INFO===");
        System.out.println(fileSystem);

        System.out.println("===PRINTING FILE SYSTEM INFO AFTER SORTING===");
        fileSystem.sortBySize();
        System.out.println(fileSystem);

        System.out.println("===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===");
        System.out.println(fileSystem.findLargestFile());


    }
}

