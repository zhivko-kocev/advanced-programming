//package mk.ukim.finki.AdvancedTasks2;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//class DeliveryPerson{
//    private final String id;
//    private final String name;
//    private Location location;
//    private final List<Float> orders;
//
//    public DeliveryPerson(String id, String name, Location location) {
//        this.id = id;
//        this.name = name;
//        this.location = location;
//        this.orders=new ArrayList<>();
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Location getLocation() {
//        return location;
//    }
//
//    public void setLocation(Location location) {
//        this.location = location;
//    }
//    public void getPaid(Float price){
//        orders.add(price);
//    }
//    public double sum(){
//        return orders.stream().mapToDouble(Float::doubleValue).sum();
//    }
//    public double average(){
//        return orders.stream().mapToDouble(Float::doubleValue).average().orElse(0);
//    }
//
//    @Override
//    public String toString() {
//        return String.format("ID: %s Name: %s Total deliveries: %d Total delivery fee: %.2f Average delivery fee: %.2f",id,name,orders.size(),sum(),average());
//    }
//}
//
//class Restaurant{
//    private final String id;
//    private final String name;
//    private final Location location;
//
//    private final List<Float> prices;
//
//    public Restaurant(String id, String name, Location location) {
//        this.id = id;
//        this.name = name;
//        this.location = location;
//        this.prices=new ArrayList<>();
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Location getLocation() {
//        return location;
//    }
//    public void addPrice(Float price){
//        prices.add(price);
//    }
//    public double average(){
//        return prices.stream().mapToDouble(Float::doubleValue).average().orElse(0);
//    }
//    public double sum(){
//        return prices.stream().mapToDouble(Float::doubleValue).sum();
//    }
//    @Override
//    public String toString() {
//        return String.format("ID: %s Name: %s Total orders: %d Total amount earned: %.2f Average amount earned: %.2f",id,name,prices.size(),sum(),average());
//    }
//}
//
//class User{
//    private final String id;
//    private final String name;
//    private final Map<String,Location> addresses;
//    private final List<Float> spent;
//
//    public User(String id, String name) {
//        this.id = id;
//        this.name = name;
//        this.addresses=new HashMap<>();
//        this.spent=new ArrayList<>();
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Map<String, Location> getAddresses() {
//        return addresses;
//    }
//    public void addAddress(String addressName, Location location){
//        addresses.put(addressName,location);
//    }
//    public void pay(Float price){
//        spent.add(price);
//    }
//    public double sum(){
//        return spent.stream().mapToDouble(Float::doubleValue).sum();
//    }
//    public double average(){
//        return spent.stream().mapToDouble(Float::doubleValue).average().orElse(0);
//    }
//
//    @Override
//    public String toString() {
//        return String.format("ID: %s Name: %s Total orders: %d Total amount spent: %.2f Average amount spent: %.2f",id,name,spent.size(),sum(),average());
//    }
//}
//
//class DeliveryApp{
//    private final String name;
//    private final Map<String,DeliveryPerson> deliveryPeople;
//    private final Map<String,Restaurant> restaurants;
//    private final Map<String,User> users;
//
//    public DeliveryApp(String name) {
//        this.name = name;
//        this.deliveryPeople=new HashMap<>();
//        this.restaurants=new HashMap<>();
//        this.users=new HashMap<>();
//    }
//
//    public void registerDeliveryPerson(String id, String name, Location location) {
//        deliveryPeople.put(id,new DeliveryPerson(id,name,location));
//    }
//
//    public void addRestaurant(String id, String name, Location location) {
//        restaurants.put(id,new Restaurant(id,name,location));
//    }
//
//    public void addUser(String id, String name) {
//        users.put(id,new User(id,name));
//    }
//
//    public void addAddress(String id, String addressName, Location location) {
//        users.get(id).addAddress(addressName,location);
//    }
//
//    public void orderFood(String userId, String userAddressName, String restaurantId, float cost) {
//        DeliveryPerson closest=deliveryPeople.values().stream().sorted(Comparator.comparing(d->Math.abs(d.getLocation().distance(restaurants.get(restaurantId).getLocation())))).collect(Collectors.toList()).get(0);
//        closest.setLocation(users.get(userId).getAddresses().get(userAddressName));
//        closest.getPaid((float) (90+(restaurants.get(restaurantId).getLocation().distance(users.get(userId).getAddresses().get(userAddressName))/10)*10));
//        closest.setLocation(users.get(userId).getAddresses().get(userAddressName));
//        users.get(userId).pay(cost);
//        restaurants.get(restaurantId).addPrice(cost);
//
//    }
//
//    public void printUsers() {
//        users.values().stream().sorted(Comparator.comparing(User::sum).thenComparing(User::getName).reversed()).forEach(System.out::println);
//    }
//
//    public void printRestaurants() {
//        restaurants.values().stream().sorted(Comparator.comparing(Restaurant::average).thenComparing(Restaurant::getName).reversed()).forEach(System.out::println);
//    }
//
//    public void printDeliveryPeople() {
//        deliveryPeople.values().stream().sorted(Comparator.comparing(DeliveryPerson::sum).reversed()).forEach(System.out::println);
//    }
//}
//interface Location {
//    int getX();
//
//    int getY();
//
//    default int distance(Location other) {
//        int xDiff = Math.abs(getX() - other.getX());
//        int yDiff = Math.abs(getY() - other.getY());
//        return xDiff + yDiff;
//    }
//}
//
//class LocationCreator {
//    public static Location create(int x, int y) {
//
//        return new Location() {
//            @Override
//            public int getX() {
//                return x;
//            }
//
//            @Override
//            public int getY() {
//                return y;
//            }
//        };
//    }
//}
//
//public class DeliveryAppTester {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        String appName = sc.nextLine();
//        DeliveryApp app = new DeliveryApp(appName);
//        while (sc.hasNextLine()) {
//            String line = sc.nextLine();
//            String[] parts = line.split(" ");
//
//            if (parts[0].equals("addUser")) {
//                String id = parts[1];
//                String name = parts[2];
//                app.addUser(id, name);
//            } else if (parts[0].equals("registerDeliveryPerson")) {
//                String id = parts[1];
//                String name = parts[2];
//                int x = Integer.parseInt(parts[3]);
//                int y = Integer.parseInt(parts[4]);
//                app.registerDeliveryPerson(id, name, LocationCreator.create(x, y));
//            } else if (parts[0].equals("addRestaurant")) {
//                String id = parts[1];
//                String name = parts[2];
//                int x = Integer.parseInt(parts[3]);
//                int y = Integer.parseInt(parts[4]);
//                app.addRestaurant(id, name, LocationCreator.create(x, y));
//            } else if (parts[0].equals("addAddress")) {
//                String id = parts[1];
//                String name = parts[2];
//                int x = Integer.parseInt(parts[3]);
//                int y = Integer.parseInt(parts[4]);
//                app.addAddress(id, name, LocationCreator.create(x, y));
//            } else if (parts[0].equals("orderFood")) {
//                String userId = parts[1];
//                String userAddressName = parts[2];
//                String restaurantId = parts[3];
//                float cost = Float.parseFloat(parts[4]);
//                app.orderFood(userId, userAddressName, restaurantId, cost);
//            } else if (parts[0].equals("printUsers")) {
//                app.printUsers();
//            } else if (parts[0].equals("printRestaurants")) {
//                app.printRestaurants();
//            } else {
//                app.printDeliveryPeople();
//            }
//
//        }
//    }
//}
//
