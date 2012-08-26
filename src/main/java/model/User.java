package model;

public class User {

    private int id;
    private String username;
    private int age;

    public User() {
    }

    public User(String u) {
        username = u;
    }

    public User(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("User");
        sb.append("{id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}
