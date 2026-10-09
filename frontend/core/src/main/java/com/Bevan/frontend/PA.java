class Person {
    String name;

    Person(String name) {
        this.name = name;
    }
}

public class PA {
    public static void main(String[] args) {
        Person b = new Person("Bevan");

        System.out.println("Hello, " + b.name + "!");
    }
}