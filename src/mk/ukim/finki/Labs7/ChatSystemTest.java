//package mk.ukim.finki.Labs7;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.*;
//import java.util.TreeSet;
//import java.util.stream.Collectors;
//
//class ChatRoom {
//    private final String name;
//    private final List<String> users;
//
//    public ChatRoom(String name) {
//        this.name = name;
//        this.users = new ArrayList<>();
//    }
//
//    public void addUser(String username) {
//        users.add(username);
//    }
//
//    public void removeUser(String username) {
//        users.remove(username);
//    }
//
//    public boolean hasUser(String username) {
//        return users.contains(username);
//    }
//
//    public int numUsers() {
//        return users.size();
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    @Override
//    public String toString() {
//        return (users.isEmpty()) ? String.format("%s\nEMPTY", name) : String.format("%s\n%s", name,
//                String.join("\n", users)
//        );
//    }
//}
//
//class ChatSystem {
//    private Map<String, ChatRoom> chatRooms;
//    private List<String> users;
//
//    public ChatSystem() {
//        this.chatRooms = new TreeMap<>();
//        this.users = new ArrayList<>();
//    }
//
//    public void addRoom(String roomName) {
//        chatRooms.put(roomName, new ChatRoom(roomName));
//    }
//
//    public void removeRoom(String roomName) {
//        chatRooms.remove(roomName);
//    }
//
//    public ChatRoom getRoom(String roomName) {
//        ChatRoom c = chatRooms.getOrDefault(roomName, null);
//        if (c == null) {
//            throw new NoSuchRoomException();
//        }
//        return c;
//    }
//
//}
//
//public class ChatSystemTest {
//
//    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
//        Scanner jin = new Scanner(System.in);
//        int k = jin.nextInt();
//        if (k == 0) {
//            ChatRoom cr = new ChatRoom(jin.next());
//            int n = jin.nextInt();
//            for (int i = 0; i < n; ++i) {
//                k = jin.nextInt();
//                if (k == 0) cr.addUser(jin.next());
//                if (k == 1) cr.removeUser(jin.next());
//                if (k == 2) System.out.println(cr.hasUser(jin.next()));
//            }
//            System.out.println();
//            System.out.println(cr);
//            n = jin.nextInt();
//            if (n == 0) return;
//            ChatRoom cr2 = new ChatRoom(jin.next());
//            for (int i = 0; i < n; ++i) {
//                k = jin.nextInt();
//                if (k == 0) cr2.addUser(jin.next());
//                if (k == 1) cr2.removeUser(jin.next());
//                if (k == 2) cr2.hasUser(jin.next());
//            }
//            System.out.println(cr2);
//        }
//        if (k == 1) {
//            ChatSystem cs = new ChatSystem();
//            Method[] mts = cs.getClass().getMethods();
//            while (true) {
//                String cmd = jin.next();
//                if (cmd.equals("stop")) break;
//                if (cmd.equals("print")) {
//                    System.out.println(cs.getRoom(jin.next()) + "\n");
//                    continue;
//                }
//                for (Method m : mts) {
//                    if (m.getName().equals(cmd)) {
//                        String[] params = new String[m.getParameterTypes().length];
//                        for (int i = 0; i < params.length; ++i) params[i] = jin.next();
//                        m.invoke(cs, params);
//                    }
//                }
//            }
//        }
//    }
//
//}
//
