package mk.ukim.finki.Labs6;

import java.util.*;

class SuperString {

    private LinkedList<String> strings;

    Stack<String> lastAdded;

    public SuperString() {
        this.strings = new LinkedList<>();
        this.lastAdded = new Stack<>();
    }

    public void append(String s) {
        strings.add(s);
        lastAdded.push(s);
    }

    public void insert(String s) {
        strings.add(0, s);
        lastAdded.push(s);
    }

    public boolean contains(String s) {
        if (strings.isEmpty()) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        strings.forEach(sb::append);
        return sb.toString().contains(s);
    }

    private String reverseString(String string) {
        return new StringBuilder(string).reverse().toString();
    }

    public void reverse() {
        if (strings.isEmpty()) {
            return;
        }
        LinkedList<String> tmp = new LinkedList<>();
        Stack<String> tmpStack = new Stack<>();
        for (String element : lastAdded) {
            tmpStack.push(reverseString(element));
        }
        Collections.reverse(strings);
        for (String string : strings) {
            tmp.add(reverseString(string));

        }
        lastAdded = tmpStack;
        strings = tmp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        strings.forEach(sb::append);
        return sb.toString();
    }

    public void removeLast(int k) {
        for (int i = 0; i < k; i++) {
            strings.remove(lastAdded.pop());
        }
    }
}

public class SuperStringTest {
    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) {
            SuperString s = new SuperString();
            while (true) {
                int command = jin.nextInt();
                if (command == 0) {//append(String s)
                    s.append(jin.next());
                }
                if (command == 1) {//insert(String s)
                    s.insert(jin.next());
                }
                if (command == 2) {//contains(String s)
                    System.out.println(s.contains(jin.next()));
                }
                if (command == 3) {//reverse()
                    s.reverse();
                }
                if (command == 4) {//toString()
                    System.out.println(s);
                }
                if (command == 5) {//removeLast(int k)
                    s.removeLast(jin.nextInt());
                }
                if (command == 6) {//end
                    break;
                }
            }
        }
    }
}
