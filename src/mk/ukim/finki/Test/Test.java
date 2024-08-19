package mk.ukim.finki.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Test {
    public static void main(String[] args) {
        // Пример податоци за тимовите (реден број, име, одиграни натпревари, победи, нерешени, поени)
        Object[][] teamsData = {
                {1, "Тим1", 10, 7, 2, 23},
                {2, "Тим2", 10, 6, 3, 21},
                {3, "Тим3", 10, 5, 1, 16},
                {4, "Тим4", 10, 4, 4, 16},
                {5, "Тим5", 10, 3, 3, 12}
        };

        // Форматирање и печатење на табелата
        printTable(teamsData);
    }

    public static void printTable(Object[][] data) {
        // Формат за секој ред во табелата
        String rowFormat = "|%4d  |%-15s  |%17d  |%7d  |%8d  |%7d  |\n";

        // Печатење на заглавието на табелата
        System.out.println("+------+------+-----------------+-----------------+--------+-------+--------+");
        System.out.printf("| Ред  | Тим  | Одиграни натпревари | Победи           | Нерешени | Поени  |\n");
        System.out.println("+------+------+-----------------+-----------------+--------+-------+--------+");

        // Печатење на податоците за секој тим
        for (Object[] row : data) {
            System.out.format(rowFormat, row);
        }

        // Печатење на долниот дел на табелата
        System.out.println("+------+------+-----------------+-----------------+--------+-------+--------+");
    }
}

