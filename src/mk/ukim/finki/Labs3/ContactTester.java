package mk.ukim.finki.Labs3;

import java.util.*;

class InvalidNameException extends Exception {

    public String name;

    public InvalidNameException(String name) {
        this.name = name;
    }
}

class InvalidNumberException extends Exception {
    public InvalidNumberException() {
    }
}

class MaximumSizeExceddedException extends Exception {
    public MaximumSizeExceddedException() {
    }
}

class Contact {
    private final String name;
    private String[] numbers;
    private int momLength;

    public Contact(String name, String... numbers) throws InvalidNumberException, InvalidNameException, MaximumSizeExceddedException {
        if (!validName(name)) {
            throw new InvalidNameException(name);
        }
        if (!validNumbers(numbers)) {
            throw new InvalidNumberException();
        }
        if (numbers.length > 5) {
            throw new MaximumSizeExceddedException();
        }
        this.name = name;
        this.numbers = numbers;
        this.momLength = numbers.length;

    }

    public Contact(String name) throws InvalidNameException {
        if (!validName(name)) {
            throw new InvalidNameException(name);
        }
        this.name = name;
        this.momLength = 0;
        this.numbers = new String[0];
    }

    private boolean validNumber(String number) {
        if (number.length() != 9) {
            return false;
        }
        if (!(number.startsWith("071") || number.startsWith("072") || number.startsWith("075") || number.startsWith("076")
                || number.startsWith("077") || number.startsWith("078") || number.startsWith("070"))) {
            return false;
        }
        return number.chars().allMatch(Character::isDigit);
    }

    private boolean validNumbers(String[] numbers) {

        return Arrays.stream(numbers).allMatch(this::validNumber);
    }

    protected boolean validName(String name) {
        if (name.length() > 10 || name.length() < 4) {
            return false;
        }
        return name.chars().allMatch(c -> Character.isDigit(c) || Character.isAlphabetic(c));
    }

    public String getName() {
        return name;
    }

    public String[] getNumbers() {
        return Arrays.stream(numbers).sorted().toArray(String[]::new);
    }

    public void addNumber(String number) throws MaximumSizeExceddedException, InvalidNumberException {

        if (momLength == 5) {
            throw new MaximumSizeExceddedException();
        }
        if (!validNumber(number)) {
            throw new InvalidNumberException();
        }
        String[] newArray = Arrays.copyOf(numbers, momLength + 1);
        newArray[momLength++] = number;
        numbers = newArray;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s\n%d\n", name, momLength));
        for (String number : getNumbers()) {
            sb.append(number).append("\n");
        }
        return sb.toString();
    }

    public boolean hasNumber(String prefix) {
        return Arrays.stream(numbers).anyMatch(s -> s.contains(prefix));
    }

}

class PhoneBook {
    private final ArrayList<Contact> contacts;
    private final static int MAX_CONTACTS = 250;

    public PhoneBook() {
        this.contacts = new ArrayList<>();
    }

    private boolean hasSameName(String name) {
        return contacts.stream().anyMatch(c -> c.getName().equals(name));
    }

    public void addContact(Contact contact) throws MaximumSizeExceddedException, InvalidNameException {
        if (contacts.size() == MAX_CONTACTS) {
            throw new MaximumSizeExceddedException();
        }
        if (hasSameName(contact.getName())) {
            throw new InvalidNameException(contact.getName());
        }
        contacts.add(contact);
    }

    public Contact getContactForName(String name) {
        return contacts.stream().filter(c -> c.getName().equals(name)).findAny().orElse(null);

    }

    public int numberOfContacts() {
        return contacts.size();
    }

    public Contact[] getContacts() {
        return contacts.stream().sorted(Comparator.comparing(Contact::getName)).toArray(Contact[]::new);
    }

    public boolean removeContact(String name) {
        Optional<Contact> contactToDelete = contacts.stream().filter(c -> c.getName().equals(name)).findAny();
        if (contactToDelete.isPresent()) {
            contacts.remove(contactToDelete.get());
            return true;
        } else {
            return false;
        }
    }

    public Contact[] getContactsForNumber(String prefix) {
        return contacts.stream().filter(c -> c.hasNumber(prefix)).toArray(Contact[]::new);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Contact c : getContacts()) {
            sb.append(c).append("\n");
        }
        return sb.toString();
    }
}

public class ContactTester {

    public static void main(String[] args) throws Exception {
        Scanner jin = new Scanner(System.in);
        String line = jin.nextLine();
        switch (line) {
            case "test_contact":
                testContact(jin);
                break;
            case "test_phonebook_exceptions":
                testPhonebookExceptions(jin);
                break;
            case "test_usage":
                testUsage(jin);
                break;
        }
    }

//    private static void testFile(Scanner jin) throws Exception {
//        PhoneBook phonebook = new PhoneBook();
//        while (jin.hasNextLine())
//            phonebook.addContact(new Contact(jin.nextLine(), jin.nextLine().split("\\s++")));
//        String text_file = "phonebook.txt";
//        PhoneBook.saveAsTextFile(phonebook, text_file);
//        PhoneBook pb = PhoneBook.loadFromTextFile(text_file);
//        if (!pb.equals(phonebook)) System.out.println("Your file saving and loading doesn't seem to work right");
//        else System.out.println("Your file saving and loading works great. Good job!");
//    }

    private static void testUsage(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while (jin.hasNextLine()) {
            String command = jin.nextLine();
            switch (command) {
                case "add":
                    phonebook.addContact(new Contact(jin.nextLine(), jin.nextLine().split("\\s++")));
                    break;
                case "remove":
                    phonebook.removeContact(jin.nextLine());
                    break;
                case "print":
                    System.out.println(phonebook.numberOfContacts());
                    System.out.println(Arrays.toString(phonebook.getContacts()));
                    System.out.println(phonebook);
                    break;
                case "get_name":
                    System.out.println(phonebook.getContactForName(jin.nextLine()));
                    break;
                case "get_number":
                    System.out.println(Arrays.toString(phonebook.getContactsForNumber(jin.nextLine())));
                    break;
            }
        }
    }

    private static void testPhonebookExceptions(Scanner jin) {
        PhoneBook phonebook = new PhoneBook();
        boolean exception_thrown = false;
        try {
            while (jin.hasNextLine()) {
                phonebook.addContact(new Contact(jin.nextLine()));
            }
        } catch (InvalidNameException e) {
            System.out.println(e.name);
            exception_thrown = true;
        } catch (Exception e) {
        }
        if (!exception_thrown) System.out.println("Your addContact method doesn't throw InvalidNameException");
    }

    private static void testContact(Scanner jin) throws Exception {
        boolean exception_thrown = true;
        String[] names_to_test = {"And\nrej", "asd", "AAAAAAAAAAAAAAAAAAAAAA", "Ð�Ð½Ð´Ñ€ÐµÑ˜A123213", "Andrej#", "Andrej<3"};
        for (String name : names_to_test) {
            try {
                new Contact(name);
                exception_thrown = false;
            } catch (InvalidNameException e) {
                exception_thrown = true;
            }
            if (!exception_thrown) System.out.println("Your Contact constructor doesn't throw an InvalidNameException");
        }
        String[] numbers_to_test = {"+071718028", "number", "078asdasdasd", "070asdqwe", "070a56798", "07045678a", "123456789", "074456798", "073456798", "079456798"};
        for (String number : numbers_to_test) {
            try {
                new Contact("Andrej", number);
                exception_thrown = false;
            } catch (InvalidNumberException e) {
                exception_thrown = true;
            }
            if (!exception_thrown)
                System.out.println("Your Contact constructor doesn't throw an InvalidNumberException");
        }
        String[] nums = new String[10];
        for (int i = 0; i < nums.length; ++i) nums[i] = getRandomLegitNumber();
        try {
            new Contact("Andrej", nums);
            exception_thrown = false;
        } catch (MaximumSizeExceddedException e) {
            exception_thrown = true;
        }
        if (!exception_thrown)
            System.out.println("Your Contact constructor doesn't throw a MaximumSizeExceddedException");
        Random rnd = new Random(5);
        Contact contact = new Contact("Andrej", getRandomLegitNumber(rnd), getRandomLegitNumber(rnd), getRandomLegitNumber(rnd));
        System.out.println(contact.getName());
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact);
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact);
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact);
    }

    static String[] legit_prefixes = {"070", "071", "072", "075", "076", "077", "078"};
    static Random rnd = new Random();

    private static String getRandomLegitNumber() {
        return getRandomLegitNumber(rnd);
    }

    private static String getRandomLegitNumber(Random rnd) {
        StringBuilder sb = new StringBuilder(legit_prefixes[rnd.nextInt(legit_prefixes.length)]);
        for (int i = 3; i < 9; ++i)
            sb.append(rnd.nextInt(10));
        return sb.toString();
    }


}
