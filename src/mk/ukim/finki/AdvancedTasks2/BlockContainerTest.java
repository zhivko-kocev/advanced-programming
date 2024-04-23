//package mk.ukim.finki.AdvancedTasks2;
//
//import java.util.*;
//
//class Block<T extends Comparable<T>>{
//    private Set<T> elements;
//
//    public Block() {
//        this.elements=new TreeSet<>(Comparator.naturalOrder());
//    }
//
//    public Set<T> getElements() {
//        return elements;
//    }
//    public void add(T element){
//        elements.add(element);
//    }
//    public void remove(T element){
//        elements.remove(element);
//    }
//}
//class BlockContainer<T extends Comparable<T>>{
//    private int size;
//    private List<Block<T>> blocks;
//
//    public BlockContainer(int size) {
//        this.size = size;
//        this.blocks=new ArrayList<>();
//    }
//    public void add(T a){
//        if(blocks.getLast().getElements().size()!=size){
//            blocks.getLast().add(a);
//        }else{
//            blocks.add(new Block<>());
//            blocks.getLast().add(a);
//        }
//    }
//    public void remove(T a){
//        blocks.getLast().remove(a);
//        if(blocks.getLast().getElements().isEmpty()){
//            blocks.removeLast();
//        }
//    }
//}
//
//public class BlockContainerTest {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        int size = scanner.nextInt();
//        BlockContainer<Integer> integerBC = new BlockContainer<Integer>(size);
//        scanner.nextLine();
//        Integer lastInteger = null;
//        for(int i = 0; i < n; ++i) {
//            int element = scanner.nextInt();
//            lastInteger = element;
//            integerBC.add(element);
//        }
//        System.out.println("+++++ Integer Block Container +++++");
//        System.out.println(integerBC);
//        System.out.println("+++++ Removing element +++++");
//        integerBC.remove(lastInteger);
//        System.out.println("+++++ Sorting container +++++");
//        integerBC.sort();
//        System.out.println(integerBC);
//        BlockContainer<String> stringBC = new BlockContainer<String>(size);
//        String lastString = null;
//        for(int i = 0; i < n; ++i) {
//            String element = scanner.next();
//            lastString = element;
//            stringBC.add(element);
//        }
//        System.out.println("+++++ String Block Container +++++");
//        System.out.println(stringBC);
//        System.out.println("+++++ Removing element +++++");
//        stringBC.remove(lastString);
//        System.out.println("+++++ Sorting container +++++");
//        stringBC.sort();
//        System.out.println(stringBC);
//    }
//}
//
//
//
//
//
