//package mk.ukim.finki.AdvancedTasks2;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.*;
//
//class UserIdAlreadyExistsException extends Exception{
//    public UserIdAlreadyExistsException(String message) {
//        super(message);
//    }
//}
//interface ILocation{
//    double getLongitude();
//
//    double getLatitude();
//
//    LocalDateTime getTimestamp();
//}
//
//class User {
//
//    private final String name;
//    private final String id;
//
//    public User(String name, String id) {
//        this.name = name;
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getId() {
//        return id;
//    }
//    @Override
//    public String toString() {
//        return "User{" +
//                "name='" + name + '\'' +
//                ", id='" + id + '\'' +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        mk.ukim.finki.AdvancedTasks2.User user = (mk.ukim.finki.AdvancedTasks2.User) o;
//        return Objects.equals(name, user.name) && Objects.equals(id, user.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, id);
//    }
//}
//
//class StopCoronaApp{
//    private Map<User,List<ILocation>> users;
//
//    public StopCoronaApp() {
//        this.users=new HashMap<>();
//    }
//
//
//    public void addUser(String name, String id) throws UserIdAlreadyExistsException {
//        User toAdd = new User(name,id);
//        if(users.containsKey(toAdd)){
//            throw new UserIdAlreadyExistsException("");
//        }
//
//        users.putIfAbsent(toAdd,new ArrayList<>());
//    }
//}
//
//public class StopCoronaTest {
//
//    public static double timeBetweenInSeconds(ILocation location1, ILocation location2) {
//        return Math.abs(Duration.between(location1.getTimestamp(), location2.getTimestamp()).getSeconds());
//    }
//
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//
//        StopCoronaApp stopCoronaApp = new StopCoronaApp();
//
//        while (sc.hasNext()) {
//            String line = sc.nextLine();
//            String[] parts = line.split("\\s+");
//
//            switch (parts[0]) {
//                case "REG": //register
//                    String name = parts[1];
//                    String id = parts[2];
//                    try {
//                        stopCoronaApp.addUser(name, id);
//                    } catch (UserAlreadyExistException e) {
//                        System.out.println(e.getMessage());
//                    }
//                    break;
//                case "LOC": //add locations
//                    id = parts[1];
//                    List<ILocation> locations = new ArrayList<>();
//                    for (int i = 2; i < parts.length; i += 3) {
//                        locations.add(createLocationObject(parts[i], parts[i + 1], parts[i + 2]));
//                    }
//                    stopCoronaApp.addLocations(id, locations);
//
//                    break;
//                case "DET": //detect new cases
//                    id = parts[1];
//                    LocalDateTime timestamp = LocalDateTime.parse(parts[2]);
//                    stopCoronaApp.detectNewCase(id, timestamp);
//
//                    break;
//                case "REP": //print report
//                    stopCoronaApp.createReport();
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    private static ILocation createLocationObject(String lon, String lat, String timestamp) {
//        return new ILocation() {
//            @Override
//            public double getLongitude() {
//                return Double.parseDouble(lon);
//            }
//
//            @Override
//            public double getLatitude() {
//                return Double.parseDouble(lat);
//            }
//
//            @Override
//            public LocalDateTime getTimestamp() {
//                return LocalDateTime.parse(timestamp);
//            }
//        };
//    }
//}
//
