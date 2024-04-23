package mk.ukim.finki.AdvancedTasks;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class InvalidOperationException extends Exception{
    public InvalidOperationException(String message) {
        super(message);
    }
}

abstract class Item{
    protected int id;

    protected String name;
    protected int productPrice;

    public Item(String id, String name, String productPrice) {
        this.id = Integer.parseInt(id);
        this.name = name;
        this.productPrice = Integer.parseInt(productPrice);
    }

    public static Item createItem(String type,String id, String name, String productPrice,String quantity) throws InvalidOperationException {
        if(type.equals("WS")){
            if(Integer.parseInt(quantity)==0){
                throw new InvalidOperationException("The quantity of the product with id "+id+" can not be 0.");
            }
            return new WSItem(id,name,productPrice,quantity);
        }else{
            if(Double.parseDouble(quantity)==0){
                throw new InvalidOperationException("The quantity of the product with id "+id+" can not be 0.");
            }
            return new PSItem(id,name,productPrice,quantity);
        }
    }

    public int getId() {
        return id;
    }

    abstract double fullPrice();
     double moneySaved(){
         return fullPrice()*0.10;
     }
}
class WSItem extends Item{

    private final int quantity;

    public WSItem(String id, String name, String productPrice, String quantity) {
        super(id, name, productPrice);
        this.quantity = Integer.parseInt(quantity);
    }


    @Override
    double fullPrice() {
        return quantity*productPrice;
    }
}
class PSItem extends Item{

    private final double quantity;

    public PSItem(String id, String name, String productPrice, String quantity) {
        super(id, name, productPrice);
        this.quantity = Double.parseDouble(quantity);
    }

    @Override
    double fullPrice() {
        return (quantity/1000)*productPrice;
    }

}

class ShoppingCart{

    private final List<Item> items;

    public ShoppingCart() {
        this.items=new ArrayList<>();
    }

    void addItem(String itemData) throws InvalidOperationException {
        String [] parts=itemData.split(";");
        items.add(Item.createItem(parts[0],parts[1],parts[2],parts[3],parts[4]));
    }
    void printShoppingCart(OutputStream os){
        PrintWriter pw=new PrintWriter(os);
        items.stream().sorted(Comparator.comparingDouble(Item::fullPrice).reversed()).forEach(i->pw.println(String.format("%d - %.2f",i.getId(),i.fullPrice())));
        pw.close();
    }
    void blackFridayOffer(List<Integer> discountItems, OutputStream os) throws InvalidOperationException {
        PrintWriter pw=new PrintWriter(os);
        if(discountItems.isEmpty()){
            throw new InvalidOperationException("There are no products with discount.");
        }
        items.stream().filter(i->discountItems.contains(i.getId())).forEach(i-> pw.println(String.format("%d - %.2f",i.getId(),i.moneySaved())));
        pw.close();
    }
}


public class ShoppingTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();

        int items = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < items; i++) {
            try {
                cart.addItem(sc.nextLine());
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        List<Integer> discountItems = new ArrayList<>();
        int discountItemsCount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < discountItemsCount; i++) {
            discountItems.add(Integer.parseInt(sc.nextLine()));
        }

        int testCase = Integer.parseInt(sc.nextLine());
        if (testCase == 1) {
            cart.printShoppingCart(System.out);
        } else if (testCase == 2) {
            try {
                cart.blackFridayOffer(discountItems, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}