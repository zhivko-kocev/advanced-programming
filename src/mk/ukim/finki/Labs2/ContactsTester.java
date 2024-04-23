    package mk.ukim.finki.Labs2;

    import java.text.DecimalFormat;
    import java.util.Arrays;
    import java.util.Scanner;


    abstract class Contact{
        private final String date;

        public Contact(String date) {
            this.date = date;
        }
        public boolean isNewerThan(Contact c){
            String [] myDate=date.split("-");
            int myDay=Integer.parseInt(myDate[2]),myMonth=Integer.parseInt(myDate[1]),myYear=Integer.parseInt(myDate[0]);
            String [] otherDate=c.date.split("-");
            int otherDay=Integer.parseInt(otherDate[2]),otherMonth=Integer.parseInt(otherDate[1]),otherYear=Integer.parseInt(otherDate[0]);
            if(myYear<otherYear){
                return false;
            }else if(myYear>otherYear){
                return true;
            }else if(myMonth<otherMonth){
                return false;
            }else if(myMonth>otherMonth){
                return true;
            }else if(myDay<otherDay){
                return false;
            }else return myDay > otherDay;
        }
        abstract String getType();

    }
    class EmailContact extends Contact{
        private final String email;

        public EmailContact(String date, String email) {
            super(date);
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        @Override
        String getType() {
            return "Email";
        }

        @Override
        public String toString() {
            return "\""+email+"\"";
        }
    }
    class PhoneContact extends Contact{
        private final String phone;
        enum Operator{VIP, ONE, TMOBILE}
        public PhoneContact(String date, String phone) {
            super(date);
            this.phone = phone;
        }

        public String getPhone() {
            return phone;
        }

        @Override
        String getType() {
            return "Phone";
        }
        public Operator getOperator(){
            int numOperator=Character.getNumericValue(phone.charAt(2));
            if(numOperator==0 || numOperator==1 || numOperator==2){
                return Operator.TMOBILE;
            }else if(numOperator==5 || numOperator==6){
                return Operator.ONE;
            }else return Operator.VIP;
        }

        @Override
        public String toString() {
            return "\""+phone+"\"";
        }
    }
    class Student{
        private final String firstName;
        private final String lastName;
        private final String city;
        private final int age;
        private final long index;
        private Contact[]contacts;

        public Student(String firstName, String lastName, String city, int age, long index) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.city = city;
            this.age = age;
            this.index = index;
            contacts=new Contact[0];
        }
        public void addEmailContact(String date,String email){
            EmailContact newContact=new EmailContact(date,email);
            if(this.contacts==null){
                this.contacts=new Contact[1];
                this.contacts[0]=newContact;
            }else{
                Contact []tmp=Arrays.copyOf(contacts,contacts.length+1);
                tmp[tmp.length-1]=newContact;
                contacts=tmp;
            }
        }
        public void addPhoneContact(String date,String phone){
            PhoneContact newContact=new PhoneContact(date, phone);
            if(this.contacts==null){
                this.contacts=new Contact[1];
                this.contacts[0]=newContact;
            }else{
                Contact []tmp=Arrays.copyOf(contacts,contacts.length+1);
                tmp[tmp.length-1]=newContact;
                contacts=tmp;
            }
        }
        public Contact [] getEmailContacts(){
            return Arrays.stream(contacts).filter(i-> i.getType().equals("Email")).toArray(Contact[]::new);
        }
        public Contact[] getPhoneContacts(){
            return Arrays.stream(contacts).filter(i-> i.getType().equals("Phone")).toArray(Contact[]::new);
        }

        public String getCity() {
            return city;
        }

        public long getIndex() {
            return index;
        }
        public String getFullName(){
            return firstName+" "+lastName;
        }
        public Contact getLatestContact(){
            Contact newest=contacts[0];
            for (Contact c:contacts) {
                if(c.isNewerThan(newest)){
                    newest=c;
                }
            }
            return newest;
        }

        @Override
        public String toString() {
            return "{\"ime\":\"" + firstName + "\", \"prezime\":\"" + lastName +
                    "\", \"vozrast\":" + age + ", \"grad\":\"" + city +
                    "\", \"indeks\":" + index + ", \"telefonskiKontakti\":" +
                    Arrays.toString(getPhoneContacts()) +
                    ", \"emailKontakti\":" +
                    Arrays.toString(getEmailContacts()) +
                    "}";

        }
        public int getNumContacts(){
            return contacts.length;
        }
    }
    class Faculty{
        private final String name;
        private final Student [] students;

        public Faculty(String name, Student[] students) {
            this.name = name;
            this.students = students;
        }
        public int countStudentsFromCity(String cityName){
            int cnt=0;
            for (Student student:students) {
                if(cityName.equals(student.getCity())){
                    cnt++;
                }
            }
            return cnt;
        }
        public Student getStudent(long index){
            for (Student student:students) {
                if(student.getIndex()==index){
                    return student;
                }
            }
            return null;
        }
        public double getAverageNumberOfContacts(){
            double sum=0;
            for (Student student:students) {
                sum+=student.getNumContacts();
            }
            return sum/students.length;
        }
        public Student getStudentWithMostContacts(){
            Student mostContacts=students[0];
            for (Student student:students) {
                if(student.getNumContacts()>mostContacts.getNumContacts()){
                    mostContacts=student;
                }else if(student.getNumContacts()==mostContacts.getNumContacts()){
                    if(student.getIndex()>mostContacts.getIndex()){
                        mostContacts=student;
                    }
                }
            }
            return mostContacts;
        }

        @Override
        public String toString() {
            return  "{\"fakultet\":\"" + name + "\", \"studenti\":" +
                    Arrays.toString(students) +
                    "}";
        }
    }
    public class ContactsTester {

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            int tests = scanner.nextInt();
            Faculty faculty = null;

            int rvalue = 0;
            long rindex = -1;

            DecimalFormat df = new DecimalFormat("0.00");

            for (int t = 0; t < tests; t++) {

                rvalue++;
                String operation = scanner.next();

                switch (operation) {
                    case "CREATE_FACULTY": {
                        String name = scanner.nextLine().trim();
                        int N = scanner.nextInt();

                        Student[] students = new Student[N];

                        for (int i = 0; i < N; i++) {
                            rvalue++;

                            String firstName = scanner.next();
                            String lastName = scanner.next();
                            String city = scanner.next();
                            int age = scanner.nextInt();
                            long index = scanner.nextLong();

                            if ((rindex == -1) || (rvalue % 13 == 0))
                                rindex = index;

                            Student student = new Student(firstName, lastName, city,
                                    age, index);
                            students[i] = student;
                        }

                        faculty = new Faculty(name, students);
                        break;
                    }

                    case "ADD_EMAIL_CONTACT": {
                        long index = scanner.nextInt();
                        String date = scanner.next();
                        String email = scanner.next();

                        rvalue++;

                        if ((rindex == -1) || (rvalue % 3 == 0))
                            rindex = index;

                        faculty.getStudent(index).addEmailContact(date, email);
                        break;
                    }

                    case "ADD_PHONE_CONTACT": {
                        long index = scanner.nextInt();
                        String date = scanner.next();
                        String phone = scanner.next();

                        rvalue++;

                        if ((rindex == -1) || (rvalue % 3 == 0))
                            rindex = index;

                        faculty.getStudent(index).addPhoneContact(date, phone);
                        break;
                    }

                    case "CHECK_SIMPLE": {
                        System.out.println("Average number of contacts: "
                                + df.format(faculty.getAverageNumberOfContacts()));

                        rvalue++;

                        String city = faculty.getStudent(rindex).getCity();
                        System.out.println("Number of students from " + city + ": "
                                + faculty.countStudentsFromCity(city));

                        break;
                    }

                    case "CHECK_DATES": {

                        rvalue++;

                        System.out.print("Latest contact: ");
                        Contact latestContact = faculty.getStudent(rindex)
                                .getLatestContact();
                        if (latestContact.getType().equals("Email"))
                            System.out.println(((EmailContact) latestContact)
                                    .getEmail());
                        if (latestContact.getType().equals("Phone"))
                            System.out.println(((PhoneContact) latestContact)
                                    .getPhone()
                                    + " ("
                                    + ((PhoneContact) latestContact).getOperator()
                                    .toString() + ")");

                        if (faculty.getStudent(rindex).getEmailContacts().length > 0
                                && faculty.getStudent(rindex).getPhoneContacts().length > 0) {
                            System.out.print("Number of email and phone contacts: ");
                            System.out
                                    .println(faculty.getStudent(rindex)
                                            .getEmailContacts().length
                                            + " "
                                            + faculty.getStudent(rindex)
                                            .getPhoneContacts().length);

                            System.out.print("Comparing dates: ");
                            int posEmail = rvalue
                                    % faculty.getStudent(rindex).getEmailContacts().length;
                            int posPhone = rvalue
                                    % faculty.getStudent(rindex).getPhoneContacts().length;

                            System.out.println(faculty.getStudent(rindex)
                                    .getEmailContacts()[posEmail].isNewerThan(faculty
                                    .getStudent(rindex).getPhoneContacts()[posPhone]));
                        }

                        break;
                    }

                    case "PRINT_FACULTY_METHODS": {
                        System.out.println("Faculty: " + faculty.toString());
                        System.out.println("Student with most contacts: "
                                + faculty.getStudentWithMostContacts().toString());
                        break;
                    }

                }

            }

            scanner.close();
        }
    }
