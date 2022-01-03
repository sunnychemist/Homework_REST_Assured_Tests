package pojo;

public class Person {
    private String name;
    private String job;

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public Person(String name, String job) {
        this.name = name;
        this.job = job;
    }
}
