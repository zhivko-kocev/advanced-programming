package mk.ukim.finki.AdvancedTasks2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

class Item1{

    private final int price;
    private final int discountPrice;

    private final int discount;

    public Item1(String discountPrice, String price) {
        this.price = Integer.parseInt(price);
        this.discountPrice = Integer.parseInt(discountPrice);
        this.discount= (int) Math.floor(100-((double) (this.discountPrice * 100) /this.price));
    }

    public int getDiscount() {
        return discount;
    }

    public int totalDiscount(){
        return price-discountPrice;
    }

    @Override
    public String toString() {
        return String.format("%2d%% %d/%d",discount,discountPrice,price);
    }
}

class Store{

    private final String name;
    private final List<Item1> items;

    public Store(String name, List<Item1> items) {
        this.name = name;
        this.items = items;
    }

    public double averageDiscount(){
        return items.stream().mapToDouble(Item1::getDiscount).average().getAsDouble();
    }

    public int totalDiscount(){
        return items.stream().mapToInt(Item1::totalDiscount).sum();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(name).append("\n");
        sb.append(String.format("Average discount: %.1f%%\n",averageDiscount()));
        sb.append("Total discount: ").append(totalDiscount()).append("\n");
        List<Item1> tmp=items.stream().sorted(Comparator.comparingInt(Item1::getDiscount).thenComparing(Item1::totalDiscount).reversed()).collect(Collectors.toList());
        for (int i = 0; i < tmp.size()-1; i++) {
            sb.append(tmp.get(i)).append("\n");
        }
        sb.append(tmp.get(tmp.size()-1));
        return sb.toString();
    }
}

class Discounts{
    private final List<Store> stores;

    public Discounts() {
        this.stores = new ArrayList<>();
    }

    public List<Store> byAverageDiscount(){
        List<Store> s= stores.stream().sorted(Comparator.comparingDouble(Store::averageDiscount).thenComparing(Store::getName)).skip(stores.size()-3).collect(Collectors.toList());
        Collections.reverse(s);
        return s;
    }
    public List<Store> byTotalDiscount(){
        List<Store> s= stores.stream().sorted(Comparator.comparingInt(Store::totalDiscount).thenComparing(Store::getName).reversed()).skip(stores.size()-3).collect(Collectors.toList());
        Collections.reverse(s);
        return s;
    }
    public int readStores(InputStream is){
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                String name = line.split("\\s+")[0];
                List<Item1> objItems = Arrays.stream(line.split("\\s+")).skip(1).map(s->new Item1(s.split(":")[0],s.split(":")[1])).collect(Collectors.toList());
                stores.add(new Store(name,objItems));
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return stores.size();
    }

}
public class DiscountsTest {
    public static void main(String[] args){
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    }
}