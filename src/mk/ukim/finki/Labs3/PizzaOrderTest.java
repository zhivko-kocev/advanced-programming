package mk.ukim.finki.Labs3;

import java.util.*;
import java.util.stream.IntStream;
class InvalidExtraTypeException extends Exception{
    public InvalidExtraTypeException() {
    }
}
class InvalidPizzaTypeException extends Exception{
    public InvalidPizzaTypeException() {
    }
}
class  ItemOutOfStockException extends Exception{
    public ItemOutOfStockException(Item item) {
    }
}
class ArrayIndexOutOfBоundsException extends Exception{
    public ArrayIndexOutOfBоundsException(int index) {
    }
}
class EmptyOrder extends Exception{
    public EmptyOrder() {
    }
}
class OrderLockedException extends Exception{
    public OrderLockedException() {
    }
}
interface Item {
    int getPrice();

    String getName();

    void setCount(int count);

    int getCount();
}

class PizzaItem implements Item {
    String name;
    int count;

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public PizzaItem(String name) throws InvalidPizzaTypeException {
        if (name.equals("Standard") || name.equals("Pepperoni") || name.equals("Vegetarian")) {
            this.name = name;
        } else {
            throw new InvalidPizzaTypeException();
        }
    }

    @Override
    public int getPrice() {
        if (this.name.equals("Standard")) {
            return 10;
        } else if (this.name.equals("Pepperoni")) {
            return 12;
        } else {
            return 8;
        }
    }

    @Override
    public String getName() {
        return name;
    }

}

class ExtraItem implements Item {
    String name;
    int count;

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public ExtraItem(String name) throws InvalidExtraTypeException {
        if (name.equals("Coke") || name.equals("Ketchup")) {
            this.name = name;
        } else {
            throw new InvalidExtraTypeException();
        }

    }

    @Override
    public int getPrice() {
        if (this.name.equals("Coke")) {
            return 5;
        } else {
            return 3;
        }
    }

    @Override
    public String getName() {
        return name;
    }
}

class Order {
    LinkedList<Item> items;
    boolean locked;

    public Order() {
        items = new LinkedList<>();
        locked = false;
    }
    private int isPresent(Item item){
        for (int i = 0; i < items.size(); i++) {
            if(item.getName().equals(items.get(i).getName())){
                return i;
            }
        }
        return -1;
    }
    public void addItem(Item item, int count) throws OrderLockedException, ItemOutOfStockException {
        if (locked) {
            throw new OrderLockedException();
        }
        if (count > 10) {
            throw new ItemOutOfStockException(item);
        }
        if (isPresent(item)>-1) {
            items.get(isPresent(item)).setCount(count);
        } else {
            item.setCount(count);
            items.add(item);
        }
    }

    public void removeItem(int index) throws OrderLockedException, ArrayIndexOutOfBоundsException {
        if (locked) {
            throw new OrderLockedException();
        }
        if (index > items.size()) {
            throw new ArrayIndexOutOfBоundsException(index);
        }
        items.remove(index);
    }

    public void lock() throws EmptyOrder {
        if(items.isEmpty()){
            throw new EmptyOrder();
        }
        locked = true;
    }

    public int getPrice() {
        int sum = 0;
        for (Item item : items) {
            sum += item.getPrice() * item.getCount();
        }
        return sum;
    }

    public void displayOrder() {
        IntStream.range(0,items.size()).forEach(i -> {
            Item item=items.get(i);
            System.out.printf("%3d.%-15sx%2d%5d$\n",i+1,item.getName(),item.getCount(),item.getPrice()*item.getCount());
        });
        System.out.printf("%-22s%5d$\n","Total:",getPrice());
    }
}

public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}