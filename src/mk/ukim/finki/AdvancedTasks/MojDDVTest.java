package mk.ukim.finki.AdvancedTasks;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class AmountNotAllowedException extends Exception {

    private final Receipt r;

    public AmountNotAllowedException(Receipt r) {
        this.r = r;
    }

    @Override
    public String getMessage() {
        return String.format("Receipt with amount %d is not allowed to be scanned", r.sumArticles());
    }
}

class Article {

    private final int price;
    private final char taxType;

    public Article(String price, String taxType) {
        this.price = Integer.parseInt(price);
        this.taxType = taxType.charAt(0);
    }

    public int getPrice() {
        return price;
    }

    public Double getTaxReturn() {
        return (taxType == 'A') ? price * 0.18 : (taxType == 'B') ? price * 0.05 : 0;
    }
}

class Receipt {

    private final Integer id;
    private final List<Article> articles;

    public Receipt(String id, List<Article> articles) {
        this.id = Integer.parseInt(id);
        this.articles = articles;
    }

    public static Receipt createReceipt(String id, List<Article> articles) throws AmountNotAllowedException {
        if (articles.stream().mapToInt(Article::getPrice).sum() > 30000) {
            throw new AmountNotAllowedException(new Receipt(id, articles));
        }

        return new Receipt(id, articles);
    }

    public Integer getId() {
        return id;
    }

    public int sumArticles() {

        return articles.stream().mapToInt(Article::getPrice).sum();
    }

    public Double sumTaxReturn() {
        return articles.stream().mapToDouble(Article::getTaxReturn).sum()*0.15;
    }
}

class MojDDV {

    private final List<Receipt> receipts;

    public MojDDV() {
        this.receipts = new ArrayList<>();
    }

    void readRecords(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] values = line.split("\\s+");
            String id = values[0];
            List<Article> tmp = new ArrayList<>();
            for (int i = 1; i < values.length - 1; i += 2) {
                tmp.add(new Article(values[i], values[i + 1]));
            }

            try {
                receipts.add(Receipt.createReceipt(id, tmp));
            } catch (AmountNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        }
        sc.close();
    }

    void printTaxReturns(OutputStream outputStream) {
        PrintStream out = new PrintStream(outputStream);
        receipts.forEach(r -> out.printf("%10d\t%10d\t%10.5f%n", r.getId(), r.sumArticles(), r.sumTaxReturn()));

    }
    void printStatistics (OutputStream outputStream){

        PrintStream out=new PrintStream(outputStream);
        Double min=receipts.stream().mapToDouble(Receipt::sumTaxReturn).min().orElse(0);
        Double max=receipts.stream().mapToDouble(Receipt::sumTaxReturn).max().orElse(0);
        Double sum=receipts.stream().mapToDouble(Receipt::sumTaxReturn).sum();
        Integer count= receipts.size();
        Double average=receipts.stream().mapToDouble(Receipt::sumTaxReturn).average().orElse(0);
        out.printf("min:\t%.3f%n",min);
        out.printf("max:\t%.3f%n",max);
        out.printf("sum:\t%.3f%n",sum);
        out.printf("count:\t%d%n",count);
        out.printf("avg:\t%.3f%n",average);
        out.close();
    }

}

public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojDDV.printStatistics(System.out);

    }
}
