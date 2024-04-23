package mk.ukim.finki.AdvancedTasks;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


class CategoryNotFoundException extends Exception{

    private final String name;
    public CategoryNotFoundException(String name) {
        this.name=name;
    }

    @Override
    public String getMessage() {
        return String.format("Category %s was not found",name);
    }
}
class Category{
    private final String name;

    public Category(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }
}
abstract class NewsItem{

    private final String title;
    private final Date date;
    private final Category category;

    public NewsItem(String title, Date date, Category category) {
        this.title = title;
        this.date = date;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public Category getCategory() {
        return category;
    }

    abstract public String getTeaser();
}

class TextNewsItem extends NewsItem{

    private final String text;

    public TextNewsItem(String title, Date date, Category category, String text) {
        super(title, date, category);
        this.text = text;
    }


    @Override
    public String getTeaser() {
        String sb = getTitle() + "\n" +
                ChronoUnit.MINUTES.between(getDate().toInstant(), Calendar.getInstance().toInstant()) + "\n" +
                Arrays.stream(text.split("")).limit(80).collect(Collectors.joining()) + "\n";
        return sb;
    }
}

class MediaNewsItem extends NewsItem{

    private final String url;
    private final int views;

    public MediaNewsItem(String title, Date date, Category category, String url, int views) {
        super(title, date, category);
        this.url = url;
        this.views = views;
    }

    @Override
    public String getTeaser() {
        String sb = getTitle() + "\n" +
                ChronoUnit.MINUTES.between(getDate().toInstant(), Calendar.getInstance().toInstant()) + "\n" +
                url + "\n" +
                views + "\n";
        return sb;
    }
}
class FrontPage{

    private final Category [] categories;
    private final List<NewsItem> items;

    public FrontPage(Category[] categories) {
        this.categories = categories;
        this.items=new ArrayList<>();
    }

    void addNewsItem(NewsItem newsItem){
        items.add(newsItem);
    }

    List<NewsItem> listByCategory(Category category){
        return items.stream().filter(i->i.getCategory().hashCode()==category.hashCode()).collect(Collectors.toList());
    }
    List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException {
        if(Arrays.stream(categories).noneMatch(c->c.getName().equals(category))){
            throw new CategoryNotFoundException(category);
        }
        return items.stream().filter(i->i.getCategory().getName().equals(category)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        items.forEach(i->sb.append(i.getTeaser()));
        return sb.toString();
    }
}
public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for(Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch(CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}


// Vasiot kod ovde